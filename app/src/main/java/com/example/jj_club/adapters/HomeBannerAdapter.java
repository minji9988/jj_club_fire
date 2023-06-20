package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.models.HomeBannerItem;

import java.util.List;

public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.BannerViewHolder> {
    private List<HomeBannerItem> bannerItems;

    public HomeBannerAdapter(List<HomeBannerItem> bannerItems) {
        this.bannerItems = bannerItems;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_banner_item, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBannerAdapter.BannerViewHolder holder, int position) {
        HomeBannerItem bannerItem = bannerItems.get(position);
        Glide.with(holder.itemView)
                .load(bannerItem.getImageUrl())  // Here we use `getImageResId` instead of `getImageUrl`.
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bannerItem의 프로모션 ID를 사용해 특정 페이지로 이동하는 코드를 작성합니다.
//                Intent intent = new Intent(holder.itemView.getContext(), PromotionDetailActivity.class);
//                intent.putExtra("promotion_id", bannerItem.getPromotionId());
//                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bannerItems.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner_image);
        }
    }
}

