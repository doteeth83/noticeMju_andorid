package com.example.notice_mju;

public class PostModel {
    private int id;
    private String caption;
    private String imageUrl;

    public PostModel(int id, String caption, String imageUrl) {
        this.id = id;
        this.caption = caption;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

