package com.example.jj_club.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeItem implements Serializable {
    private String title;
    private String recruitPeriod;
    private String fee;
    private String interview;
    private String meetingName;
    private String userId;
    private String promotionNumber;
    private String promotionTarget;
    private String promotionIntroduce;
    private String promotionPlace;
    private Long timeStamp;
    private Long reversedTimestamp;
    private Map<String, Boolean> likes;
    private String imageUrl;
    private List<String> selectedButtons;
    private String mbti;

    private Map<String, String> joinStatuses;

    private String promotionId;

    private String key;


    public HomeItem() {}

    public HomeItem(String title, String recruitPeriod, String fee, String interview,
                    String meetingName, String userId, String promotionNumber, String promotionTarget,
                    String promotionIntroduce, String promotionPlace, String imageUrl, List<String> selectedButtons,
                    String mbti, String promotionId) {

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
        this.imageUrl = imageUrl;
        this.selectedButtons = selectedButtons;
        this.mbti = mbti;
        this.timeStamp = System.currentTimeMillis();
        this.reversedTimestamp = Long.MAX_VALUE - this.timeStamp;
        this.likes = new HashMap<>();
        this.joinStatuses = new HashMap<>();
        this.promotionId = promotionId;


    }

    public Map<String, String> getJoinStatuses() {
        return this.joinStatuses;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPromotionId() {
        return promotionId;
    }

    //getter and setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikesCount() {
        return likes != null ? likes.size() : 0;
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

    public String getPromotionNumber() {
        return promotionNumber;
    }

    public void setPromotionNumber(String promotionNumber) {
        this.promotionNumber = promotionNumber;
    }

    public String getPromotionTarget() {
        return promotionTarget;
    }

    public void setPromotionTarget(String promotionTarget) {
        this.promotionTarget = promotionTarget;
    }

    public String getPromotionIntroduce() {
        return promotionIntroduce;
    }

    public void setPromotionIntroduce(String promotionIntroduce) {
        this.promotionIntroduce = promotionIntroduce;
    }

    public String getPromotionPlace() {
        return promotionPlace;
    }

    public void setPromotionPlace(String promotionPlace) {
        this.promotionPlace = promotionPlace;
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

    public boolean isLikedByUser(String userId) {
        if (likes == null) {
            return false;
        }
        return likes.containsKey(userId);
    }

    public void toggleLike(String userId) {
        if (isLikedByUser(userId)) {
            likes.remove(userId);
        } else {
            if (likes == null) {
                likes = new HashMap<>();
            }
            likes.put(userId, true);
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getSelectedButtons() {
        return selectedButtons;
    }

    public void setSelectedButtons(List<String> selectedButtons) {
        this.selectedButtons = selectedButtons;
    }

    public String getMBTI() {
        return mbti;
    }

    public void setMBIT(String mbti) {
        this.mbti = mbti;
    }

    // Add this new method for getting the joinStatuses field

    // Add this new method for setting the joinStatuses field
    public void setJoinStatuses(Map<String, String> joinStatuses) {
        this.joinStatuses = joinStatuses;
    }

    // Check the join status of a user for this promotion
    public String getJoinStatusForUser(String userId) {
        if (joinStatuses == null) {
            return "not_applied";
        }
        return joinStatuses.getOrDefault(userId, "not_applied");
    }

    // Set the join status of a user for this promotion
    public void setJoinStatusForUser(String userId, String status) {
        if (joinStatuses == null) {
            joinStatuses = new HashMap<>();
        }
        joinStatuses.put(userId, status);
    }

}
