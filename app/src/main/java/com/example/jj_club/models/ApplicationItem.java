package com.example.jj_club.models;

public class ApplicationItem {
    private String appTitle;
    private String appContent;

    public ApplicationItem() {
        // Default constructor required for Firebase
    }

    public ApplicationItem(String appTitle, String appContent) {
        this.appTitle = appTitle;
        this.appContent = appContent;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppContent() {
        return appContent;
    }

    public void setAppContent(String appContent) {
        this.appContent = appContent;
    }
}

