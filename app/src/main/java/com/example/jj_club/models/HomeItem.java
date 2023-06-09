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
    private Long timeStamp;
    private Map<String, Boolean> likes;

    public HomeItem() {
    }

    public HomeItem(String title, String recruitPeriod, String fee, String interview, String meetingName, String userId) {
        this.title = title;
        this.recruitPeriod = recruitPeriod;
        this.fee = fee;
        this.interview = interview;
        this.meetingName = meetingName;
        this.userId = userId;
        this.timeStamp = System.currentTimeMillis();
        this.likes = new HashMap<>();
    }

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

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }
}
