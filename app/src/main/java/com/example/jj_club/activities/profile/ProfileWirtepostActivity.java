package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class ProfileWirtepostActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromWirtepostActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_wirtepost);

        btn_GoBackProfile_fromWirtepostActivity = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromWirtepostActivity);
        btn_GoBackProfile_fromWirtepostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }
}