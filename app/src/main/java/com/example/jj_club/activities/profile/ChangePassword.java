package com.example.jj_club.activities.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jj_club.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button btn_changePassword;
    private EditText et_changePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btn_changePassword = (Button) findViewById(R.id.btn_changePassword);
        et_changePassword = (EditText) findViewById(R.id.et_changePassword);

        btn_changePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.et_changePassword)).getText().toString();

                if (email.length() > 0) {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                showToast("이메일을 보냈습니다");
                            }
                        }
                    });
                } else {
                    showToast( "이메일을 입력해 주세요.");
                }
            }
        });
    }
    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}