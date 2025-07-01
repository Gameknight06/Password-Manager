import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AES256 {

    public static String encrypt(String stringToEncrypt, String secretKey) {
        try {
            // Generate a random 16-byte IV.
            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[16];
            random.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Create a secret key spec from our derived key string
            byte[] secretKeyBytes = Base64.getDecoder().decode(secretKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);

            byte[] ciphertext = cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8));

            // Combine IV and ciphertext
            byte[] finalCiphertext = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv, 0, finalCiphertext, 0, iv.length);
            System.arraycopy(ciphertext, 0, finalCiphertext, iv.length, ciphertext.length);

            return Base64.getEncoder().encodeToString(finalCiphertext);
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
            return null;
        }
    }

    public static String decrypt(String stringToDecrypt, String secretKey) {
        try {
            byte[] decodedCiphertext = Base64.getDecoder().decode(stringToDecrypt);

            // Extract the IV
            byte[] iv = new byte[16];
            System.arraycopy(decodedCiphertext, 0, iv, 0, iv.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Extract the actual ciphertext
            int ciphertextLength = decodedCiphertext.length - 16;
            byte[] ciphertext = new byte[ciphertextLength];
            System.arraycopy(decodedCiphertext, 16, ciphertext, 0, ciphertextLength);

            // Create a secret key spec
            byte[] secretKeyBytes = Base64.getDecoder().decode(secretKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

            byte[] original = cipher.doFinal(ciphertext);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error while decrypting (may be due to incorrect password): " + e);
            return null;
        }
    }
}
