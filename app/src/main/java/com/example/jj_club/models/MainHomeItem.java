package com.example.jj_club.models;

import java.util.List;

public class MainHomeItem {
    private String id;
    private String imageUrl;
    private String title;
    private String recruitPeriod;
    private String mbti;
    private List<String> selectedButtons;

    public MainHomeItem() {
        // Empty constructor required for Firebase
    }

    public MainHomeItem(String id, String imageUrl, String title, String recruitPeriod, String mbti, List<String> selectedButtons) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.recruitPeriod = recruitPeriod;
        this.mbti = mbti;
        this.selectedButtons = selectedButtons;
    }

    public String getId() {
        return id;
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

    public String getMbti() {
        return mbti;
    }

    public List<String> getSelectedButtons() {
        return selectedButtons;
    }

    public void setSelectedButtons(List<String> selectedButtons) {
        this.selectedButtons = selectedButtons;
    }
}
