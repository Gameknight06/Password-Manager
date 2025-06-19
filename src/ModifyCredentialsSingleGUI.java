import javax.swing.*;

public class ModifyCredentialsSingleGUI {
    private static JFrame frame;
    private static String currentPassword;
    private static JLabel errorLabel;
    private static JTextField newPasswordField;
    private static JTextField newUsernameField;
    private static String usernameToModify;
    private static String locationToModify;
    private static JLabel successOrError;

    /**
     * This method initializes the ModifyCredentialsSingleGUI.
     * It sets up the frame, panel, labels, text fields, and buttons for modifying credentials.
     * It retrieves the current password for the username to modify from the VaultManager.
     * The user can change the username/email and password, and save the changes.
     * If the new password or username is the same as the current one, or if they contain spaces,
     * an error message is displayed.
     */
    public static void Initialize(String locationToModify, String usernameToModify, JLabel success) {
        ModifyCredentialsSingleGUI.usernameToModify = usernameToModify;
        ModifyCredentialsSingleGUI.locationToModify = locationToModify;
        ModifyCredentialsSingleGUI.successOrError = success;
        currentPassword = VaultManager.findPassword(locationToModify, usernameToModify); // Get the current password for the username to modify

        frame = GUIUtils.createAndConfigureFrame("Modify Credentials",700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the frame

        int labelWidth = 200;
        int labelHeight = 50;
        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 130;
        int buttonHeight = 50;
        int centerX = (frame.getWidth() - fieldWidth) / 2;

        errorLabel = new JLabel(); // Label for error messages
        errorLabel.setBounds(centerX + 190, 80, 1200, 60);
        panel.add(errorLabel);

        JLabel usernameLabel = new JLabel("Current Username/Email:");
        usernameLabel.setBounds(centerX - 180, 30, labelWidth, labelHeight);
        panel.add(usernameLabel);

        JTextField currentUsernameField = new JTextField(usernameToModify, 20);
        currentUsernameField.setBounds(centerX - 185, 70, fieldWidth, fieldHeight);
        currentUsernameField.setEditable(false);
        panel.add(currentUsernameField);

        JLabel newUsernameLabel = new JLabel("New Username/Email:");
        newUsernameLabel.setBounds(centerX - 180, 110, labelWidth, labelHeight);
        panel.add(newUsernameLabel);

        newUsernameField = new JTextField(20);
        newUsernameField.setBounds(centerX - 185, 150, fieldWidth, fieldHeight);
        panel.add(newUsernameField);

        JLabel passwordLabel = new JLabel("Current Password:");
        passwordLabel.setBounds(centerX - 180, 210, labelWidth, labelHeight);
        panel.add(passwordLabel);

        JTextField currentPasswordField = new JTextField(currentPassword, 20);
        currentPasswordField.setBounds(centerX - 185, 250, fieldWidth, fieldHeight);
        currentPasswordField.setEditable(false);
        panel.add(currentPasswordField);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(centerX - 180, 290, labelWidth, labelHeight);
        panel.add(newPasswordLabel);

        newPasswordField = new JTextField(20);
        newPasswordField.setBounds(centerX - 185, 330, fieldWidth, fieldHeight);
        panel.add(newPasswordField);

        JButton backButton = new JButton("Cancel"); // Button to go back to the previous screen
        backButton.setBounds(centerX + 250, 250, buttonWidth, buttonHeight);
        backButton.addActionListener(e -> {
            success.setText("");
            frame.dispose();
            ModifyCredentialsGUI.Initialize();
        });
        panel.add(backButton);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(centerX + 250, 150, buttonWidth, buttonHeight);
        saveButton.addActionListener(e -> saveButton());
        panel.add(saveButton);


        frame.setVisible(true);
    }

    /**
     * This method is called when the "Save" button is clicked.
     * It retrieves the new password and username from the text fields,
     * validates them, and then calls the VaultManager to modify the credentials.
     * If the new password or username is the same as the current one,
     * or if they contain spaces or are empty, an error message is displayed.
     */
    private static void saveButton() {
        String newPassword = newPasswordField.getText();
        String newUsername = newUsernameField.getText();

        if (newPassword.equals(currentPassword) || newUsername.equals(usernameToModify)) {
            errorLabel.setText("Password or Username/Email cannot be the same as the current one!");
            return;
        } else if (newPassword.contains(" ") || newUsername.contains(" ")) {
            errorLabel.setText("Password or Username/Email cannot contain spaces!");
            return;
        }

        if (newPassword.isEmpty() && newUsername.isEmpty()) {
            errorLabel.setText("Password or Username/Email cannot be empty!");
            return;
        } else if (newPassword.isEmpty()) {
            newPassword = currentPassword; // Keep the old password if new is empty
        } else if (newUsername.isEmpty()) {
            newUsername = usernameToModify; // Keep the old username if new is empty
        }
        VaultManager.modifyLogin(locationToModify, newPassword, usernameToModify, newUsername);
        successOrError.setText("Modified Credentials Successfully");
        frame.dispose();
        ModifyCredentialsGUI.Initialize();
    }
}
