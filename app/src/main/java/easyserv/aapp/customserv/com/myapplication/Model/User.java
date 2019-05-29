package easyserv.aapp.customserv.com.myapplication.Model;

public class User {

    private String id;
    private String fullname;
    private String bio;
    private String imageURL;
    private String username;
    private String password;
    private String phoneNumber;

    public User(String id, String fullname, String bio, String imageURL, String username, String password, String phoneNumber) {
        this.id = id;
        this.fullname = fullname;
        this.bio = bio;
        this.imageURL = imageURL;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
