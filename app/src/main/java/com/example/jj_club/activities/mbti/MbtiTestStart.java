package com.example.jj_club.activities.mbti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class MbtiTestStart extends AppCompatActivity {

    private Button btnStartTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mbti_test_start);

        btnStartTest = findViewById(R.id.button_login);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of MbtiTestQ1Activity and start it
                Intent intent = new Intent(MbtiTestStart.this, com.example.jj_club.activities.mbti.MbtiTestQ1Activity.class);
                startActivity(intent);
            }
        });
    }
}
