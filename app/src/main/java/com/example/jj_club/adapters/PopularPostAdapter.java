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

public class PopularPostAdapter extends FirebaseRecyclerAdapter<MainHomeItem, PopularPostAdapter.PopularPostHolder> {

    public PopularPostAdapter(@NonNull FirebaseRecyclerOptions<MainHomeItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public PopularPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout in reverse order
        return new PopularPostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull PopularPostHolder holder, int position, @NonNull MainHomeItem model) {
        // Adjust the position to bind items in reverse order
        int reversePosition = getItemCount() - 1 - position;

        // Populate the item with the data in model
        Glide.with(holder.itemView.getContext())
                .load(getItem(reversePosition).getImageUrl())
                .into(holder.postImageView);
        holder.titleTextView.setText(getItem(reversePosition).getTitle());
        holder.recruitPeriodTextView.setText(getItem(reversePosition).getRecruitPeriod());

        // Set the scale type of the ImageView to centerCrop
        holder.postImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        // Get the ID of the current item in reverse order
        String itemId = getRef(reversePosition).getKey();



        // Set a click listener on the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the item ID to the PromotionDetailActivity
                Intent intent = new Intent(holder.itemView.getContext(), PromotionDetailActivity.class);
                intent.putExtra("promotion_id", itemId);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }



    public class PopularPostHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView recruitPeriodTextView;
        ImageView postImageView;

        public PopularPostHolder(View itemView) {
            super(itemView);

            postImageView = itemView.findViewById(R.id.image_view_home_item);
            titleTextView = itemView.findViewById(R.id.text_view_home_item_title);
            recruitPeriodTextView = itemView.findViewById(R.id.item_home_post_date);
        }
    }
}
