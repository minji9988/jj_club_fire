package com.example.jj_club.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.PromotionWrite1;
import com.example.jj_club.adapters.MainHomeAdapter;
import com.example.jj_club.models.MainHomeItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private RecyclerView latestPostsRecyclerView;
    private MainHomeAdapter adapter;

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
        writePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PromotionWrite1.class);
                startActivity(intent);
            }
        });

        TextView moreLatestPostsTextView = view.findViewById(R.id.btn_more_latest_posts);
        moreLatestPostsTextView.setOnClickListener(new MoreLatestPostsClickListener());

        return view;
    }

    private void setupRecyclerView(View view) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("promotions");
        DatabaseReference userLikesDatabase = FirebaseDatabase.getInstance().getReference().child("userLikes");
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


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        // adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
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
