package com.example.jj_club.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.activities.profile.ProfileRecivedapplicationformActivity2;
import com.example.jj_club.models.ApplicationItem;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ApplicationItemAdapter extends RecyclerView.Adapter<ApplicationItemAdapter.ApplicationItemViewHolder> { //알트엔터로 클래스랑 implement method뽑기


    private ArrayList<ApplicationItem> arrayList; //어레이리스트를 ApplicationItem에 연결해놓음

    private Context context; //액티비티마다 콘텍스트가 있는데 어뎁터에서 액티비티 액션을 가져올때\

    private HomeItemAdapter.OnItemClickListener mListener;

    private DatabaseReference mDatabase;



    //알트 인서트 -> 컨스트럭쳐
    public ApplicationItemAdapter(ArrayList<ApplicationItem> arrayList, Context context) {
        this.arrayList = arrayList;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("applicationItems");
        this.context = context;

    }

    @NonNull
    @Override
    public ApplicationItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //리스트뷰가 어뎁터에 연결되고 뷰홀더를 최초로 만들어냄
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.application_received_item,parent,false);
        ApplicationItemViewHolder holder = new ApplicationItemViewHolder(view);
        return holder;
    }

    //arrayList_HomeItem 이미지 부분 프로필로 바꿔야함
    @Override
    public void onBindViewHolder(@NonNull ApplicationItemViewHolder holder, int position) {//각 아이템에 대해 실제로 매칭시켜줌
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getFromUserId())
                .into(holder.iv_profileImage);
        holder.tv_id.setText(arrayList.get(position).getFromUserId());
        //holder.tv_PW.setText(String.valueOf(arrayList.get(position).getPw())); //에러뜸 int 니까 String.valutof()로감싸줘야함 arrayList.get(position).getPw()
        holder.tv_application_content.setText(arrayList.get(position).getAppIntro());


        //클릭시 화면전환
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 항목 클릭 시 실행될 코드 작성
                // 새로운 화면으로 전환하는 코드를 추가하세요
                // 예를 들어, 다음과 같이 Intent를 사용하여 새로운 화면으로 이동할 수 있습니다:
                Intent intent = new Intent(view.getContext(), ProfileRecivedapplicationformActivity2.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0); //참이면 어레이리스트 사이즈를 가지고와라
    }

    public interface OnItemClickListener {
        void onItemClick(String sendToUserId);
    }
    private OnItemClickListener listener;//추가

    public void setOnItemClickListener(OnItemClickListener listener) { //sendToUserId를 listener로하고
        this.listener = listener;                                       // mListener = listener;추가
    }


    /*
    public void setOnItemClickListener(HomeItemAdapter.OnItemClickListener listener) {
        mListener = listener;
    }*/


    //리사이클러 홀더 상속
    public class ApplicationItemViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profileImage;
        TextView tv_id;
        TextView tv_application_content;

        public ApplicationItemViewHolder(@NonNull View itemView) { //생성자
            super(itemView);
            this.iv_profileImage = itemView.findViewById(R.id.iv_profileImage);
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_application_content = itemView.findViewById(R.id.tv_application_content);

        }
    }

}



