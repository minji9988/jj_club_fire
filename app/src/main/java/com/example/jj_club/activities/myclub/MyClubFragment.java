package com.example.jj_club.activities.myclub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
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
    private List<Club> clubList;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_club, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        clubList = new ArrayList<>();
        myClubAdapter = new MyClubAdapter(getActivity(), clubList);
        recyclerView.setAdapter(myClubAdapter);

        // Firebase에서 데이터 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference("clubs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clubList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Club club = snapshot.getValue(Club.class);
                    clubList.add(club);
                }
                myClubAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "데이터를 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
