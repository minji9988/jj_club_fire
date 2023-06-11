package com.example.jj_club.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jj_club.R;
import com.example.jj_club.models.LoveitItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;

public class LoveitAdapter extends RecyclerView.Adapter<LoveitAdapter.LoveitViewHolder> {
    private ArrayList<LoveitItem> arrayList; //어레이리스트를 LoveitItem에 연결해놓음
    private Context context; //액티비티마다 콘텍스트가 있는데 어뎁터에서 액티비티 액션을 가져올때

    //알트+인설트 ->constructor
    public LoveitAdapter(ArrayList<LoveitItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public LoveitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_more_item,parent,false);
        LoveitViewHolder holder = new LoveitViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoveitViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImageUrl())
                .into(holder.item_more_post_img); //이미지뷰안에 넣어주기
        holder.item_more_post_title.setText(arrayList.get(position).getTitle());
        holder.item_more_post_contents.setText(arrayList.get(position).getContents());
        holder.item_more_post_date.setText(arrayList.get(position).getRecruitPeroid()); //우선 모집기간
        holder.item_more_post_love_count.setText(arrayList.get(position).getLikes());

    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size():0);
    }

    //home_more_item 연결
    public class LoveitViewHolder extends RecyclerView.ViewHolder {
        ImageView item_more_post_img;
        TextView item_more_post_title;
        TextView item_more_post_contents;
        TextView item_more_post_date;
        TextView item_more_post_love_count;

        public LoveitViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item_more_post_date =itemView.findViewById(R.id.item_more_post_date);
            this.item_more_post_img =itemView.findViewById(R.id.item_more_post_img);
            this.item_more_post_title =itemView.findViewById(R.id.item_more_post_title);
            this.item_more_post_contents =itemView.findViewById(R.id.item_more_post_contents);
            this.item_more_post_love_count =itemView.findViewById(R.id.item_more_post_love_count);

        }
    }
}
