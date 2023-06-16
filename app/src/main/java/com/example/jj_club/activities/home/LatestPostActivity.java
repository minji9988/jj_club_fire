package com.example.jj_club.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.adapters.HomeItemAdapter;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LatestPostActivity extends AppCompatActivity {

    private static final String TAG = "LatestPostActivity";
    private RecyclerView mRecyclerView;  // 리사이클러뷰 객체
    private HomeItemAdapter mAdapter;  // 어댑터 객체
    private DatabaseReference mDatabase;  // Firebase 데이터베이스 참조 객체
    private DatabaseReference mUserLikesDatabase;  // 사용자가 좋아하는 게시물을 저장하는 Firebase 데이터베이스 참조 객체
    private ImageButton btnBack;  // 뒤로가기 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_latest_post);

        // 리사이클러뷰 초기화
        mRecyclerView = findViewById(R.id.latest_page_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);  // 역순으로 아이템 표시
        layoutManager.setStackFromEnd(true);  // 아래쪽에서부터 아이템 쌓기
        mRecyclerView.setLayoutManager(layoutManager);

        // Firebase 데이터베이스 참조 초기화
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        mUserLikesDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(getCurrentUserUid()).child("likedPosts");

        // 게시물을 시간 순서대로 정렬하기 위한 쿼리 생성
        Query query = mDatabase.orderByChild("timeStamp");

        // FirebaseRecyclerOptions를 사용하여 어댑터에 전달할 옵션 설정
        FirebaseRecyclerOptions<HomeItem> options =
                new FirebaseRecyclerOptions.Builder<HomeItem>()
                        .setQuery(query, HomeItem.class)
                        .build();

        // 어댑터 생성 및 리사이클러뷰에 설정
        mAdapter = new HomeItemAdapter(options, mUserLikesDatabase);
        mRecyclerView.setAdapter(mAdapter);

        // 아이템 클릭 이벤트 처리
        mAdapter.setOnItemClickListener(new HomeItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String postId) {
                // PromotionDetailActivity로 이동
                Intent intent = new Intent(LatestPostActivity.this, PromotionDetailActivity.class);
                intent.putExtra("promotion_id", postId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });


        // 뒤로가기 버튼 초기화 및 클릭 이벤트 처리
        btnBack = findViewById(R.id.btn_back_latest_posts);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // 뒤로가기 동작 수행
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();  // 어댑터의 데이터 청취 시작
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.stopListening();  // 어댑터의 데이터 청취 중지
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;  // 어댑터 객체 해제
    }

    // 현재 사용자의 UID를 반환하는 메서드
    private String getCurrentUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "Back button pressed");
    }
}
