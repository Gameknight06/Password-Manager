import javax.swing.*;
import java.util.List;

public class ModifyCredentialsGUI {

    private boolean successful = false;

    private JLabel success;
    private static JComboBox<String> credentialList;

    public ModifyCredentialsGUI() {

        JFrame frame = new JFrame("Modify Credentials");
        JPanel panel = new JPanel();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        frame.add(panel);

        int labelWidth = 600;
        int labelHeight = 60;
        int fieldWidth = 800;
        int fieldHeight = 60;
        int buttonWidth = 400;
        int buttonHeight = 80;
        int centerX = (3440 - fieldWidth) / 2;

        success = new JLabel("");
        success.setBounds(centerX, 300, 1200, 60);
        panel.add(success);

        JLabel label = new JLabel("Select credentials to modify:");
        label.setBounds(centerX - 350, 500, labelWidth, labelHeight);
        panel.add(label);

        credentialList = new JComboBox<>();
        credentialList.setBounds(centerX + 300, 500, fieldWidth, fieldHeight);
        refreshBox();
        panel.add(credentialList);

        JButton editButton = new JButton("Edit");
        editButton.setBounds((3440 - buttonWidth) / 2, 700, buttonWidth, buttonHeight);
        editButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();
            ModifyPasswordGUI(selected);
            if (successful) {
                success.setText("Password successfully changed!");
            }
        });
        panel.add(editButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setBounds(50, 50, buttonWidth, buttonHeight);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        frame.setVisible(true);
    }

    private boolean ModifyPasswordGUI(String locationToModify) {
        String currentPassword = FileAccessing.findPassword(locationToModify);

        JFrame frame = new JFrame("Change Password");
        JPanel panel = new JPanel();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);
        frame.add(panel);

        int labelWidth = 600;
        int labelHeight = 60;
        int fieldWidth = 800;
        int fieldHeight = 60;
        int buttonWidth = 400;
        int buttonHeight = 80;
        int centerX = (3440 - fieldWidth) / 2;

        success = new JLabel("");
        success.setBounds(centerX, 300, 1200, 60);
        panel.add(success);

        JLabel passwordLabel = new JLabel("Current Password:   " + currentPassword);
        passwordLabel.setBounds(centerX - 350, 500, labelWidth, labelHeight);
        panel.add(passwordLabel);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(centerX - 350, 600, labelWidth, labelHeight);
        panel.add(newPasswordLabel);

        JTextField newPasswordField = new JTextField(20);
        newPasswordField.setBounds(centerX + 300, 600, fieldWidth, fieldHeight);
        panel.add(newPasswordField);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 50, buttonWidth, buttonHeight);
        backButton.addActionListener(e -> {
            frame.dispose();
        });
        panel.add(backButton);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds((3440 - buttonWidth) / 2, 800, buttonWidth, buttonHeight);
        saveButton.addActionListener(e -> {
            String username = FileAccessing.findUsername(locationToModify);
            String password = newPasswordField.getText();
            if (password.equals(currentPassword)) {
                success.setText("Password cannot be the same as current password!");
                return;
            } else if (password.isEmpty() || password.contains(" ")) {
                success.setText("Password cannot be empty or contain spaces!");
                return;
            }
            FileAccessing.modifyLogin(locationToModify, password, username);
            successful = true;
            frame.dispose();
        });
        panel.add(saveButton);


        frame.setVisible(true);

        return false;
    }

    private static void refreshBox() {
        List<String> locations = FileAccessing.readLocationsFromFile();
        credentialList.removeAllItems();

        for (String location : locations) {
            credentialList.addItem(location);
        }
    }

}