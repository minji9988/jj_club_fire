package com.example.jj_club.activities.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.jj_club.R;
import com.example.jj_club.models.HomeItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyClubFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private Map<Integer, Button> buttons;
    private Map<Integer, Boolean> buttonStatus;
    private List<String> selectedButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_study_club_filter);


        int[] buttonIds = {
                R.id.promotion_2_filter_study_btn,
                R.id.promotion_2_filter_club_btn,
                R.id.promotion_2_filter_misc_btn,
                R.id.promotion_2_filter_entj_btn,
                R.id.promotion_2_filter_enfj_btn,
                R.id.promotion_2_filter_esfj_btn,
                R.id.promotion_2_filter_estj_btn,
                R.id.promotion_2_filter_entp_btn,
                R.id.promotion_2_filter_enfp_btn,
                R.id.promotion_2_filter_esfp_btn,
                R.id.promotion_2_filter_estp_btn,
                R.id.promotion_2_filter_intp_btn,
                R.id.promotion_2_filter_infp_btn,
                R.id.promotion_2_filter_isfp_btn,
                R.id.promotion_2_filter_istp_btn,
                R.id.promotion_2_filter_intj_btn,
                R.id.promotion_2_filter_infj_btn,
                R.id.promotion_2_filter_isfj_btn,
                R.id.promotion_2_filter_istj_btn,

                R.id.promotion_2_filter_sports_btn,
                R.id.promotion_2_filter_social_btn,
                R.id.promotion_2_filter_travel_btn,
                R.id.promotion_2_filter_religion_btn,
                R.id.promotion_2_filter_volunteer_btn,
                R.id.promotion_2_filter_language_btn,
                R.id.promotion_2_filter_employment_btn,
                R.id.promotion_2_filter_game_btn,
                R.id.promotion_2_filter_startup_btn,
                R.id.promotion_2_filter_culture_btn,
                R.id.promotion_2_filter_misc_btn2,
                R.id.promotion_2_filter_crafts_btn2
        };


        buttons = new HashMap<>();
        buttonStatus = new HashMap<>();
        selectedButtons = new ArrayList<>();

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

                // Add the selectedButtons list to the intent
                intent.putStringArrayListExtra("selectedButtons", (ArrayList<String>) selectedButtons);

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
            selectedButtons.remove(button.getText().toString());
        } else {
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            buttonStatus.put(v.getId(), true);
            selectedButtons.add(button.getText().toString());
        }
    }

    public void saveHomeItemToDatabase(HomeItem item) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = databaseReference.child("promotions").push().getKey();
        databaseReference.child("promotions").child(key).setValue(item);
    }
}
