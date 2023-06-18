package com.example.jj_club.activities.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.HomeItemAdapter;
import com.example.jj_club.adapters.SearchAdapter;
import com.example.jj_club.models.HomeItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SearchView search_view;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private List<HomeItem> homeItemList;
    private SearchAdapter searchAdapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_search);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("promotions");

        recyclerView = findViewById(R.id.recycler_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeItemList = new ArrayList<>();
        searchAdapter = new SearchAdapter(homeItemList);
        recyclerView.setAdapter(searchAdapter);

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = s.toString().toLowerCase();
                filterHomeItems(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                homeItemList.clear();
                for(DataSnapshot  homeItemSnapshot : dataSnapshot.getChildren()){
                    HomeItem homeItem = homeItemSnapshot.getValue(HomeItem.class);
                    homeItemList.add(homeItem);
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, "Error: "+error.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        });
    }
    private void filterHomeItems(String searchQuery){
        List<HomeItem> filteredList = new ArrayList<>();
        for (HomeItem homeItem : homeItemList){
            if (homeItem.getTitle().toLowerCase().contains(searchQuery)){
                filteredList.add(homeItem);
            }
        }
        searchAdapter.filterList(filteredList);
    }
}