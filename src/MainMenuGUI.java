import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI implements ActionListener {

    private JFrame frame;

    public MainMenuGUI() {

        frame = new JFrame("Password Manager");
        JPanel panel = new JPanel();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);
        panel.setFocusable(true);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        frame.add(panel);

        int buttonWidth = 250;
        int buttonHeight = 70;
        int centerX = (500 - buttonWidth) / 2;
        int startY = 20;
        int gap = 80;

        JButton addButton = new JButton("Save new credentials");
        addButton.setBounds(centerX, startY, buttonWidth, buttonHeight);
        addButton.addActionListener(e -> {
            frame.dispose();
            new AddCredentialGUI();
        });
        panel.add(addButton);

        JButton viewButton = new JButton("View credentials");
        viewButton.setBounds(centerX, startY + gap, buttonWidth, buttonHeight);
        viewButton.addActionListener(e -> {
            frame.dispose();
            new ViewCredentialsGUI();
        });
        panel.add(viewButton);

        JButton modifyButton = new JButton("Modify existing credentials");
        modifyButton.setBounds(centerX, startY + 2 * gap, buttonWidth, buttonHeight);
        modifyButton.addActionListener(e -> {
            frame.dispose();
            new ModifyCredentialsGUI();
        });
        panel.add(modifyButton);

        JButton deleteButton = new JButton("Delete existing credentials");
        deleteButton.setBounds(centerX, startY + 3 * gap, buttonWidth, buttonHeight);
        deleteButton.addActionListener(e -> {
            frame.dispose();
            new DeleteCredentialsGUI();
        });
        panel.add(deleteButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(centerX, startY + 4 * gap, buttonWidth, buttonHeight);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
