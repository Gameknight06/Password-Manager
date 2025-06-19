import javax.swing.*;

public class ModifyCredentialsGUI {

    private static JLabel successOrError;
    private static JComboBox<String> credentialList;
    private static JComboBox<String> userEmailList;
    private static JFrame frame;

    /**
     * Initializes the Modify Credentials GUI.
     * Sets up the frame, panel, and components for modifying credentials.
     */
    public static void Initialize() {

        frame = GUIUtils.createAndConfigureFrame("Modify Credentials",700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int centerX = (frame.getWidth() - fieldWidth) / 2;

        successOrError = GUIUtils.createMessageLabel(panel, centerX + 20, 70, 300, 60); // Label for success and error messages

        credentialList = new JComboBox<>();
        credentialList.setBounds(centerX - 50, 140, fieldWidth, fieldHeight);
        GUIUtils.refreshLocations(credentialList); // Refresh the list of locations
        panel.add(credentialList);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(centerX + 300, 140, buttonWidth, buttonHeight);
        editButton.addActionListener(e -> editButton()); // Call the method to handle editing
        panel.add(editButton);

        GUIUtils.addMenuButton(frame, panel, 10, 10, buttonWidth, buttonHeight);

        frame.setVisible(true);
    }

    /**
     * Handles the UI of modifying duplicate credentials for a single location.
     * It displays a message indicating that there are multiple entries for the selected location,
     * allows the user to select a specific username/email from a dropdown,
     * and provides options to edit the selected credentials or go back to the modify credentials GUI.
     */
    private static void duplicateLocation(String location) {
        JFrame frame = GUIUtils.createAndConfigureFrame("Duplicate Location",400, 300);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the frame

        int fieldWidth = 200;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 40;
        int centerX = (frame.getWidth() - buttonWidth) / 2;

        JLabel messageLabel = new JLabel("There is more than one entry for " + location);
        messageLabel.setBounds(0, 0, 360, 50);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel);

        successOrError = GUIUtils.createMessageLabel(panel, centerX - 80, 20, 300, 60); // Label for success and error messages

        userEmailList = new JComboBox<>();
        userEmailList.setBounds(centerX - 110, 70, fieldWidth, fieldHeight);
        GUIUtils.refreshUsernames(userEmailList, location); // Refresh usernames based on the selected location
        panel.add(userEmailList);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(centerX + 100, 70, buttonWidth, buttonHeight);
        editButton.addActionListener(e -> {
            String selected = (String) userEmailList.getSelectedItem();
            if (selected == null) {
                successOrError.setText("No credentials available");
                return;
            } else if(selected.equals("Select Username/Email...")) { // Check for default selection
                successOrError.setText("Please select a valid location");
                return;
            }
            frame.dispose();
            ModifyCredentialsSingleGUI.Initialize(location, selected, successOrError);
        });
        panel.add(editButton);

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
     * Handles the action of editing credentials for a single location.
     * It checks for duplicates and either opens the single modification GUI or opens the duplicate location GUI.
     */
    private static void editButton() {
        String selected = (String) credentialList.getSelectedItem();
        String username = VaultManager.findUsername(selected);
        assert selected != null;
        if(selected.equals("Select Location...")) { // Check for default selection
            successOrError.setText("Please select a valid location");
            return;
        }

        if (GUIUtils.hasDuplicates(selected)) {
            frame.dispose();
            duplicateLocation(selected);
        } else {
            frame.dispose();
            ModifyCredentialsSingleGUI.Initialize(selected, username, successOrError); // Pass the successOrError label to the single modification GUI
        }
    }
}