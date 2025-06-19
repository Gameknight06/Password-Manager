import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private static final File APP_CONFIG_DIR = new File(System.getProperty("user.home"), ".password_manager");
    private static final File CONFIG_FILE = new File(APP_CONFIG_DIR, "pm_config.properties");
    private static final String VAULT_PATH_KEY = "vaultPath";

    /**
     * Initializes the configuration directory and file.
     * This method ensures that the configuration directory exists and creates it if it does not.
     */
    public static void saveVaultPath(String vaultPath) {

        if (!APP_CONFIG_DIR.exists()) {
            if (!APP_CONFIG_DIR.mkdirs()) {
                System.err.println("Failed to create configuration directory.");
                return;
            }
        }

        Properties prop = new Properties();
        prop.setProperty(VAULT_PATH_KEY, vaultPath);

        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            prop.store(output, "Password Manager Configuration");
            System.out.println("Configuration saved successfully to: " + CONFIG_FILE.getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("Error saving configuration: " + ex.getMessage());
        }
    }

    /**
     * Loads the vault path from the configuration file.
     * This method reads the configuration file and retrieves the vault path.
     */
    public static String loadVaultPath() {
        Properties prop = new Properties();

        if (!CONFIG_FILE.exists()) {
            return null; // Configuration file does not exist
        }

        try (InputStream input = new java.io.FileInputStream(CONFIG_FILE)) { // Open the configuration file
            prop.load(input); // Load properties from the file
            return prop.getProperty(VAULT_PATH_KEY); // Return the vault path
        } catch (IOException ex) {
            System.err.println("Error loading configuration: " + ex.getMessage());
            return null;
        }
    }
}
