package com.example.jj_club.activities.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.jj_club.R;
import com.example.jj_club.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileEditActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromProfileEdit;
    private ImageButton btn_profileImage;

    private EditText btn_editTextNickname, btn_editTextMbti, btn_editTextPassword;
    private Button btn_save;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");
                    btn_profileImage.setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        //연결
        btn_editTextNickname = findViewById(R.id.btn_editTextNickname);
        btn_editTextMbti = findViewById(R.id.btn_editTextMbti);
        btn_editTextPassword = findViewById(R.id.btn_editTextPassword);
        btn_save = findViewById(R.id.btn_save);
        btn_profileImage = (ImageButton) findViewById(R.id.btn_profileImage);
        btn_GoBackProfile_fromProfileEdit = (ImageButton) findViewById(R.id.btn_GoBackProfile_fromProfileEdit);

/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");
*/

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNickname();
            }
        });



        //카메라 권한
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);

        //뒤로 돌아가기
        btn_GoBackProfile_fromProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //이미지 올리기 버튼
        btn_profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        btn_editTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEditActivity.this, ChangePassword.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void changeNickname(){
        String newNickname = btn_editTextNickname.getText().toString().trim();
        String newMbti = btn_editTextMbti.getText().toString().trim();

        if(newNickname.isEmpty()||newMbti.isEmpty()){
            Toast.makeText(this,"Please enter a nickname",Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if(user !=null){
            String uid = user.getUid();
            DatabaseReference userRef = mDatabase.child("users").child(uid);
            userRef.child("name").setValue(newNickname)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProfileEditActivity.this,"changed successfully",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ProfileEditActivity.this,"Failed change",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        if(user !=null){
            String uid = user.getUid();
            DatabaseReference userRef = mDatabase.child("users").child(uid);
            userRef.child("phoneNumber").setValue(newMbti)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProfileEditActivity.this,"changed successfully",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ProfileEditActivity.this,"Failed change",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}