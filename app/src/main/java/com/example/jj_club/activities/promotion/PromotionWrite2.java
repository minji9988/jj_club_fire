package com.example.jj_club.activities.promotion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.activities.home.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionWrite2 extends AppCompatActivity {
    private static final String TAG = "PromotionWrite2";

    private EditText editPromotionNumber;  // 모집 인원 입력 필드
    private EditText editPromotionTarget;  // 모집 대상 입력 필드
    private EditText editPromotionIntroduce;  // 모임 소개 입력 필드

    private Button promotion_write2_buttonNext;  // 다음 버튼
    private Button imageButton;  // 이미지 업로드 버튼
    private Button promotion_write2_buttonFilter;  // 스터티/ 동아리 필터 버튼

    private Button promotion_write2_buttonFilter_2;  // 단과대 필터 버튼


    private String promotionId;  // 프로모션 ID
    private String imageUrl = "";  // 이미지 업로드 후 이미지 URL 저장 변수
    private List<String> selectedButtons;  // 선택한 버튼의 텍스트들

    private static final int PICK_IMAGE_REQUEST = 1;  // 이미지 선택 요청 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_write2);

        editPromotionNumber = findViewById(R.id.editPromotionNumber);
        editPromotionTarget = findViewById(R.id.editPromotionTarget);
        editPromotionIntroduce = findViewById(R.id.editPromotionIntroduce);



        promotion_write2_buttonNext = findViewById(R.id.promotion_write2_buttonNext);
        imageButton = findViewById(R.id.buttonPromotionImage1);  // 레이아웃에 추가한 이미지 업로드 버튼
        promotion_write2_buttonFilter = findViewById(R.id.promotion_study_filter);  // 스터디/동아리 필터
        promotion_write2_buttonFilter_2 = findViewById(R.id.promotion_college_filter); // 단과대 필터


        promotionId = getIntent().getStringExtra("promotionId");  // 이전 액티비티로부터 전달받은 프로모션 ID

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        promotion_write2_buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String promotionNumber = editPromotionNumber.getText().toString();  // 모집 인원
                    String promotionTarget = editPromotionTarget.getText().toString();  // 모집 대상
                    String promotionIntroduce = editPromotionIntroduce.getText().toString();  // 모임 소개

                    if (promotionNumber.isEmpty() || promotionTarget.isEmpty() || promotionIntroduce.isEmpty()) {
                        Toast.makeText(PromotionWrite2.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (promotionId != null) {
                            saveToRealtimeDatabase(promotionId, user.getUid(), promotionNumber, promotionTarget, promotionIntroduce);
                        } else {
                            Log.e(TAG, "promotionId is null");
                            Toast.makeText(PromotionWrite2.this, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(PromotionWrite2.this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        promotion_write2_buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromotionWrite2.this, StudyClubFilterActivity.class);
                startActivity(intent);
            }
        });

        promotion_write2_buttonFilter_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromotionWrite2.this, CollegeFilterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        // Get the selectedButtons list from the intent
        selectedButtons = intent.getStringArrayListExtra("selectedButtons");
    }

    /**
     * Firebase Realtime Database에 프로모션 정보 저장
     *
     * @param promotionId        프로모션 ID
     * @param userId             사용자 ID
     * @param promotionNumber    모집 인원
     * @param promotionTarget    모집 대상
     * @param promotionIntroduce 모임 소개
     */
    private void saveToRealtimeDatabase(String promotionId, String userId, String promotionNumber, String promotionTarget, String promotionIntroduce) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("promotions").child(promotionId);

        Map<String, Object> promotionUpdates = new HashMap<>();
        promotionUpdates.put("promotionNumber", promotionNumber);
        promotionUpdates.put("promotionTarget", promotionTarget);
        promotionUpdates.put("promotionIntroduce", promotionIntroduce);
        promotionUpdates.put("imageUrl", imageUrl);
        promotionUpdates.put("selectedButtons", selectedButtons);  // Add the selectedButtons list to the Firebase update

        databaseRef.updateChildren(promotionUpdates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Promotion added with ID: " + promotionId);
                    Intent intent = new Intent(PromotionWrite2.this, MainActivity.class);
                    intent.putExtra("promotionId", promotionId);  // 다음 액티비티로 프로모션 ID 전달
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding promotion", e);
                    Toast.makeText(PromotionWrite2.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    /**
     * 파일 선택기 열기
     */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * 이미지 선택 결과 처리
     *
     * @param requestCode 요청 코드
     * @param resultCode  결과 코드
     * @param data        선택한 이미지 데이터
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            uploadFile(imageUri);
        }
    }

    /**
     * 파일 업로드
     *
     * @param fileUri 파일 URI
     */
    private void uploadFile(Uri fileUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");

        final StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(fileUri));

        fileReference.putFile(fileUri)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // 파일 업로드가 성공하면 다운로드 URL 가져오기 위해 작업 계속 진행
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            imageUrl = downloadUri.toString();  // 이미지 URL 저장
                        } else {
                            // 업로드 실패 처리
                            Toast.makeText(PromotionWrite2.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 파일의 확장자 가져오기
     *
     * @param uri 파일 URI
     * @return 파일의 확장자
     */
    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }
}
