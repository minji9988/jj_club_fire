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

    // 디버그 메시지를 로깅하기 위한 태그
    private static final String TAG = "LoginActivity";

    // Firebase Authentication 인스턴스
    private FirebaseAuth mAuth;

    // UI 요소
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextButton;

    // 데이터베이스 참조 (주석 처리됨)
    // private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);

        // Firebase Authentication 초기화
        mAuth = FirebaseAuth.getInstance();

        // UI 요소를 변수에 할당
        emailEditText = findViewById(R.id.editText_email_login);
        passwordEditText = findViewById(R.id.editText_password);
        loginButton = findViewById(R.id.button_login);
        registerTextButton = findViewById(R.id.registerTextButton);

        // 로그인 버튼에 클릭 리스너 설정
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // 이메일과 비밀번호가 비어있는지 확인
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(email)) {
                        emailEditText.setHintTextColor(Color.RED);
                    }
                    if (TextUtils.isEmpty(password)) {
                        passwordEditText.setHintTextColor(Color.RED);
                    }
                    Toast.makeText(LoginActivity.this, "모든 입력란을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // Firebase Authentication을 사용하여 로그인 시도
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

        // 회원가입 텍스트 버튼에 클릭 리스너 설정
        registerTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EmailVerificationActivity.class);
                startActivity(intent);
            }
        });
    }

    // Firebase Authentication 예외 메시지 가져오기
    private String getFirebaseAuthErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthException) {
            FirebaseAuthException authException = (FirebaseAuthException) exception;
            return authException.getMessage();
        }
        return exception.getMessage();
    }
}
