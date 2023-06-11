package com.example.jj_club.models;

public class MainHomeItem {
    private String id; // 추가된 필드
    private String imageUrl;
    private String title;
    private String recruitPeriod;

    public MainHomeItem() {
        // Empty constructor required for Firebase
    }

    public MainHomeItem(String id, String imageUrl, String title, String recruitPeriod) { // 수정된 생성자
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.recruitPeriod = recruitPeriod;
    }

    public String getId() { // 추가된 getter 메서드
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getRecruitPeriod() {
        return recruitPeriod;
    }
}
