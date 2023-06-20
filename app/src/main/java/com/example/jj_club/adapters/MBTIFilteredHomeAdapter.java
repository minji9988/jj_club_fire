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

import java.util.ArrayList;
import java.util.List;

public class MBTIFilteredHomeAdapter extends RecyclerView.Adapter<MBTIFilteredHomeAdapter.MBTIFilteredHomeViewHolder> {

    private static final String TAG = "MBTIFilteredHomeAdapter";
    private String userMBTI;
    private List<MainHomeItem> homeItems;

    public MBTIFilteredHomeAdapter(List<MainHomeItem> homeItems, String userMBTI) {
        this.userMBTI = userMBTI;
        this.homeItems = new ArrayList<>();
        for (MainHomeItem item : homeItems) {
            if (item.getSelectedButtons() != null && item.getSelectedButtons().contains(userMBTI)) {
                this.homeItems.add(item);
            }
        }
    }
    @NonNull
    @Override
    public MBTIFilteredHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_item, parent, false);
        return new MBTIFilteredHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MBTIFilteredHomeViewHolder holder, int position) {
        MainHomeItem model = homeItems.get(position);

        Log.d(TAG, "Model selectedButtons: " + model.getSelectedButtons());
        Glide.with(holder.itemView.getContext())
                .load(model.getImageUrl())
                .into(holder.imageView);
        holder.titleTextView.setText(model.getTitle());
        holder.recruitPeriodTextView.setText(model.getRecruitPeriod());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PromotionDetailActivity.class);
                intent.putExtra("promotion_id", model.getPromotionId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    static class MBTIFilteredHomeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView recruitPeriodTextView;

        MBTIFilteredHomeViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view_home_item);
            titleTextView = view.findViewById(R.id.text_view_home_item_title);
            recruitPeriodTextView = view.findViewById(R.id.item_home_post_date);
        }
    }
}
