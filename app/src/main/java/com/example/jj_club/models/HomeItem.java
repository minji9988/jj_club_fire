package com.example.jj_club.models;

import java.util.HashMap;
import java.util.Map;

public class HomeItem {
    private String title;
    private String recruitPeriod;
    private String fee;
    private String interview;
    private String meetingName;
    private String userId;
    private String promotionNumber;
    private String promotionTarget;
    private String promotionIntroduce;
    private String promotionPlace;
    private Long timeStamp;
    private Long reversedTimestamp; // 추가된 속성
    private Map<String, Boolean> likes;

    private String imageUrl;  // 추가된 속성

    public HomeItem() {
    }

    public HomeItem(String title, String recruitPeriod, String fee, String interview, String meetingName, String userId, String promotionNumber, String promotionTarget, String promotionIntroduce, String promotionPlace, String imageUrl) {
        this.title = title;
        this.recruitPeriod = recruitPeriod;
        this.fee = fee;
        this.interview = interview;
        this.meetingName = meetingName;
        this.userId = userId;
        this.promotionNumber = promotionNumber;
        this.promotionTarget = promotionTarget;
        this.promotionIntroduce = promotionIntroduce;
        this.promotionPlace = promotionPlace;
        this.timeStamp = System.currentTimeMillis();
        this.reversedTimestamp = Long.MAX_VALUE - this.timeStamp;
        this.likes = new HashMap<>();
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Existing getters and setters...

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecruitPeriod() {
        return recruitPeriod;
    }

    public void setRecruitPeriod(String recruitPeriod) {
        this.recruitPeriod = recruitPeriod;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getInterview() {
        return interview;
    }

    public void setInterview(String interview) {
        this.interview = interview;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getReversedTimestamp() {
        return reversedTimestamp;
    }

    public void setReversedTimestamp(Long reversedTimestamp) {
        this.reversedTimestamp = reversedTimestamp;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }
}
