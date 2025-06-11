import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI implements ActionListener {

    private JFrame frame;

    public MainMenuGUI() {

        frame = new JFrame("Password Manager");
        JPanel panel = new JPanel();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        JButton addButton = new JButton("Save new credentials");
        addButton.setBounds(100, 20, 200, 25);
        addButton.addActionListener(e -> {
            frame.dispose();
            new AddCredentialGUI();
        });
        panel.add(addButton);

        JButton modifyButton = new JButton("Modify existing credentials");
        modifyButton.setBounds(100, 60, 200, 25);
        modifyButton.addActionListener(e -> {
            frame.dispose();
            new ModifyCredentialsGUI();
        });
        panel.add(modifyButton);

        JButton deleteButton = new JButton("Delete existing credentials");
        deleteButton.setBounds(100, 100, 200, 25);
        deleteButton.addActionListener(e -> {
            frame.dispose();
            new DeleteCredentialsGUI();
        });
        panel.add(deleteButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
