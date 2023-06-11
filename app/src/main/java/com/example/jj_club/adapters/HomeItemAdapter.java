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

// 최신글 더보기 페이지와 연결된 것

public class HomeItemAdapter extends FirebaseRecyclerAdapter<HomeItem, HomeItemAdapter.HomeItemViewHolder> {

    private DatabaseReference mDatabase; // Firebase 실시간 데이터베이스 참조
    private DatabaseReference mUserLikesDatabase; // 사용자 좋아요 정보 데이터베이스 참조

    public HomeItemAdapter(@NonNull FirebaseRecyclerOptions<HomeItem> options, DatabaseReference userLikesDatabase) {
        super(options);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        mUserLikesDatabase = userLikesDatabase;
    }

    /**
     * 뷰홀더 생성: xml 파일의 post_item.xml와 연결하는 느낌.
     */
    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 뷰 인플레이팅
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_more_item, parent, false);
        return new HomeItemViewHolder(view);
    }

    /**
     * 뷰홀더에 데이터 바인딩
     */
    @Override
    protected void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position, @NonNull HomeItem model) {

        // 데이터 설정
        holder.title.setText(model.getTitle()); // 게시물 제목 설정
        holder.description.setText(model.getPromotionIntroduce()); // 모집 기간 설정
        holder.likeCount.setText(String.valueOf(model.getLikes() != null ? model.getLikes().size() : 0)); // 좋아요 수 설정

        String postId = getRef(position).getKey(); // 게시물 식별자 가져오기
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 사용자 ID 가져오기

        // 이미지 설정
        if (model.getImageUrl() != null && !model.getImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(model.getImageUrl())
                    .into(holder.postImage);
        }

        // 좋아요 버튼 상태 설정
        if (model.getLikes() != null && model.getLikes().containsKey(userId)) {
            holder.likeButton.setImageResource(R.drawable.icon_love_blue);
        } else {
            holder.likeButton.setImageResource(R.drawable.icon_love_outline);
        }

        // 좋아요 버튼 클릭 리스너 설정
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getLikes() == null) {
                    model.setLikes(new HashMap<>());
                }

                // 좋아요 상태 토글
                if (model.getLikes().containsKey(userId)) {
                    model.getLikes().remove(userId);
                    mDatabase.child(postId).child("likes").setValue(model.getLikes().isEmpty() ? null : model.getLikes());
                } else {
                    model.getLikes().put(userId, true);
                    mDatabase.child(postId).child("likes").setValue(model.getLikes());
                }
            }
        });
    }

    // 뷰홀더 클래스
    static class HomeItemViewHolder extends RecyclerView.ViewHolder {
        TextView title; // 게시물 제목
        ImageView postImage; // 게시물 이미지
        TextView description; // 글 상세 소개
        ImageButton likeButton; // 좋아요 버튼
        TextView likeCount; // 좋아요 수

        HomeItemViewHolder(View view) {
            super(view);
            // 아이템 뷰 내부에서 뷰 바인딩
            postImage = view.findViewById(R.id.item_more_post_img);
            title = view.findViewById(R.id.item_more_post_title);
            description = view.findViewById(R.id.item_more_post_contents);
            likeButton = view.findViewById(R.id.item_more_white_love);
            likeCount = view.findViewById(R.id.item_more_post_love_count);
        }
    }
}
