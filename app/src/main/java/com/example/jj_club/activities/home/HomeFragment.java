package com.example.jj_club.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.PromotionWrite1;
import com.example.jj_club.adapters.HomeItemAdapter;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {

    private RecyclerView latestPostsRecyclerView;
    private HomeItemAdapter adapter;

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

        return view;
    }

    private void setupRecyclerView(View view) {
        Query query = FirebaseFirestore.getInstance()
                .collection("promotions")
                .orderBy("recruitPeriod", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<HomeItem> options = new FirestoreRecyclerOptions.Builder<HomeItem>()
                .setQuery(query, HomeItem.class)
                .build();

        adapter = new HomeItemAdapter(options);

        latestPostsRecyclerView = view.findViewById(R.id.recycler_view_main_page_latest);
        latestPostsRecyclerView.setHasFixedSize(true);
        latestPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        latestPostsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        Log.d(TAG, "Adapter started listening");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
            Log.d(TAG, "Adapter stopped listening");
        }
    }
}
