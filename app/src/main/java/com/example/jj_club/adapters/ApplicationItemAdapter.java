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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ApplicationItemAdapter extends RecyclerView.Adapter<ApplicationItemAdapter.ApplicationItemViewHolder> {
    private ArrayList<ApplicationItem> arrayList;
    private Context context;

    public ApplicationItemAdapter(ArrayList<ApplicationItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ApplicationItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_received_item, parent, false);
        return new ApplicationItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationItemViewHolder holder, int position) {
        ApplicationItem applicationItem = arrayList.get(position);

        Glide.with(holder.itemView)
                .load(applicationItem.getFromUserId())
                .into(holder.iv_profileImage);

        holder.tv_name.setText(applicationItem.getAppName());
        holder.tv_intro.setText(applicationItem.getAppIntro());

        holder.itemView.setOnClickListener(v -> {
            // DB에서 applicationId에 해당하는 sendToUserId 조회
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("applicationItems")
                    .child(applicationItem.getApplicationId())
                    .child("sendToUserId");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String sendToUserId = dataSnapshot.getValue(String.class);

                        // 신청서 글 아이디 ProfileRecivedapplicationformActivity2로 넘겨주는 곳
                        Intent intent = new Intent(context, ProfileRecivedapplicationformActivity2.class);
                        intent.putExtra("applicationId", applicationItem.getApplicationId());
                        intent.putExtra("sendToUserId", sendToUserId);
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Error handling
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ApplicationItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profileImage;
        TextView tv_name, tv_intro;

        public ApplicationItemViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_profileImage = itemView.findViewById(R.id.received_item_profileImage);
            tv_name = itemView.findViewById(R.id.received_page_user_name);
            tv_intro = itemView.findViewById(R.id.received_page_user_intro);
        }
    }
}

