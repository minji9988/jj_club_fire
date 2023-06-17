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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_received_item2);

        tv_received_form_name = findViewById(R.id.tv_received_form_name);
        tv_received_form_classof = findViewById(R.id.tv_received_form_classof);
        tv_received_form_phone = findViewById(R.id.tv_received_form_phone);
        tv_introduce = findViewById(R.id.tv_introduce);
        button_back = findViewById(R.id.button_back);
        button_approve = findViewById(R.id.btn_approval);

        // Get applicationId from intent
        String applicationId = getIntent().getStringExtra("applicationId");

        Log.d("FirebaseDebug", "applicationId: " + applicationId);

        // Get the reference to the specific application item using the applicationId
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("applicationItems").child(applicationId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApplicationItem applicationItem = dataSnapshot.getValue(ApplicationItem.class);
                    if (applicationItem != null) {

                        // Set data to text views
                        tv_received_form_name.setText(applicationItem.getAppName());
                        tv_received_form_classof.setText(applicationItem.getAppNumber());
                        tv_received_form_phone.setText(applicationItem.getAppPhone());
                        tv_introduce.setText(applicationItem.getAppIntro());

                        button_approve.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Get the promotionId (clubId in ApplicationItem) and userId (fromUserId in ApplicationItem) from applicationItem
                                String promotionId = applicationItem.getPromotionId();
                                String userId = applicationItem.getFromUserId();

                                DatabaseReference promotionReference = FirebaseDatabase.getInstance().getReference("promotions").child(promotionId);
                                promotionReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // Get the current joinStatuses field
                                            Map<String, String> joinStatuses = (Map<String, String>) dataSnapshot.child("joinStatuses").getValue();

                                            // Check if joinStatuses is not null
                                            if (joinStatuses == null) {
                                                joinStatuses = new HashMap<>();
                                            }

                                            // Add userId to the joinStatuses field with status "approved"
                                            joinStatuses.put(userId, "approved");

                                            // Update the joinStatuses field in the database
                                            promotionReference.child("joinStatuses").setValue(joinStatuses)
                                                    .addOnCompleteListener(task -> {
                                                        if (task.isSuccessful()) {
                                                            // Show a toast message when the joinStatuses field is successfully updated
                                                            Toast.makeText(ProfileRecivedapplicationformActivity2.this, "승인되었습니다.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            // Show a toast message when the joinStatuses field failed to update
                                                            Toast.makeText(ProfileRecivedapplicationformActivity2.this, "승인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle error
                                        Log.d("FirebaseDebug", "Error: " + databaseError.getMessage());
                                    }
                                });
                            }
                        });

                    } else {
                        // Handle case when ApplicationItem is null
                        Log.d("FirebaseDebug", "ApplicationItem is null");
                    }
                } else {
                    // Handle case when DataSnapshot does not exist
                    Log.d("FirebaseDebug", "DataSnapshot does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error or cancellation
                Log.d("FirebaseDebug", "Error: " + databaseError.getMessage());
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
