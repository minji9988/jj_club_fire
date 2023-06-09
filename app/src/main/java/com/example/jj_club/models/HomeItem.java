package com.example.jj_club.models;

public class HomeItem {
    private String title; // 글 제목
    private String recruitPeriod; // 모집 기간
    private String fee; // 회비
    private String interview; // 면접 유무
    private String meetingName; // 모임 이름
    private String userId;

    private Long timeStamp; // 글 작성 시간

    public HomeItem() {
        // Firestore에서 사용하는 빈 생성자
    }

    public HomeItem(String title, String recruitPeriod, String fee, String interview, String meetingName, String userId) {
        this.title = title;
        this.recruitPeriod = recruitPeriod;
        this.fee = fee;
        this.interview = interview;
        this.meetingName = meetingName;
        this.userId = userId;
        this.timeStamp = System.currentTimeMillis();  // 서버 시간을 사용하여 타임스탬프 설정
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

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

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
