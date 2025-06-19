import javax.swing.*;

public class ViewCredentialsGUI {

    private static JLabel success;
    private static JComboBox<String> credentialList;
    private static JFrame frame;
    private static JComboBox<String> userEmailList;

    /**
     * Initializes the View Credentials GUI.
     * Sets up the frame, panel, and components for viewing credentials.
     */
    public static void Initialize() {
        frame = GUIUtils.createAndConfigureFrame("View Credentials", 700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int centerX = (frame.getWidth() - fieldWidth) / 2;

        success = GUIUtils.createMessageLabel(panel, centerX + 20, 70, 300, 60);

        credentialList = new JComboBox<>();
        credentialList.setBounds(centerX - 50, 140, fieldWidth, fieldHeight);
        GUIUtils.refreshLocations(credentialList);
        panel.add(credentialList);

        JButton viewButton = new JButton("View");
        viewButton.setBounds(centerX + 300, 140, buttonWidth, buttonHeight);
        viewButton.addActionListener(e -> viewButton());
        panel.add(viewButton);

        GUIUtils.addMenuButton(frame, panel, 10, 10, buttonWidth, buttonHeight);

        frame.setVisible(true);
    }

    /**
     * Displays the credentials for a specific location and username.
     * Creates a new JFrame to show the credentials in a read-only format.
     */
    private static void viewCredentials(String locationToFind, String username) {
        String password = VaultManager.findPassword(locationToFind, username);

        JFrame frame = new JFrame("View Credentials");
        JPanel panel = new JPanel();
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);
        panel.setFocusable(true);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        frame.add(panel);

        int fieldWidth = 300;
        int fieldHeight = 50;
        int labelWidth = 600;
        int labelHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int centerX = (700 - fieldWidth) / 2;

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(centerX, 65, labelWidth, labelHeight);
        panel.add(locationLabel);

        JLabel usernameLabel = new JLabel("Username/Email:");
        usernameLabel.setBounds(centerX, 145, labelWidth, labelHeight);
        panel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(centerX, 225, labelWidth, labelHeight);
        panel.add(passwordLabel);

        JTextField locationField = new JTextField(locationToFind);
        locationField.setBounds(centerX - 5, 100, fieldWidth, fieldHeight);
        locationField.setEditable(false);
        panel.add(locationField);

        JTextField usernameField = new JTextField(username);
        usernameField.setBounds(centerX - 5, 180, fieldWidth, fieldHeight);
        usernameField.setEditable(false);
        panel.add(usernameField);

        JTextField passwordField = new JTextField(password);
        passwordField.setBounds(centerX - 5, 260, fieldWidth, fieldHeight);
        passwordField.setEditable(false);
        panel.add(passwordField);

        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 10, buttonWidth, buttonHeight);
        backButton.putClientProperty("JButton.buttonType", "square");
        backButton.addActionListener(e -> {
            frame.dispose();
            Initialize();
        });
        panel.add(backButton);

        frame.setVisible(true);
    }

    /**
     * Handles the case where there are duplicate entries for a location.
     * Displays a new JFrame with a JComboBox to select the username/email associated with the location.
     */
    private static void duplicateLocation(String location) {
        JFrame frame = new JFrame("Duplicate Location");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setFocusable(true);
        panel.setLayout(null);
        frame.add(panel);

        int fieldWidth = 200;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 40;
        int centerX = (frame.getWidth() - buttonWidth) / 2;

        JLabel messageLabel = new JLabel("There is more than one entry for " + location);
        messageLabel.setBounds(0, 0, 360, 50);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel);

        success = GUIUtils.createMessageLabel(panel, centerX - 85, 20, 300, 60);

        userEmailList = new JComboBox<>();
        userEmailList.setBounds(centerX - 110, 70, fieldWidth, fieldHeight);
        GUIUtils.refreshUsernames(userEmailList, location);
        panel.add(userEmailList);

        JButton viewButton = new JButton("View");
        viewButton.setBounds(centerX + 100, 70, buttonWidth, buttonHeight);
        viewButton.addActionListener(e -> viewButtonDup());
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
     * Handles the action of viewing credentials for a single location.
     * It checks for duplicates and either opens the single view GUI or opens the duplicate location GUI.
     */
    private static void viewButton() {
        String selected = (String) credentialList.getSelectedItem();
        String username = VaultManager.findUsername(selected);
        if (selected == null) {
            success.setText("No credentials available");
            return;
        } else if(selected.equals("Select Location...")) {
            success.setText("Please select a valid location");
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

    /**
     * Handles the action of viewing credentials for a single location with a specific username/email.
     * It retrieves the selected username/email and calls the viewCredentials method.
     */
    private static void viewButtonDup() {
        String selected = (String) credentialList.getSelectedItem();
        String username = (String) userEmailList.getSelectedItem();
        if (username == null) {
            success.setText("No credentials available");
            return;
        } else if(username.equals("Select Username/Email...")) {
            success.setText("Please select a valid location");
            return;
        }
        frame.dispose();
        viewCredentials(selected, username);
    }

}