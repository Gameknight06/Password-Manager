import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
TO-DO:

 */

public class Main {

    /**
     * Main method to start the Password Manager application.
     * It initializes the vault path, checks if the vault exists,
     * and either opens the login GUI or the setup GUI depending on whether the vault file exists or not.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String vaultPath = ConfigManager.loadVaultPath();

            if (vaultPath != null) {
                VaultManager.setVaultFile(vaultPath);

                if (VaultManager.vaultExists()) {
                    LoginGUI.login();
                } else {
                    SetupGUI.Initialize();
                }
            } else {
                SetupGUI.Initialize();
            }
        });

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        UIManager.put("TextComponent.arc", 25);
        UIManager.put("Button.arc", 10);
        UIManager.put("TitlePane.unifiedBackground", false);
        UIManager.put("TitlePane.background", new Color(30, 144, 255));
        UIManager.put("TitlePane.foreground", Color.WHITE);

    }
}
