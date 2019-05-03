package easyserv.aapp.customserv.com.myapplication.Model;

public class Hookah {

    String description;
    String description_2;
    String map;

    String image;
    String photo_1;
    String photo_2;
    String photo_3;
    String photo_4;
    String photo_5;

    String tel;
    String time;
    String title;

    //на будущее
    String order;
    String max_person;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setMax_person(String max_person) {
        this.max_person = max_person;
    }

    public String getMax_person() {
        return max_person;
    }



    public Hookah(String description, String description_2, String map, String photo_1, String photo_2, String photo_3, String photo_4, String photo_5, String tel, String time, String title, String image, String order, String max_person) {
        this.description = description;
        this.description_2 = description_2;
        this.map = map;
        this.photo_1 = photo_1;
        this.photo_2 = photo_2;
        this.photo_3 = photo_3;
        this.photo_4 = photo_4;
        this.photo_5 = photo_5;
        this.tel = tel;
        this.time = time;
        this.title = title;
        this.image = image;
        this.order = order;
        this.max_person = max_person;
    }

    public Hookah() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_2() {
        return description_2;
    }

    public void setDescription_2(String description_2) {
        this.description_2 = description_2;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPhoto_1() {
        return photo_1;
    }

    public void setPhoto_1(String photo_1) {
        this.photo_1 = photo_1;
    }

    public String getPhoto_2() {
        return photo_2;
    }

    public void setPhoto_2(String photo_2) {
        this.photo_2 = photo_2;
    }

    public String getPhoto_3() {
        return photo_3;
    }

    public void setPhoto_3(String photo_3) {
        this.photo_3 = photo_3;
    }

    public String getPhoto_4() {
        return photo_4;
    }

    public void setPhoto_4(String photo_4) {
        this.photo_4 = photo_4;
    }

    public String getPhoto_5() {
        return photo_5;
    }

    public void setPhoto_5(String photo_5) {
        this.photo_5 = photo_5;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
