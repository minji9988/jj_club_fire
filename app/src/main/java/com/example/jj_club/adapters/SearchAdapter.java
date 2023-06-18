package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_more_post_title);
        }
    }
}


