package com.example.jj_club.activities.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.MoreSameMBTIAdapter;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

                // Create query to get all promotions
                // Use a query that gets all promotions
                Query allPromotionsQuery = promotionsRef;

                FirebaseRecyclerOptions<HomeItem> options = new FirebaseRecyclerOptions.Builder<HomeItem>()
                        .setQuery(allPromotionsQuery, HomeItem.class)
                        .build();

                adapter = new MoreSameMBTIAdapter(options, userMBTI);


                // Use a custom adapter that filters based on the user's MBTI

                sameMBTIRecyclerView = findViewById(R.id.same_MBTI_page_recyclerview);
                sameMBTIRecyclerView.setHasFixedSize(true);
                sameMBTIRecyclerView.setLayoutManager(new LinearLayoutManager(SameMBTIActivity.this));
                sameMBTIRecyclerView.setAdapter(adapter);

                adapter.startListening();
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
        if(adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }
}