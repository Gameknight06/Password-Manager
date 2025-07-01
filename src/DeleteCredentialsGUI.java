import javax.swing.*;
import java.awt.*;

public class DeleteCredentialsGUI {

    private static JLabel successOrError;
    private static final JComboBox<String> credentialList = new JComboBox<>();
    private static JComboBox<String> userEmailList = new JComboBox<>();
    private static String location;
    private static JFrame frame;

    /**
     * Initializes the Delete Credentials GUI.
     * Sets up the frame, panel, and components for deleting credentials.
     */
    public static void Initialize(String message, Color color) {

        frame = GUIUtils.createAndConfigureFrame("Delete Credentials", 700, 500);
        JPanel panel = (JPanel) frame.getContentPane().getComponent(0);

        int boxWidth = 250;
        int boxHeight = 50;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int labelWidth = 300;
        int labelHeight = 50;

        successOrError = new JLabel(message, SwingConstants.CENTER);
        successOrError.setForeground(color);

        GUIUtils.refreshLocations(credentialList); // Refresh the list of locations

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteButton()); // Call the method to handle editing

        JButton menuButton = GUIUtils.addMenuButton(frame);

        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        credentialList.setPreferredSize(new java.awt.Dimension(boxWidth, boxHeight));
        deleteButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
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
                                .addComponent(deleteButton, buttonWidth, buttonWidth, buttonWidth)
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
                                .addComponent(deleteButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGap(200)
        );

        frame.setVisible(true);
    }

    /**
     * Handles the UI of deleting duplicate credentials for a single location.
     * It displays a message indicating that there are multiple entries for the selected location,
     * allows the user to select a specific username/email from a dropdown.
     * It provides options to delete the selected credentials or go back to the delete credentials GUI.
     */
    private static void duplicateLocation(String location) {
        DeleteCredentialsGUI.location = location;

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
        JButton backButton = new JButton("Back");
        JButton deleteButton = new JButton("Delete");


        GUIUtils.refreshUsernames(userEmailList, location); // Refresh usernames based on the selected location
        successOrError.setForeground(Color.RED);

        deleteButton.addActionListener(e -> deleteButtonDup());

        backButton.addActionListener(e -> {
            frame.dispose();
            Initialize("Select credentials to delete:", Color.WHITE);
        });

        messageLabel.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        successOrError.setPreferredSize(new java.awt.Dimension(labelWidth, labelHeight));
        userEmailList.setPreferredSize(new java.awt.Dimension(boxWidth, boxHeight));
        deleteButton.setPreferredSize(new java.awt.Dimension(buttonWidth, buttonHeight));
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
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(userEmailList, boxWidth, boxWidth, boxWidth)
                                .addGap(15)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(deleteButton, buttonWidth, buttonWidth, buttonWidth)
                                )
                                .addGap(0, 0, Short.MAX_VALUE)
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
                                .addComponent(deleteButton, buttonHeight, buttonHeight, buttonHeight)
                        )
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        frame.setVisible(true);
    }

    /**
     * Handles the action of deleting duplicate credentials for a single location.
     * It deletes the selected credentials by username/email and returns to the delete credentials GUI.
     */
    private static void deleteButtonDup() {
        String selected = (String) userEmailList.getSelectedItem();
        if (selected == null) {
            successOrError.setForeground(Color.RED);
            successOrError.setText("No credentials available");
            return;
        } else if(selected.equals("Select Username/Email...")) {
            successOrError.setForeground(Color.RED);
            successOrError.setText("Please select a valid location");
            return;
        }
        if (areYouSure()) {
            VaultManager.deleteLogin(location, selected);
            frame.dispose();
            Initialize("Deleted Credentials Successfully", Color.GREEN);
        }
    }

    /**
     * Handles the action of deleting credentials.
     * If there are duplicates, it calls the duplicateLocation method.
     * Otherwise, it deletes the selected credentials and refreshes the list.
     */
    private static void deleteButton() {
        String selected = (String) credentialList.getSelectedItem();
        String username = VaultManager.findUsername(selected);
        assert selected != null;
        if(selected.equals("Select Location...")) {
            successOrError.setForeground(Color.RED);
            successOrError.setText("Please select a valid location");
            return;
        }

        if (GUIUtils.hasDuplicates(selected)) {
            frame.dispose();
            duplicateLocation(selected);
        } else if(areYouSure()) {
            VaultManager.deleteLogin(selected, username);
            successOrError.setForeground(Color.GREEN);
            successOrError.setText("Deleted Credentials Successfully");
            GUIUtils.refreshLocations(credentialList);
        }
    }

    private static boolean areYouSure() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete these credentials?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }
}