import javax.swing.*;
import java.util.List;

public class ViewCredentialsGUI {

    private JLabel success;
    private static JComboBox<String> credentialList;

    public ViewCredentialsGUI() {

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

        success = new JLabel("");
        success.setBounds(centerX + 20, 70, 1200, 60);
        panel.add(success);

        credentialList = new JComboBox<>();
        credentialList.setBounds(centerX - 50, 140, fieldWidth, fieldHeight);
        refreshBox();
        panel.add(credentialList);

        JButton viewButton = new JButton("View");
        viewButton.setBounds(centerX + 300, 140, buttonWidth, buttonHeight);
        viewButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();
            if (selected == null) {
                success.setText("No credentials available");
                return;
            } else if(selected.equals("Select location...")) {
                success.setText("Please select a valid location");
                return;
            }
            frame.dispose();
            viewCredentials(selected);
        });
        panel.add(viewButton);

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

    private static void viewCredentials(String locationToFind) {
        String password = FileAccessing.findPassword(locationToFind);
        String username = FileAccessing.findUsername(locationToFind);

        JFrame frame = new JFrame("View Credentials");
        JPanel panel = new JPanel();
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            new ModifyCredentialsGUI();
        });
        panel.add(backButton);

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