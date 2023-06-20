package com.example.jj_club.models;

public class HomeBannerItem {
    private String imageUrl;
    private String promotionId;

    public HomeBannerItem(String imageUrl, String promotionId) {
        this.imageUrl = imageUrl;
        this.promotionId = promotionId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPromotionId() {
        return promotionId;
    }
}
