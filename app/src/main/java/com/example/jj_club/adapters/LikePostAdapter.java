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

import com.bumptech.glide.Glide;
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

public class LikePostAdapter extends FirebaseRecyclerAdapter<HomeItem, LikePostAdapter.LikePostViewHolder> {

    private DatabaseReference mDatabase;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private OnItemClickListener mListener;

    private static final String TAG = "LikePostAdapter";


    public LikePostAdapter(@NonNull FirebaseRecyclerOptions<HomeItem> options) {
        super(options);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
    }

    @NonNull
    @Override
    public LikePostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_more_item, parent, false);
        return new LikePostViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull LikePostViewHolder holder, int position, @NonNull HomeItem model) {
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getPromotionIntroduce());
        holder.likeCount.setText(String.valueOf(model.getLikes() != null ? model.getLikes().size() : 0));  // Added this line

        String postId = getRef(position).getKey();

        if (model.getImageUrl() != null && !model.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(model.getImageUrl())
                    .into(holder.postImage);
        }

        if (model.getLikes() != null && model.getLikes().containsKey(userId)) {
            holder.likeButton.setImageResource(R.drawable.icon_love_blue);
        } else {
            holder.likeButton.setImageResource(R.drawable.icon_love_outline);
        }

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference likesRef = mDatabase.child(postId).child("likes");
                DatabaseReference likesCountRef = mDatabase.child(postId).child("likesCount");

                // Start a transaction on the likes count
                likesCountRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        if (model.getLikes() == null) {
                            model.setLikes(new HashMap<>());
                        }

                        if (model.getLikes().containsKey(userId)) {
                            model.getLikes().remove(userId);
                            // Decrement the like count
                            mutableData.setValue((Long) mutableData.getValue() - 1);
                        } else {
                            model.getLikes().put(userId, true);
                            // Increment the like count
                            mutableData.setValue((Long) mutableData.getValue() + 1);
                        }

                        // Set the likes map
                        likesRef.setValue(model.getLikes().isEmpty() ? null : model.getLikes());

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            Log.e(TAG, "Could not update likes count", databaseError.toException());
                        } else if (committed) {
                            // Update the likeCount view
                            holder.likeCount.setText(String.valueOf(model.getLikes() != null ? model.getLikes().size() : 0));  // Added this line
                        }
                    }
                });
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(postId);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(String postId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class LikePostViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView postImage;
        TextView description;
        ImageButton likeButton;
        TextView likeCount;  // Added this line

        LikePostViewHolder(View view) {
            super(view);
            postImage = view.findViewById(R.id.item_more_post_img);
            title = view.findViewById(R.id.item_more_post_title);
            description = view.findViewById(R.id.item_more_post_contents);
            likeButton = view.findViewById(R.id.item_more_white_love);
            likeCount = view.findViewById(R.id.item_more_post_love_count);  // Added this line
        }
    }
}
