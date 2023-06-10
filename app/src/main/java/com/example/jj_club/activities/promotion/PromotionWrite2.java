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
import java.util.Map;

public class PromotionWrite2 extends AppCompatActivity {
    private static final String TAG = "PromotionWrite2";

    private EditText editPromotionNumber;
    private EditText editPromotionTarget;
    private EditText editPromotionIntroduce;
    private EditText editPromotionPlace;

    private Button promotion_write2_buttonNext;
    private Button imageButton;

    private String promotionId;
    private String imageUrl = ""; // Store the image URL after upload

    private static final int PICK_IMAGE_REQUEST = 1; // Request code for picking image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_write2);

        editPromotionNumber = findViewById(R.id.editPromotionNumber);
        editPromotionTarget = findViewById(R.id.editPromotionTarget);
        editPromotionIntroduce = findViewById(R.id.editPromotionIntroduce);
        editPromotionPlace = findViewById(R.id.editPromotionPlace);

        promotion_write2_buttonNext = findViewById(R.id.promotion_write2_buttonNext);
        imageButton = findViewById(R.id.buttonPromotionImage1); // Make sure to add this button in your layout

        promotionId = getIntent().getStringExtra("promotionId"); // Get the promotionId passed from the previous activity

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
                    String promotionNumber = editPromotionNumber.getText().toString();
                    String promotionTarget = editPromotionTarget.getText().toString();
                    String promotionIntroduce = editPromotionIntroduce.getText().toString();
                    String promotionPlace = editPromotionPlace.getText().toString();

                    if (promotionNumber.isEmpty() || promotionTarget.isEmpty() || promotionIntroduce.isEmpty() || promotionPlace.isEmpty()) {
                        Toast.makeText(PromotionWrite2.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (promotionId != null) {
                            saveToRealtimeDatabase(promotionId, user.getUid(), promotionNumber, promotionTarget, promotionIntroduce, promotionPlace);
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
    }

    private void saveToRealtimeDatabase(String promotionId, String userId, String promotionNumber, String promotionTarget, String promotionIntroduce, String promotionPlace) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("promotions").child(promotionId);

        Map<String, Object> promotionUpdates = new HashMap<>();
        promotionUpdates.put("promotionNumber", promotionNumber);
        promotionUpdates.put("promotionTarget", promotionTarget);
        promotionUpdates.put("promotionIntroduce", promotionIntroduce);
        promotionUpdates.put("promotionPlace", promotionPlace);
        promotionUpdates.put("imageUrl", imageUrl);

        databaseRef.updateChildren(promotionUpdates)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Promotion added with ID: " + promotionId);
                    Intent intent = new Intent(PromotionWrite2.this, MainActivity.class);
                    intent.putExtra("promotionId", promotionId); // Pass the promotionId to the next Activity
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding promotion", e);
                    Toast.makeText(PromotionWrite2.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            uploadFile(imageUri);
        }
    }

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

                        // Continue with the task to get the download URL
                        return fileReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            imageUrl = downloadUri.toString(); // Store the image URL
                        } else {
                            // Handle failures
                            Toast.makeText(PromotionWrite2.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }
}
