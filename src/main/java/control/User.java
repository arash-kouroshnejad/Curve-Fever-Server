package control;

public class User {
    private final String USERNAME;
    private final byte[] PassHash;

    public User(String USERNAME, byte[] PASSHASH) {
        this.USERNAME = USERNAME;
        this.PassHash = PASSHASH;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public byte[] getPassHash() {
        return PassHash;
    }
}
