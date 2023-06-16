package com.example.jj_club.activities.promotion;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.MessageAdapter;
import com.example.jj_club.models.Message;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

public class ChatActivity extends AppCompatActivity {

    private EditText inputMessage;
    private RecyclerView messageRecyclerView;
    private DatabaseReference messageDatabaseReference;
    private MessageAdapter messageAdapter;
    private String username;  // Replace with actual username

    private String chatRoomId;  // The unique ID of the chat room

    private HashSet<String> profanitySet;  // 욕설을 저장할 HashSet


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_chat);

        inputMessage = findViewById(R.id.edit_chat_message);
        ImageButton sendButton = findViewById(R.id.btn_send);
        messageRecyclerView = findViewById(R.id.recycler_chat_messages);

        // Get the chat room ID from the intent or bundle
        chatRoomId = getIntent().getStringExtra("chatRoomId");

        // Use the chat room ID in the database reference
        messageDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Messages").child(chatRoomId);

        // Initialize profanity set
        this.profanitySet = new HashSet<>();

        try {
            // Get InputStream from the text file
            InputStream inputStream = getResources().openRawResource(R.raw.word);
            // Get a Scanner object for the InputStream
            Scanner scanner = new Scanner(inputStream);
            // Read each line of the file
            while (scanner.hasNextLine()) {
                // Add each line (profanity word) to the set
                profanitySet.add(scanner.nextLine().trim());
            }
            // Close the Scanner
            scanner.close();
        } catch (Exception e) {
            // Handle possible errors.
            e.printStackTrace();
        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        setUpRecyclerView();


    }

    private void setUpRecyclerView() {
        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(messageDatabaseReference, Message.class)
                        .build();

        messageAdapter = new MessageAdapter(options);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(layoutManager);
        messageRecyclerView.setAdapter(messageAdapter);
    }


    private void sendMessage() {
        String rawMessageContent = inputMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(rawMessageContent)) {
            // Filter message content
            String messageContent = rawMessageContent; // 새로운 변수에 원래 메시지 저장
            for (String profanity : profanitySet) {
                messageContent = messageContent.replaceAll(profanity, "***");
            }

            final String finalMessageContent = messageContent; // 최종 메시지를 저장할 final 변수 생성

            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            long timestamp = System.currentTimeMillis();

            // Get reference to the 'users' node
            DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");

            userDatabaseReference.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user name from dataSnapshot
                    String currentUserName = dataSnapshot.child("name").getValue(String.class);

                    // Create message
                    Message chatMessage = new Message(
                            messageDatabaseReference.push().getKey(),
                            currentUserId,
                            currentUserName,
                            finalMessageContent,  // 변경된 부분
                            null,  // Replace with image URL, if any
                            timestamp);

                    // Save message to 'Messages' node
                    messageDatabaseReference.child(chatMessage.getMessageId()).setValue(chatMessage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });

            inputMessage.setText("");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (messageAdapter != null) {
            messageAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (messageAdapter != null) {
            messageAdapter.stopListening();
        }
    }

}
