package com.example.jj_club.activities.myclub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.models.MyClubItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyClubFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyClubAdapter myClubAdapter;
    private List<MyClubItem> myClubItemList = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_club, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_my_club);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myClubAdapter = new MyClubAdapter(myClubItemList, getContext());

        recyclerView.setAdapter(myClubAdapter);

        loadUserClubs();

        return view;
    }

    private void loadUserClubs() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            DatabaseReference promotionsRef = FirebaseDatabase.getInstance().getReference("promotions");

            promotionsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myClubItemList.clear();
                    for (DataSnapshot promotionSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot joinStatusesSnapshot = promotionSnapshot.child("joinStatuses");
                        if (joinStatusesSnapshot.hasChild(currentUserId)) {
                            String status = joinStatusesSnapshot.child(currentUserId).getValue(String.class);
                            if ("approved".equals(status)) {
                                String promotionId = promotionSnapshot.getKey(); // Add this line
                                String meetingName = promotionSnapshot.child("meetingName").getValue(String.class);
                                myClubItemList.add(new MyClubItem(meetingName, promotionId)); // Modify this line
                            }
                        }
                    }
                    myClubAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error...
                }
            });
        }
    }

}
