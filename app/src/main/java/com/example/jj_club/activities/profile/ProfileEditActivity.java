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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.jj_club.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileEditActivity extends AppCompatActivity {

    private ImageButton btn_GoBackProfile_fromProfileEdit;
    private ImageButton btn_profileImage;

    private EditText btn_editTextNickname, btn_editTextMbti, btn_editTextPassword;
    private Button btn_save;

    private DatabaseReference databaseReference;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap)data.getParcelableExtra("data");
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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");


        // 저장 버튼 눌렀을때
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemberInformation();
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

    }

    //왜 새로생기지..?
    private void saveMemberInformation() {
        String nickname = btn_editTextNickname.getText().toString().trim();
        //String mbti = btn_editTextMbti.getText().toString().trim();
        String password = btn_editTextPassword.getText().toString().trim();

        // Update member information in Firebase
        databaseReference.child("name").setValue(nickname);
        //databaseReference.child("mbti").setValue(mbti);
        //databaseReference.child("password").setValue(password);

        Toast.makeText(this, "Member information saved.", Toast.LENGTH_SHORT).show();
    }
}