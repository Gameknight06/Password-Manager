import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
TO-DO:
Add ability to add more than one set of credentials per location
General UI revision

 */

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);

        MainMenuGUI mainMenu = new MainMenuGUI();

    }
}
