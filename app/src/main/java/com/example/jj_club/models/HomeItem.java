package com.example.jj_club.models;

import java.util.HashMap;
import java.util.Map;

public class HomeItem {
    private String title; // 홈 아이템의 제목
    private String recruitPeriod; // 모집 기간
    private String fee; // 회비
    private String interview; // 면접 여부
    private String meetingName; // 모임 이름
    private String userId; // 유저의 ID
    private String promotionNumber; // 홍보 번호
    private String promotionTarget; // 모집 대상
    private String promotionIntroduce; // 모임 소개
    private String promotionPlace; // 모임 장소
    private Long timeStamp; // 타임스탬프 (아이템 생성 시간)
    private Long reversedTimestamp; // 추가된 속성: 리버스 타임스탬프 (정렬을 위한 역순 타임스탬프)
    private Map<String, Boolean> likes; // 좋아요 맵 (키: 유저 ID, 값: 좋아요 여부)

    private String imageUrl;  // 추가된 속성: 이미지 URL

    public HomeItem() {
    }

    public HomeItem(String title, String recruitPeriod, String fee, String interview, String meetingName, String userId, String promotionNumber, String promotionTarget, String promotionIntroduce, String promotionPlace, String imageUrl) {
        this.title = title;
        this.recruitPeriod = recruitPeriod;
        this.fee = fee;
        this.interview = interview;
        this.meetingName = meetingName;
        this.userId = userId;
        this.promotionNumber = promotionNumber;
        this.promotionTarget = promotionTarget;
        this.promotionIntroduce = promotionIntroduce;
        this.promotionPlace = promotionPlace;
        this.timeStamp = System.currentTimeMillis();
        this.reversedTimestamp = Long.MAX_VALUE - this.timeStamp;
        this.likes = new HashMap<>();
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Existing getters and setters...

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecruitPeriod() {
        return recruitPeriod;
    }

    public void setRecruitPeriod(String recruitPeriod) {
        this.recruitPeriod = recruitPeriod;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getInterview() {
        return interview;
    }

    public void setInterview(String interview) {
        this.interview = interview;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getReversedTimestamp() {
        return reversedTimestamp;
    }

    public void setReversedTimestamp(Long reversedTimestamp) {
        this.reversedTimestamp = reversedTimestamp;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }
}
