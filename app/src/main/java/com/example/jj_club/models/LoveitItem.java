package com.example.jj_club.models;

import java.util.Map;

public class LoveitItem {
    private String title;
    private String recruitPeroid;
    private String meetingName;
    private Long timeStamp;
    private Map<String, Boolean> likes;
    private String imageUrl;

    private String contents;

    //생성자
    public LoveitItem(){

    }

    //get, set
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecruitPeroid() {
        return recruitPeroid;
    }

    public void setRecruitPeroid(String recruitPeroid) {
        this.recruitPeroid = recruitPeroid;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getLikes() {
        return likes.size();
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}

