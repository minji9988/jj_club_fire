package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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

public class ProfileWirtepostActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromWirtepostActivity;
    private RecyclerView mRecyclerView;  // 리사이클러뷰 객체
    private HomeItemAdapter mAdapter;  // 어댑터 객체
    private DatabaseReference mDatabase;  // Firebase 데이터베이스 참조 객체
    private DatabaseReference mUserLikesDatabase;  // 사용자가 좋아하는 게시물을 저장하는 Firebase 데이터베이스 참조 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_wirtepost);

        mRecyclerView = findViewById(R.id.recycler_writePost);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);  // 역순으로 아이템 표시
        layoutManager.setStackFromEnd(true);  // 아래쪽에서부터 아이템 쌓기
        mRecyclerView.setLayoutManager(layoutManager);

        mUserLikesDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(getCurrentUserUid());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        //mUserLikesDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(getCurrentUserUid()).child("likedPosts");

        //내가쓴것만 부르기
        Query query = mDatabase.orderByChild("userId").equalTo(getCurrentUserUid());

        FirebaseRecyclerOptions<HomeItem> options =
                new FirebaseRecyclerOptions.Builder<HomeItem>()
                        .setQuery(query, HomeItem.class)
                        .build();

        mAdapter = new HomeItemAdapter(options, mUserLikesDatabase);
        mRecyclerView.setAdapter(mAdapter);


        btn_GoBackProfile_fromWirtepostActivity = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromWirtepostActivity);
        btn_GoBackProfile_fromWirtepostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

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

}