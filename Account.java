import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private String username;
    private String password;
    private Profile profile;
    private ArrayList<String> friendRequests;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String firstName, String lastName, String isPublic, String bio, String interests, ArrayList<String> friendsList) {
        this.username = username;
        this.password = password;
        profile = new Profile(firstName, lastName, isPublic, bio, interests, friendsList);
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Account{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", profile=" + profile +
            ", friendRequests=" + friendRequests +
            '}';
    }
}
