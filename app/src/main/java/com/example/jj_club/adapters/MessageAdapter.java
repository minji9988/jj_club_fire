package com.example.jj_club.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.models.Message;
import com.example.jj_club.models.User;
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

public class MessageAdapter extends ListAdapter<Message, RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;


    public MessageAdapter() {
        super(new DiffUtil.ItemCallback<Message>() {
            @Override
            public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                return TextUtils.equals(oldItem.getMessageId(), newItem.getMessageId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                return true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == RecyclerView.NO_POSITION) {
            return VIEW_TYPE_SENT;
        }

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message model = getItem(position);

        if (holder instanceof SentMessageHolder) {
            ((SentMessageHolder) holder).bind(model);
        } else if (holder instanceof ReceivedMessageHolder) {
            ((ReceivedMessageHolder) holder).bind(model);
        }
    }

    public static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView imageView;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_sender_message);
            timeText = itemView.findViewById(R.id.text_message_time);
            imageView = itemView.findViewById(R.id.image_view);
        }

        void bind(Message message) {
            if (!TextUtils.isEmpty(message.getImageUrl())) {
                messageText.setVisibility(View.GONE);
                ((View) imageView.getParent()).setVisibility(View.VISIBLE);

                Glide.with(imageView)
                        .load(message.getImageUrl())
                        .into(imageView);

            } else {
                messageText.setText(message.getMessageContent());
                messageText.setVisibility(View.VISIBLE);
                ((View) imageView.getParent()).setVisibility(View.GONE);
            }

            DateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul")); //한국 시간 적용
            String formattedDate = dateFormat.format(new Date(message.getTimestamp()));
            timeText.setText(formattedDate);
        }
    }

    public static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView imageView;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_receiver_message);
            timeText = itemView.findViewById(R.id.text_message_time);
            imageView = itemView.findViewById(R.id.image_view);
            nameText = itemView.findViewById(R.id.text_receiver_name);
            profileImage = itemView.findViewById(R.id.image_receiver);
        }

        void bind(Message message) {
            if (!TextUtils.isEmpty(message.getImageUrl())) {
                messageText.setVisibility(View.GONE);
                ((View) imageView.getParent()).setVisibility(View.VISIBLE);

                Glide.with(imageView)
                        .load(message.getImageUrl())
                        .into(imageView);

            } else {
                messageText.setText(message.getMessageContent());
                messageText.setVisibility(View.VISIBLE);
                ((View) imageView.getParent()).setVisibility(View.GONE);
            }

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
