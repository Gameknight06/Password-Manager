import javax.swing.*;
import java.awt.*;

public class ViewCredentialsGUI {

    private static JLabel successOrError;
    private static JComboBox<String> credentialList;
    private static JFrame frame;
    private static JComboBox<String> userEmailList;

    /**
     * Initializes the View Credentials GUI.
     * Sets up the frame, panel, and components for viewing credentials.
     */
    public static void Initialize(String message, Color color) {
        frame = GUIUtils.createAndConfigureFrame("View Credentials", 700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int boxWidth = 250;
        int boxHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int labelWidth = 300;
        int labelHeight = 50;

        successOrError = new JLabel(message, SwingConstants.CENTER);
        successOrError.setForeground(color);

        credentialList = new JComboBox<>();
        GUIUtils.refreshLocations(credentialList);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(e -> viewButton());
        JButton menuButton = GUIUtils.addMenuButton(frame);

        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        credentialList.setPreferredSize(new java.awt.Dimension(boxWidth, boxHeight));
        viewButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        menuButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(menuButton, buttonWidth, buttonWidth, buttonWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(successOrError, labelWidth, labelWidth, labelWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(credentialList, boxWidth, boxWidth, boxWidth)
                                .addGap(20)
                                .addComponent(viewButton, buttonWidth, buttonWidth, buttonWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )

        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(menuButton, buttonHeight, buttonHeight, buttonHeight)
                        .addGap(20)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(successOrError, labelHeight, labelHeight, labelHeight)
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(credentialList, boxHeight, boxHeight, boxHeight)
                                .addComponent(viewButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGap(200)
        );

        frame.setVisible(true);
    }

    /**
     * Displays the credentials for a specific location and username.
     * Creates a new JFrame to show the credentials in a read-only format.
     */
    private static void viewCredentials(String locationToFind, String username) {
        String password = VaultManager.findPassword(locationToFind, username);

        JFrame frame = GUIUtils.createAndConfigureFrame("View Credentials", 700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int labelWidth = 150;
        int labelHeight = 15;
        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 130;
        int buttonHeight = 50;

        JLabel locationLabel = new JLabel("  Location:");
        JLabel usernameLabel = new JLabel("  Username/Email:");
        JLabel passwordLabel = new JLabel("  Password:");

        JTextField locationField = new JTextField(locationToFind);
        locationField.setEditable(false);

        JTextField usernameField = new JTextField(username);
        usernameField.setEditable(false);

        JTextField passwordField = new JTextField(password);
        passwordField.setEditable(false);

        JButton backButton = new JButton("Back");
        backButton.putClientProperty("JButton.buttonType", "square");
        backButton.addActionListener(e -> {
            frame.dispose();
            Initialize("Select credentials to view", Color.WHITE);
        });

        locationLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        usernameLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        passwordLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        locationField.setPreferredSize(new java.awt.Dimension(fieldWidth, fieldHeight));
        usernameField.setPreferredSize(new java.awt.Dimension(fieldWidth, fieldHeight));
        passwordField.setPreferredSize(new java.awt.Dimension(fieldWidth, fieldHeight));
        backButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backButton, buttonWidth, buttonWidth, buttonWidth)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(locationLabel, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(locationField, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(usernameLabel, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(usernameField, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(passwordLabel, fieldWidth, fieldWidth, fieldWidth)
                                        .addComponent(passwordField, fieldWidth, fieldWidth, fieldWidth)
                                )
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(backButton, buttonHeight, buttonHeight, buttonHeight)
                        .addGap(50)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(locationLabel, labelHeight, labelHeight, labelHeight)
                                .addGap(5)
                                .addComponent(locationField, fieldHeight, fieldHeight, fieldHeight)
                        )
                        .addGap(15)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(usernameLabel, labelHeight, labelHeight, labelHeight)
                                .addGap(5)
                                .addComponent(usernameField, fieldHeight, fieldHeight, fieldHeight)
                        )
                        .addGap(15)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(passwordLabel, labelHeight, labelHeight, labelHeight)
                                .addGap(5)
                                .addComponent(passwordField, fieldHeight, fieldHeight, fieldHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
        );


        frame.setVisible(true);
    }

    /**
     * Handles the case where there are duplicate entries for a location.
     * Displays a new JFrame with a JComboBox to select the username/email associated with the location.
     */
    private static void duplicateLocation(String location) {
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

        JLabel messageLabel = new JLabel("There is more than one entry for " + location);
        successOrError = new JLabel();
        userEmailList = new JComboBox<>();
        JButton viewButton = new JButton("View");
        JButton backButton = new JButton("Back");

        GUIUtils.refreshUsernames(userEmailList, location); // Refresh usernames based on the selected location
        successOrError.setForeground(Color.RED);

        viewButton.addActionListener(e -> {
            String selected = (String) userEmailList.getSelectedItem();
            if (selected == null) {
                successOrError.setText("No credentials available");
                return;
            } else if(selected.equals("Select Username/Email...")) { // Check for default selection
                successOrError.setText("Please select a valid location");
                return;
            }
            frame.dispose();
            viewCredentials(location, selected);
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            Initialize("Select credentials to view:", Color.WHITE);
        });

        messageLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        userEmailList.setPreferredSize(new java.awt.Dimension(boxWidth, boxHeight));
        viewButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
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
                                        .addComponent(viewButton, buttonWidth, buttonWidth, buttonWidth)
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
                                .addComponent(viewButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
    }

    /**
     * Handles the action of viewing credentials.
     * If there are duplicates, it calls the duplicateLocation method.
     * Otherwise, it views the selected credentials.
     */
    private static void viewButton() {
        String selected = (String) credentialList.getSelectedItem();
        String username = VaultManager.findUsername(selected);
        if (selected == null) {
            successOrError.setText("No credentials available");
            return;
        } else if(selected.equals("Select Location...")) {
            successOrError.setForeground(Color.RED);
            successOrError.setText("Please select a valid location");
            return;
        }

        if (GUIUtils.hasDuplicates(selected)) {
            frame.dispose();
            duplicateLocation(selected);
        } else {
            frame.dispose();
            viewCredentials(selected, username);
        }
    }

}