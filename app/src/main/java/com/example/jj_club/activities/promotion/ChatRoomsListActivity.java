package com.example.jj_club.activities.promotion;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.ChatRoomListAdapter;
import com.example.jj_club.models.ChatRoom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomsListActivity extends AppCompatActivity {

    private RecyclerView recyclerChatRoomList;
    private List<ChatRoom> chatRoomList = new ArrayList<>();
    private ChatRoomListAdapter chatRoomListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_detail);

        recyclerChatRoomList = findViewById(R.id.recycler_chat_room_list);
        chatRoomListAdapter = new ChatRoomListAdapter(chatRoomList);
        recyclerChatRoomList.setAdapter(chatRoomListAdapter);

        fetchChatRooms();
    }

    private void fetchChatRooms() {
        DatabaseReference chatRoomsRef = FirebaseDatabase.getInstance().getReference("chatrooms");
        chatRoomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatRoomList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatRoom chatRoom = snapshot.getValue(ChatRoom.class);
                    if (chatRoom != null) {
                        chatRoom.setRoomId(snapshot.getKey());
                        chatRoomList.add(chatRoom);
                    }
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