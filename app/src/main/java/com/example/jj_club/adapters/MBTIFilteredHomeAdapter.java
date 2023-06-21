package com.example.jj_club.adapters;

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
import com.example.jj_club.models.MainHomeItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MBTIFilteredHomeAdapter extends RecyclerView.Adapter<MBTIFilteredHomeAdapter.MBTIFilteredHomeViewHolder> {

    private static final String TAG = "MBTIFilteredHomeAdapter";
    private String userMBTI;
    private List<MainHomeItem> homeItems;

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(MainHomeItem item);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MBTIFilteredHomeAdapter(DatabaseReference databaseRef, String userMBTI) {
        this.userMBTI = userMBTI;
        this.homeItems = new ArrayList<>();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                homeItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainHomeItem item = snapshot.getValue(MainHomeItem.class);
                    if (item != null) {
                        List<String> selectedButtons = item.getSelectedButtons();
                        // Check if selectedButtons is not null and contains userMBTI
                        if (selectedButtons != null && selectedButtons.contains(userMBTI)) {
                            homeItems.add(item);
                        }
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Failed to fetch data: " + databaseError.getMessage());
            }
        });

    }

    @NonNull
    @Override
    public MBTIFilteredHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_item, parent, false);
        return new MBTIFilteredHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MBTIFilteredHomeViewHolder holder, int position) {
        MainHomeItem model = homeItems.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(model);
                }
            }
        });

        Glide.with(holder.itemView.getContext())
                .load(model.getImageUrl())
                .into(holder.imageView);
        holder.titleTextView.setText(model.getTitle());
        holder.recruitPeriodTextView.setText(model.getRecruitPeriod());
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
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
