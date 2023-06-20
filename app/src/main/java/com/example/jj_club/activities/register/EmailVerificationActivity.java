package com.example.jj_club.activities.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailEditText, verificationCodeEditText;
    private Button sendVerificationButton, verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email_verification);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        sendVerificationButton = findViewById(R.id.sendVerificationButton);
        verifyButton = findViewById(R.id.verifyButton);

        sendVerificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (isEmailValid(email)) {
                    sendVerificationEmail(email);
                } else {
                    Toast.makeText(EmailVerificationActivity.this,
                            "'@jj.ac.kr' 이메일로만 회원가입할 수 있습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                boolean isEmailVerified = user.isEmailVerified();

                                if (isEmailVerified) {
                                    Intent intent = new Intent(EmailVerificationActivity.this, SignUpActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(EmailVerificationActivity.this,
                                            "이메일이 아직 인증되지 않았습니다. 이메일을 확인해주세요.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EmailVerificationActivity.this,
                                        "사용자 정보를 다시 불러오는데 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(EmailVerificationActivity.this,
                            "이메일 인증을 완료해야 다음 페이지로 이동할 수 있습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (email.isEmpty()) {
                    emailEditText.setHintTextColor(Color.RED);
                    return;
                }

                final FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                boolean isEmailVerified = user.isEmailVerified();

                                if (isEmailVerified) {
                                    Intent intent = new Intent(EmailVerificationActivity.this, SignUpActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(EmailVerificationActivity.this,
                                            "이메일이 아직 인증되지 않았습니다. 이메일을 확인해주세요.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EmailVerificationActivity.this,
                                        "사용자 정보를 다시 불러오는데 실패했습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(EmailVerificationActivity.this,
                            "이메일 인증을 완료해야 다음 페이지로 이동할 수 있습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationEmail(String email) {
        mAuth.createUserWithEmailAndPassword(email, "your_default_password")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(EmailVerificationActivity.this,
                                                            "인증 이메일이 " + user.getEmail() + "로 전송되었습니다.",
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(EmailVerificationActivity.this,
                                                            "인증 이메일을 보내는데 실패했습니다.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(EmailVerificationActivity.this,
                                        "사용자를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EmailVerificationActivity.this,
                                    "사용자 생성에 실패했습니다. 이미 등록된 이메일일 수 있습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@jj\\.ac\\.kr$";
        return email.matches(emailRegex);
    }
}
