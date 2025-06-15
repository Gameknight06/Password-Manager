import javax.swing.*;
import java.util.List;

public class ModifyCredentialsGUI {

    private static JLabel success = new JLabel("");
    private static JComboBox<String> credentialList;

    public ModifyCredentialsGUI() {

        JFrame frame = new JFrame("Modify Credentials");
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
        int buttonWidth = 150;
        int buttonHeight = 50;
        int centerX = (700 - fieldWidth) / 2;

        success.setBounds(centerX + 20, 70, 1200, 60);
        panel.add(success);

        credentialList = new JComboBox<>();
        credentialList.setBounds(centerX - 50, 140, fieldWidth, fieldHeight);
        refreshBox();
        panel.add(credentialList);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(centerX + 300, 140, buttonWidth, buttonHeight);
        editButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();
            assert selected != null;
            if(selected.equals("Select location...")) {
                success.setText("Please select a valid location");
                return;
            }
            frame.dispose();
            ModifyPasswordGUI(selected);
        });
        panel.add(editButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.putClientProperty("JButton.buttonType", "square");
        menuButton.setBounds(10, 10, buttonWidth, buttonHeight);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        frame.setVisible(true);
    }

    private void ModifyPasswordGUI(String locationToModify) {
        String currentPassword = FileAccessing.findPassword(locationToModify);
        String currentUsername = FileAccessing.findUsername(locationToModify);

        JFrame frame = new JFrame("Change Password");
        JPanel panel = new JPanel();
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);
        panel.setFocusable(true);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        frame.add(panel);

        int labelWidth = 200;
        int labelHeight = 50;
        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 130;
        int buttonHeight = 50;
        int centerX = (700 - fieldWidth) / 2;

        JLabel successful = new JLabel("");
        successful.setBounds(centerX + 190, 80, 1200, 60);
        panel.add(successful);

        JLabel usernameLabel = new JLabel("Current Username/Email:");
        usernameLabel.setBounds(centerX - 180, 30, labelWidth, labelHeight);
        panel.add(usernameLabel);

        JTextField currentUsernameField = new JTextField(currentUsername, 20);
        currentUsernameField.setBounds(centerX - 185, 70, fieldWidth, fieldHeight);
        currentUsernameField.setEditable(false);
        panel.add(currentUsernameField);

        JLabel newUsernameLabel = new JLabel("New Username/Email:");
        newUsernameLabel.setBounds(centerX - 180, 110, labelWidth, labelHeight);
        panel.add(newUsernameLabel);

        JTextField newUsernameField = new JTextField(20);
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

        JTextField newPasswordField = new JTextField(20);
        newPasswordField.setBounds(centerX - 185, 330, fieldWidth, fieldHeight);
        panel.add(newPasswordField);

        JButton backButton = new JButton("Cancel");
        backButton.setBounds(centerX + 250, 250, buttonWidth, buttonHeight);
        backButton.addActionListener(e -> {
            success.setText("");
            frame.dispose();
            new ModifyCredentialsGUI();
        });
        panel.add(backButton);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(centerX + 250, 150, buttonWidth, buttonHeight);
        saveButton.addActionListener(e -> {
            String newPassword = newPasswordField.getText();
            String newUsername = newUsernameField.getText();

            if (newPassword.equals(currentPassword) || newUsername.equals(currentUsername)) {
                successful.setText("Password or Username/Email cannot be the same as the current one!");
                return;
            } else if (newPassword.contains(" ") || newUsername.contains(" ")) {
                successful.setText("Password or Username/Email cannot contain spaces!");
                return;
            }

            if (newPassword.isEmpty() && newUsername.isEmpty()) {
                successful.setText("Password or Username/Email cannot be empty!");
                return;
            } else if (newPassword.isEmpty()) {
                newPassword = currentPassword; // Keep the old password if new is empty
            } else if (newUsername.isEmpty()) {
                newUsername = currentUsername; // Keep the old username if new is empty
            }
            FileAccessing.modifyLogin(locationToModify, newPassword, newUsername);
            success.setText("Modified Credentials Successfully");
            frame.dispose();
            new ModifyCredentialsGUI();;
        });
        panel.add(saveButton);


        frame.setVisible(true);
    }

    private static void refreshBox() {
        List<String> locations = FileAccessing.readLocationsFromFile();
        credentialList.removeAllItems();
        locations.add(0, "Select location...");

        for (String location : locations) {
            credentialList.addItem(location);
        }
    }

}