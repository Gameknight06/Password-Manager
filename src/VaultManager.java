import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class VaultManager {

    private static File vaultFile;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Initializing Gson for pretty printing
    private static final String VERIFICATION_CANARY = "VAULT_CORRECTLY_DECRYPTED";

    /**
     * Sets the vault file path.
     */
    public static void setVaultFile(String path) {
        if (path != null && !path.isEmpty()) {
            vaultFile = new File(path);
        }
    }

    /**
     * Checks if the vault file exists.
     * @return true if the vault file exists, false otherwise.
     */
    public static boolean vaultExists() {
        return vaultFile != null && vaultFile.exists();
    }

    /**
     * Creates a new vault with the provided master password.
     * It generates a KDF salt, derives a key, and saves the vault with an initial verification hash.
     */
    public static void createNewVault(char[] masterPassword) {
        if (vaultFile == null) {
            System.err.println("Vault file path is not set.");
            return;
        }

        File parentDir = vaultFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        byte[] kdfSaltBytes = KeyDerivation.generateSalt();
        String kdfSaltString = Base64.getEncoder().encodeToString(kdfSaltBytes);
        String derivedKey = KeyDerivation.deriveKey(masterPassword, kdfSaltBytes);
        String verificationHash = AES256.encrypt(VERIFICATION_CANARY, derivedKey);

        PasswordVault vault = new PasswordVault();
        vault.setKdfSalt(kdfSaltString);
        vault.setVerificationHash(verificationHash);

        saveVault(vault);
        System.out.println("Vault created successfully.");
    }

    /**
     * Unlocks the vault using the provided master password.
     * It derives a key from the password and checks it against the stored verification hash.
     * If successful, it sets the active session key.
     */
    public static boolean unlockVault(char[] masterPassword) {

        PasswordVault vault = loadVault();
        if (vault == null || vault.getKdfSalt() == null) {
            System.err.println("Vault file is missing or corrupted.");
            return false;
        }

        byte[] kdfSalt = Base64.getDecoder().decode(vault.getKdfSalt()); // decode the salt from the vault
        String testKey = KeyDerivation.deriveKey(masterPassword, kdfSalt); // test key to check if the password is correct
        String decryptedCanary = AES256.decrypt(vault.getVerificationHash(), testKey); // decrypt the verification hash with the derived key

        if (VERIFICATION_CANARY.equals(decryptedCanary)) {
            SessionManager.setActiveKey(testKey); // Set the active key for the session
            return true;
        }
        return false;
    }

    /**
     * Writes the vault to the specified file.
     * Locks the vault by clearing the active session key.
     */
    public static void saveVault(PasswordVault vault) {
        if (vaultFile == null) {
            System.err.println("Vault file path is not set.");
            return;
        }
        try (FileWriter writer = new FileWriter(vaultFile)) {
            gson.toJson(vault, writer); // Serialize the vault object to JSON and write it to the file
        } catch (IOException ex) {
            System.out.println("Error saving vault: " + ex.getMessage());
        }
    }

    /**
     * Loads the vault from the specified file.
     * If the file does not exist, it returns null.
     */
    public static PasswordVault loadVault() {
        if (vaultFile == null || !vaultFile.exists()) { // Check if the vault file is set and exists
            System.out.println("Vault file does not exist.");
            return null;
        }
        try (FileReader reader = new FileReader(vaultFile)) {
            return gson.fromJson(reader, PasswordVault.class); // Deserialize the JSON file back into a PasswordVault object
        } catch (IOException ex) {
            System.out.println("Error loading vault: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Saves a new login credential to the vault.
     * It encrypts the password using the active session key and adds it to the vault.
     * If the session is not active or the vault is not loaded, it prints an error message.
     */
    public static void saveLogin(String password, String passwordLocation, String username) {
            if (!SessionManager.isSessionActive()) { // Check if the session is active
                System.out.println("Session is not active. Please unlock the vault first.");
                return;
            }
            PasswordVault vault = loadVault();
            if (vault == null || vault.getCredentials() == null) { // Check if the vault is loaded and has credentials
                System.out.println("Vault is not loaded.");
                return;
            }
            String encryptedPassword = AES256.encrypt(password, SessionManager.getActiveKey()); // Encrypt the password using the active session key
            vault.getCredentials().add(new Credentials(passwordLocation, username, encryptedPassword)); // Add the new credentials to the vault
            saveVault(vault); // Save the updated vault
            System.out.println("Credentials saved successfully");
    }

    /**
     * Deletes a login credential from the vault based on the specified location and username.
     * If the session is not active or the vault is not loaded, it prints an error message.
     */
    public static void deleteLogin(String locationToDelete, String usernameToDelete) {
        if (!SessionManager.isSessionActive()) { // Check if the session is active
            System.out.println("Session is not active. Please unlock the vault first.");
            return;
        }
        PasswordVault vault = loadVault();
        if (vault == null || vault.getCredentials() == null) {
            System.out.println("Vault is not loaded.");
            return;
        }

        boolean removed = vault.getCredentials().removeIf(cred -> cred.getPasswordLocation().equalsIgnoreCase(locationToDelete) && cred.getUsername().equalsIgnoreCase(usernameToDelete)); // Remove the credentials matching the specified location and username
        if (removed) {
            saveVault(vault);
            System.out.println("Credentials deleted successfully");
        } else {
            System.out.println("No credentials found for the specified location.");
        }
    }

    /**
     * Reads all password locations from the vault.
     */
    public static List<String> readLocationsFromFile() {
        PasswordVault vault = loadVault();
        if (vault == null || vault.getCredentials() == null) {
            System.out.println("Vault is not loaded.");
            return Collections.emptyList();
        }
        return vault.getCredentials().stream()
                .map(Credentials::getPasswordLocation)
                .collect(Collectors.toList());
    }

    /**
     * Modifies an existing login credential in the vault.
     * It updates the username and/or password for the specified location and username.
     * If the session is not active or the vault is not loaded, it prints an error message.
     */
    public static void modifyLogin(String locationToModify, String password, String usernameToModify, String username) {
        if (!SessionManager.isSessionActive()) {
            System.out.println("Session is not active. Please unlock the vault first.");
            return;
        }

        PasswordVault vault = loadVault();
        if (vault == null || vault.getCredentials() == null) {
            System.out.println("Vault is not loaded.");
            return;
        }

        Optional<Credentials> credentialToModify = vault.getCredentials().stream()
                .filter(cred -> cred.getPasswordLocation().equalsIgnoreCase(locationToModify))
                .filter(cred -> cred.getUsername().equalsIgnoreCase(usernameToModify))
                .findFirst();

        if (credentialToModify.isPresent()) {
            Credentials cred = credentialToModify.get();
            cred.setUsername(username);

            String encryptedPassword = AES256.encrypt(password, SessionManager.getActiveKey());
            cred.setPassword(encryptedPassword);

            saveVault(vault);
            System.out.println("Credentials modified successfully");
        } else {
            System.out.println("No credentials found for the specified location.");
        }
    }

    /**
     * Finds the password for a specific location and username.
     */
    public static String findPassword(String locationToFind, String username) {
        PasswordVault vault = loadVault();
        if (vault == null || vault.getCredentials() == null || !SessionManager.isSessionActive()) {
            System.out.println("Vault is not loaded.");
            return null;
        }

        Optional<String> encryptedPasswordOpt = vault.getCredentials().stream()
                .filter(cred -> cred.getPasswordLocation().equalsIgnoreCase(locationToFind))
                .filter(cred -> cred.getUsername().equalsIgnoreCase(username))
                .map(Credentials::getPassword)
                .findFirst();

        if (encryptedPasswordOpt.isPresent()) {
            return AES256.decrypt(encryptedPasswordOpt.get(), SessionManager.getActiveKey());
        } else {
            System.out.println("No credentials found for the specified location and username.");
            return null;
        }
    }

    /**
     * Finds the username for a specific location.
     */
    public static String findUsername(String locationToFind) {
        PasswordVault vault = loadVault();
        if (vault == null || vault.getCredentials() == null) {
            System.out.println("Vault is not loaded.");
            return null;
        }

        return vault.getCredentials().stream()
                .filter(cred -> cred.getPasswordLocation().equalsIgnoreCase(locationToFind))
                .map(Credentials::getUsername)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a list of usernames for a specific location.
     * It filters the credentials based on the provided location and returns the usernames.
     */
    public static List<String> getUsernames(String location) {
        PasswordVault vault = loadVault();
        if (vault == null || vault.getCredentials() == null) {
            System.out.println("Vault is not loaded.");
            return Collections.emptyList();
        }
        return vault.getCredentials().stream()
                .filter(cred -> cred.getPasswordLocation().equalsIgnoreCase(location))
                .map(Credentials::getUsername)
                .collect(Collectors.toList());
    }
}
