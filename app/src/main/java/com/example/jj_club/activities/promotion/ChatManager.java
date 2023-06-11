package com.example.jj_club.activities.promotion;

import com.example.jj_club.models.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatManager {
    private static final String CHAT_ROOMS_CHILD = "chatrooms";
    private DatabaseReference databaseReference;

    public ChatManager() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference(CHAT_ROOMS_CHILD);
    }

    public void writeMessage(String chatRoomId, Message message) {
        databaseReference.child(chatRoomId).push().setValue(message);
    }

    public DatabaseReference getChatMessagesReference(String chatRoomId) {
        return databaseReference.child(chatRoomId);
    }
}
