package com.example.jj_club.models;

public class MyClubItem {
    private String meetingName;
    private String promotionId;

    public MyClubItem() { }


    public MyClubItem(String meetingName, String promotionId) { // Modify this line
        this.meetingName = meetingName;
        this.promotionId = promotionId; // Add this line
    }

    public String getMeetingName() {
        return meetingName;
    }

    public String getPromotionId() { // Add this method
        return promotionId;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
}
