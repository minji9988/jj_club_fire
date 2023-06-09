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
    private RecyclerView mRecyclerView;
    private HomeItemAdapter mAdapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserLikesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_latest_post);

        mRecyclerView = findViewById(R.id.latest_page_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true); // 역순으로 표시
        layoutManager.setStackFromEnd(true); // 아래쪽에서부터 쌓기
        mRecyclerView.setLayoutManager(layoutManager);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        mUserLikesDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(getCurrentUserUid()).child("likedPosts");
        Query query = mDatabase.orderByChild("timeStamp");

        FirebaseRecyclerOptions<HomeItem> options =
                new FirebaseRecyclerOptions.Builder<HomeItem>()
                        .setQuery(query, HomeItem.class)
                        .build();

        mAdapter = new HomeItemAdapter(options, mUserLikesDatabase);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }

    private String getCurrentUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
