package com.example.jj_club.activities.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.jj_club.R;

import java.util.HashMap;
import java.util.Map;

public class StudyClubFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private Map<Integer, Button> buttons;
    private Map<Integer, Boolean> buttonStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_study_club_filter);

        int[] buttonIds = {
                R.id.promotion_2_filter_study_btn,
                R.id.promotion_2_filter_club_btn,
                R.id.promotion_2_filter_misc_btn,
                R.id.promotion_2_filter_entf_btn,
                R.id.promotion_2_filter_enfj_btn,
                // 추가 버튼 id들...
        };

        buttons = new HashMap<>();
        buttonStatus = new HashMap<>();

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
            buttons.put(id, button);
            buttonStatus.put(id, false);
        }

        Button filterCheckButton = findViewById(R.id.promotionWrite2_filter_check_btn);
        filterCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyClubFilterActivity.this, PromotionWrite2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button button = buttons.get(v.getId());

        if (buttonStatus.get(v.getId())) {
            button.setBackgroundResource(R.drawable.shadow);
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            buttonStatus.put(v.getId(), false);
        } else {
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            buttonStatus.put(v.getId(), true);
        }
    }
}
