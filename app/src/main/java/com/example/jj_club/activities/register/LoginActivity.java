package com.example.jj_club.activities.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.example.jj_club.activities.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editText_email_login);
        passwordEditText = findViewById(R.id.editText_password);
        loginButton = findViewById(R.id.button_login);
        registerTextButton = findViewById(R.id.registerTextButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(email)) {
                        emailEditText.setHintTextColor(Color.RED);
                    }
                    if (TextUtils.isEmpty(password)) {
                        passwordEditText.setHintTextColor(Color.RED);
                    }
                    Toast.makeText(LoginActivity.this, "모든 입력란을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "로그인 성공");
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e(TAG, "로그인 실패", task.getException());
                                    String errorMessage = getFirebaseAuthErrorMessage(task.getException());
                                    Toast.makeText(LoginActivity.this, "로그인 실패: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        registerTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EmailVerificationActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getFirebaseAuthErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthException) {
            FirebaseAuthException authException = (FirebaseAuthException) exception;
            return authException.getMessage();
        }
        return exception.getMessage();
    }
}
