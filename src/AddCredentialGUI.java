import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddCredentialGUI implements ActionListener {

    private JTextField passwordField;
    private JTextField user_emailField;
    private JTextField locationField;
    private JLabel success;

    public AddCredentialGUI() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        // Label for where the password is used
        JLabel locationLabel = new JLabel("Enter location where the password is used: ");
        locationLabel.setBounds(10, 40, 250, 25);
        panel.add(locationLabel);

        JLabel user_emailLabel = new JLabel("Enter username/email address: ");
        user_emailLabel.setBounds(10, 70, 250, 25);
        panel.add(user_emailLabel);

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(10, 100, 250, 25);
        panel.add(passwordLabel);


        locationField = new JTextField(20);
        locationField.setBounds(280, 40, 250, 25);
        panel.add(locationField);
        user_emailField = new JTextField(20);
        user_emailField.setBounds(280, 70, 250, 25);
        panel.add(user_emailField);
        passwordField = new JTextField(20);
        passwordField.setBounds(280, 100, 250, 25);
        panel.add(passwordField);

        JButton encryptButton = new JButton("Save");
        encryptButton.setBounds(600, 70, 80, 25);
        encryptButton.addActionListener(this);
        panel.add(encryptButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setBounds(10, 5, 140, 25);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        success = new JLabel("");
        success.setBounds(280, 10, 320, 25);
        panel.add(success);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> locations = FileAccessing.readFromFile();
        String password = passwordField.getText();
        String user_email = user_emailField.getText();
        String location = locationField.getText();

        if (!password.isEmpty() && !locations.contains(location)) {
            FileAccessing.saveLogin(password, location, user_email);
            success.setText("Successfully Encrypted and saved!");
            clearFields();
        } else if (locations.contains(location)) {
            success.setText("Login credentials for this location already exists!");
        } else {
            success.setText("Password field is empty!");
        }
    }

    private void clearFields() {
        passwordField.setText("");
        user_emailField.setText("");
        locationField.setText("");
        success.setText("Enter new credentials:");
    }
}
