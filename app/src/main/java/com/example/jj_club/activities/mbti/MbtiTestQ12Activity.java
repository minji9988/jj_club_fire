package com.example.jj_club.activities.mbti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class MbtiTestQ12Activity extends AppCompatActivity {

    private Button btnLogin1;
    private Button btnLogin2;

    private int iPoints = 0; // I에 대한 포인트
    private int ePoints = 0; // E에 대한 포인트
    private int sPoints = 0; // S에 대한 포인트
    private int nPoints = 0; // N에 대한 포인트
    private int tPoints = 0; // T에 대한 포인트
    private int fPoints = 0; // F에 대한 포인트
    private int jPoints = 0; // J에 대한 포인트
    private int pPoints = 0; // P에 대한 포인트

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mbti_test_q12);

        btnLogin1 = findViewById(R.id.button23);
        btnLogin2 = findViewById(R.id.button24);

        btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // '반복되고, 체계적인 일상이 좋아.' 버튼이 클릭되었을 때
                jPoints++; // J에 1 포인트 추가
                handlePageTransition();
            }
        });

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // '매일매일 변화가 있어야지!' 버튼이 클릭되었을 때
                pPoints++; // P에 1 포인트 추가
                handlePageTransition();
            }
        });
    }

    private void handlePageTransition() {
        // 계산하여 최종 MBTI 결정
        String mbti = calculateMbti();

        // 페이지 전환
        Intent intent = null;
        switch (mbti) {
            case "ENTP":
                intent = new Intent(MbtiTestQ12Activity.this,com.example.jj_club.activities.mbti.entp.class);
                break;
            case "INFJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ISTJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "INFP":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ISTP":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "INTJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "INTP":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ENFP":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ESTP":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ENFJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ESTJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ESFP":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ISFJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ISFP":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ENTJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            case "ESFJ":
                intent = new Intent(MbtiTestQ12Activity.this, com.example.jj_club.activities.mbti.infj.class);
                break;
            // 다른 MBTI 경우의 수도 추가
        }

        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 새로운 태스크로 액티비티 시작
            startActivity(intent);
        }
    }

    private String calculateMbti() {
        String mbti = "";

        // I/E 항목
        if (iPoints > ePoints) {
            mbti += "I";
        } else {
            mbti += "E";
        }

        // S/N 항목
        if (sPoints > nPoints) {
            mbti += "S";
        } else {
            mbti += "N";
        }

        // T/F 항목
        if (tPoints > fPoints) {
            mbti += "T";
        } else {
            mbti += "F";
        }

        // P/J 항목
        if (pPoints > jPoints) {
            mbti += "P";
        } else {
            mbti += "J";
        }

        return mbti;
    }
}
