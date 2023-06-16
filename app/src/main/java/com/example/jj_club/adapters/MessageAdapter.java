package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.models.Message;
import com.example.jj_club.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    public MessageAdapter(@NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (message.getSenderId().equals(currentUserId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.promotion_chat_sender, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.promotion_chat_receiver, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Message model) {
        if (holder instanceof SentMessageHolder) {
            ((SentMessageHolder) holder).bind(model);
        } else if (holder instanceof ReceivedMessageHolder) {
            ((ReceivedMessageHolder) holder).bind(model);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_sender_message);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessageContent());

            DateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul")); //한국 시간 적용
            String formattedDate = dateFormat.format(new Date(message.getTimestamp()));
            timeText.setText(formattedDate);

        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_receiver_message);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_receiver_name);
            profileImage = itemView.findViewById(R.id.image_receiver);
        }

        void bind(Message message) {
            messageText.setText(message.getMessageContent());

            DateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul")); //한국 시간 적용
            String formattedDate = dateFormat.format(new Date(message.getTimestamp()));
            timeText.setText(formattedDate);


            // Get sender's information from Firebase
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(message.getSenderId());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User sender = dataSnapshot.getValue(User.class);
                    if (sender != null) {
                        nameText.setText(sender.getName());

                        // Load the profile image using Picasso or Glide
                        // For now, we'll use a placeholder image
                        profileImage.setImageResource(R.drawable.chat_sender_img);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle possible errors.
                }
            });
        }
    }
}
