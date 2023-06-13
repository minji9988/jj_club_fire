package com.example.jj_club.models;

import java.io.Serializable;

public class ApplicationItem implements Serializable {

    private String fromUserId; // User who sends the application
    private String promotionId; // Promotion id associated with the application
    private String appName; // Application name
    private String appNumber; // Application number
    private String appPhone; // Application phone number
    private String appIntro; // Application introduction
    private String sendToUserId; // User who should receive the application

    // Default constructor
    public ApplicationItem() { }

    // Constructor for initializing ApplicationItem
    public ApplicationItem(String fromUserId, String promotionId, String appName, String appNumber, String appPhone, String appIntro) {
        this.fromUserId = fromUserId;
        this.promotionId = promotionId;
        this.appName = appName;
        this.appNumber = appNumber;
        this.appPhone = appPhone;
        this.appIntro = appIntro;
    }

    // Getter and Setter methods for all variables

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    public String getAppPhone() {
        return appPhone;
    }

    public void setAppPhone(String appPhone) {
        this.appPhone = appPhone;
    }

    public String getAppIntro() {
        return appIntro;
    }

    public void setAppIntro(String appIntro) {
        this.appIntro = appIntro;
    }

    public String getSendToUserId() {
        return sendToUserId;
    }

    public void setSendToUserId(String sendToUserId) {
        this.sendToUserId = sendToUserId;
    }
}
