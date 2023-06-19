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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<HomeItem> homeItemList;

    public SearchAdapter(List<HomeItem> homeItemList) {
        this.homeItemList = homeItemList;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_more_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        HomeItem homeItem = homeItemList.get(position);
        holder.titleTextView.setText(homeItem.getTitle());
        holder.item_more_post_contents.setText(homeItem.getPromotionIntroduce());
        //holder.item_more_post_love_count.setText(String.valueOf(model.getLikes() !=null ? model.getLikes().size() : 0));
        holder.item_more_post_date.setText(homeItem.getRecruitPeriod());
    }

    @Override
    public int getItemCount() {
        return homeItemList.size();
    }
    public void filterList(List<HomeItem> filteredList) {
        homeItemList = filteredList;
        notifyDataSetChanged();
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView item_more_post_contents, item_more_post_date,item_more_post_love_count;
        ImageView item_more_post_img;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_more_post_title);
            item_more_post_contents = itemView.findViewById(R.id.item_more_post_contents);
            item_more_post_date = itemView.findViewById(R.id.item_more_post_date);
            item_more_post_love_count = itemView.findViewById(R.id.item_more_post_love_count);
            item_more_post_img = itemView.findViewById(R.id.item_more_post_img);

        }
    }
}


