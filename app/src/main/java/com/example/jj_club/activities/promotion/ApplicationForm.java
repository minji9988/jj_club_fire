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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplicationForm extends AppCompatActivity {

    private EditText editTextAppTitle;
    private EditText editTextAppContent;
    private Button buttonTurnIn;
    private ImageButton buttonBack;

    private DatabaseReference databaseReference;
    private DatabaseReference promotionsReference;

    private String promotionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_application_form);

        editTextAppTitle = findViewById(R.id.editTextAppTitle);
        editTextAppContent = findViewById(R.id.editTextAppContent);
        buttonTurnIn = findViewById(R.id.button_TurnIn);
        buttonBack = findViewById(R.id.button_back);

        databaseReference = FirebaseDatabase.getInstance().getReference("applicationItems");
        promotionsReference = FirebaseDatabase.getInstance().getReference("promotions");

        promotionId = getIntent().getStringExtra("promotion_id");

        buttonTurnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextAppTitle.getText().toString().trim();
                String content = editTextAppContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(ApplicationForm.this, "양식을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    final String fromUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    promotionsReference.child(promotionId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String sendToUserId = dataSnapshot.child("userId").getValue(String.class);

                                ApplicationItem applicationItem = new ApplicationItem(fromUserId, promotionId, title, content);
                                applicationItem.setSendToUserId(sendToUserId);

                                databaseReference.push().setValue(applicationItem)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(ApplicationForm.this, "제출이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                finish();
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

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ApplicationForm.this, "데이터를 참조 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
