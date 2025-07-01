import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginGUI {
    private static JFrame frame;
    private static JLabel errorLabel;
    private static JPasswordField passwordField;

    /**
     * Initializes the login GUI for the password manager.
     * Prompts the user to enter their master password to unlock the vault.
     */
    public static void login() {
        frame = GUIUtils.createAndConfigureFrame("Login", 500, 350);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int componentWidth = 150;
        int passwordFieldWidth = 300;
        int errorLabelWidth = 400;
        int titleHeight = 30;
        int passwordFieldHeight = 50;
        int loginButtonHeight = 50;
        int errorLabelHeight = 40;

        JLabel titleLabel = new JLabel("Enter Master Password", SwingConstants.CENTER);
        errorLabel = new JLabel("", SwingConstants.CENTER);
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> loginButton());

        titleLabel.setPreferredSize(new java.awt.Dimension(componentWidth, titleHeight));
        passwordField.setPreferredSize(new java.awt.Dimension(passwordFieldWidth, passwordFieldHeight));
        loginButton.setPreferredSize(new java.awt.Dimension(componentWidth, loginButtonHeight));
        errorLabel.setPreferredSize(new java.awt.Dimension(errorLabelWidth, errorLabelHeight));

        errorLabel.setForeground(Color.RED);

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE) // Flexible space on the left
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(titleLabel, componentWidth, componentWidth, componentWidth) // min, pref, max
                                        .addComponent(passwordField, passwordFieldWidth, passwordFieldWidth, passwordFieldWidth)
                                        .addComponent(loginButton, componentWidth, componentWidth, componentWidth)
                                        .addComponent(errorLabel, errorLabelWidth, errorLabelWidth, errorLabelWidth)
                                )
                                .addGap(0, 0, Short.MAX_VALUE) // Flexible space on the right
                        )
        );


        layout.setVerticalGroup(
            layout.createSequentialGroup()
                    .addGap(5)
                    .addComponent(errorLabel, errorLabelHeight, errorLabelHeight, errorLabelHeight)
                    .addComponent(titleLabel, titleHeight, titleHeight, titleHeight)
                    .addGap(15)
                    .addComponent(passwordField, passwordFieldHeight, passwordFieldHeight, passwordFieldHeight)
                    .addGap(15)
                    .addComponent(loginButton, loginButtonHeight, loginButtonHeight, loginButtonHeight)
                    .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
    }


    /**
     * Handles the login button action.
     * Validates the entered password and unlocks the vault if correct.
     */
    private static void loginButton() {
        char[] password = passwordField.getPassword();

        if (password.length == 0) {
            errorLabel.setText("Password cannot be empty");
            return;
        }
        boolean isPasswordCorrect = VaultManager.unlockVault(password);

        if (isPasswordCorrect) {
            System.out.println("Password correct");
            frame.dispose();
            MainMenuGUI.Initialize();
        } else {
            errorLabel.setText("Incorrect password. Please try again.");
        }
        // Clear the password field after checking
        Arrays.fill(password, ' ');
        passwordField.setText("");
    }
}

