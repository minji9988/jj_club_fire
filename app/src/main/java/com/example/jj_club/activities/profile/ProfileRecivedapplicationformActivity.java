package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class ProfileRecivedapplicationformActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromRecivedapplicationformActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_recivedapplicationform);

        btn_GoBackProfile_fromRecivedapplicationformActivity = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromRecivedapplicationformActivity);
        btn_GoBackProfile_fromRecivedapplicationformActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
}