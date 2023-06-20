package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.models.ApplicationItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileRecivedapplicationformActivity2 extends AppCompatActivity {

    private TextView tv_received_form_name;
    private TextView tv_received_form_classof;
    private TextView tv_received_form_phone;
    private TextView tv_introduce;
    private ImageButton button_back;
    private Button button_approve;
    private Button button_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_received_item2);

        initView();

        String applicationId = getIntent().getStringExtra("applicationId");
        Log.d("FirebaseDebug", "applicationId: " + applicationId);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("applicationItems").child(applicationId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                processApplicationData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                handleDatabaseError(databaseError);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        tv_received_form_name = findViewById(R.id.tv_received_form_name);
        tv_received_form_classof = findViewById(R.id.tv_received_form_classof);
        tv_received_form_phone = findViewById(R.id.tv_received_form_phone);
        tv_introduce = findViewById(R.id.tv_introduce);
        button_back = findViewById(R.id.button_back);
        button_approve = findViewById(R.id.btn_approval);
        button_reject = findViewById(R.id.btn_rejection);  // Reject button view binding
    }

    private void processApplicationData(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            ApplicationItem applicationItem = dataSnapshot.getValue(ApplicationItem.class);

            if (applicationItem != null) {
                setApplicationData(applicationItem);
                setApproveButtonBehavior(applicationItem);
                setRejectButtonBehavior(applicationItem);  // Setting up reject button behavior
            } else {
                Log.d("FirebaseDebug", "ApplicationItem is null");
            }
        } else {
            Log.d("FirebaseDebug", "DataSnapshot does not exist");
        }
    }

    private void setApplicationData(ApplicationItem applicationItem) {
        tv_received_form_name.setText(applicationItem.getAppName());
        tv_received_form_classof.setText(applicationItem.getAppNumber());
        tv_received_form_phone.setText(applicationItem.getAppPhone());
        tv_introduce.setText(applicationItem.getAppIntro());
    }

    private void setApproveButtonBehavior(ApplicationItem applicationItem) {
        button_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String promotionId = applicationItem.getPromotionId();
                String userId = applicationItem.getFromUserId();

                DatabaseReference promotionReference = FirebaseDatabase.getInstance().getReference("promotions").child(promotionId);
                promotionReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        processPromotionData(dataSnapshot, promotionReference, userId, "승인");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        handleDatabaseError(databaseError);
                    }
                });
            }
        });
    }

    // Add reject button behavior here
    private void setRejectButtonBehavior(ApplicationItem applicationItem) {
        button_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String promotionId = applicationItem.getPromotionId();
                String userId = applicationItem.getFromUserId();

                DatabaseReference promotionReference = FirebaseDatabase.getInstance().getReference("promotions").child(promotionId);
                promotionReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        processPromotionData(dataSnapshot, promotionReference, userId, "거절");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        handleDatabaseError(databaseError);
                    }
                });
            }
        });
    }

    private void processPromotionData(@NonNull DataSnapshot dataSnapshot, DatabaseReference promotionReference, String userId, String status) {
        if (dataSnapshot.exists()) {
            Map<String, String> joinStatuses = (Map<String, String>) dataSnapshot.child("joinStatuses").getValue();

            if (joinStatuses == null) {
                joinStatuses = new HashMap<>();
            }

            joinStatuses.put(userId, status);
            updateJoinStatuses(promotionReference, joinStatuses, status);
        }
    }

    private void updateJoinStatuses(DatabaseReference promotionReference, Map<String, String> joinStatuses, String status) {
        promotionReference.child("joinStatuses").setValue(joinStatuses)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileRecivedapplicationformActivity2.this, status.equals("승인") ? "승인되었습니다." : "거절되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileRecivedapplicationformActivity2.this, status.equals("승인") ? "승인에 실패했습니다." : "거절에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleDatabaseError(@NonNull DatabaseError databaseError) {
        Log.d("FirebaseDebug", "Error: " + databaseError.getMessage());
    }
}
