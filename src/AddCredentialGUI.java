import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class AddCredentialGUI implements ActionListener {

    private JTextField passwordField;
    private JTextField user_emailField;
    private JTextField locationField;
    private JLabel success;

    public AddCredentialGUI() {

        JFrame frame = new JFrame("Add New Credentials");
        JPanel panel = new JPanel();
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setFocusable(true);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        panel.setLayout(null);
        frame.add(panel);


        int fieldWidth = 300;
        int fieldHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 40;
        int centerX = (700 - fieldWidth) / 2;


        locationField = new JTextField("Location", 20);
        locationField.setForeground(Color.GRAY);
        locationField.setBounds(centerX, 100, fieldWidth, fieldHeight);
        locationField.setToolTipText("Enter the location where the password is used");
        locationField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (locationField.getText().equals("Location")) {
                    locationField.setText("");
                    locationField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (locationField.getText().isEmpty()) {
                    locationField.setForeground(Color.GRAY);
                    locationField.setText("Location");
                }
            }
        });
        panel.add(locationField);

        user_emailField = new JTextField("Username/Email", 20);
        user_emailField.setBounds(centerX, 170, fieldWidth, fieldHeight);
        user_emailField.setForeground(Color.GRAY);
        user_emailField.setToolTipText("Enter the username or email address associated with the password");
        user_emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (user_emailField.getText().equals("Username/Email")) {
                    user_emailField.setText("");
                    user_emailField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (user_emailField.getText().isEmpty()) {
                    user_emailField.setForeground(Color.GRAY);
                    user_emailField.setText("Username/Email");
                }
            }
        });
        panel.add(user_emailField);

        passwordField = new JTextField("Password", 20);
        passwordField.setForeground(Color.GRAY);
        passwordField.setBounds(centerX, 240, fieldWidth, fieldHeight);
        passwordField.setToolTipText("Enter the password for the specified location and username/email");
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("Password");
                }
            }
        });
        panel.add(passwordField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(centerX + 70, 340, buttonWidth, buttonHeight);
        saveButton.addActionListener(this);
        panel.add(saveButton);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.putClientProperty("JButton.buttonType", "square");
        menuButton.setBounds(10, 10, buttonWidth, buttonHeight);
        menuButton.addActionListener(e -> {
            frame.dispose();
            new MainMenuGUI();
        });
        panel.add(menuButton);

        success = new JLabel("");
        success.setBounds(centerX + 20, 50, 1200, 60);
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