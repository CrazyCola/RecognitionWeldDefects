package com.example.olechka.recognitionwelddefects;

/**
 * Created by Olechka on 21.05.2017.
 */
public class ReportModel {

    private String title;
    private String description;
    private String image; // Picasa or Glide

    public ReportModel(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
