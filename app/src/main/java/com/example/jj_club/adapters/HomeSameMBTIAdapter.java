package com.example.jj_club.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.models.MainHomeItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeSameMBTIAdapter extends FirebaseRecyclerAdapter<MainHomeItem, HomeSameMBTIAdapter.HomeSameMBTIHolder> {

    private DatabaseReference mDatabase;

    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private String userMBTI;

    public HomeSameMBTIAdapter(@NonNull FirebaseRecyclerOptions<MainHomeItem> options, String userMBTI) {
        super(options);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotions");
        this.userMBTI = userMBTI;
    }

    @NonNull
    @Override
    public HomeSameMBTIHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item, parent, false);
        return new HomeSameMBTIHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeSameMBTIHolder holder, int position, @NonNull MainHomeItem model) {
        Log.d("HomeSameMBTIAdapter", "onBindViewHolder called for position: " + position);
        Log.d("HomeSameMBTIAdapter", "User MBTI: " + userMBTI);
        Log.d("HomeSameMBTIAdapter", "Item MBTI: " + model.getSelectedButtons());

        if (model.getSelectedButtons().contains(userMBTI)) {
            Log.d("HomeSameMBTIAdapter", "MBTI match found for position: " + position);
            Glide.with(holder.itemView.getContext())
                    .load(getItem(position).getImageUrl())
                    .into(holder.postImageView);
            holder.titleTextView.setText(getItem(position).getTitle());
            holder.recruitPeriodTextView.setText(getItem(position).getRecruitPeriod());

            String itemId = getRef(position).getKey();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), PromotionDetailActivity.class);
                    intent.putExtra("promotion_id", itemId);
                    holder.itemView.getContext().startActivity(intent);
                }
            });

            // Make the view visible
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            Log.d("HomeSameMBTIAdapter", "No MBTI match found for position: " + position);
            // Hide the view
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    public class HomeSameMBTIHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView recruitPeriodTextView;
        ImageView postImageView;

        public HomeSameMBTIHolder(View itemView) {
            super(itemView);

            postImageView = itemView.findViewById(R.id.image_view_home_item);
            titleTextView = itemView.findViewById(R.id.text_view_home_item_title);
            recruitPeriodTextView = itemView.findViewById(R.id.item_home_post_date);
        }
    }
}
