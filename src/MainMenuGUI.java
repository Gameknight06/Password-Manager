import javax.swing.*;
import java.awt.*;

public class MainMenuGUI {
    private static JFrame frame;

    /**
     * Initializes the main menu GUI for the password manager.
     * Provides options to save, view, modify, or delete credentials, as well as exiting the program.
     * Sets up the frame, panel, and buttons for user interaction.
     */
    public static void Initialize() {

        frame = GUIUtils.createAndConfigureFrame("Main Menu", 500, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the frame

        int buttonWidth = 250;
        int buttonHeight = 70;



        JButton addButton = new JButton("Save new credentials"); // Button to add new credentials
        addButton.addActionListener(e -> {
            frame.dispose();
            AddCredentialGUI.Initialize("Please enter new credentials below:", Color.WHITE);
        });

        JButton viewButton = new JButton("View credentials"); // Button to view existing credentials
        viewButton.addActionListener(e -> {
            frame.dispose();
            ViewCredentialsGUI.Initialize("Select credentials to view:", Color.WHITE);
        });

        JButton modifyButton = new JButton("Modify existing credentials"); // Button to modify existing credentials
        modifyButton.addActionListener(e -> {
            frame.dispose();
            ModifyCredentialsGUI.Initialize("Select credentials to modify:", Color.WHITE);
        });

        JButton deleteButton = new JButton("Delete existing credentials"); // Button to delete existing credentials
        deleteButton.addActionListener(e -> {
            frame.dispose();
            DeleteCredentialsGUI.Initialize("Select credentials to delete:", Color.WHITE);
        });

        JButton exitButton = new JButton("Exit"); // Button to exit the application
        exitButton.addActionListener(e -> System.exit(0));

        addButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        viewButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        modifyButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        deleteButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        exitButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE) // Flexible space on the left
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(addButton, buttonWidth, buttonWidth, buttonWidth)
                                        .addComponent(viewButton, buttonWidth, buttonWidth, buttonWidth)
                                        .addComponent(modifyButton, buttonWidth, buttonWidth, buttonWidth)
                                        .addComponent(deleteButton, buttonWidth, buttonWidth, buttonWidth)
                                        .addComponent(exitButton, buttonWidth, buttonWidth, buttonWidth)
                                )
                                .addGap(0, 0, Short.MAX_VALUE) // Flexible space on the right
                        )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(addButton, buttonHeight, buttonHeight, buttonHeight)
                .addGap(15)
                .addComponent(viewButton, buttonHeight, buttonHeight, buttonHeight)
                .addGap(15)
                .addComponent(modifyButton, buttonHeight, buttonHeight, buttonHeight)
                .addGap(15)
                .addComponent(deleteButton, buttonHeight, buttonHeight, buttonHeight)
                .addGap(15)
                .addComponent(exitButton, buttonHeight, buttonHeight, buttonHeight)
                .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
    }
}
