package com.example.jj_club.models;

import java.util.List;

public class MainHomeItem {
    private String id;
    private String imageUrl;
    private String title;
    private String recruitPeriod;
    private String mbti;

    private String promotionId;
    private List<String> selectedButtons;

    public MainHomeItem() {
        // Empty constructor required for Firebase
    }

    public MainHomeItem(String id, String imageUrl, String title, String recruitPeriod, String mbti, String promotionId, List<String> selectedButtons) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.recruitPeriod = recruitPeriod;
        this.mbti = mbti;
        this.promotionId = promotionId;
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

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public List<String> getSelectedButtons() {
        return selectedButtons;
    }

    public boolean matchesMBTI(String userMBTI) {
        return selectedButtons != null && selectedButtons.contains(userMBTI);
    }


    public void setSelectedButtons(List<String> selectedButtons) {
        this.selectedButtons = selectedButtons;
    }
}
