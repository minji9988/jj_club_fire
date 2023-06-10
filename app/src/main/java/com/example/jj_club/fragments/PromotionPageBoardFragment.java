//package com.example.jj_club.fragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.jj_club.R;
//import com.example.jj_club.adapters.BoardAdapter;
//import com.example.jj_club.models.request.BoardRequest;
//import com.example.jj_club.network.PromotionPageInterface;
//import com.example.jj_club.network.RetrofitClient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class PromotionPageBoardFragment extends Fragment {
//
//    private ListView listView;
//    private BoardAdapter boardAdapter;
//    private List<BoardRequest> boardList;
//
//    private PromotionPageInterface promotionPageInterface;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.promotion_page_board, container, false);
//
//        // Retrofit 인스턴스 생성
//        promotionPageInterface = RetrofitClient.getClient().create(PromotionPageInterface.class);
//
//        listView = view.findViewById(R.id.boardlist);
//        boardList = new ArrayList<>();
//        boardAdapter = new BoardAdapter(requireContext(), boardList);
//        listView.setAdapter(boardAdapter);
//
//        // 서버에서 데이터 가져오기
//        getBoardItems();
//
//        return view;
//    }
//
//    private void getBoardItems() {
//        Call<List<BoardRequest>> call = promotionPageInterface.getBoardItems();
//        call.enqueue(new Callback<List<BoardRequest>>() {
//            @Override
//            public void onResponse(Call<List<BoardRequest>> call, Response<List<BoardRequest>> response) {
//                if (response.isSuccessful()) {
//                    List<BoardRequest> boardItems = response.body();
//                    if (boardItems != null) {
//                        boardList.addAll(boardItems);
//                        boardAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<BoardRequest>> call, Throwable t) {
//                // 요청 실패 처리
//            }
//        });
//    }
//}
