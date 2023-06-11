package com.example.jj_club.activities.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
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
    protected void onStop() {
        super.onStop();
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
}
