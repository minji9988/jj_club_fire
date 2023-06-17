package com.example.jj_club.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.profile.ProfileRecivedapplicationformActivity2;
import com.example.jj_club.activities.profile.ProfileSendapplicationformActivity2;
import com.example.jj_club.models.ApplicationItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ApplicationItemSendAdapter extends RecyclerView.Adapter<ApplicationItemSendAdapter.ApplicationItemSendViewHolder>{

    private ArrayList<ApplicationItem> arrayList;
    private DatabaseReference mDatabase;
    private Context context;


    public ApplicationItemSendAdapter(ArrayList<ApplicationItem> arrayList, Context context) {
        this.arrayList = arrayList;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("applicationItems");
        this.context = context;

    }
    @NonNull
    @Override
    public ApplicationItemSendAdapter.ApplicationItemSendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_send_item, parent, false);
        ApplicationItemSendViewHolder holder = new ApplicationItemSendViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ApplicationItemSendAdapter.ApplicationItemSendViewHolder holder, int position) {
        holder.tv_id2.setText(arrayList.get(position).getFromUserId());
        holder.tv_application_content2.setText(arrayList.get(position).getAppIntro());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 항목 클릭 시 실행될 코드 작성
                // 새로운 화면으로 전환하는 코드를 추가하세요
                // 예를 들어, 다음과 같이 Intent를 사용하여 새로운 화면으로 이동할 수 있습니다:
                Intent intent = new Intent(view.getContext(), ProfileSendapplicationformActivity2.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0); //참이면 어레이리스트 사이즈를 가지고와라
    }

    public interface OnItemClickListener {
        void onItemClick(String sendToUserId);
    }
    private ApplicationItemAdapter.OnItemClickListener listener;//추가

    public void setOnItemClickListener(ApplicationItemAdapter.OnItemClickListener listener) { //sendToUserId를 listener로하고
        this.listener = listener;                                       // mListener = listener;추가
    }

    public class ApplicationItemSendViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id2, tv_application_content2;
        public ApplicationItemSendViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_id2 = itemView.findViewById(R.id.tv_id2);
            this.tv_application_content2 = itemView.findViewById(R.id.tv_application_content2);

        }
    }
}