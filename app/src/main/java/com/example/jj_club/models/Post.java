package com.example.jj_club.models;

public class Post {
    private String title; // 글 제목
    private String description; // 글 상세 소개

    public Post() {
        // 기본 생성자 (Firebase에서 사용)
    }

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
