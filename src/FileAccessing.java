import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileAccessing {
    // Loading in the secret key and salt from the file that stores them.
    private static String secretKey = readFile(new File("C:\\Users\\Destr\\Documents\\PasswordKeySalt\\Key.txt"));
    private static String salt = readFile(new File("C:\\Users\\Destr\\Documents\\PasswordKeySalt\\Salt.txt"));
    private static File passwordFile = new File("C:\\Users\\Destr\\Documents\\Passwords\\EncryptedPasswords.txt");


    public static String readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);

            return scanner.nextLine();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }
    }

    public static void saveLogin(String password, String passwordLocation, String username) {
        try {
            String encryptedPassword = AES256.encrypt(password, secretKey, salt);

            FileWriter writer = new FileWriter(passwordFile, true); // Get the passwords file
            writer.write("Login location: " + passwordLocation + "\nUsername/Email: " + username + "\nPassword: " + encryptedPassword + "\n"); // Save the credentials in a formatted style
            writer.close();
            System.out.println("Password saved");
        } catch (IOException e) {
            System.out.println("Error while saving password");
            e.printStackTrace();
        }
    }

    public static List<String> readFromFile() {
        List<String> locations = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(passwordFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Login location: ")) {
                    String location = line.substring("Login location: ".length()).trim();
                    locations.add(location);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return locations;
    }

    public static void deleteLogin(String locationToDelete) {
        List<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(passwordFile);

            while (scanner.hasNextLine()) {
                String line1 = scanner.nextLine(); // Login location
                String line2 = scanner.hasNextLine() ? scanner.nextLine() : ""; // Username/Email
                String line3 = scanner.hasNextLine() ? scanner.nextLine() : ""; // Password

                if (!line1.trim().equalsIgnoreCase("Login location: " + locationToDelete.trim())) {
                    lines.add(line1);
                    lines.add(line2);
                    lines.add(line3);
                }
            }
            scanner.close();

            FileWriter writer = new FileWriter(passwordFile, false); // Overwriting the file
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            writer.close();
            System.out.println("Entry deleted successfully");
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public static void modifyLogin(String locationToModify, String password, String username) {
        List<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(passwordFile);

            while (scanner.hasNextLine()) {
                String line1 = scanner.nextLine(); // Login location
                String line2 = scanner.hasNextLine() ? scanner.nextLine() : ""; // Username/Email
                String line3 = scanner.hasNextLine() ? scanner.nextLine() : ""; // Password

                if (!line1.trim().equalsIgnoreCase("Login location: " + locationToModify.trim())) {
                    String encryptedPassword = AES256.encrypt(password, secretKey, salt);

                    lines.add(line1);
                    lines.add(line2);
                    lines.add("Password: " + encryptedPassword + "\n");
                } else {
                    lines.add(line1);
                    lines.add(line2);
                    lines.add(line3);
                }
            }
            scanner.close();

            FileWriter writer = new FileWriter(passwordFile, false); // Overwriting the file
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            writer.close();
            System.out.println("Password modified successfully");
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public static String findPassword(String locationToFind) {
        String password = "";

        try {
            Scanner scanner = new Scanner(passwordFile);

            while (scanner.hasNextLine()) {
                String line1 = scanner.nextLine(); // Login location

                if (line1.trim().equalsIgnoreCase("Login location: " + locationToFind.trim())) {
                    String line2 = scanner.hasNextLine() ? scanner.nextLine() : ""; // Username/Email
                    String line3 = scanner.hasNextLine() ? scanner.nextLine() : ""; // Password

                    if (line3.startsWith("Password: ")) {
                        scanner.close();
                        password = line3.substring("Password: ".length()).trim();
                        return AES256.decrypt(password, secretKey, salt);
                    }
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return null;
    }

    public static String findUsername(String locationToFind) {

        try {
            Scanner scanner = new Scanner(passwordFile);

            while (scanner.hasNextLine()) {
                String line1 = scanner.nextLine(); // Login location

                if (line1.trim().equalsIgnoreCase("Login location: " + locationToFind.trim())) {
                    String line2 = scanner.hasNextLine() ? scanner.nextLine() : ""; // Username/Email
                    String line3 = scanner.hasNextLine() ? scanner.nextLine() : ""; //Password

                    if (line2.startsWith("Username/Email: ")) {
                        scanner.close();
                        return line2.substring("Username/Email: ".length()).trim();
                    }
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return null;
    }
}
