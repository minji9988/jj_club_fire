package com.example.jj_club.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.profile.ProfileSendapplicationformActivity2;
import com.example.jj_club.models.ApplicationItem;
import com.example.jj_club.models.HomeItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

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
    public ApplicationItemSendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_send_item, parent, false);
        return new ApplicationItemSendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationItemSendViewHolder holder, int position) {
        ApplicationItem applicationItem = arrayList.get(position);

        holder.tv_id2.setText(applicationItem.getAppName());
        holder.tv_application_content2.setText(applicationItem.getAppIntro());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();


        DatabaseReference promotionItemRef = FirebaseDatabase.getInstance().getReference("promotions")
                .child(applicationItem.getPromotionId());

        promotionItemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    HomeItem homeItem = dataSnapshot.getValue(HomeItem.class); // 이 부분은 어떤 클래스를 사용해야 할지 확인하셔야 합니다.
                    Map<String, String> joinStatuses = homeItem.getJoinStatuses();

                    if (joinStatuses != null) {
                        String joinStatus = joinStatuses.get(userId);

                        if (joinStatus != null) {
                            switch (joinStatus) {
                                case "승인":
                                    holder.tv_joinStatus.setText("승인");
                                    break;
                                case "승인 거절":
                                    holder.tv_joinStatus.setText("승인 거절");
                                    break;
                                default:
                                    holder.tv_joinStatus.setText("승인 대기중");
                                    break;
                            }
                        } else {
                            holder.tv_joinStatus.setText("상태 정보 없음");
                        }
                    } else {
                        holder.tv_joinStatus.setText("상태 정보 없음");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", error.getMessage());
            }
        });

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
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ApplicationItemSendViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id2, tv_application_content2, tv_joinStatus;
        public ApplicationItemSendViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_id2 = itemView.findViewById(R.id.tv_id2);
            this.tv_application_content2 = itemView.findViewById(R.id.tv_application_content2);
            this.tv_joinStatus = itemView.findViewById(R.id.tv_reject0Rapproval); // 해당 TextView를 초기화
        }
    }

}