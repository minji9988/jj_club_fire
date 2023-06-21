package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.HomeItemAdapter2;
import com.example.jj_club.adapters.PromotionAdapter;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileLoveitActivity extends AppCompatActivity {


    /*
    private ImageButton btn_GoBackProfile_fromLoveIt;
    private DatabaseReference databaseReference;
    private List<HomeItem> likedPromotions;
    private RecyclerView recyclerView;
    private PromotionAdapter promotionAdapter;
    */
    private ImageButton btn_GoBackProfile_fromLoveIt;
    private RecyclerView mRecyclerView;
    private HomeItemAdapter2  mAdapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserLikesDatabase; // 사용자가 좋아하는 게시물을 저장하는 Firebase 데이터베이스 참조 객체


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_loveit);
/*
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_loveIt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of liked promotions
        likedPromotions = new ArrayList<>();

        // Set up the RecyclerView adapter
        promotionAdapter = new PromotionAdapter(likedPromotions);
        recyclerView.setAdapter(promotionAdapter);

        // Retrieve liked promotions from Firebase
        retrieveLikedPromotions();
        */




        mRecyclerView = findViewById(R.id.recycler_loveIt);  // 리사이클러뷰 초기화
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true); // 역순으로 아이템 표시
        layoutManager.setStackFromEnd(true);// 아래쪽에서부터 아이템 쌓기
        mRecyclerView.setLayoutManager(layoutManager);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");

        Query query = mDatabase;
        FirebaseRecyclerOptions<HomeItem> options =
                new FirebaseRecyclerOptions.Builder<HomeItem>()
                        .setQuery(query, HomeItem.class)
                        .build();

        mAdapter = new HomeItemAdapter2(options, mUserLikesDatabase);
        mRecyclerView.setAdapter(mAdapter);



        btn_GoBackProfile_fromLoveIt = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromLoveIt);
        btn_GoBackProfile_fromLoveIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    /*
    private void retrieveLikedPromotions(){
        DatabaseReference likedPromotionsRef = databaseReference.child("promotions");
        likedPromotionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likedPromotions.clear();
                for (DataSnapshot promothinSnapshot : dataSnapshot.getChildren()){
                    String id = promothinSnapshot.getKey();
                    String title = promothinSnapshot.child()
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }
    private  String getUserId(){
        return ""
    }
    */

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
    private String getCurrentUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}

/*
public class YourActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private List<Promotion> likedPromotions;
    private RecyclerView recyclerView;
    private PromotionAdapter promotionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of liked promotions
        likedPromotions = new ArrayList<>();

        // Set up the RecyclerView adapter
        promotionAdapter = new PromotionAdapter(likedPromotions);
        recyclerView.setAdapter(promotionAdapter);

        // Retrieve liked promotions from Firebase
        retrieveLikedPromotions();
    }

    private void retrieveLikedPromotions() {
        // Get the reference to the liked promotions in the database
        DatabaseReference likedPromotionsRef = databaseReference.child("promotions");

        // Attach a ValueEventListener to listen for data changes
        likedPromotionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likedPromotions.clear(); // Clear the existing list

                // Iterate through each child node (promotion) in the "promotions" node
                for (DataSnapshot promotionSnapshot : dataSnapshot.getChildren()) {
                    // Get the promotion ID, title, and liked status
                    String id = promotionSnapshot.getKey();
                    String title = promotionSnapshot.child("title").getValue(String.class);
                    boolean liked = promotionSnapshot.child("likes").child(getUserId()).getValue(Boolean.class);

                    // Create a Promotion object with the retrieved data
                    Promotion promotion = new Promotion(id, title, liked);

                    // Add the promotion to the list if it is liked
                    if (liked) {
                        likedPromotions.add(promotion);
                    }
                }

                // Notify the adapter that the data has changed
                promotionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Log.e("Firebase", "Error retrieving liked promotions: " + databaseError.getMessage());
            }
        });
    }

    private String getUserId() {
        // Replace this with your logic to get the current user's ID
        return "your_user_id";
    }
}

 */