package com.example.jj_club.adapters;

import android.content.Intent;
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
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomeItemAdapter2 extends FirebaseRecyclerAdapter<HomeItem, HomeItemAdapter2.HomeItemViewHolder> {

    private DatabaseReference mDatabase;
    private DatabaseReference mUserLikesDatabase;

    public HomeItemAdapter2(@NonNull FirebaseRecyclerOptions<HomeItem> options, DatabaseReference userLikesDatabase) {
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

        //좋아요 누른것만 뜨게하는 추가부분, 이거 추가하면 최신글이 안보인다
        if (model.getLikes() != null && model.getLikes().containsKey(userId) && model.getLikes().get(userId)) {
            holder.title.setText(model.getTitle());
            holder.description.setText(model.getPromotionIntroduce());
            holder.likeCount.setText(String.valueOf(model.getLikes() != null ? model.getLikes().size() : 0));

            // 나머지 코드
        } else {
            holder.itemView.setVisibility(View.GONE); // 좋아요가 true가 아닌 경우 해당 아이템을 숨깁니다.
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

        // itemView를 클릭했을 때 PromotionDetailActivity로 이동합니다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PromotionDetailActivity.class);
                intent.putExtra("PostId", postId);
                v.getContext().startActivity(intent);
            }
        });
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
