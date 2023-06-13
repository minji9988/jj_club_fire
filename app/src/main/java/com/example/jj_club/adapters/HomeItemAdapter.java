package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HomeItemAdapter extends FirebaseRecyclerAdapter<HomeItem, HomeItemAdapter.HomeItemViewHolder> {

    private DatabaseReference mDatabase;
    private DatabaseReference mUserLikesDatabase;
    private OnItemClickListener mListener;

    public HomeItemAdapter(@NonNull FirebaseRecyclerOptions<HomeItem> options, DatabaseReference userLikesDatabase) {
        super(options);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        mUserLikesDatabase = userLikesDatabase; //this추가
    }

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_more_item, parent, false);
        return new HomeItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position, @NonNull HomeItem model) {
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getPromotionIntroduce());
        holder.likeCount.setText(String.valueOf(model.getLikes() != null ? model.getLikes().size() : 0));

        String postId = getRef(position).getKey();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
                if (model.getLikes() == null) {
                    model.setLikes(new HashMap<>());
                }

                if (model.getLikes().containsKey(userId)) {
                    model.getLikes().remove(userId);
                    mDatabase.child(postId).child("likes").setValue(model.getLikes().isEmpty() ? null : model.getLikes());
                } else {
                    model.getLikes().put(userId, true);
                    mDatabase.child(postId).child("likes").setValue(model.getLikes());
                }
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

    static class HomeItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView postImage;
        TextView description;
        ImageButton likeButton;
        TextView likeCount;

        HomeItemViewHolder(View view) {
            super(view);
            postImage = view.findViewById(R.id.item_more_post_img);
            title = view.findViewById(R.id.item_more_post_title);
            description = view.findViewById(R.id.item_more_post_contents);
            likeButton = view.findViewById(R.id.item_more_white_love);
            likeCount = view.findViewById(R.id.item_more_post_love_count);
        }

        public void setLiked(boolean isLiked) {

            // 좋아요 상태에 따라 UI를 업데이트하는 코드 작성
            if (isLiked) {
                // Display the home_more_item.xml layout
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                View homeMoreItemView = inflater.inflate(R.layout.home_more_item, (ViewGroup) itemView, false);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                ((ViewGroup) itemView).removeAllViews();
                ((ViewGroup) itemView).addView(homeMoreItemView, layoutParams);
            } else {
                // Remove any previous home_more_item.xml layout if not liked
               ((ViewGroup) itemView).removeAllViews();
            }
        }
        
    }
}
