package com.example.jj_club.activities.promotion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.MessageAdapter;
import com.example.jj_club.models.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class ChatActivity extends AppCompatActivity implements ValueEventListener {

    private EditText inputMessage;
    private RecyclerView messageRecyclerView;
    private DatabaseReference messageDatabaseReference;
    private final MessageAdapter messageAdapter = new MessageAdapter();
    private String username;  // Replace with actual username

    private String chatRoomId;  // The unique ID of the chat room

    private HashSet<String> profanitySet;  // 욕설을 저장할 HashSet

    private ImageButton btnAddMedia;
    private Uri selectedImageUri;
    private static final int RC_IMAGE_PICKER = 123;
    private StorageReference chatImagesStorageReference;

    private boolean isRegisteredValueEventListener = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_chat);

        inputMessage = findViewById(R.id.edit_chat_message);
        ImageButton sendButton = findViewById(R.id.btn_send);
        messageRecyclerView = findViewById(R.id.recycler_chat_messages);
        btnAddMedia = findViewById(R.id.btn_add_media);

        // Get the chat room ID from the intent or bundle
        chatRoomId = getIntent().getStringExtra("chatRoomId");

        // Use the chat room ID in the database reference
        messageDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Messages").child(chatRoomId);
        chatImagesStorageReference = FirebaseStorage.getInstance().getReference().child("chat_images");

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
                sendMessage(null);
            }
        });

        btnAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Choose an image"), RC_IMAGE_PICKER);
            }
        });

        setUpRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_PICKER && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            uploadImage();
        }
    }

    private void uploadImage() {
        if (selectedImageUri != null) {
            final StorageReference imageRef = chatImagesStorageReference.child(selectedImageUri.getLastPathSegment());

            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Now we can include the image URL in our message
                            sendMessage(uri.toString());
                            // Notify the adapter about dataset change
//                            messageAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(layoutManager);
        messageRecyclerView.setAdapter(messageAdapter);

        // Register the AdapterDataObserver
        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int messageCount = messageAdapter.getItemCount();
                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (messageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    messageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
    }

    private void sendMessage(@Nullable String imageUrl) {
        String rawMessageContent = imageUrl != null ? "사진" : inputMessage.getText().toString().trim();
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
                            finalMessageContent,  // 욕설 필터링된 최종 메세지
                            imageUrl,  // Use the imageUrl argument here
                            timestamp);

                    // Save message to 'Messages' node
                    messageDatabaseReference.child(chatMessage.getMessageId()).setValue(chatMessage)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Get reference to the 'chatrooms' node
                                    DatabaseReference chatRoomRef = FirebaseDatabase.getInstance().getReference("chatrooms").child(chatRoomId);
                                    chatRoomRef.child("lastMessage").setValue(finalMessageContent);  // Update last message in ChatRoom
                                    // Notify the adapter about dataset change
//                                    messageAdapter.notifyDataSetChanged();
                                }
                            });
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

        if (!isRegisteredValueEventListener) {
            isRegisteredValueEventListener = true;
            messageDatabaseReference.addValueEventListener(this);
        }
    }

    @Override
    protected void onStop() {
        if (isRegisteredValueEventListener) {
            isRegisteredValueEventListener = false;
            messageDatabaseReference.removeEventListener(this);
        }

        super.onStop();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        ArrayList<Message> messages = new ArrayList<>();

        for (DataSnapshot child : snapshot.getChildren()) {
            messages.add(child.getValue(Message.class));
        }

        messageAdapter.submitList(messages);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
    }
}

