package com.example.jj_club.activities.promotion;

// PromotionPage.java

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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

        TabLayout tabLayout = findViewById(R.id.tap);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (tab.getId()) {
                    case R.id.tab_info:
                        PromotionPageInfoFragment infoFragment = new PromotionPageInfoFragment();
                        fragmentTransaction.replace(R.id.information_tap, infoFragment);
                        break;
                    case R.id.tab_reviews:
                        PromotionPageReviewsFragment reviewsFragment = new PromotionPageReviewsFragment();
                        fragmentTransaction.replace(R.id.information_tap, reviewsFragment);
                        break;
                    case R.id.tab_qa:
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
                // Handle tab unselected event if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselected event if needed
            }
        });
    }
}
