package com.example.jj_club.activities.register;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);

        mAuth = FirebaseAuth.getInstance();


    }
}