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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        int labelWidth = 600;
        int labelHeight = 60;
        int fieldWidth = 800;
        int fieldHeight = 60;
        int buttonWidth = 400;
        int buttonHeight = 80;
        int centerX = (3440 - fieldWidth) / 2;

        // Label for where the password is used
        JLabel locationLabel = new JLabel("Enter location where the password is used: ");
        locationLabel.setBounds(centerX - 350, 400, labelWidth, labelHeight);
        panel.add(locationLabel);

        JLabel user_emailLabel = new JLabel("Enter username/email address: ");
        user_emailLabel.setBounds(centerX - 350, 500, labelWidth, labelHeight);
        panel.add(user_emailLabel);

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(centerX - 350, 600, labelWidth, labelHeight);
        panel.add(passwordLabel);


        locationField = new JTextField(20);
        locationField.setBounds(centerX + 300, 400, fieldWidth, fieldHeight);
        panel.add(locationField);
        user_emailField = new JTextField(20);
        user_emailField.setBounds(centerX + 300, 500, fieldWidth, fieldHeight);
        panel.add(user_emailField);
        passwordField = new JTextField(20);
        passwordField.setBounds(centerX + 300, 600, fieldWidth, fieldHeight);
        panel.add(passwordField);

        JButton encryptButton = new JButton("Save");
        encryptButton.setBounds((3440 - buttonWidth) / 2, 800, buttonWidth, buttonHeight);
        encryptButton.addActionListener(this);
        panel.add(encryptButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setBounds(50, 50, buttonWidth, buttonHeight);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        success = new JLabel("");
        success.setBounds(centerX, 200, 1200, 60);
        panel.add(success);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> locations = FileAccessing.readLocationsFromFile();
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