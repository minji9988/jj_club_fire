package com.example.jj_club.activities.myclub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.activities.promotion.PromotionDetailActivity;
import com.example.jj_club.models.MyClubItem;

import java.util.List;

public class MyClubAdapter extends RecyclerView.Adapter<MyClubAdapter.MyClubViewHolder> {
    private List<MyClubItem> myClubItems;
    private Context context;

    public MyClubAdapter(List<MyClubItem> myClubItems, Context context) {
        this.myClubItems = myClubItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_club_item, parent, false);
        return new MyClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClubViewHolder holder, int position) {
        MyClubItem myClubItem = myClubItems.get(position);
        holder.textClubName.setText(myClubItem.getMeetingName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyClubItem item = myClubItems.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, PromotionDetailActivity.class);
                intent.putExtra("promotion_id", item.getPromotionId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myClubItems.size();
    }

    public static class MyClubViewHolder extends RecyclerView.ViewHolder {
        public TextView textClubName;

        public MyClubViewHolder(@NonNull View itemView) {
            super(itemView);
            textClubName = itemView.findViewById(R.id.text_club_name);
        }
    }
}
