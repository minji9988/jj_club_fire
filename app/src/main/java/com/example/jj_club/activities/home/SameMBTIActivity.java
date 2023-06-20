package com.example.jj_club.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.adapters.MoreSameMBTIAdapter;
import com.example.jj_club.models.HomeItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SameMBTIActivity extends AppCompatActivity {

    private static final String TAG = "SameMBTIActivity";

    private RecyclerView sameMBTIRecyclerView;
    private MoreSameMBTIAdapter adapter;


    private FirebaseUser currentUser;
    private DatabaseReference usersRef;
    private DatabaseReference promotionsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_same_mbtiactivity);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        promotionsRef = FirebaseDatabase.getInstance().getReference().child("promotions");

        setupRecyclerView();

        ImageButton btnBackSamePosts = findViewById(R.id.btn_back_same_posts);
        btnBackSamePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setupRecyclerView() {
        // Fetch current user's MBTI
        usersRef.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userMBTI = snapshot.child("mbti").getValue(String.class);
                Log.d(TAG, "User's MBTI: " + userMBTI);

                promotionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<HomeItem> filteredItems = new ArrayList<>();

                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            HomeItem item = postSnapshot.getValue(HomeItem.class);
                            if (item != null) {
                                List<String> selectedButtons = item.getSelectedButtons();
                                if (selectedButtons != null && selectedButtons.contains(userMBTI)) {
                                    filteredItems.add(item);
                                }
                            }
                        }

                        // Now use the filtered list with the MoreSameMBTIAdapter
                        adapter = new MoreSameMBTIAdapter(filteredItems, userMBTI);

                        // Add this after setting the adapter:
                        adapter.setOnItemClickListener(new MoreSameMBTIAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(HomeItem item, String key) {
                                Intent intent = new Intent(SameMBTIActivity.this, PromotionDetailActivity.class);
                                intent.putExtra("selectedItem", item);  // Ensure HomeItem is Serializable or Parcelable
                                intent.putExtra("promotion_id", key);
                                startActivity(intent);
                            }
                        });


                        sameMBTIRecyclerView = findViewById(R.id.same_MBTI_page_recyclerview);
                        sameMBTIRecyclerView.setHasFixedSize(true);
                        sameMBTIRecyclerView.setLayoutManager(new LinearLayoutManager(SameMBTIActivity.this));
                        sameMBTIRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "Error fetching promotions: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Error fetching user's MBTI: " + error.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // No need to start listening as we are not using FirebaseRecyclerAdapter
    }

    @Override
    protected void onStop() {
        super.onStop();
        // No need to stop listening as we are not using FirebaseRecyclerAdapter
    }
}