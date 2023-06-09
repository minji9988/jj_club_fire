package com.example.jj_club.activities.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.activities.home.MainActivity;
import com.example.jj_club.models.HomeItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PromotionWrite1 extends AppCompatActivity {
    private static final String TAG = "PromotionWrite1";

    private EditText editTextTitle;
    private EditText editTextMeetingName;
    private EditText editTextInterview;
    private EditText editTextFee;
    private EditText editTextRecruitPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_write1);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextMeetingName = findViewById(R.id.editTextGroupName);
        editTextInterview = findViewById(R.id.editTextInterview);
        editTextFee = findViewById(R.id.editTextMembershipFee);
        editTextRecruitPeriod = findViewById(R.id.editTextRecruitmentPeriod);

        Button nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String title = editTextTitle.getText().toString();
                    String meetingName = editTextMeetingName.getText().toString();
                    String interview = editTextInterview.getText().toString();
                    String fee = editTextFee.getText().toString();
                    String recruitPeriod = editTextRecruitPeriod.getText().toString();

                    if (title.isEmpty() || meetingName.isEmpty() || interview.isEmpty() || fee.isEmpty() || recruitPeriod.isEmpty()) {
                        Toast.makeText(PromotionWrite1.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        saveToRealtimeDatabase(user.getUid(), title, meetingName, interview, fee, recruitPeriod);
                        Intent intent = new Intent(PromotionWrite1.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(PromotionWrite1.this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveToRealtimeDatabase(String userId, String title, String meetingName, String interview, String fee, String recruitPeriod) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("promotions");

        String promotionId = databaseRef.push().getKey(); // Generate a unique ID for the promotion

        HomeItem promotion = new HomeItem(title, recruitPeriod, fee, interview, meetingName, userId);

        databaseRef.child(promotionId).setValue(promotion)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Promotion added with ID: " + promotionId))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding promotion", e));
    }
}
