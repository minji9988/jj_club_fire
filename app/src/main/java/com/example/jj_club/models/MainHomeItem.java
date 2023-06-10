package com.example.jj_club.models;

public class MainHomeItem {
    private String imageUrl;
    private String title;
    private String recruitPeriod;

    public MainHomeItem() {
        // Empty constructor required for Firebase
    }

    public MainHomeItem(String imageUrl, String title, String recruitPeriod) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.recruitPeriod = recruitPeriod;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getRecruitPeriod() {
        return recruitPeriod;
    }
}
