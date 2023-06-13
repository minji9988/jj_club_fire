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
import com.example.jj_club.adapters.LikePostAdapter;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LikePostActivity extends AppCompatActivity {

    private static final String TAG = "LikedPostActivity";
    private RecyclerView mRecyclerView;  // RecyclerView object
    private LikePostAdapter mAdapter;  // Adapter object
    private DatabaseReference mDatabase;  // Firebase database reference object
    private ImageButton btnBack;  // Back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_like_post);

        // Initialize the RecyclerView
        mRecyclerView = findViewById(R.id.like_page_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);

        // Initialize Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");

        // Create a query to sort posts by likesCount
        Query query = mDatabase.orderByChild("likesCount");

        // Set options to pass to the adapter using FirebaseRecyclerOptions
        FirebaseRecyclerOptions<HomeItem> options =
                new FirebaseRecyclerOptions.Builder<HomeItem>()
                        .setQuery(query, HomeItem.class)
                        .build();

        // Create the adapter and set it on the RecyclerView
        mAdapter = new LikePostAdapter(options);
        mRecyclerView.setAdapter(mAdapter);

        // Handle item click events
        mAdapter.setOnItemClickListener(new LikePostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String postId) {
                // Move to PromotionDetailActivity
                Intent intent = new Intent(LikePostActivity.this, PromotionDetailActivity.class);
                intent.putExtra("promotion_id", postId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Initialize the back button and handle click events
        btnBack = findViewById(R.id.btn_back_like_posts);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // Perform back button action
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();  // Start listening for data in the adapter
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.stopListening();  // Stop listening for data in the adapter
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;  // Release the adapter object
    }

    // Method to return the current user's UID
    private String getCurrentUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "Back button pressed");
    }
}