import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GUIUtils {

    /**
     * Creates and configures a JFrame with the specified title, width, and height.
     * Sets the default close operation to EXIT_ON_CLOSE, centers the frame on the screen,
     * and adds a JPanel with null layout to the frame.
     */
    public static JFrame createAndConfigureFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        JPanel panel = new JPanel();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        panel.setFocusable(true);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        frame.add(panel);
        return frame;
    }

    /**
     * Refreshes the JComboBox with locations read from the VaultManager.
     * Adds a default item "Select Location..." at the beginning.
     */
    public static void refreshLocations(JComboBox<String> credentialList) {
        List<String> locations = VaultManager.readLocationsFromFile();
        credentialList.removeAllItems();

        Set<String> uniqueLocations = new HashSet<>(locations);
        if (locations != null) {
            uniqueLocations.addAll(locations); // Ensure all locations are unique
        }

        credentialList.addItem("Select Location...");
        for (String location : uniqueLocations) { // Add unique locations to the JComboBox
            credentialList.addItem(location);
        }
    }

    /**
     * Refreshes the JComboBox with usernames associated with the selected location.
     * Adds a default item "Select Username/Email..." at the beginning.
     */
    public static void refreshUsernames(JComboBox<String> userEmailList, String selectedLocation) {
        List<String> userEmails = VaultManager.getUsernames(selectedLocation);
        userEmailList.removeAllItems();
        userEmails.add(0, "Select Username/Email...");

        if (userEmails != null) {
            for (String userEmail : userEmails) {
                userEmailList.addItem(userEmail);
            }
        }
    }

    /**
     * Checks if there are duplicate entries for a given location in the list of locations.
     */
    public static boolean hasDuplicates(String location) {
        List<String> locations = VaultManager.readLocationsFromFile();
        if (locations == null || locations.isEmpty()) {
            return false;
        }
        int count = 0;
        for (String loc : locations) {
            if (loc.equals(location)) {
                count++;
            }
            if (count > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a "Back to Menu" button to the specified panel at the given coordinates.
     * When clicked, it disposes of the current frame and initializes the MainMenuGUI.
     */
    public static JButton addMenuButton(JFrame frame) {
        JButton menuButton = new JButton("Back to Menu");
        menuButton.putClientProperty("JButton.buttonType", "square");
        menuButton.addActionListener(e -> {
            frame.dispose();
            MainMenuGUI.Initialize();
        });
        return menuButton;
    }
}
