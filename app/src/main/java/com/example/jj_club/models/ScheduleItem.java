package com.example.jj_club.models;


public class ScheduleItem {
    private String date;
    private String schedule;

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
}