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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.Map;

public class MoreSameMBTIAdapter extends FirebaseRecyclerAdapter<HomeItem, MoreSameMBTIAdapter.MoreSameMBTIViewHolder> {


    private static final String TAG = "MoreSameMBTIAdapter";
    private DatabaseReference mDatabase;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String userMBTI;  // User's MBTI

    public MoreSameMBTIAdapter(@NonNull FirebaseRecyclerOptions<HomeItem> options, String userMBTI) {
        super(options);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        this.userMBTI = userMBTI;  // Set user's MBTI
        Log.d(TAG, "Adapter initialized with user MBTI: " + userMBTI);
    }

    @NonNull
    @Override
    public MoreSameMBTIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_more_item, parent, false);
        return new MoreSameMBTIViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MoreSameMBTIViewHolder holder, int position, @NonNull HomeItem model) {
        Log.d(TAG, "onBindViewHolder called for position " + position);
        Log.d(TAG, "Model toString: " + model.toString());  // 이 코드는 MainHomeItem에서 toString() 메소드를 오버라이드 한 경우 유용합니다.

        // 아래는 각 필드를 직접 로그에 출력하는 코드입니다. 필요에 따라 수정하십시오.
        Log.d(TAG, "Model promotionIntroduce: " + model.getPromotionIntroduce());
        Log.d(TAG, "Model imageUrl: " + model.getImageUrl());
        Log.d(TAG, "Model selectedButtons: " + model.getSelectedButtons());

        // selectedButtons null check
        if (model.getSelectedButtons() == null) {
            Log.d("MoreSameMBTIAdapter", "Model selectedButtons is null at position: " + position);
            // Do not update the view if selectedButtons is null.
            return;
        } else {
            Log.d("MoreSameMBTIAdapter", "Model selectedButtons: " + model.getSelectedButtons());
            // Rest of your code...
        }

        // Only display this item if it matches the user's MBTI
        if (model.getSelectedButtons().contains(userMBTI)) {
            Log.d(TAG, "Item at position " + position + " matches user MBTI");
            holder.title.setText(model.getTitle());
            holder.description.setText(model.getPromotionIntroduce());
            holder.likeCount.setText(String.valueOf(model.getLikesCount()));

            // handle setting image etc similar to LikePostAdapter

            String postId = getRef(position).getKey();

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
                                    // Unstar the post and remove self from stars
                                    likes.remove(userId);
                                } else {
                                    // Star the post and add self to stars
                                    likes.put(userId, true);
                                }
                            }
                            // Set value and report transaction success
                            mutableData.setValue(likes);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@NonNull DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                            // Update UI on transaction completion
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

            // Make the view visible
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            // Hide the view
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
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