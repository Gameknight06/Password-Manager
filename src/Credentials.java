public class Credentials {
    private String passwordLocation;
    private String username;
    private String password;

    public Credentials() {}

    public Credentials(String passwordLocation, String username, String password) {
        this.passwordLocation = passwordLocation;
        this.username = username;
        this.password = password;
    }

    public String getPasswordLocation() {
        return passwordLocation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
