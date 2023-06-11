package com.example.jj_club.activities.promotion;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jj_club.R;
import com.example.jj_club.fragments.PromotionPageInfoFragment;
import com.example.jj_club.fragments.PromotionPageQAFragment;
import com.example.jj_club.fragments.PromotionPageReviewsFragment;
import com.google.android.material.tabs.TabLayout;

public class PromotionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_page);

        // button_application에 클릭 리스너 추가
        Button applicationButton = findViewById(R.id.button_application);
        applicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ApplicationForm 액티비티로 이동
                Intent intent = new Intent(PromotionPage.this, ApplicationForm.class);
                startActivity(intent);
            }
        });

        TabLayout tabLayout = findViewById(R.id.tap);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (tab.getPosition()) {
                    case 0:
                        PromotionPageInfoFragment infoFragment = new PromotionPageInfoFragment();
                        fragmentTransaction.replace(R.id.information_tap, infoFragment);
                        break;
                    case 1:
                        PromotionPageReviewsFragment reviewsFragment = new PromotionPageReviewsFragment();
                        fragmentTransaction.replace(R.id.information_tap, reviewsFragment);
                        break;
                    case 2:
                        PromotionPageQAFragment qaFragment = new PromotionPageQAFragment();
                        fragmentTransaction.replace(R.id.information_tap, qaFragment);
                        break;
                    default:
                        break;
                }

                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 필요한 경우 탭 선택 취소 이벤트 처리
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 필요한 경우 탭 다시 선택 이벤트 처리
            }
        });
    }
}
