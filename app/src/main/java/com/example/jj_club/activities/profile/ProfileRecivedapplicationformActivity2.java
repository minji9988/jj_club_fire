package com.example.jj_club.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.ApplicationForm;
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.models.ApplicationItem;
import com.example.jj_club.models.HomeItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileRecivedapplicationformActivity2 extends AppCompatActivity {

    private FirebaseDatabase database;

    private DatabaseReference mDatabase;

    private TextView tv_received_form_name, tv_received_form_classof, tv_received_form_phone;
    private TextView tv_introduce;
    private Button btn_approval;

    private ApplicationItem model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_received_item2);

        tv_received_form_name = findViewById(R.id. tv_received_form_name);
        tv_received_form_classof = findViewById(R.id. tv_received_form_classof);
        tv_received_form_phone = findViewById(R.id. tv_received_form_phone);
        tv_introduce = findViewById(R.id. tv_introduce);

        mDatabase=FirebaseDatabase.getInstance().getReference().child("applicationItems");


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String key = getIntent().getStringExtra("sendToUserId");
        if (key != null){
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("applicationItems").child(key);

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ApplicationItem applicationItem = dataSnapshot.getValue(ApplicationItem.class);
                    tv_received_form_name.setText(applicationItem.getAppName());
                    tv_received_form_classof.setText(applicationItem.getAppNumber());
                    tv_received_form_phone.setText(applicationItem.getAppPhone());
                    tv_introduce.setText(applicationItem.getAppIntro());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {

        }
        
        //승인 버튼 눌렀을때 appApproval이 true가 되도록
        btn_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모델 객체의 승인(appApproval 정보가 없는 경우, 새로운 해시맵 객체를 생성하여 설정
                if(model.getAppApproval()==null){
                    model.setAppApproval(new HashMap<>());
                }
                // 승인 처음 누른 경우, 승인 정보에 해당 사용자를 추가하고 데이터베이스에 업데이트
                else {
                    model.getAppApproval().put(userId, true);
                    //밑에 userId가 아니라 postId로 해야하나
                    mDatabase.child(userId).child("appApproval").setValue(model.getAppApproval());
                }
            }
        });

        
    }
}