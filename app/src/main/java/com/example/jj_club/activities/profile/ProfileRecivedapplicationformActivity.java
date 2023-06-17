package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.ApplicationItemAdapter;
import com.example.jj_club.models.ApplicationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileRecivedapplicationformActivity extends AppCompatActivity {

    private static final String TAG = "ProfileRecivedapplicationformActivity";
    private RecyclerView recyclerView;

    private ApplicationItemAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<ApplicationItem> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ImageButton btn_GoBackProfile_fromRecivedapplicationformActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_recivedapplicationform);

        recyclerView = findViewById(R.id.recycler_receivedForm);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("applicationItems");

        // 사용자 uid 가져오기
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = database.getReference("applicationItems").orderByChild("sendToUserId").equalTo(currentUserId);

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
                // Handle error...
            }
        });

        mAdapter = new ApplicationItemAdapter(arrayList, this);
        recyclerView.setAdapter(mAdapter);

        btn_GoBackProfile_fromRecivedapplicationformActivity = findViewById(R.id.btn_GoBackProfile_fromRecivedapplicationformActivity);
        btn_GoBackProfile_fromRecivedapplicationformActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
