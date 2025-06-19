import javax.swing.*;

public class DeleteCredentialsGUI {

    private static JLabel success;
    private static JLabel successOrError;
    private static JComboBox<String> credentialList = new JComboBox<>();
    private static JComboBox<String> userEmailList = new JComboBox<>();
    private static String location;
    private static JFrame frame;

    /**
     * Initializes the Delete Credentials GUI.
     * Sets up the frame, panel, and components for deleting credentials.
     */
    public static void Initialize() {

        frame = GUIUtils.createAndConfigureFrame("Delete Credentials", 700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int centerX = (700 - fieldWidth) / 2;

        success = GUIUtils.createMessageLabel(panel, centerX + 20, 70, 300, 60);

        credentialList.setBounds(centerX - 50, 140, fieldWidth, fieldHeight);
        GUIUtils.refreshLocations(credentialList);
        panel.add(credentialList);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(centerX + 300, 140, buttonWidth, buttonHeight);
        deleteButton.addActionListener(e -> deleteButton());
        panel.add(deleteButton);

        GUIUtils.addMenuButton(frame, panel, 10, 10, buttonWidth, buttonHeight);

        frame.setVisible(true);
    }

    /**
     * Handles the UI of deleting duplicate credentials for a single location.
     * It displays a message indicating that there are multiple entries for the selected location,
     * allows the user to select a specific username/email from a dropdown,
     * and provides options to delete the selected credentials or go back to the delete credentials GUI.
     */
    private static void duplicateLocation(String location) {
        DeleteCredentialsGUI.location = location;

        frame = GUIUtils.createAndConfigureFrame("Duplicate Location", 400, 300);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int fieldWidth = 200;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 40;
        int centerX = (frame.getWidth() - buttonWidth) / 2;

        JLabel messageLabel = new JLabel("There is more than one entry for " + location);
        messageLabel.setBounds(0, 0, 360, 50);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel);

        successOrError = GUIUtils.createMessageLabel(panel, centerX - 85, 20, 300, 60);

        userEmailList = new JComboBox<>();
        userEmailList.setBounds(centerX - 110, 70, fieldWidth, fieldHeight);
        GUIUtils.refreshUsernames(userEmailList, location);
        panel.add(userEmailList);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(centerX + 100, 70, buttonWidth, buttonHeight);
        deleteButton.addActionListener(e -> deleteButtonDup());
        panel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(centerX + 100, 140, buttonWidth, buttonHeight);
        backButton.addActionListener(e -> {
            frame.dispose();
            Initialize();
        });
        panel.add(backButton);

        frame.setVisible(true);
    }

    /**
     * Handles the action of deleting duplicate credentials for a single location.
     * It deletes the selected credentials by username/email and returns to the delete credentials GUI.
     */
    private static void deleteButtonDup() {
        String selected = (String) userEmailList.getSelectedItem();
        if (selected == null) {
            successOrError.setText("No credentials available");
            return;
        } else if(selected.equals("Select Username/Email...")) {
            successOrError.setText("Please select a valid location");
            return;
        }
        if (areYouSure()) {
            VaultManager.deleteLogin(location, selected);
            frame.dispose();
            Initialize();
            success.setText("Deleted Credentials Successfully");
        }
    }

    /**
     * Handles the action of deleting credentials.
     * If there are duplicates, it calls the duplicateLocation method.
     * Otherwise, it deletes the selected credentials and refreshes the list.
     */
    private static void deleteButton() {
        String selected = (String) credentialList.getSelectedItem();
        String username = VaultManager.findUsername(selected);
        assert selected != null;
        if(selected.equals("Select Location...")) {
            success.setText("Please select a valid location");
            return;
        }

        if (GUIUtils.hasDuplicates(selected)) {
            frame.dispose();
            duplicateLocation(selected);
        } else if(areYouSure()) {
            VaultManager.deleteLogin(selected, username);
            success.setText("Deleted Credentials Successfully");
            GUIUtils.refreshLocations(credentialList);
        }
    }

    private static boolean areYouSure() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete these credentials?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }
}