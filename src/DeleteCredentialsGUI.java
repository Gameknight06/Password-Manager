import javax.swing.*;
import java.util.List;

public class DeleteCredentialsGUI {

    private JLabel success;
    private static List<String> locations = FileAccessing.readLocationsFromFile();
    private static JComboBox<String> credentialList = new JComboBox<>();

    public DeleteCredentialsGUI() {

        JFrame frame = new JFrame("Delete Credentials");
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

        JLabel label = new JLabel("Select credential to delete:");
        label.setBounds(centerX - 350, 500, labelWidth, labelHeight);
        panel.add(label);

        credentialList.setBounds(centerX + 300, 500, fieldWidth, fieldHeight);
        refreshBox();
        panel.add(credentialList);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds((3440 - buttonWidth) / 2, 700, buttonWidth, buttonHeight);
        deleteButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();
            FileAccessing.deleteLogin(selected);
            success.setText("Deleted Credentials Successfully");
            refreshBox();
        });
        panel.add(deleteButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setBounds(50, 50, buttonWidth, buttonHeight);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        frame.setVisible(true);
    }

    private static void refreshBox() {
        locations = FileAccessing.readLocationsFromFile();
        credentialList.removeAllItems();

        for (String location : locations) {
            credentialList.addItem(location);
        }
    }
}