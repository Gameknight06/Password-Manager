import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class AddCredentialGUI {

    private static JTextField passwordField;
    private static JTextField user_emailField;
    private static JTextField locationField;
    private static JLabel success;
    private static JComboBox<String> userEmailList;
    private static JFrame frame;
    private static JLabel error;

    /**
     * Initializes the Add Credential GUI.
     * This method sets up the frame, panel, and input fields for adding new credentials.
     * It includes focus listeners for input fields, a save button, and a back button to return to the main menu.
     */
    public static void Initialize() {

        frame = GUIUtils.createAndConfigureFrame("Add New Credentials", 700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 40;
        int centerX = (frame.getWidth() - fieldWidth) / 2;


        locationField = new JTextField("Location", 20);
        locationField.setForeground(Color.GRAY);
        locationField.setBounds(centerX, 100, fieldWidth, fieldHeight);
        locationField.setToolTipText("Enter the location where the password is used");
        locationField.addFocusListener(new FocusListener() { // FocusListener to handle taking out and putting in text based on focus
            @Override
            public void focusGained(FocusEvent e) {
                if (locationField.getText().equals("Location")) {
                    locationField.setText("");
                    locationField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (locationField.getText().isEmpty()) {
                    locationField.setForeground(Color.GRAY);
                    locationField.setText("Location");
                }
            }
        });
        panel.add(locationField);

        user_emailField = new JTextField("Username/Email", 20);
        user_emailField.setBounds(centerX, 170, fieldWidth, fieldHeight);
        user_emailField.setForeground(Color.GRAY);
        user_emailField.setToolTipText("Enter the username or email address associated with the password");
        user_emailField.addFocusListener(new FocusListener() { // FocusListener to handle taking out and putting in text based on focus
            @Override
            public void focusGained(FocusEvent e) {
                if (user_emailField.getText().equals("Username/Email")) {
                    user_emailField.setText("");
                    user_emailField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (user_emailField.getText().isEmpty()) {
                    user_emailField.setForeground(Color.GRAY);
                    user_emailField.setText("Username/Email");
                }
            }
        });
        panel.add(user_emailField);

        passwordField = new JTextField("Password", 20);
        passwordField.setForeground(Color.GRAY);
        passwordField.setBounds(centerX, 240, fieldWidth, fieldHeight);
        passwordField.setToolTipText("Enter the password for the specified location and username/email");
        passwordField.addFocusListener(new FocusListener() { // FocusListener to handle taking out and putting in text based on focus
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("Password");
                }
            }
        });
        panel.add(passwordField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(centerX + 70, 340, buttonWidth, buttonHeight);
        saveButton.addActionListener(e -> saveButton());
        panel.add(saveButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.putClientProperty("JButton.buttonType", "square");
        menuButton.setBounds(10, 10, buttonWidth, buttonHeight);
        menuButton.addActionListener(e -> {
            frame.dispose();
            MainMenuGUI.Initialize();
        });
        panel.add(menuButton);

        success = new JLabel();
        success.setBounds(centerX, 50, 300, 60);
        success.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(success);

        frame.setVisible(true);
    }

    /**
     * Clears the input fields and resets the success message.
     * This method is called after saving credentials or when the user wants to enter new credentials.
     */
    private static void clearFields() {
        passwordField.setText("");
        user_emailField.setText("");
        locationField.setText("");
        success.setText("Enter new credentials:");
    }

    /**
     * Handles the action of saving the credentials entered by the user.
     * It checks if the fields are filled, validates the location, and saves the credentials using VaultManager.
     * If the location already exists, it prompts the user to overwrite or save as additional credentials.
     */
    private static void saveButton() {
        List<String> locations = VaultManager.readLocationsFromFile();
        List<String> testLocations = VaultManager.readLocationsFromFile();
        testLocations.replaceAll(String::toUpperCase);
        String password = passwordField.getText();
        String user_email = user_emailField.getText();
        String location = locationField.getText();
        String testLocation = location.toUpperCase();

        if (location.equals("Location") || user_email.equals("Username/Email") || password.equals("Password")) {
            success.setText("Please fill in all fields!");
            return;
        }

        if (!password.isEmpty() && !testLocations.contains(testLocation)) {
            VaultManager.saveLogin(password, location, user_email);
            clearFields();
            success.setText("Successfully Encrypted and saved!");
        } else if (testLocations.contains(testLocation)) {
            String realLocation = "";
            for (String loc : locations) {
                if (loc.equalsIgnoreCase(location)) {
                    realLocation = loc;
                }
            }
            duplicateLocation(realLocation);
        } else {
            success.setText("Password field is empty!");
        }
    }

    /**
     * Handles the case where the user tries to add credentials for a location that already exists.
     * It prompts the user with options to overwrite the existing credentials, save as additional credentials, or to cancel.
     */
    private static void duplicateLocation(String location) {
        JFrame frame = new JFrame("Duplicate Location");
        JPanel panel = new JPanel();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setFocusable(true);
        panel.setLayout(null);
        frame.add(panel);

        int buttonWidth = 150;
        int buttonHeight = 40;
        int centerX = (frame.getWidth() - buttonWidth) / 2;

        JLabel messageLabel = new JLabel("Credentials for this location already exist.");
        messageLabel.setBounds(centerX - 30, 0, 360, 50);
        panel.add(messageLabel);

        JButton overwriteButton = new JButton("Overwrite");
        overwriteButton.setBounds(centerX - 80, 40, buttonWidth, buttonHeight);
        overwriteButton.addActionListener(e -> {
            frame.dispose();
            duplicateLocationOverwrite(location);
        });
        panel.add(overwriteButton);

        JButton additionalButton = new JButton("Save as Additional");
        additionalButton.setBounds(centerX + 80, 40, buttonWidth, buttonHeight);
        additionalButton.addActionListener(e -> {
            VaultManager.saveLogin(passwordField.getText(), location, user_emailField.getText());
            clearFields();
            success.setText("Successfully Saved as Additional Credentials!");
            frame.dispose();
        });
        panel.add(additionalButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(centerX, 100, buttonWidth, buttonHeight);
        cancelButton.addActionListener(e -> {
            clearFields();
            success.setText("Operation Cancelled. Please enter new credentials:");
            frame.dispose();
        });
        panel.add(cancelButton);

        frame.setVisible(true);
    }

    /**
     * Handles the GUI of overwriting duplicate credentials for a specific location.
     * It allows the user to select a username/email from a dropdown and overwrite the credentials.
     */
    private static void duplicateLocationOverwrite(String location) {
        frame = GUIUtils.createAndConfigureFrame("Duplicate Location", 400, 300);
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

        error = new JLabel("");
        error.setBounds(centerX - 85, 20, 300, 60);
        panel.add(error);

        userEmailList = new JComboBox<>();
        userEmailList.setBounds(centerX - 110, 70, fieldWidth, fieldHeight);
        GUIUtils.refreshUsernames(userEmailList, location);
        panel.add(userEmailList);

        JButton viewButton = new JButton("Overwrite");
        viewButton.setBounds(centerX + 100, 70, buttonWidth, buttonHeight);
        viewButton.addActionListener(e -> overwriteButton(location));
        panel.add(viewButton);

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
     * Handles the action of overwriting existing credentials for a specific location.
     * It retrieves the selected username/email from the dropdown and updates the credentials in the VaultManager.
     */
    private static void overwriteButton(String location) {
        String selected = (String) userEmailList.getSelectedItem();
        if (selected == null) {
            error.setText("No credentials available");
            return;
        } else if(selected.equals("Select Username/Email...")) {
            error.setText("Please select a valid location");
            return;
        }
        VaultManager.modifyLogin(location, passwordField.getText(), selected, user_emailField.getText());
        clearFields();
        success.setText("Successfully Overwritten!");
        frame.dispose();
    }
}