package de.ronnyritscher.mvvm_examplerecyclerview.models;

public class Umfrage {

    private String imageUrl;
    private String title;
    private String text;


    //CONSTRUCTOR
    public Umfrage(String imageUrl, String title, String text) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.text = text;
    }

    //GETTER SETTER
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
