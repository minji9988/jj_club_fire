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

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private RecyclerView.LayoutManager layoutManager; //리사이클러뷰는 레이아웃매니저랑 연결해줘야하는게 있으
    private ArrayList<ApplicationItem> arrayList; //어뎁터에서 만든거랑 똑같게
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ImageButton btn_GoBackProfile_fromRecivedapplicationformActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_recivedapplicationform);

        recyclerView=findViewById(R.id.recycler_receivedForm);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //User객체를 담은 어레이리스트(어뎁터쪽으로 날릴려고함)

        database = FirebaseDatabase.getInstance(); //파이어베이스db를 가져와라(연동)
        databaseReference = database.getReference("applicationItems"); //db테이블 연결,   firebase콘솔의 applicationItems를 의미


        // 사용자 uid사져오기
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // 받은 신청서만 쿼리(해당 부분 수정)
        Query query = database.getReference("applicationItems").orderByChild("sendToUserId").equalTo(currentUserId);
        // 기존 코드: databaseReference = database.getReference("applicationItems").orderByChild("sendToUserId").equalTo(currentUserId).getRef(); //추가1
        // 이미 48줄에 databaseReference를 선언했는데 또 선언하고 있음.
        // 55번째 줄에서 getRef() 메소드는 원래의 레퍼런스를 반환(하는 메소드이므로, 즉 48번째 줄에 있는 모든 신청서들을 반환하게 돼서
        // orderByChild와 equalTo 호출이 무시되는 문제가 발생. 이 쿼리가 사용자가 받은 신청서만 보여주도록 하는 쿼리인데 이걸 무시하게 되니
        // 자연스럽게 모든 신청서가 노출되는 문제 발생

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ApplicationItem applicationItem = snapshot1.getValue(ApplicationItem.class);
                    arrayList.add(applicationItem);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapter = new ApplicationItemAdapter(arrayList,this); //커스텀어뎁터 클래스 여기서 소환
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어뎁터 연결


        btn_GoBackProfile_fromRecivedapplicationformActivity = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromRecivedapplicationformActivity);
        btn_GoBackProfile_fromRecivedapplicationformActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        
        //추가
        /*
        public void onItemClick(int position;) {
            ApplicationItem applicationItem = arrayList.get(position);
            String fromUserId = applicationItem.getFromUserId();

            // Pass the fromUserId to the next activity
            Intent intent = new Intent(ProfileRecivedapplicationformActivity.this, ProfileRecivedapplicationformActivity2.class);
            intent.putExtra("fromUserId", fromUserId);
            startActivity(intent);
        }
         */


    }
}