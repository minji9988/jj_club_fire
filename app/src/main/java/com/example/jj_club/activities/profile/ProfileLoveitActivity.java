package com.example.jj_club.activities.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class ProfileLoveitActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromLoveIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_loveit);


        btn_GoBackProfile_fromLoveIt = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromLoveIt);
        btn_GoBackProfile_fromLoveIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                /*Intent intent = new Intent(ProfileLoveitActivity.this, ProfileFragment.class);
                startActivity(intent);
                */
            }
        });
    }
}