import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static String secretKey = readFile(new File("C:\\Users\\Destr\\Documents\\PasswordKeySalt\\Key.txt"));
    private static String salt = readFile(new File("C:\\Users\\Destr\\Documents\\PasswordKeySalt\\Salt.txt"));
    private File encryptedPasswordsFile = new File("C:\\Users\\Destr\\Documents\\Passwords");

    public static void main(String[] args) throws IOException {

        MainMenuGUI mainMenu = new MainMenuGUI();

        File keyFile = new File("C:\\Users\\Destr\\Documents\\PasswordKeySalt\\Key.txt");
        File saltFile = new File("C:\\Users\\Destr\\Documents\\PasswordKeySalt\\Salt.txt");

        String secretKey = readFile(keyFile);
        String salt = readFile(saltFile);

        String password = "password";
        String passwordLocation = "Default";


    }

    public static String readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);

            return scanner.nextLine();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }
    }

    public static void saveLogIn(String password, String passwordLocation, String username) {
        try {
            String encryptedPassword = AES256.encrypt(password, secretKey, salt);

            FileWriter writer = new FileWriter("C:\\Users\\Destr\\Documents\\Passwords\\EncryptedPasswords.txt", true);
            writer.write("Login location: " + passwordLocation + "\nUsername/Email: " + username + "\nPassword: " + encryptedPassword + "\n");
            writer.close();
            System.out.println("Password saved");
        } catch (IOException e) {
            System.out.println("Error while saving password");
            e.printStackTrace();
        }
    }
}
