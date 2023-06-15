package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    }
}