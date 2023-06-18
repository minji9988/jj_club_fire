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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        ApplicationItem applicationItem = arrayList.get(position);

        holder.tv_id2.setText(applicationItem.getAppName());
        holder.tv_application_content2.setText(applicationItem.getAppIntro());

        holder.itemView.setOnClickListener(v -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("applicationItems")
                    .child(applicationItem.getApplicationId())
                    .child("fromUserId");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String fromUserId = dataSnapshot.getValue(String.class);

                        Intent intent = new Intent(context, ProfileSendapplicationformActivity2.class);
                        intent.putExtra("applicationId", applicationItem.getApplicationId());
                        intent.putExtra("fromUserId", fromUserId);
                        context.startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 항목 클릭 시 실행될 코드 작성
                // 새로운 화면으로 전환하는 코드를 추가하세요
                // 예를 들어, 다음과 같이 Intent를 사용하여 새로운 화면으로 이동할 수 있습니다:
                Intent intent = new Intent(view.getContext(), ProfileSendapplicationformActivity2.class);
                view.getContext().startActivity(intent);
            }

        });*/

    }
    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0); //참이면 어레이리스트 사이즈를 가지고와라
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