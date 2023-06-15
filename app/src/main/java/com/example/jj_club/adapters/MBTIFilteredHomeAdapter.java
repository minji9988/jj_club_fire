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

public class MBTIFilteredHomeAdapter extends FirebaseRecyclerAdapter<MainHomeItem, MBTIFilteredHomeAdapter.MBTIFilteredHomeViewHolder> {

    private static final String TAG = "MBTIFilteredHomeAdapter";
    private String userMBTI;

    public MBTIFilteredHomeAdapter(@NonNull FirebaseRecyclerOptions<MainHomeItem> options, String userMBTI) {
        super(options);
        this.userMBTI = userMBTI;
    }

    @Override
    protected void onBindViewHolder(@NonNull MBTIFilteredHomeViewHolder holder, int position, @NonNull MainHomeItem model) {
        if (model.getSelectedButtons() == null) {
            Log.d(TAG, "Model selectedButtons is null at position: " + position);
            return;
        } else {
            Log.d(TAG, "Model selectedButtons: " + model.getSelectedButtons());
            if (model.getSelectedButtons().contains(userMBTI)) {
                Glide.with(holder.itemView.getContext())
                        .load(model.getImageUrl())
                        .into(holder.imageView);
                holder.titleTextView.setText(model.getTitle());
                holder.recruitPeriodTextView.setText(model.getRecruitPeriod());

                String itemId = getRef(position).getKey();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(), PromotionDetailActivity.class);
                        intent.putExtra("promotion_id", itemId);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });

                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
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
