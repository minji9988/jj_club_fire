package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.models.ApplicationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileSendapplicationformActivity2 extends AppCompatActivity {

    private DatabaseReference mDatabase;

    //send는 뒤에다 2붙임
    private TextView tv_received_form_name2,tv_received_form_classof2, tv_received_form_phone2;
    private TextView tv_introduce2;
    private ApplicationItem model;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_send_item2);

        tv_received_form_name2 = findViewById(R.id. tv_received_form_name2);
        tv_received_form_classof2 = findViewById(R.id. tv_received_form_classof2);
        tv_received_form_phone2 = findViewById(R.id. tv_received_form_phone2);
        tv_introduce2 = findViewById(R.id. tv_introduce2);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("applicationItems");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String key = getIntent().getStringExtra("fromUserId");
        if (key != null){
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("applicationItems").child(key);

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ApplicationItem applicationItem = dataSnapshot.getValue(ApplicationItem.class);
                    tv_received_form_name2.setText(applicationItem.getAppName());
                    tv_received_form_classof2.setText(applicationItem.getAppNumber());
                    tv_received_form_phone2.setText(applicationItem.getAppPhone());
                    tv_introduce2.setText(applicationItem.getAppIntro());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {

        }




    }

}
