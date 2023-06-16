package com.example.jj_club.activities.promotion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.adapters.ChatRoomListAdapter;
import com.example.jj_club.models.ChatRoom;
import com.example.jj_club.models.HomeItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionDetailActivity extends AppCompatActivity {

    private LinearLayout layoutIntro;
    private LinearLayout layoutCalendar;
    private LinearLayout layoutChatting;
    private ImageButton btnBack;
    private Button btnApply; // Added button for applying

    // Views for intro layout
    private ImageView imagePromotion;
    private TextView textRecruitPeriod, textFee, textInterview, textMeetingName, textRecruitmentCount, textClubIntro, textRecruitmentTarget;

    private RecyclerView recyclerChatRoomList;
    private List<ChatRoom> chatRoomList = new ArrayList<>();
    private ChatRoomListAdapter chatRoomListAdapter;

    private ImageButton btnAddChatRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_detail);

        // chat
        recyclerChatRoomList = findViewById(R.id.recycler_chat_room_list); // RecyclerView의 ID를 적절히 수정해주세요.
        chatRoomListAdapter = new ChatRoomListAdapter(chatRoomList);
        recyclerChatRoomList.setAdapter(chatRoomListAdapter);

        btnAddChatRoom = findViewById(R.id.promotion_add_chat_btn); // Create chat room btn

        btnAddChatRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChatRoom();
            }
        });


        chatRoomListAdapter.setOnItemClickListener(new ChatRoomListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChatRoom chatRoom) {
                Intent intent = new Intent(PromotionDetailActivity.this, ChatActivity.class);
                intent.putExtra("room_id", chatRoom.getRoomId());
                startActivity(intent);
            }
        });


        // Find the layout views
        layoutIntro = findViewById(R.id.layout_intro);
        layoutCalendar = findViewById(R.id.layout_calendar);
        layoutChatting = findViewById(R.id.layout_chatting);
        btnBack = findViewById(R.id.promotion_detail_btn_back);
        btnApply = findViewById(R.id.promotion_apply_btn); // Initialize the apply button

        // Find the views in intro layout
        imagePromotion = findViewById(R.id.image_promotion);
        textRecruitPeriod = findViewById(R.id.promotion_detail_text_date);
        textFee = findViewById(R.id.text_membership_fee_amount);
        textInterview = findViewById(R.id.text_interview_status);
        textMeetingName = findViewById(R.id.promotion_detail_text_title);
        textRecruitmentCount = findViewById(R.id.text_recruitment_count_amount);
        textClubIntro = findViewById(R.id.text_club_intro_activities_details);
        textRecruitmentTarget = findViewById(R.id.text_recruitment_target_details);

        // Set up the TabLayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Check which tab is selected
                if (tab.getPosition() == 0) { // Introduction tab
                    layoutIntro.setVisibility(View.VISIBLE);
                    layoutCalendar.setVisibility(View.GONE);
                    layoutChatting.setVisibility(View.GONE);
                } else if (tab.getPosition() == 1) { // Calendar tab
                    layoutIntro.setVisibility(View.GONE);
                    layoutCalendar.setVisibility(View.VISIBLE);
                    layoutChatting.setVisibility(View.GONE);
                } else if (tab.getPosition() == 2) { // Chatting tab
                    layoutIntro.setVisibility(View.GONE);
                    layoutCalendar.setVisibility(View.GONE);
                    layoutChatting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });

        // Set the onClickListener for the back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();  // Close the current activity and go back to the previous activity
                Log.d("PromotionDetailActivity", "Back button pressed");  // Add log message for back button click
            }
        });

        // Hide the chat-related elements initially
        layoutChatting.setVisibility(View.GONE);


        // Set the onClickListener for the apply button
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PromotionDetailActivity.this, ApplicationForm.class);
                String promotionId = getIntent().getStringExtra("promotion_id"); // Get the promotion_id from the intent
                intent.putExtra("promotion_id", promotionId); // Add the promotion_id to the intent
                startActivity(intent);
            }
        });


        // Get data from the database
        String key = getIntent().getStringExtra("promotion_id"); // get the key from the intent

        if (key != null) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("promotions").child(key);

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HomeItem homeItem = dataSnapshot.getValue(HomeItem.class);

                    // Get the data and display it on the screen
                    Glide.with(getApplicationContext()).load(homeItem.getImageUrl()).into(imagePromotion);
                    textRecruitPeriod.setText(homeItem.getRecruitPeriod());
                    textFee.setText(homeItem.getFee());
                    textInterview.setText(homeItem.getInterview());
                    textMeetingName.setText(homeItem.getMeetingName());
                    textRecruitmentCount.setText(homeItem.getPromotionNumber());
                    textClubIntro.setText(homeItem.getPromotionIntroduce());
                    textRecruitmentTarget.setText(homeItem.getPromotionTarget());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to get data from the DB
                    Log.e("PromotionDetailActivity", "Failed to retrieve data from Firebase: " + databaseError.getMessage());
                }
            });
        } else {
            Log.e("PromotionDetailActivity", "Failed to retrieve key from intent");
        }

        // Fetch chat rooms
        fetchChatRooms();
    }

    private void createChatRoom() {


        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create a Chat Room");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When the user clicks "OK", create a new chat room with the given name
                String chatRoomName = input.getText().toString();
                createNewChatRoom(chatRoomName);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }



    private void createNewChatRoom(String chatRoomName) {
        String roomId = FirebaseDatabase.getInstance().getReference("chatrooms").push().getKey();
        Map<String, Boolean> users = new HashMap<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            // User is not signed in, cannot create a chat room
            Log.e("PromotionDetailActivity", "Failed to create new chat room: User is not signed in.");
            return;
        }

        String currentUserId = currentUser.getUid();
        users.put(currentUserId, true);

        ChatRoom chatRoom = new ChatRoom(roomId, users, "", chatRoomName);

        // get the promotion_id from the intent and set it to the chat room
        String promotionId = getIntent().getStringExtra("promotion_id");
        chatRoom.setPromotionId(promotionId);

        if (roomId != null) {
            FirebaseDatabase.getInstance().getReference("chatrooms").child(roomId).setValue(chatRoom).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Fetch chat rooms again to update the list
                    fetchChatRooms();
                } else {
                    Log.e("PromotionDetailActivity", "Failed to create new chat room: " + task.getException().getMessage());
                }
            });
        }
    }



    private void fetchChatRooms() {
        String promotionId = getIntent().getStringExtra("promotion_id"); // get the promotion_id from the intent
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference("chatrooms");

        chatRoomsRef.orderByChild("promotionId").equalTo(promotionId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatRoomList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                    chatRoomList.add(chatRoom);
                }
                chatRoomListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PromotionDetailActivity", "Failed to fetch chat rooms: " + databaseError.getMessage());
            }
        });
    }

}