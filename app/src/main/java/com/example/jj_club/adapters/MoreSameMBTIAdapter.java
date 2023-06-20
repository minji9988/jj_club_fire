package com.example.jj_club.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.models.HomeItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoreSameMBTIAdapter extends RecyclerView.Adapter<MoreSameMBTIAdapter.MoreSameMBTIViewHolder> {

    private static final String TAG = "MoreSameMBTIAdapter";
    private DatabaseReference mDatabase;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String userMBTI;  // User's MBTI
    private List<HomeItem> filteredItems;

    public MoreSameMBTIAdapter(List<HomeItem> items, String userMBTI) {
        this.filteredItems = items;
        this.userMBTI = userMBTI;  // Set user's MBTI
        Log.d(TAG, "Adapter initialized with user MBTI: " + userMBTI);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
    }

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(HomeItem item, String key);
    }


    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MoreSameMBTIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_more_item, parent, false);
        return new MoreSameMBTIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreSameMBTIViewHolder holder, int position) {
        HomeItem model = filteredItems.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(filteredItems.get(position), filteredItems.get(position).getKey());
                    }

                }
            }
        });


        if (model.getSelectedButtons() == null) {
            Log.d("MoreSameMBTIAdapter", "Model selectedButtons is null at position: " + position);
            return;
        } else {
            Log.d("MoreSameMBTIAdapter", "Model selectedButtons: " + model.getSelectedButtons());
        }

        if (model.getSelectedButtons().contains(userMBTI)) {
            Log.d(TAG, "Item at position " + position + " matches user MBTI");
            holder.title.setText(model.getTitle());
            holder.description.setText(model.getPromotionIntroduce());
            holder.likeCount.setText(String.valueOf(model.getLikesCount()));

            String postId = model.getPromotionId();


            if (model.getLikes() != null && model.getLikes().containsKey(userId)) {
                holder.likeButton.setImageResource(R.drawable.icon_love_blue);
            } else {
                holder.likeButton.setImageResource(R.drawable.icon_love_outline);
            }

            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference likesRef = mDatabase.child(postId).child("likes");
                    likesRef.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Map<String, Boolean> likes = (Map<String, Boolean>) mutableData.getValue();
                            if (likes == null) {
                                likes = new HashMap<>();
                                likes.put(userId, true);
                            } else {
                                if (likes.containsKey(userId)) {
                                    likes.remove(userId);
                                } else {
                                    likes.put(userId, true);
                                }
                            }
                            mutableData.setValue(likes);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@NonNull DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                            holder.likeCount.setText(String.valueOf(model.getLikesCount()));
                            if (model.getLikes().containsKey(userId)) {
                                holder.likeButton.setImageResource(R.drawable.icon_love_blue);
                            } else {
                                holder.likeButton.setImageResource(R.drawable.icon_love_outline);
                            }
                        }
                    });
                }
            });

            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    static class MoreSameMBTIViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView postImage;
        ImageButton likeButton;
        TextView likeCount;

        MoreSameMBTIViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_more_post_title);
            description = view.findViewById(R.id.item_more_post_contents);
            postImage = view.findViewById(R.id.item_more_post_img);
            likeButton = view.findViewById(R.id.item_more_white_love);
            likeCount = view.findViewById(R.id.item_more_post_love_count);
        }
    }
}
