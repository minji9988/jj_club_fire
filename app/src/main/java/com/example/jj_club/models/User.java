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

    public String getName() {
        return name;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    // 추가적인 getter 및 setter 메서드를 필요에 따라 포함시킬 수 있습니다.
}
