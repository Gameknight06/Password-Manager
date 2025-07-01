import javax.swing.*;
import java.awt.*;

public class ModifyCredentialsGUI {

    private static JLabel successOrError;
    private static JComboBox<String> credentialList;
    private static JComboBox<String> userEmailList;
    private static JFrame frame;

    /**
     * Initializes the Modify Credentials GUI.
     * Sets up the frame, panel, and components for modifying credentials.
     */
    public static void Initialize(String message, Color color) {

        frame = GUIUtils.createAndConfigureFrame("Modify Credentials",700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int boxWidth = 250;
        int boxHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int labelWidth = 300;
        int labelHeight = 50;

        successOrError = new JLabel(message, SwingConstants.CENTER);
        successOrError.setForeground(color);

        credentialList = new JComboBox<>();
        GUIUtils.refreshLocations(credentialList); // Refresh the list of locations

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> editButton()); // Call the method to handle editing
        JButton menuButton = GUIUtils.addMenuButton(frame);


        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        credentialList.setPreferredSize(new java.awt.Dimension(boxWidth, boxHeight));
        editButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        menuButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(menuButton, buttonWidth, buttonWidth, buttonWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(successOrError, labelWidth, labelWidth, labelWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(credentialList, boxWidth, boxWidth, boxWidth)
                                .addGap(20)
                                .addComponent(editButton, buttonWidth, buttonWidth, buttonWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )

        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(menuButton, buttonHeight, buttonHeight, buttonHeight)
                        .addGap(20)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(successOrError, labelHeight, labelHeight, labelHeight)
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(credentialList, boxHeight, boxHeight, boxHeight)
                            .addComponent(editButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGap(200)
        );

        frame.setVisible(true);
    }

    /**
     * Handles the UI of modifying duplicate credentials for a single location.
     * It displays a message indicating that there are multiple entries for the selected location,
     * allows the user to select a specific username/email from a dropdown,
     * and provides options to edit the selected credentials or go back to the modify credentials GUI.
     */
    private static void duplicateLocation(String location) {
        JFrame frame = GUIUtils.createAndConfigureFrame("Duplicate Location",500, 400);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0); // Get the panel from the frame

        int boxWidth = 250;
        int boxHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int backButtonWidth = 110;
        int backButtonHeight = 40;
        int labelWidth = 300;
        int labelHeight = 50;

        JLabel messageLabel = new JLabel("There is more than one entry for " + location);
        successOrError = new JLabel();
        userEmailList = new JComboBox<>();
        JButton editButton = new JButton("Edit");
        JButton backButton = new JButton("Back");

        GUIUtils.refreshUsernames(userEmailList, location); // Refresh usernames based on the selected location
        successOrError.setForeground(Color.RED);

        editButton.addActionListener(e -> {
            String selected = (String) userEmailList.getSelectedItem();
            if (selected == null) {
                successOrError.setText("No credentials available");
                return;
            } else if(selected.equals("Select Username/Email...")) { // Check for default selection
                successOrError.setText("Please select a valid location");
                return;
            }
            frame.dispose();
            ModifyCredentialsSingleGUI.Initialize(location, selected, successOrError);
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            Initialize("Select credentials to modify:", Color.WHITE);
        });

        messageLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        userEmailList.setPreferredSize(new java.awt.Dimension(boxWidth, boxHeight));
        editButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
        backButton.setPreferredSize(new java.awt.Dimension(backButtonWidth, backButtonHeight));

        GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backButton, backButtonWidth, backButtonWidth, backButtonWidth)
                                .addGap(0, 0, Short.MAX_VALUE)
                        )
                        .addComponent(messageLabel)
                        .addComponent(successOrError)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE) // Push to center
                                .addComponent(userEmailList, boxWidth, boxWidth, boxWidth)
                                .addGap(15) // Space between combo box and buttons
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(editButton, buttonWidth, buttonWidth, buttonWidth)
                                )
                                .addGap(0, 0, Short.MAX_VALUE) // Push to center
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(backButton, backButtonHeight, backButtonHeight, backButtonHeight)
                        .addGap(45)
                        .addComponent(messageLabel)
                        .addGap(20)
                        .addComponent(successOrError)
                        .addGap(5)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(userEmailList, boxHeight, boxHeight, boxHeight)
                                .addComponent(editButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
    }

    /**
     * Handles the action when the "Edit" button is clicked.
     * It determines if there are duplicate credentials for the selected location and navigates to the appropriate GUI.
     */
    private static void editButton() {
        String selected = (String) credentialList.getSelectedItem();
        String username = VaultManager.findUsername(selected);
        assert selected != null;
        if(selected.equals("Select Location...")) { // Check for default selection
            successOrError.setForeground(Color.RED);
            successOrError.setText("Please select a valid location");
            return;
        }

        if (GUIUtils.hasDuplicates(selected)) {
            frame.dispose();
            duplicateLocation(selected);
        } else {
            frame.dispose();
            ModifyCredentialsSingleGUI.Initialize(selected, username, successOrError); // Pass the successOrError label to the single modification GUI
        }
    }
}