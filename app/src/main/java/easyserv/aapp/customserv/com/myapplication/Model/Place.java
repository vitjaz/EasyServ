package easyserv.aapp.customserv.com.myapplication.Model;

public class Place {

    private String title;
    private String description;
    private int num;
    private String image;

    public Place() {
    }

    public Place(String title, String description, int num, String image) {
        this.title = title;
        this.description = description;
        this.num = num;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
