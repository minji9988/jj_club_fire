package com.example.jj_club.models;

public class Message {
    private String messageId;
    private String senderId;
    private String senderName;  // sender's name
    private String messageContent;
    private String imageUrl;  // 이미지의 URL
    private long timestamp;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Message(String messageId, String senderId, String senderName, String messageContent, String imageUrl, long timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.messageContent = messageContent;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
