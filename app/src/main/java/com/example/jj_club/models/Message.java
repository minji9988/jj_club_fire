package com.example.jj_club.models;
public class Message {
    private String message;
    private String senderName;
    private String senderImage;
    private boolean isMe;

    public Message(String userId, String userName, String userImageURL, String messageText, long l) {}

    public Message(String message, boolean isMe) {
        this.message = message;
        this.isMe = isMe;
    }

    public Message(String message, String senderName, String senderImage, boolean isMe) {
        this.message = message;
        this.senderName = senderName;
        this.senderImage = senderImage;
        this.isMe = isMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
