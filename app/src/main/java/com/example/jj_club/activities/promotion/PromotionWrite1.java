package com.example.jj_club.activities.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




// 최신글 sub page making
// 그 페이지에서 recycler view 만들고
// 데이터 받아와서 출력하는 거 해보자.





import com.example.jj_club.R;
import com.example.jj_club.activities.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
                        saveToFirestore(user.getUid(), title, meetingName, interview, fee, recruitPeriod);
                        Intent intent = new Intent(PromotionWrite1.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(PromotionWrite1.this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveToFirestore(String userId, String title, String meetingName, String interview, String fee, String recruitPeriod) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> promotion = new HashMap<>();
        promotion.put("userId", userId);
        promotion.put("title", title);
        promotion.put("meetingName", meetingName);
        promotion.put("interview", interview);
        promotion.put("fee", fee);
        promotion.put("recruitPeriod", recruitPeriod);

        db.collection("promotions")
                .add(promotion)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}
