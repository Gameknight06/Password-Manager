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
        frame = new JFrame("Password Manager Login");
        JPanel panel = new JPanel();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);
        panel.setFocusable(true);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        frame.add(panel);

        int fieldWidth = 300;
        int fieldHeight = 50;
        int centerX = (frame.getWidth() - fieldWidth) / 2;

        JLabel titleLabel = new JLabel("Enter Master Password");
        titleLabel.setBounds(centerX, 30, fieldWidth, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(centerX, 150, fieldWidth, 40);
        loginButton.addActionListener(e -> loginButton());
        panel.add(loginButton);

        errorLabel = new JLabel("");
        errorLabel.setBounds(centerX, 115, fieldWidth, 25);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel);

        passwordField = new JPasswordField();
        passwordField.setForeground(Color.GRAY);
        passwordField.setBounds(centerX, 70, fieldWidth, fieldHeight);
        panel.add(passwordField);

        frame.setVisible(true);
    }

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

