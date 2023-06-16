package com.example.jj_club.models;

public class User {
    private String id;
    private String name;
    private String mbti;

    public User() {
        // 기본 생성자는 Firebase에서 객체를 역직렬화할 때 사용됩니다.
    }

    public User(String id, String name, String mbti) {
        this.id = id;
        this.name = name;
        this.mbti = mbti;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }


}