package com.example.jj_club.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.ApplicationForm;
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.models.ApplicationItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileRecivedapplicationformActivity2 extends AppCompatActivity {

    private FirebaseDatabase database;

    private DatabaseReference databaseReference;

    private TextView tv_received_form_name, tv_received_form_classof, tv_received_form_phone;
    private TextView tv_introduce;
    private Button btn_approval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_received_item2);

        tv_received_form_name = findViewById(R.id. tv_received_form_name);
        tv_received_form_classof = findViewById(R.id. tv_received_form_classof);
        tv_received_form_phone = findViewById(R.id. tv_received_form_phone);
        tv_introduce = findViewById(R.id. tv_introduce);

/*
        Intent intent = new Intent(,);
        String promotionId = getIntent().getStringExtra("promotion_id"); // Get the promotion_id from the intent
        intent.putExtra("promotion_id", promotionId); // Add the promotion_id to the intent

 */
        String key = getIntent().getStringExtra("promotion_id");
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


    }
}