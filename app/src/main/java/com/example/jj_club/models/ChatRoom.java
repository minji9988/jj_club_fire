package com.example.jj_club.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {
    private String roomId;
    private Map<String, Boolean> users = new HashMap<>(); // 채팅방에 참여하는 사용자들
    private String lastMessage;
    private String roomName; // 채팅방 이름

    private ArrayList<String> participantIds; // 참여자들의 ID를 저장하는 리스트

    private String promotionId; // 채팅방과 글을 연결하는 변수

    public ChatRoom() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ChatRoom(String roomId, Map<String, Boolean> users, String lastMessage, String roomName) {
        this.roomId = roomId;
        this.users = users;
        this.lastMessage = lastMessage;
        this.roomName = roomName; // Add this line to initialize the roomName
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<String> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(ArrayList<String> participantIds) {
        this.participantIds = participantIds;
    }

    public String getPromotionId() {
        return this.promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
}
