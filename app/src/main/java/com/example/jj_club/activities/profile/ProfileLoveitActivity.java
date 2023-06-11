package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.adapters.LoveitAdapter;
import com.example.jj_club.models.LoveitItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileLoveitActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromLoveIt;

    private RecyclerView recycler_loveIt;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager; //리사이클러뷰는 레이아웃매니저랑 연결해줘야하는게 있으
    private ArrayList<LoveitItem> arrayList; //어뎁터에서 만든거랑 똑같게
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_loveit);

        recycler_loveIt=findViewById(R.id.recycler_loveIt);
        recycler_loveIt.setHasFixedSize(true); //리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(this);
        recycler_loveIt.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //User객체를 담은 어레이리스트(어뎁터쪽으로 날릴려고함)

        database = FirebaseDatabase.getInstance(); //파이어베이스db를 가져와라(연동)
        databaseReference = database.getReference("promotions"); //db테이블 연결,//   firebase콘솔의 User를 의미
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //파이어베이스 db를 받아오는곳
                arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){ //반복문으로 데이터 list 추출
                    LoveitItem loveitItem = snapshot1.getValue( LoveitItem.class);  //유저클래스 안에다가 파이어베이스db로 부터 가져온애를 유저클래스에 박아주고 걔를 arraylsit에 담아서 어뎁터로 쏘는거
                    //만들어 뒀던 객체에 데이터를 담는다
                    arrayList.add(loveitItem);                //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { //디비가 가져오던중 에러발생시

                //Log.e("MainActivity",String.valueOf(databaseError.toException())); //에러문 출력
            }
        });
        adapter = new LoveitAdapter(arrayList,this); //커스텀어뎁터 클래스 여기서 소환
        recycler_loveIt.setAdapter(adapter); //리사이클러뷰에 어뎁터 연결


        btn_GoBackProfile_fromLoveIt = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromLoveIt);
        btn_GoBackProfile_fromLoveIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                /*Intent intent = new Intent(ProfileLoveitActivity.this, ProfileFragment.class);
                startActivity(intent);
                */
            }
        });
    }
}