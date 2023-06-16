package com.example.jj_club.activities.myclub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;

import java.util.List;

public class MyClubAdapter extends RecyclerView.Adapter<MyClubAdapter.ViewHolder> {
    private Context context;
    private List<Club> clubList;

    public MyClubAdapter(Context context, List<Club> clubList) {
        this.context = context;
        this.clubList = clubList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_club, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Club club = clubList.get(position);
        holder.clubNameTextView.setText(club.getClubName());
    }

    @Override
    public int getItemCount() {
        return clubList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView clubNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            clubNameTextView = itemView.findViewById(R.id.clubNameTextView);
        }
    }
}
