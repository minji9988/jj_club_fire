package com.example.jj_club.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HomeItemAdapter extends FirebaseRecyclerAdapter<HomeItem, HomeItemAdapter.HomeItemViewHolder> {

    public HomeItemAdapter(@NonNull FirebaseRecyclerOptions<HomeItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_more_item, parent, false);
        return new HomeItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position, @NonNull HomeItem model) {
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getRecruitPeriod());
    }

    static class HomeItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        HomeItemViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_more_post_title);
            description = view.findViewById(R.id.item_more_post_contents);
        }
    }
}
