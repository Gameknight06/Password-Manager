import java.util.ArrayList;
import java.util.List;

public class PasswordVault {
    private String kdfSalt;

    private String verificationHash;

    private List<Credentials> credentials;

    /**
     * Default constructor initializes the credentials list.
     */
    public PasswordVault() {
        this.credentials = new ArrayList<>();
    }

    public String getKdfSalt() { return kdfSalt; }
    public String getVerificationHash() { return verificationHash; }
    public List<Credentials> getCredentials() { return credentials; }

    public void setKdfSalt(String kdfSalt) { this.kdfSalt = kdfSalt; }
    public void setVerificationHash(String verificationHash) { this.verificationHash = verificationHash; }
    public void setCredentials(List<Credentials> credentials) { this.credentials = credentials; }
}
