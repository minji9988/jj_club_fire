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

    private EditText editTextAppName; //신청서 이름
    private EditText editTextAppNumber; //신청서 번호
    private EditText editTextAppPhone; //신청서 전화번호
    private EditText editTextAppIntro; //신청서 소개
    private Button buttonTurnIn; //신청서 제출 버튼
    private ImageButton buttonBack; //뒤로 돌아가기 버튼

    private DatabaseReference databaseReference;
    private DatabaseReference promotionsReference;

    private String promotionId; //홍보글 자체 id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_application_form);

        // Find the EditText views by id
        editTextAppName = findViewById(R.id.editTextAppName);
        editTextAppNumber = findViewById(R.id.editTextAppNumber);
        editTextAppPhone = findViewById(R.id.editTextAppPhone);
        editTextAppIntro = findViewById(R.id.editTextAppIntro);
        buttonTurnIn = findViewById(R.id.button_TurnIn);
        buttonBack = findViewById(R.id.button_back);

        // Get references to the database
        databaseReference = FirebaseDatabase.getInstance().getReference("applicationItems");
        promotionsReference = FirebaseDatabase.getInstance().getReference("promotions");

        // Get the promotion id from the intent
        promotionId = getIntent().getStringExtra("promotion_id");

        // Set up the event for the button click
        buttonTurnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextAppName.getText().toString().trim();
                String number = editTextAppNumber.getText().toString().trim();
                String phone = editTextAppPhone.getText().toString().trim();
                String intro = editTextAppIntro.getText().toString().trim();

                // If any of the fields are empty, show a Toast message
                if (name.isEmpty() || number.isEmpty() || phone.isEmpty() || intro.isEmpty()) {
                    Toast.makeText(ApplicationForm.this, "양식을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // If all fields are filled, start the process of sending the application
                    final String fromUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    promotionsReference.child(promotionId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String sendToUserId = dataSnapshot.child("userId").getValue(String.class);

                                // Create a new ApplicationItem object
                                ApplicationItem applicationItem = new ApplicationItem(fromUserId, promotionId, name, number, phone, intro);
                                applicationItem.setSendToUserId(sendToUserId);

                                // Save the application to the database
                                databaseReference.push().setValue(applicationItem)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // If the save was successful, show a Toast message and finish the activity
                                                Toast.makeText(ApplicationForm.this, "제출이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // If the save was not successful, show a Toast message
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

        // Set up the event for the back button click
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
