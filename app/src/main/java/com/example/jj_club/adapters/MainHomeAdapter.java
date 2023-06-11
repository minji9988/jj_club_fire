package com.example.jj_club.adapters;

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
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.models.MainHomeItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

// 메인 페이지와 연결된 것

public class MainHomeAdapter extends FirebaseRecyclerAdapter<MainHomeItem, MainHomeAdapter.MainHomeViewHolder> {

    public MainHomeAdapter(@NonNull FirebaseRecyclerOptions<MainHomeItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainHomeViewHolder holder, int position, @NonNull MainHomeItem model) {
        // Set data to views in the item layout
        Glide.with(holder.itemView.getContext())
                .load(model.getImageUrl())
                .into(holder.imageView);
        holder.titleTextView.setText(model.getTitle());
        holder.recruitPeriodTextView.setText(model.getRecruitPeriod());

        String itemId = getRef(position).getKey(); // Add this line to get the ID of the current item

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PromotionDetailActivity.class);
                intent.putExtra("promotion_id", itemId); // Pass the item ID as an extra
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MainHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item, parent, false);
        return new MainHomeViewHolder(view);
    }

    static class MainHomeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView recruitPeriodTextView;

        public MainHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_home_item);
            titleTextView = itemView.findViewById(R.id.text_view_home_item_title);
            recruitPeriodTextView = itemView.findViewById(R.id.item_home_post_date);
        }
    }
}