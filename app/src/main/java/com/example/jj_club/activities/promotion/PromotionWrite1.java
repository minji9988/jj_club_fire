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
import com.example.jj_club.models.HomeItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PromotionWrite1 extends AppCompatActivity {
    private static final String TAG = "PromotionWrite1";

    private EditText editTextTitle; // 게시글 제목 입력 필드
    private EditText editTextMeetingName; // 모임 이름 입력 필드
    private EditText editTextInterview; // 면접 유무 입력 필드
    private EditText editTextFee; // 회비 입력 필드
    private EditText editTextRecruitPeriod; // 모집 기간 입력 필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_write1);

        editTextTitle = findViewById(R.id.editTextTitle); // 게시글 제목을 입력받는 EditText
        editTextMeetingName = findViewById(R.id.editTextGroupName); // 모임 이름을 입력받는 EditText
        editTextInterview = findViewById(R.id.editTextInterview); // 면접 유무를 입력받는 EditText
        editTextFee = findViewById(R.id.editTextMembershipFee); // 회비를 입력받는 EditText
        editTextRecruitPeriod = findViewById(R.id.editTextRecruitmentPeriod); // 모집 기간을 입력받는 EditText

        Button nextButton = findViewById(R.id.promotion_write1_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String title = editTextTitle.getText().toString(); // 게시글 제목을 문자열로 가져옴
                    String meetingName = editTextMeetingName.getText().toString(); // 모임 이름을 문자열로 가져옴
                    String interview = editTextInterview.getText().toString(); // 면접 유무를 문자열로 가져옴
                    String fee = editTextFee.getText().toString(); // 회비를 문자열로 가져옴
                    String recruitPeriod = editTextRecruitPeriod.getText().toString(); // 모집 기간을 문자열로 가져옴

                    if (title.isEmpty() || meetingName.isEmpty() || interview.isEmpty() || fee.isEmpty() || recruitPeriod.isEmpty()) {
                        Toast.makeText(PromotionWrite1.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        String promotionId = saveToRealtimeDatabase(user.getUid(), title, meetingName, interview, fee, recruitPeriod);

                        Intent intent = new Intent(PromotionWrite1.this, PromotionWrite2.class);
                        intent.putExtra("promotionId", promotionId); // 다음 Activity로 promotionId 전달
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(PromotionWrite1.this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 실시간 데이터베이스에 게시글 정보를 저장합니다.
     *
     * @param userId         사용자 ID
     * @param title          게시글 제목
     * @param meetingName    모임 이름
     * @param interview      면접 유무
     * @param fee            회비
     * @param recruitPeriod  모집 기간
     * @return               생성된 게시글의 고유 ID
     */
    private String saveToRealtimeDatabase(String userId, String title, String meetingName, String interview, String fee, String recruitPeriod) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("promotions");

        String promotionId = databaseRef.push().getKey(); // 고유한 게시글 ID 생성









        HomeItem promotion = new HomeItem(title, recruitPeriod, fee, interview, meetingName, userId, null, null, null, null, null, null, null);

        databaseRef.child(promotionId).setValue(promotion)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Promotion added with ID: " + promotionId))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding promotion", e));

        return promotionId;
    }
}
