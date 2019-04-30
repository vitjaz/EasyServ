package easyserv.aapp.customserv.com.myapplication.Model;

public class ReviewObj {
    private String sender;
    private String text;
    private String image;

    public ReviewObj(String sender, String text, String image) {
        this.sender = sender;
        this.text = text;
        this.image = image;
    }

    public ReviewObj() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
