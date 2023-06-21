package com.example.jj_club.models;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ApplicationItem implements Serializable {

    private String fromUserId; // User who sends the application, 신청서 보내는사람
    private String promotionId; // Promotion id associated with the application(신청서id)
    private String appName; // Application name
    private String appNumber; // Application number
    private String appPhone; // Application phone number
    private String appIntro; // Application introduction
    private String sendToUserId; // User who should receive the application, 신청서 작성한사람(받는사람)
    private Map<String, Boolean> appApproval;

    private String applicationId; // 추가: 신청서의 아이디

    private Map<String, String> joinStatuses;


    // Default constructor
    public ApplicationItem() { }


    // Constructor for initializing ApplicationItem
    public ApplicationItem(String fromUserId, String promotionId, String appName, String appNumber, String appPhone, String appIntro, String applicationId) {
        this.fromUserId = fromUserId;
        this.promotionId = promotionId;
        this.appName = appName;
        this.appNumber = appNumber;
        this.appPhone = appPhone;
        this.appIntro = appIntro;
        this.appApproval = new HashMap<>();
        this.applicationId = applicationId;
    }

    // getter and setter for joinStatuses
    public Map<String, String> getJoinStatuses() {
        return joinStatuses;
    }

    public void setJoinStatuses(Map<String, String> joinStatuses) {
        this.joinStatuses = joinStatuses;
    }


    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public Map<String, Boolean> getAppApproval() {
        return appApproval;
    }

    public void setAppApproval(Map<String, Boolean> appApproval) {
        this.appApproval = appApproval;
    }

    public boolean isApprovalByUser(String userId){
        if (appApproval == null){
            return  false;
        }
        return appApproval.containsKey(userId);
    }

}
