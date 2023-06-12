package com.example.jj_club.activities.mbti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.activities.home.MainActivity;

public class infj extends AppCompatActivity {

    private Button btnLogin1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mbti_infj);

        btnLogin1 = findViewById(R.id.button_check2);

        btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼1이 클릭되었을 때 mbti_test_q2.xml 페이지로 이동
                Intent intent = new Intent(infj.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}