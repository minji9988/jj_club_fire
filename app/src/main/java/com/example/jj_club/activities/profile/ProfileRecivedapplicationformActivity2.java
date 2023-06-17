package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.models.ApplicationItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

                        // 텍스트 뷰에 데이터 설정
                        tv_received_form_name.setText(applicationItem.getAppName());
                        tv_received_form_classof.setText(applicationItem.getAppNumber());
                        tv_received_form_phone.setText(applicationItem.getAppPhone());
                        tv_introduce.setText(applicationItem.getAppIntro());


                    } else {
                        // 데이터가 null인 경우 처리
                        Log.d("FirebaseDebug", "ApplicationItem is null");
                    }
                } else {
                    // 데이터가 존재하지 않는 경우 처리
                    Log.d("FirebaseDebug", "DataSnapshot does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 읽어오기 실패 또는 취소된 경우 처리
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
