package com.example.jj_club.activities.mbti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class MbtiTestQ8Activity extends AppCompatActivity {

    private Button btnLogin1;
    private Button btnLogin2;


    private int tScore = 0; // I의 점수
    private int fScore = 0; // E의 점수

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mbti_test_q8);

        btnLogin1 = findViewById(R.id.button15);
        btnLogin2 = findViewById(R.id.button16);  // Change the ID

        btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // '드디어 나만의 시간! 너무 편하다' 버튼 클릭 시 I에 1 포인트 추가
                tScore += 1;

                // 다음 질문 페이지로 이동
                goToNextQuestion();
            }
        });

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // '심심하고 너무 조용해' 버튼 클릭 시 E에 1 포인트 추가
                fScore += 1;

                // 다음 질문 페이지로 이동
                goToNextQuestion();
            }
        });
    }

    // 다음 질문 페이지로 이동하는 메소드
    private void goToNextQuestion() {
        // MbtiTestQ2Activity로 이동
        Intent intent = new Intent(MbtiTestQ8Activity.this, com.example.jj_club.activities.mbti.MbtiTestQ9Activity.class);
        intent.putExtra("tScore", tScore); // I의 점수 전달
        intent.putExtra("fScore", fScore); // E의 점수 전달
        startActivity(intent);
    }
}