package com.example.jj_club.activities.promotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jj_club.R;

public class PromotionWrite3 extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1; // 이미지 선택 요청 코드

    private Button imageButton1;
    private Button imageButton2;
    private Button imageButton3;

    private boolean isImageSelected1 = false;
    private boolean isImageSelected2 = false;
    private boolean isImageSelected3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_write3);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);

        // 이미지 버튼 클릭 이벤트 설정
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(1);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(2);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(3);
            }
        });
    }

    private void selectImage(int imageButtonNumber) {
        // 이미지 선택을 위한 코드 작성
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, imageButtonNumber);
    }

    // 이미지 선택 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    // imageButton1에 이미지 선택 결과를 적용
                    isImageSelected1 = true;
                    imageButton1.setBackgroundResource(R.drawable.image_select_complete);
                    break;
                case 2:
                    // imageButton2에 이미지 선택 결과를 적용
                    isImageSelected2 = true;
                    imageButton2.setBackgroundResource(R.drawable.image_select_complete);
                    break;
                case 3:
                    // imageButton3에 이미지 선택 결과를 적용
                    isImageSelected3 = true;
                    imageButton3.setBackgroundResource(R.drawable.image_select_complete);
                    break;
            }
        }
    }
}
