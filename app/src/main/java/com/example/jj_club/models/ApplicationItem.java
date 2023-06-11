package com.example.jj_club.models;

public class ApplicationItem {
    private String fromUserId;
    private String promotionId;
    private String title;
    private String content;
    private String sendToUserId;

    public ApplicationItem() {
        // Default constructor required for calls to DataSnapshot.getValue(ApplicationItem.class)
    }

    public ApplicationItem(String fromUserId, String promotionId, String title, String content) {
        this.fromUserId = fromUserId;
        this.promotionId = promotionId;
        this.title = title;
        this.content = content;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendToUserId() {
        return sendToUserId;
    }

    public void setSendToUserId(String sendToUserId) {
        this.sendToUserId = sendToUserId;
    }
}
