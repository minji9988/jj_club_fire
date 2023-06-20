package com.example.jj_club.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText nameEditText, phoneNumberEditText, passwordEditText, recheckPasswordEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_sign_up);

        // Firebase Authentication 인스턴스 초기화
        mAuth = FirebaseAuth.getInstance();
        // Firebase Realtime Database의 "users" 노드에 대한 참조 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        // UI 요소 변수에 할당
        nameEditText = findViewById(R.id.nameEditText);  // 닉네임
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText); // 전화번호
        passwordEditText = findViewById(R.id.passwordEditText); // 비밀번호
        recheckPasswordEditText = findViewById(R.id.recheckPasswordEditText); // 비밀번호 다시 입력란
        signUpButton = findViewById(R.id.signUpButton); // 회원가입 버튼

        // 회원가입 버튼에 클릭 리스너 설정
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 정보 가져오기
                String name = nameEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String recheckPassword = recheckPasswordEditText.getText().toString();

                // 비밀번호가 6자 이상인지 확인
                if (password.length() >= 6) {
                    // 비밀번호와 비밀번호 확인이 일치하는지 확인
                    if (password.equals(recheckPassword)) {
                        // 현재 인증된 사용자 가져오기
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // 비밀번호 업데이트 시도
                            user.updatePassword(password)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // 사용자 정보를 HashMap에 저장
                                                Map<String, Object> userInfo = new HashMap<>();
                                                userInfo.put("email", user.getEmail());
                                                userInfo.put("name", name);
                                                userInfo.put("phoneNumber", phoneNumber);

                                                // Firebase Realtime Database에 사용자 정보 저장
                                                mDatabase.child(user.getUid()).setValue(userInfo)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                // 회원가입 성공 메시지 출력
                                                                Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                                                                // 로그인 화면으로 이동
                                                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                // 회원가입 실패 메시지 출력
                                                                Toast.makeText(SignUpActivity.this, "회원가입 실패: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                            } else {
                                                // 비밀번호 업데이트 실패 메시지 출력
                                                Toast.makeText(SignUpActivity.this, "비밀번호 업데이트 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // 인증된 사용자가 아닌 경우 메시지 출력
                            Toast.makeText(SignUpActivity.this, "이메일 인증이 된 사용자가 아닙니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 비밀번호 일치하지 않는 경우 메시지 출력
                        Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 비밀번호가 6자 이상이 아닌 경우 메시지 출력
                    Toast.makeText(SignUpActivity.this, "비밀번호를 6자 이상 입력해야 합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
