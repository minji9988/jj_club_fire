package com.example.jj_club.activities.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.ChatAdapter;
import com.example.jj_club.models.Message;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatFragment extends Fragment {
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private EditText messageEditText;
    private ImageButton sendButton;
    private DatabaseReference chatManager;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.promotion_chat_fragment, container, false);

        setupChatRecyclerView(view);

        messageEditText = view.findViewById(R.id.edit_chat_message);
        sendButton = view.findViewById(R.id.btn_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString();
                if (!messageText.isEmpty()) {
                    // 메시지를 Firebase에 저장합니다.
                    Message message = new Message("userId", "UserName", "UserImageURL", messageText, System.currentTimeMillis());
                    chatManager.push().setValue(message);
                    messageEditText.setText("");
                }
            }
        });

        return view;
    }

    private void setupChatRecyclerView(View view) {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference().child("chatRooms").child("chatRoomId").child("messages");
        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(messagesRef.orderByChild("timestamp"), Message.class)
                .build();

        chatAdapter = new ChatAdapter(options);

        chatRecyclerView = view.findViewById(R.id.recycler_view_chat);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatRecyclerView.setAdapter(chatAdapter);

        chatManager = messagesRef;
    }

    @Override
    public void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        chatAdapter.stopListening();
    }
}
