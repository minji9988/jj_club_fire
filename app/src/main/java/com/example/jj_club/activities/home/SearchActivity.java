package com.example.jj_club.activities.home;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jj_club.R;

public class SearchActivity extends AppCompatActivity {

    SearchView search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_search);

        search_view = (SearchView)findViewById(R.id.search_view);

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //둘중 하나만 사용하는거
            @Override
            // 검색 버튼이 눌러졌을 때 이벤트 처리
            public  boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            // 검색어가 변경되었을 때 이벤트 처리(실시간으로 변하는거)
            public  boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
}