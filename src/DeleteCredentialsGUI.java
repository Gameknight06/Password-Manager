import javax.swing.*;
import java.util.List;

public class DeleteCredentialsGUI {

    private JLabel success;
    private static List<String> locations = FileAccessing.readFromFile();
    private static JComboBox<String> credentialList = new JComboBox<>();

    public DeleteCredentialsGUI() {

        JFrame frame = new JFrame("Delete Credentials");
        JPanel panel = new JPanel();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        frame.add(panel);

        success = new JLabel("");
        success.setBounds(190, 110, 250, 25);
        panel.add(success);

        JLabel label = new JLabel("Select credential to delete:");
        label.setBounds(10, 40, 200, 25);
        panel.add(label);

        credentialList.setBounds(10, 80, 200, 25);
        refreshBox();
        panel.add(credentialList);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(230, 80, 100, 25);
        deleteButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();
            FileAccessing.deleteLogin(selected);
            success.setText("Deleted Credentials Successfully");
            refreshBox();
        });
        panel.add(deleteButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setBounds(10, 5, 140, 25);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        frame.setVisible(true);
    }

    private static void refreshBox() {
        locations = FileAccessing.readFromFile();
        credentialList.removeAllItems();

        for (String location : locations) {
            credentialList.addItem(location);
        }
    }
}
