import javax.swing.*;

public class MainMenuGUI {
    private static JFrame frame;

    /**
     * Initializes the main menu GUI for the password manager.
     * Provides options to save, view, modify, or delete credentials, as well as exiting the program.
     * Sets up the frame, panel, and buttons for user interaction.
     */
    public static void Initialize() {

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
        int centerX = (frame.getWidth() - 250) / 2;
        int startY = 20;
        int gap = 80;

        JButton addButton = new JButton("Save new credentials"); // Button to add new credentials
        addButton.setBounds(centerX, startY, buttonWidth, buttonHeight);
        addButton.addActionListener(e -> {
            frame.dispose();
            AddCredentialGUI.Initialize();
        });
        panel.add(addButton);

        JButton viewButton = new JButton("View credentials"); // Button to view existing credentials
        viewButton.setBounds(centerX, startY + gap, buttonWidth, buttonHeight);
        viewButton.addActionListener(e -> {
            frame.dispose();
            ViewCredentialsGUI.Initialize();
        });
        panel.add(viewButton);

        JButton modifyButton = new JButton("Modify existing credentials"); // Button to modify existing credentials
        modifyButton.setBounds(centerX, startY + 2 * gap, buttonWidth, buttonHeight);
        modifyButton.addActionListener(e -> {
            frame.dispose();
            ModifyCredentialsGUI.Initialize();
        });
        panel.add(modifyButton);

        JButton deleteButton = new JButton("Delete existing credentials"); // Button to delete existing credentials
        deleteButton.setBounds(centerX, startY + 3 * gap, buttonWidth, buttonHeight);
        deleteButton.addActionListener(e -> {
            frame.dispose();
            DeleteCredentialsGUI.Initialize();
        });
        panel.add(deleteButton);

        JButton exitButton = new JButton("Exit"); // Button to exit the application
        exitButton.setBounds(centerX, startY + 4 * gap, buttonWidth, buttonHeight);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        frame.setVisible(true);
    }
}
