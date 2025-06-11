import javax.swing.*;

public class ModifyCredentialsGUI {

    private JLabel success;

    public ModifyCredentialsGUI() {

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

        JComboBox<String> credentialList = new JComboBox<>();
        credentialList.setBounds(10, 80, 200, 25);
        panel.add(credentialList);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(230, 80, 100, 25);
        deleteButton.addActionListener(e -> {
            String selected = (String) credentialList.getSelectedItem();

            success.setText("Modified Credentials Successfully");
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
}
