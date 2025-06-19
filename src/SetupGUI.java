import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class SetupGUI {

    private static JFrame frame;
    private static JPasswordField passwordField;
    private static JPasswordField confirmPasswordField;
    private static JLabel errorLabel;
    private static JTextField locationField;
    private static String vaultFilePath;

    /**
     * Initializes the setup GUI for creating a new vault.
     * Prompts the user to select a vault location and set a master password.
     */
    public static void Initialize() {
        frame = new JFrame("Create Your Vault");
        JPanel panel = new JPanel();
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);
        frame.add(panel);

        int fieldWidth = 300;
        int fieldHeight = 35;
        int centerX = (frame.getWidth() - fieldWidth) / 2;

        JLabel locationLabel = new JLabel("Vault Location:");
        locationLabel.setBounds(centerX, 20, fieldWidth, 25);
        panel.add(locationLabel);

        locationField = new JTextField();
        locationField.setBounds(centerX, 45, fieldWidth, fieldHeight);
        locationField.setEditable(false);
        locationField.setBackground(Color.WHITE);
        locationField.setForeground(Color.BLACK);
        panel.add(locationField);

        JButton browseButton = new JButton("Browse...");
        browseButton.setBounds(centerX, 85,  fieldWidth, fieldHeight);
        panel.add(browseButton);

        JLabel passwordLabel = new JLabel("Master Password:");
        passwordLabel.setBounds(centerX, 135, fieldWidth, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(centerX, 160, fieldWidth, fieldHeight);
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Master Password:");
        confirmPasswordLabel.setBounds(centerX, 205, fieldWidth, 25);
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(centerX, 230, fieldWidth, fieldHeight);
        panel.add(confirmPasswordField);

        errorLabel = new JLabel();
        errorLabel.setBounds(centerX, 270, fieldWidth, 25);
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel);

        JButton createVaultButton = new JButton("Create Vault");
        createVaultButton.setBounds(centerX, 305, fieldWidth, 50);
        panel.add(createVaultButton);

        // Action Listeners
        browseButton.addActionListener(e -> selectVaultLocation());
        createVaultButton.addActionListener(e -> createVault());

        frame.setVisible(true);
    }

    /**
     * Opens a file chooser dialog to select the vault location.
     * The selected directory will be used to create the vault file.
     */
    private static void selectVaultLocation() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only allow directory selection
        fileChooser.setDialogTitle("Select Vault Location");

        int returnValue = fileChooser.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();

            vaultFilePath = selectedDirectory.getAbsolutePath() + File.separator + "vault.json"; // Set the vault file path
            locationField.setText(vaultFilePath);
            errorLabel.setText("");
        }
    }

    /**
     * Validates the input fields and creates a new vault with the provided master password.
     * If the passwords do not match or are empty, it displays an error message.
     */
    private static void createVault() {
        if (vaultFilePath == null || vaultFilePath.trim().isEmpty()) {
            errorLabel.setText("Please select a vault location.");
            return;
        }

        char[] password = passwordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        if (password.length == 0) {
            errorLabel.setText("Password cannot be empty.");
            return;
        }

        if (!Arrays.equals(password, confirmPassword)) {
            errorLabel.setText("Passwords do not match. Please try again.");
            passwordField.setText("");
            confirmPasswordField.setText("");
            passwordField.requestFocus();
            return;
        }

        VaultManager.setVaultFile(vaultFilePath);
        VaultManager.createNewVault(password);

        ConfigManager.saveVaultPath(vaultFilePath);

        VaultManager.unlockVault(password);
        Arrays.fill(password, ' '); // Clear password
        Arrays.fill(confirmPassword, ' '); // Clear confirm password

        frame.dispose();
        MainMenuGUI.Initialize();
    }
}
