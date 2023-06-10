//package com.example.jj_club.fragments;
//
//// PromotionPageInfoFragment.java
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.jj_club.R;
//import com.google.android.material.tabs.TabLayout;
//
//public class PromotionPageInfoFragment extends Fragment {
//
//    public PromotionPageInfoFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.promotion_info_page, container, false);
//
//        TabLayout tabLayout = view.findViewById(R.id.tap);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                FragmentManager fragmentManager = getChildFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                switch (tab.getId()) {
//                    case R.id.tab_intro:
//                        // 해당 탭에 맞는 프래그먼트를 생성하여 프레임 레이아웃에 표시합니다.
//                        // 예시: PromotionPageIntroFragment
//                        PromotionPageIntroFragment introFragment = new PromotionPageIntroFragment();
//                        fragmentTransaction.replace(R.id.fragment_container, introFragment);
//                        break;
//                    case R.id.tab_board:
//                        // 해당 탭에 맞는 프래그먼트를 생성하여 프레임 레이아웃에 표시합니다.
//                        // 예시: PromotionPageBoardFragment
//                        PromotionPageBoardFragment boardFragment = new PromotionPageBoardFragment();
//                        fragmentTransaction.replace(R.id.fragment_container, boardFragment);
//                        break;
//                    case R.id.tab_calendar:
//                        // 해당 탭에 맞는 프래그먼트를 생성하여 프레임 레이아웃에 표시합니다.
//                        // 예시: PromotionPageCalendarFragment
//                        PromotionPageCalendarFragment calendarFragment = new PromotionPageCalendarFragment();
//                        fragmentTransaction.replace(R.id.fragment_container, calendarFragment);
//                        break;
//                    case R.id.tab_qa:
//                        // 해당 탭에 맞는 프래그먼트를 생성하여 프레임 레이아웃에 표시합니다.
//                        // 예시: PromotionPageChatFragment
//                        PromotionPageChatFragment chatFragment = new PromotionPageChatFragment();
//                        fragmentTransaction.replace(R.id.fragment_container, chatFragment);
//                        break;
//                    default:
//                        break;
//                }
//
//                fragmentTransaction.commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                // Handle tab unselected event if needed
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                // Handle tab reselected event if needed
//            }
//        });
//
//        return view;
//    }
//}
//
