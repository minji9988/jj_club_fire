package com.example.jj_club.activities.mbti;

public class ApplicationItem {
    private String mbti;

    public ApplicationItem() {
        // Firebase를 위해 기본 생성자 필요
    }

    public ApplicationItem(String mbti) {
        this.mbti = mbti;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }
}
