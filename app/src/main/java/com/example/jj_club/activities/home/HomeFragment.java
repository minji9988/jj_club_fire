package com.example.jj_club.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.mbti.MbtiTestStart;
import com.example.jj_club.activities.promotion.PromotionWrite1;
import com.example.jj_club.adapters.MBTIFilteredHomeAdapter;
import com.example.jj_club.adapters.MainHomeAdapter;
import com.example.jj_club.adapters.PopularPostAdapter;
import com.example.jj_club.models.MainHomeItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private RecyclerView popularPostsRecyclerView;
    private PopularPostAdapter popularPostAdapter;

    private RecyclerView latestPostsRecyclerView;
    private MainHomeAdapter adapter;

    private RecyclerView sameMBTIPostsRecyclerView;
    private MBTIFilteredHomeAdapter MBTIFilteredHomeAdapter;
    private String userMBTI = "ENFJ";


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setupRecyclerView(view);

        ImageButton writePostButton = view.findViewById(R.id.btn_write_post);
        ImageView blurImageView = view.findViewById(R.id.fragment_home_blur);
        writePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PromotionWrite1.class);
                startActivity(intent);
            }
        });

        // Find the home_mbti_test_btn button
        Button mbtiTestButton = view.findViewById(R.id.home_mbti_test_btn);

// fetch user information from database
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 사용자가 MBTI 검사를 완료했는지 확인합니다.
                boolean mbtiTestCompleted = dataSnapshot.child("mbti").exists();
                if (mbtiTestCompleted) {
                    // 사용자가 MBTI 검사를 완료한 경우, 블러 이미지를 숨깁니다.
                    mbtiTestButton.setVisibility(View.GONE);
                    blurImageView.setVisibility(View.GONE);
                } else {
                    // 사용자가 MBTI 검사를 아직 완료하지 않은 경우, 블러 이미지를 표시합니다.
                    mbtiTestButton.setVisibility(View.VISIBLE);
                    blurImageView.setVisibility(View.VISIBLE);


                    // Set a click listener on the button
                    mbtiTestButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Start the MbtiTestStartActivity when the button is clicked
                            Intent intent = new Intent(getActivity(), MbtiTestStart.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // handle error
            }
        });



        TextView moreLatestPostsTextView = view.findViewById(R.id.btn_more_latest_posts);
        moreLatestPostsTextView.setOnClickListener(new MoreLatestPostsClickListener());

        TextView morePopularPostsButton = view.findViewById(R.id.btn_more_popular_posts);
        morePopularPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LikePostActivity.class);
                startActivity(intent);
            }
        });

        // Find the btn_more_same_mbti_posts button
        TextView moreSameMBTIPostsButton = view.findViewById(R.id.btn_more_same_mbti_posts);

        // Set a click listener on the button
        moreSameMBTIPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SameMBTIActivity when the button is clicked
                Intent intent = new Intent(getActivity(), SameMBTIActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setupRecyclerView(View view) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("promotions");
        DatabaseReference userLikesDatabase = FirebaseDatabase.getInstance().getReference().child("userLikes");

        setupLatestPosts(view, databaseRef);
        setupPopularPosts(view, databaseRef);
        setupSameMBTIPosts(view, databaseRef);

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 데이터 로드가 완료된 후, RecyclerView의 스크롤 위치를 설정
                latestPostsRecyclerView.scrollToPosition(0);
                popularPostsRecyclerView.scrollToPosition(0);
                sameMBTIPostsRecyclerView.scrollToPosition(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 발생 시 처리
                logMessage(databaseError.getMessage());
            }
        });
    }

    private void setupLatestPosts(View view, DatabaseReference databaseRef) {
        Query query = databaseRef.orderByChild("reversedTimestamp");
        FirebaseRecyclerOptions<MainHomeItem> options = new FirebaseRecyclerOptions.Builder<MainHomeItem>()
                .setQuery(query, MainHomeItem.class)
                .build();

        adapter = new MainHomeAdapter(options);

        latestPostsRecyclerView = view.findViewById(R.id.recycler_view_main_page_latest);
        latestPostsRecyclerView.setHasFixedSize(true);
        latestPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        latestPostsRecyclerView.setAdapter(adapter);
    }

    private void setupPopularPosts(View view, DatabaseReference databaseRef) {
        Query popularPostsQuery = databaseRef.orderByChild("likesCount");  // likesCount를 기준으로 오름차순 정렬
        FirebaseRecyclerOptions<MainHomeItem> popularPostsOptions = new FirebaseRecyclerOptions.Builder<MainHomeItem>()
                .setQuery(popularPostsQuery, MainHomeItem.class)
                .build();

        popularPostAdapter = new PopularPostAdapter(popularPostsOptions);

        popularPostsRecyclerView = view.findViewById(R.id.recycler_view_main_page_popular);
        popularPostsRecyclerView.setHasFixedSize(true);
        popularPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularPostsRecyclerView.setAdapter(popularPostAdapter);
    }

    private void setupSameMBTIPosts(View view, DatabaseReference databaseRef) {
        Query sameMBTIPostsQuery = databaseRef.orderByChild("reversedTimestamp");
        FirebaseRecyclerOptions<MainHomeItem> sameMBTIPostsOptions = new FirebaseRecyclerOptions.Builder<MainHomeItem>()
                .setQuery(sameMBTIPostsQuery, MainHomeItem.class)
                .build();

        MBTIFilteredHomeAdapter = new MBTIFilteredHomeAdapter(sameMBTIPostsOptions, userMBTI);

        sameMBTIPostsRecyclerView = view.findViewById(R.id.recycler_view_main_page_same_mbti);
        sameMBTIPostsRecyclerView.setHasFixedSize(true);
        sameMBTIPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        sameMBTIPostsRecyclerView.setAdapter(MBTIFilteredHomeAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
        if (popularPostAdapter != null) {
            popularPostAdapter.startListening();
        }
        if (MBTIFilteredHomeAdapter != null) {
            MBTIFilteredHomeAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        adapter.stopListening();
//        popularPostAdapter.stopListening();  // stop listening to popularPostAdapter when the fragment is not visible
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
        MBTIFilteredHomeAdapter.stopListening();
        popularPostAdapter.stopListening();  // stop listening to popularPostAdapter when the view is destroyed
    }

    private class MoreLatestPostsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Navigate to LatestPostActivity when "more" is clicked.
            Intent intent = new Intent(getActivity(), LatestPostActivity.class);
            startActivity(intent);
        }
    }

    // Logcat에 오류 및 디버그 메시지 출력을 위한 메서드
    private void logMessage(String message) {
        Log.d(TAG, message);
    }
}