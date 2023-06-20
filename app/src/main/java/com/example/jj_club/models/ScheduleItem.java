package com.example.jj_club.models;

public class ScheduleItem {
    private String date;
    private String schedule;

    private String promotionId;

    public ScheduleItem() {
    }

    public ScheduleItem(String date, String schedule) {
        this.date = date;
        this.schedule = schedule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
}
