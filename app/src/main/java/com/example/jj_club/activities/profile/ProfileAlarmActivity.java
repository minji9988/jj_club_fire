package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class ProfileAlarmActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromProfileAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_alarm);

        btn_GoBackProfile_fromProfileAlarm = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromProfileAlarm);

        btn_GoBackProfile_fromProfileAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }
}