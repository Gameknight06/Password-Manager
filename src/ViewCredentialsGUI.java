import javax.swing.*;
import java.util.List;

public class ViewCredentialsGUI {

    private JLabel success;
    private static JComboBox<String> credentialList;

    public ViewCredentialsGUI() {

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

        JLabel label = new JLabel("Select credentials to view:");
        label.setBounds(centerX - 350, 500, labelWidth, labelHeight);
        panel.add(label);

        credentialList = new JComboBox<>();
        credentialList.setBounds(centerX + 300, 500, fieldWidth, fieldHeight);
        refreshBox();
        panel.add(credentialList);

        JButton viewButton = new JButton("View");
        viewButton.setBounds((3440 - buttonWidth) / 2, 700, buttonWidth, buttonHeight);
        viewButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();
            if (selected == null) {
                success.setText("No credentials available");
                return;
            }
            viewCredentials(selected);
        });
        panel.add(viewButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setBounds(50, 50, buttonWidth, buttonHeight);
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
        String location = FileAccessing.findLocation(locationToFind);

        JFrame frame = new JFrame("View Credentials");
        JPanel panel = new JPanel();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(null);
        frame.add(panel);

        int labelWidth = 600;
        int labelHeight = 60;
        int buttonWidth = 400;
        int buttonHeight = 80;
        int centerX = (3440 - labelWidth) / 2;

        JLabel locationLabel = new JLabel("Location:      " + location);
        locationLabel.setBounds(centerX, 400, labelWidth, labelHeight);
        panel.add(locationLabel);

        JLabel usernameLabel = new JLabel("Username:   " + username);
        usernameLabel.setBounds(centerX, 500, labelWidth, labelHeight);
        panel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password:   " + password);
        passwordLabel.setBounds(centerX, 600, labelWidth, labelHeight);
        panel.add(passwordLabel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 50, buttonWidth, buttonHeight);
        backButton.addActionListener(e -> {
            frame.dispose();
        });
        panel.add(backButton);

        frame.setVisible(true);
    }

    private static void refreshBox() {
        List<String> locations = FileAccessing.readLocationsFromFile();
        credentialList.removeAllItems();

        for (String location : locations) {
            credentialList.addItem(location);
        }
    }

}