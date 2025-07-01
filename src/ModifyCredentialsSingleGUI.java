import javax.swing.*;
import java.awt.*;

public class ModifyCredentialsSingleGUI {
    private static JFrame frame;
    private static String currentPassword;
    private static JLabel errorLabel;
    private static JTextField newPasswordField;
    private static JTextField newUsernameField;
    private static String usernameToModify;
    private static String locationToModify;

    /**
     * This method initializes the ModifyCredentialsSingleGUI.
     * It sets up the frame, panel, labels, text fields, and buttons for modifying credentials.
     * It retrieves the current password for the username to modify from the VaultManager.
     * The user can change the username/email and password and save the changes.
     * If the new password or username is the same as the current one, or if they contain spaces,
     * an error message is displayed.
     */
    public static void Initialize(String locationToModify, String usernameToModify, JLabel successOrError) {
        ModifyCredentialsSingleGUI.usernameToModify = usernameToModify;
        ModifyCredentialsSingleGUI.locationToModify = locationToModify;
        ModifyCredentialsSingleGUI.errorLabel = successOrError;
        currentPassword = VaultManager.findPassword(locationToModify, usernameToModify); // Get the current password for the username to modify

        frame = GUIUtils.createAndConfigureFrame("Modify Credentials",700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the frame

        int labelWidth = 150;
        int labelHeight = 50;
        int errorLabelWidth = 400;
        int errorLabelHeight = 30;
        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 130;
        int buttonHeight = 50;

        newUsernameField = new JTextField(20);
        newPasswordField = new JTextField(20);

        JTextField currentUsernameField = new JTextField(usernameToModify, 20);
        JTextField currentPasswordField = new JTextField(currentPassword, 20);

        JLabel usernameLabel = new JLabel("  Current Username/Email:");
        JLabel newUsernameLabel = new JLabel("  New Username/Email:");
        JLabel passwordLabel = new JLabel("  Current Password:");
        JLabel newPasswordLabel = new JLabel("  New Password:");

        currentUsernameField.setEditable(false);
        currentPasswordField.setEditable(false);
        errorLabel.setForeground(Color.RED);


        JButton cancelButton = new JButton("Cancel"); // Button to go back to the previous screen
        cancelButton.addActionListener(e -> {
            successOrError.setText("");
            frame.dispose();
            ModifyCredentialsGUI.Initialize("Select credentials to modify:", Color.WHITE);
        });


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveButton());

        errorLabel.setPreferredSize(new java.awt.Dimension(errorLabelWidth, errorLabelHeight));
        newUsernameField.setPreferredSize(new java.awt.Dimension(fieldWidth, fieldHeight));
        newPasswordField.setPreferredSize(new java.awt.Dimension(fieldWidth, fieldHeight));
        currentUsernameField.setPreferredSize(new java.awt.Dimension(fieldWidth, fieldHeight));
        currentPasswordField.setPreferredSize(new java.awt.Dimension(fieldWidth, fieldHeight));
        usernameLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        newUsernameLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        passwordLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        newPasswordLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        cancelButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        saveButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(errorLabel, errorLabelWidth, errorLabelWidth, errorLabelWidth)
                                        .addComponent(usernameLabel)
                                        .addComponent(currentUsernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(newUsernameLabel)
                                                        .addComponent(newUsernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                )
                                                .addGap(150)
                                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(passwordLabel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(currentPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                )
                                                .addGap(150)
                                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(newPasswordLabel)
                                        .addComponent(newPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                )
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(errorLabel, errorLabelHeight, errorLabelHeight, errorLabelHeight)
                        .addGap(20)
                        .addComponent(usernameLabel)
                        .addComponent(currentUsernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(newUsernameLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(newUsernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addComponent(passwordLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(currentPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(10)
                        .addComponent(newPasswordLabel)
                        .addComponent(newPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
        );

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
        frame.dispose();
        ModifyCredentialsGUI.Initialize("Modified Credentials Successfully", Color.GREEN);
    }
}
