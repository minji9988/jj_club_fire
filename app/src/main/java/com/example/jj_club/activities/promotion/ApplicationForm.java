package com.example.jj_club.activities.promotion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.models.ApplicationItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class ApplicationForm extends AppCompatActivity {

    private EditText editTextAppTitle;
    private EditText editTextAppContent;
    private Button buttonTurnIn;
    private ImageButton buttonBack;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_application_form);

        editTextAppTitle = findViewById(R.id.editTextAppTitle);
        editTextAppContent = findViewById(R.id.editTextAppContent);
        buttonTurnIn = findViewById(R.id.button_TurnIn);
        buttonBack = findViewById(R.id.button_back);

        databaseReference = FirebaseDatabase.getInstance().getReference("applicationItems");

        buttonTurnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextAppTitle.getText().toString().trim();
                String content = editTextAppContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(ApplicationForm.this, "양식을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // Create ApplicationItem object
                    ApplicationItem applicationItem = new ApplicationItem(title, content);

                    // Push the applicationItem to Firebase database
                    databaseReference.push().setValue(applicationItem)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ApplicationForm.this, "제출이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish(); // 이전 클래스로 돌아가기
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ApplicationForm.this, "제출이 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 이전 클래스로 돌아가기
            }
        });
    }
}
