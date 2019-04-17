package easyserv.aapp.customserv.com.myapplication.Model;

public class ReviewObj {
    private String sender;
    private String text;

    public ReviewObj(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public ReviewObj() {

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
