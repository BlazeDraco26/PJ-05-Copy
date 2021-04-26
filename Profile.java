import java.io.Serializable;
import java.util.ArrayList;

/**
 * CS 180 - Project 05 -  Profile class
 * In this class, we are creating the profile object that will be used to 
 * store a user's profile information
 * @author Saketh
 * @version 04/20/21
 */
public class Profile implements Serializable {
    private String firstName; // stores the first name
    private String lastName; // stores the last name
    private String isPublic; //whether the account is public or not
    private String bio; // Stores the bio or "about me" of a specific profile
    private String interests; // Stores the profile's likes/interests
    private ArrayList <String> friendsList; //Stores the name of the friends


    public Profile(String firstName, String lastName, String isPublic, String bio, String interests, ArrayList <String> friendsList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isPublic = isPublic;
        this.bio = bio;
        this.interests = interests;
        this.friendsList = new ArrayList<String>();
    }


    //creating getters and setters

    //for the firstName field
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //for the lastName field
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //for the isPublicBoolean
    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    //for the bio/"about me" field
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    //for the likes/interests
    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    // for the friends list

    // Need a add friend and remove friend method

    //Two separate arrayLists, one for actual friends, another for requests
    //Talk about friends list and how it interacts with the network
    //Talk about CSV file format

    // Add friend - adds each of the users to each others list
    // remove friend - removes each of the users from each others list
    // request friend - adds a friend request to the recipient's request list
    // check requests - parameters: username (recipient) ensures that there are not duplicate friend requests from the same user
    // check friends - cannot have duplicate usernames in friends list, cannot remove a friend you don't have


    @Override
    public String toString() {
        return "Profile{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", isPublic='" + isPublic + '\'' +
            ", bio='" + bio + '\'' +
            ", interests='" + interests + '\'' +
            ", friendsList=" + friendsList +
            '}';
    }
}
