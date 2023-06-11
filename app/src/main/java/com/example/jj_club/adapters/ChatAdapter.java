package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.models.Message;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChatAdapter extends FirebaseRecyclerAdapter<Message, RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    public ChatAdapter(@NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Message model) {
        if (holder.getItemViewType() == VIEW_TYPE_ME) {
            ((MessageViewHolder) holder).messageTextView.setText(model.getMessage());
        } else {
            ((OtherMessageViewHolder) holder).senderNameTextView.setText(model.getSenderName());
            ((OtherMessageViewHolder) holder).messageTextView.setText(model.getMessage());
            Glide.with(((OtherMessageViewHolder) holder).senderImageView.getContext())
                    .load(model.getSenderImage())
                    .into(((OtherMessageViewHolder) holder).senderImageView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ME) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.promotion_chat_sender, parent, false);
            return new MessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.promotion_chat_receiver, parent, false);
            return new OtherMessageViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        if (message.isMe()) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.text_message);
        }
    }

    static class OtherMessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderNameTextView;
        TextView messageTextView;
        ImageView senderImageView;

        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderNameTextView = itemView.findViewById(R.id.text_sender_name);
            messageTextView = itemView.findViewById(R.id.text_message);
            senderImageView = itemView.findViewById(R.id.image_sender);
        }
    }
}
