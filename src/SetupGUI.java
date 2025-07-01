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
        frame = GUIUtils.createAndConfigureFrame("Setup", 500, 450);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int componentWidth = 300;
        int labelHeight = 25;
        int fieldHeight = 35;
        int buttonHeight = 35;
        int createButtonHeight = 50;
        int errorLabelHeight = 25;

        JLabel locationLabel = new JLabel("Vault Location:");
        locationField = new JTextField();
        JButton browseButton = new JButton("Browse...");
        JLabel passwordLabel = new JLabel("Master Password:");
        passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Master Password:");
        confirmPasswordField = new JPasswordField();
        errorLabel = new JLabel("", SwingConstants.CENTER);
        JButton createVaultButton = new JButton("Create Vault");

        locationField.setEditable(false);
        locationField.setBackground(Color.WHITE);
        locationField.setForeground(Color.BLACK);
        errorLabel.setForeground(Color.RED);

        locationLabel.setPreferredSize(new Dimension(componentWidth, labelHeight));
        locationField.setPreferredSize(new Dimension(componentWidth, fieldHeight));
        browseButton.setPreferredSize(new Dimension(componentWidth, buttonHeight));
        passwordLabel.setPreferredSize(new Dimension(componentWidth, labelHeight));
        passwordField.setPreferredSize(new Dimension(componentWidth, fieldHeight));
        confirmPasswordLabel.setPreferredSize(new Dimension(componentWidth, labelHeight));
        confirmPasswordField.setPreferredSize(new Dimension(componentWidth, fieldHeight));
        errorLabel.setPreferredSize(new Dimension(componentWidth, errorLabelHeight));
        createVaultButton.setPreferredSize(new Dimension(componentWidth, createButtonHeight));

        browseButton.addActionListener(e -> selectVaultLocation());
        createVaultButton.addActionListener(e -> createVault());

        locationField.setBackground(Color.GRAY);
        locationField.setForeground(Color.WHITE);


        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(errorLabel, componentWidth, componentWidth, componentWidth)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(locationLabel)
                                        .addComponent(locationField, componentWidth, componentWidth, componentWidth)
                                        .addComponent(browseButton, componentWidth, componentWidth, componentWidth)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField, componentWidth, componentWidth, componentWidth)
                                        .addComponent(confirmPasswordLabel)
                                        .addComponent(confirmPasswordField, componentWidth, componentWidth, componentWidth)
                                )
                                .addComponent(createVaultButton, componentWidth, componentWidth, componentWidth)
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(errorLabel, errorLabelHeight, errorLabelHeight, errorLabelHeight)
                        .addGap(10)
                        .addComponent(locationLabel, labelHeight, labelHeight, labelHeight)
                        .addComponent(locationField, fieldHeight, fieldHeight, fieldHeight)
                        .addGap(5)
                        .addComponent(browseButton, buttonHeight, buttonHeight, buttonHeight)
                        .addGap(15)
                        .addComponent(passwordLabel, labelHeight, labelHeight, labelHeight)
                        .addComponent(passwordField, fieldHeight, fieldHeight, fieldHeight)
                        .addGap(10)
                        .addComponent(confirmPasswordLabel, labelHeight, labelHeight, labelHeight)
                        .addComponent(confirmPasswordField, fieldHeight, fieldHeight, fieldHeight)
                        .addGap(25)
                        .addComponent(createVaultButton, createButtonHeight, createButtonHeight, createButtonHeight)
                        .addGap(100)
        );

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
        Arrays.fill(confirmPassword, ' '); // Clear the confirm password field

        frame.dispose();
        MainMenuGUI.Initialize();
    }
}
