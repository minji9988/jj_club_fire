package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.models.HomeItem;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHoder> {
    private List<HomeItem> promotions;

    private PromotionAdapter(List<HomeItem> promtions){
        this.promotions = promotions;
    }
    @NonNull
    @Override
    public PromotionAdapter.PromotionViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_more_item,parent,false);
        return new PromotionViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.PromotionViewHoder holder, int position) {
        HomeItem promtion = promotions.get(position);
        holder.titleTextView.setText(promtion.getTitle());
        holder.item_more_post_contents.setText(promtion.getPromotionIntroduce());
        //holder.item_more_post_love_count.setText(String.valueOf(model.getLikes() !=null ? model.getLikes().size() : 0));
        holder.item_more_post_date.setText(promtion.getRecruitPeriod());

    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public class PromotionViewHoder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView item_more_post_contents, item_more_post_date,item_more_post_love_count;
        ImageView item_more_post_img;
        public PromotionViewHoder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_more_post_title);
            item_more_post_contents = itemView.findViewById(R.id.item_more_post_contents);
            item_more_post_date = itemView.findViewById(R.id.item_more_post_date);
            item_more_post_love_count = itemView.findViewById(R.id.item_more_post_love_count);
            item_more_post_img = itemView.findViewById(R.id.item_more_post_img);

        }
    }
}
/*
public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {

    private List<Promotion> promotions;

    public PromotionAdapter(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_promotion, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        Promotion promotion = promotions.get(position);
        holder.titleTextView.setText(promotion.getTitle());
        // Bind other data to the views as needed
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        // Add other views here as needed

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            // Initialize other views here as needed
        }
    }
}

 */