package com.example.homework3;

public class Episode {
    private String image_url;

    public Episode(String image){
        this.image_url = image;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
