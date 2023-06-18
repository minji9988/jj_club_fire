package com.example.jj_club.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.ApplicationItemSendAdapter;
import com.example.jj_club.models.ApplicationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileSendapplicationformActivity extends AppCompatActivity {
    private static final String TAG = "ProfileSendapplicationformActivity";
    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private ApplicationItemSendAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ApplicationItem> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ImageButton btn_GoBackProfile_fromSendapplicationformActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_sendapplicationform);

        btn_GoBackProfile_fromSendapplicationformActivity = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromSendapplicationformActivity);

        recyclerView = findViewById(R.id.recycler_sendForm);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance(); //파이어베이스db를 가져와라(연동)
        databaseReference = database.getReference("applicationItems");

        //내가 쓴것만 가져오기
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = database.getReference("applicationItems").orderByChild("fromUserId").equalTo(currentUserId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ApplicationItem applicationItem = snapshot1.getValue(ApplicationItem.class);
                    applicationItem.setApplicationId(snapshot1.getKey());
                    arrayList.add(applicationItem);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mAdapter = new ApplicationItemSendAdapter(arrayList,this);
        recyclerView.setAdapter(mAdapter);

        btn_GoBackProfile_fromSendapplicationformActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}