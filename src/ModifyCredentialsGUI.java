import javax.swing.*;
import java.util.List;

public class ModifyCredentialsGUI {

    private JLabel success;
    private static List<String> locations = FileAccessing.readFromFile();
    private static JComboBox<String> credentialList;

    public ModifyCredentialsGUI() {

        JFrame frame = new JFrame("Modify Credentials");
        JPanel panel = new JPanel();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        frame.add(panel);

        success = new JLabel("");
        success.setBounds(190, 110, 250, 25);
        panel.add(success);

        JLabel label = new JLabel("Select credential to modify:");
        label.setBounds(10, 40, 200, 25);
        panel.add(label);

        credentialList = new JComboBox<>();
        credentialList.setBounds(10, 80, 200, 25);
        refreshBox();
        panel.add(credentialList);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(230, 80, 100, 25);
        editButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();
            boolean successful = ModifyPasswordGUI(selected);
            if (successful) {
                success.setText("Password successfully changed!");
            }
        });
        panel.add(editButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setBounds(10, 5, 140, 25);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        frame.setVisible(true);
    }

    private boolean ModifyPasswordGUI(String location) {
        String currentPassword = FileAccessing.findPassword(location);

        JFrame frame = new JFrame("Change Password");
        JPanel panel = new JPanel();
        frame.setSize(500, 300);
        panel.setLayout(null);
        frame.add(panel);

        JLabel passwordLabel = new JLabel("Current Password:   " + currentPassword);
        passwordLabel.setBounds(10, 40, 250, 25);
        panel.add(passwordLabel);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(10, 90, 250, 25);
        panel.add(newPasswordLabel);

        JTextField newPasswordField = new JTextField(20);
        newPasswordField.setBounds(110, 90, 250, 25);
        panel.add(newPasswordField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(180, 150, 100, 25);
        saveButton.addActionListener(e -> {
            String username = FileAccessing.findUsername(location);
            FileAccessing.modifyLogin(location, currentPassword, username);
            frame.dispose();
            new ModifyCredentialsGUI();
        });
        panel.add(saveButton);


        frame.setVisible(true);

        return false;
    }

    private static void refreshBox() {
        locations = FileAccessing.readFromFile();
        credentialList.removeAllItems();

        for (String location : locations) {
            credentialList.addItem(location);
        }
    }

}
