import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
TO-DO:
Add ability to add more than one set of credentials per location
Add ability to delete specific credentials for a location based on username/email

 */

public class Main {

    public static void main(String[] args) {

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


        new MainMenuGUI();

    }
}
