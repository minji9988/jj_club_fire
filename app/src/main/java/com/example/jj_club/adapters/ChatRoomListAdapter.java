package com.example.jj_club.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.ChatActivity;
import com.example.jj_club.models.ChatRoom;

import java.util.List;

public class ChatRoomListAdapter extends RecyclerView.Adapter<ChatRoomListAdapter.ChatRoomViewHolder> {
    private List<ChatRoom> chatRoomList;
    private OnItemClickListener listener; // Add this line

    public interface OnItemClickListener { // Add this interface
        void onItemClick(ChatRoom chatRoom);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { // Add this method
        this.listener = listener;
    }

    public ChatRoomListAdapter(List<ChatRoom> chatRoomList) {
        this.chatRoomList = chatRoomList;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_chat_room_list_item, parent, false);
        return new ChatRoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        ChatRoom chatRoom = chatRoomList.get(position);
        holder.bind(chatRoom); // This will bind the data to the view

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                intent.putExtra("chatRoomId", chatRoom.getRoomId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageChatRoom;
        private TextView textChatRoomName, textLastMessage;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imageChatRoom = itemView.findViewById(R.id.promotion_chat_list_img);
            textChatRoomName = itemView.findViewById(R.id.text_chat_room_name);
            textLastMessage = itemView.findViewById(R.id.text_last_message);
        }

        void bind(ChatRoom chatRoom) {
            // Use Glide or other image loading library to load the image
            textChatRoomName.setText(chatRoom.getRoomName()); // Show chat room name instead of ID
            textLastMessage.setText(chatRoom.getLastMessage());
        }
    }
}
