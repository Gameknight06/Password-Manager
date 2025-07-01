import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class AddCredentialGUI {

    private static JTextField passwordField;
    private static JTextField user_emailField;
    private static JTextField locationField;
    private static JLabel successOrError;
    private static JComboBox<String> userEmailList;
    private static JFrame frame;
    /**
     * Initializes the Add Credential GUI.
     * This method sets up the frame, panel, and input fields for adding new credentials.
     * It includes focus listeners for input fields, a save button, and a back button to return to the main menu.
     */
    public static void Initialize(String message, Color color) {

        frame = GUIUtils.createAndConfigureFrame("Add New Credentials", 700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;

        successOrError = new JLabel(message, SwingConstants.CENTER);
        successOrError.setForeground(color);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveButton());

        JButton menuButton = GUIUtils.addMenuButton(frame);

        locationField = new JTextField("Location", 20);
        locationField.setForeground(Color.GRAY);
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

        user_emailField = new JTextField("Username/Email", 20);
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

        passwordField = new JTextField("Password", 20);
        passwordField.setForeground(Color.GRAY);
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

        locationField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        user_emailField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        passwordField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        saveButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        menuButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        successOrError.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(menuButton, buttonWidth, buttonWidth, buttonWidth)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(successOrError, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(locationField, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(user_emailField, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(passwordField, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(saveButton, buttonWidth, buttonWidth, buttonWidth)
                                )
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(menuButton, buttonHeight, buttonHeight, buttonHeight)
                        .addGap(5)
                        .addComponent(successOrError, 30, 30, 30)
                        .addGap(10)
                        .addComponent(locationField, fieldHeight, fieldHeight, fieldHeight)
                        .addGap(25)
                        .addComponent(user_emailField, fieldHeight, fieldHeight, fieldHeight)
                        .addGap(25)
                        .addComponent(passwordField, fieldHeight, fieldHeight, fieldHeight)
                        .addGap(50)
                        .addComponent(saveButton, buttonHeight, buttonHeight, buttonHeight)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
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
            successOrError.setForeground(Color.RED);
            successOrError.setText("Please fill in all fields!");
        } else if (password.contains(" ") || user_email.contains(" ") || location.contains(" ")) {
            successOrError.setForeground(Color.RED);
            successOrError.setText("No spaces allowed in any of the fields!");
        } else if (testLocations.contains(testLocation)) {
            String realLocation = "";
            for (String loc : locations) {
                if (loc.equalsIgnoreCase(location)) {
                    realLocation = loc;
                }
            }
            frame.dispose();
            duplicateLocation(realLocation);
        } else if (!password.isEmpty() && !testLocations.contains(testLocation)) {
            VaultManager.saveLogin(password, location, user_email);
            clearFields();
            successOrError.setForeground(Color.GREEN);
            successOrError.setText("Successfully Encrypted and saved!");
        }

    }

    /**
     * Handles the case where the user tries to add credentials for a location that already exists.
     * It prompts the user with options to overwrite the existing credentials, save as additional credentials, or to cancel.
     */
    private static void duplicateLocation(String location) {
        JFrame frame = GUIUtils.createAndConfigureFrame("Duplicate Location",400, 200);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the frame

        int buttonWidth = 150;
        int buttonHeight = 40;
        int labelWidth = 400;
        int labelHeight = 20;

        JLabel messageLabel = new JLabel("Credentials for this location already exist.");

        JButton overwriteButton = new JButton("Overwrite");
        overwriteButton.addActionListener(e -> {
            frame.dispose();
            duplicateLocationOverwrite(location);
        });

        JButton additionalButton = new JButton("Save as Additional");
        additionalButton.addActionListener(e -> {
            VaultManager.saveLogin(passwordField.getText(), location, user_emailField.getText());
            clearFields();
            frame.dispose();
            Initialize("Successfully Saved as Additional Credentials!", Color.GREEN);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            clearFields();
            successOrError.setForeground(Color.RED);
            successOrError.setText("Operation Cancelled. Please enter new credentials:");
            frame.dispose();
        });

        messageLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        additionalButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        overwriteButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        cancelButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(messageLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(overwriteButton, buttonWidth, buttonWidth, buttonWidth)
                                .addGap(20)
                                .addComponent(additionalButton, buttonWidth, buttonWidth, buttonWidth)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cancelButton, buttonWidth, buttonWidth, buttonWidth)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(messageLabel)
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(overwriteButton, buttonHeight, buttonHeight, buttonHeight)
                                .addComponent(additionalButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(15)
                        .addComponent(cancelButton, buttonHeight, buttonHeight, buttonHeight)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
    }

    /**
     * Handles the GUI of overwriting duplicate credentials for a specific location.
     * It allows the user to select a username/email from a dropdown and overwrite the credentials.
     */
    private static void duplicateLocationOverwrite(String location) {
        JFrame frame = GUIUtils.createAndConfigureFrame("Duplicate Location",500, 400);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the frame

        int boxWidth = 250;
        int boxHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int backButtonWidth = 110;
        int backButtonHeight = 40;
        int labelWidth = 300;
        int labelHeight = 50;

        JLabel messageLabel = new JLabel("Select which credentials to overwrite for " + location);
        successOrError = new JLabel();
        userEmailList = new JComboBox<>();
        JButton overwriteButton = new JButton("Overwrite");
        JButton backButton = new JButton("Back");

        GUIUtils.refreshUsernames(userEmailList, location); // Refresh usernames based on the selected location
        successOrError.setForeground(Color.RED);

        overwriteButton.addActionListener(e -> overwriteButton(location));

        backButton.addActionListener(e -> {
            frame.dispose();
            Initialize("Please enter new credentials below:", Color.WHITE);
        });

        messageLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        userEmailList.setPreferredSize(new java.awt.Dimension(boxWidth, boxHeight));
        overwriteButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        backButton.setPreferredSize(new java.awt.Dimension(backButtonWidth, backButtonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backButton, backButtonWidth, backButtonWidth, backButtonWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
                        .addComponent(messageLabel)
                        .addComponent(successOrError)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(userEmailList, boxWidth, boxWidth, boxWidth)
                                .addGap(15)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(overwriteButton, buttonWidth, buttonWidth, buttonWidth)
                                )
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(backButton, backButtonHeight, backButtonHeight, backButtonHeight)
                        .addGap(45)
                        .addComponent(messageLabel)
                        .addGap(20)
                        .addComponent(successOrError)
                        .addGap(5)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(userEmailList, boxHeight, boxHeight, boxHeight)
                                .addComponent(overwriteButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
    }

    /**
     * Clears the input fields and resets the success message.
     * This method is called after saving credentials or when the user wants to enter new credentials.
     * It also resets the placeholder text and color.
     */
    private static void clearFields() {
        passwordField.setText("Password");
        passwordField.setForeground(Color.GRAY);
        user_emailField.setText("Username/Email");
        user_emailField.setForeground(Color.GRAY);
        locationField.setText("Location");
        locationField.setForeground(Color.GRAY);
    }

    /**
     * Handles the action of overwriting existing credentials for a specific location.
     * It retrieves the selected username/email from the dropdown and updates the credentials in the VaultManager.
     */
    private static void overwriteButton(String location) {
        String selected = (String) userEmailList.getSelectedItem();
        if (selected == null) {
            successOrError.setForeground(Color.RED);
            successOrError.setText("No credentials available");
            return;
        } else if(selected.equals("Select Username/Email...")) {
            successOrError.setForeground(Color.RED);
            successOrError.setText("Please select a valid location");
            return;
        }
        VaultManager.modifyLogin(location, passwordField.getText(), selected, user_emailField.getText());
        clearFields();
        frame.dispose();
        Initialize("Successfully Overwritten!", Color.GREEN);
    }
}