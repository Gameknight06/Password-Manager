import javax.swing.*;
import java.util.List;

public class DeleteCredentialsGUI {

    private JLabel success;
    private static JComboBox<String> credentialList = new JComboBox<>();

    public DeleteCredentialsGUI() {

        JFrame frame = new JFrame("Delete Credentials");
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

        credentialList.setBounds(centerX - 50, 140, fieldWidth, fieldHeight);
        refreshBox();
        panel.add(credentialList);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(centerX + 300, 140, buttonWidth, buttonHeight);
        deleteButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();

            assert selected != null;
            if(selected.equals("Select location...")) {
                success.setText("Please select a valid location");
                return;
            }
            FileAccessing.deleteLogin(selected);
            success.setText("Deleted Credentials Successfully");
            refreshBox();
        });
        panel.add(deleteButton);

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

    private static void refreshBox() {
        List<String> locations = FileAccessing.readLocationsFromFile();
        credentialList.removeAllItems();
        locations.add(0, "Select location...");

        for (String location : locations) {
            credentialList.addItem(location);
        }
    }
}