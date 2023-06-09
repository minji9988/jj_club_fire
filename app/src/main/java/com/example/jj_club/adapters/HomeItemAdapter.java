package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeItemAdapter extends FirebaseRecyclerAdapter<HomeItem, HomeItemAdapter.HomeItemViewHolder> {

    private DatabaseReference mDatabase;
    private DatabaseReference mUserLikesDatabase;

    public HomeItemAdapter(@NonNull FirebaseRecyclerOptions<HomeItem> options, DatabaseReference userLikesDatabase) {
        super(options);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        mUserLikesDatabase = userLikesDatabase;
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
        holder.description.setText(model.getRecruitPeriod());
        holder.likeCount.setText(String.valueOf(model.getLikes()));

        String postId = getRef(position).getKey();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // 좋아요 상태 확인
        if (model.getLikes() > 0) {
            holder.likeButton.setImageResource(R.drawable.icon_love_blue);
        } else {
            holder.likeButton.setImageResource(R.drawable.icon_love_outline);
        }

        // 좋아요 클릭 이벤트 처리
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 좋아요 상태 변경
                if (model.getLikes() > 0) {
                    unlikePost(userId, postId, holder);
                } else {
                    likePost(userId, postId, holder);
                }
            }
        });
    }

    private void likePost(String userId, String postId, HomeItemViewHolder holder) {
        DatabaseReference userRef = mUserLikesDatabase;
        userRef.child(postId).setValue(true)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(holder.itemView.getContext(), "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                    holder.likeButton.setImageResource(R.drawable.icon_love_blue);

                    // 좋아요 개수 증가
                    int currentLikes = getItem(holder.getAdapterPosition()).getLikes();
                    mDatabase.child(postId).child("likes").setValue(currentLikes + 1);
                    getItem(holder.getAdapterPosition()).setLikes(currentLikes + 1); // update the model

                    holder.likeCount.setText(String.valueOf(currentLikes + 1)); // 좋아요 개수 갱신
                })
                .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(), "좋아요를 실패했습니다.", Toast.LENGTH_SHORT).show());
    }

    private void unlikePost(String userId, String postId, HomeItemViewHolder holder) {
        DatabaseReference userRef = mUserLikesDatabase;
        userRef.child(postId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(holder.itemView.getContext(), "좋아요를 취소했습니다.", Toast.LENGTH_SHORT).show();
                    holder.likeButton.setImageResource(R.drawable.icon_love_outline);

                    // 좋아요 개수 감소
                    int currentLikes = getItem(holder.getAdapterPosition()).getLikes();
                    if (currentLikes > 0) {
                        mDatabase.child(postId).child("likes").setValue(currentLikes - 1);
                        getItem(holder.getAdapterPosition()).setLikes(currentLikes - 1); // update the model

                        holder.likeCount.setText(String.valueOf(currentLikes - 1)); // 좋아요 개수 갱신
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(), "좋아요 취소를 실패했습니다.", Toast.LENGTH_SHORT).show());
    }

    static class HomeItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageButton likeButton;
        TextView likeCount;  // 좋아요 수를 표시하는 TextView

        HomeItemViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_more_post_title);
            description = view.findViewById(R.id.item_more_post_contents);
            likeButton = view.findViewById(R.id.item_more_white_love);
            likeCount = view.findViewById(R.id.item_more_post_love_count); // 좋아요 수 표시
        }
    }
}
