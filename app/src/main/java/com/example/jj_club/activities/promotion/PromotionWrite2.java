package com.example.jj_club.activities.promotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jj_club.R;

public class PromotionWrite2 extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextTarget;
    private EditText editTextIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_write2);

        Button btnNext = findViewById(R.id.button_next2);

        editTextNumber = findViewById(R.id.pwnumber);
        editTextTarget = findViewById(R.id.pwtarget);
        editTextIntroduce = findViewById(R.id.pwintroduce);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String name = intent.getStringExtra("name");
        String interview = intent.getStringExtra("interview");
        String fee = intent.getStringExtra("fee");

        // PromotionWrite1에서 전달받은 값들을 EditText에 설정
        editTextNumber.setText(title);
        editTextTarget.setText(name);
        editTextIntroduce.setText(interview);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromotionWrite2.this, PromotionWrite3.class);
                intent.putExtra("title", title);
                intent.putExtra("name", name);
                intent.putExtra("interview", interview);
                intent.putExtra("fee", fee);
                intent.putExtra("number", editTextNumber.getText().toString());
                intent.putExtra("target", editTextTarget.getText().toString());
                intent.putExtra("introduce", editTextIntroduce.getText().toString());
                startActivity(intent);
            }
        });
    }
}
