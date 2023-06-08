package com.example.jj_club.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jj_club.R;
import com.example.jj_club.models.HomeItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeItemAdapter extends FirestoreRecyclerAdapter<HomeItem, HomeItemAdapter.HomeItemHolder> {

    private static final String TAG = "HomeItemAdapter";

    public HomeItemAdapter(@NonNull FirestoreRecyclerOptions<HomeItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeItemHolder holder, int position, @NonNull HomeItem model) {
        holder.titleTextView.setText(model.getTitle());
        holder.recruitPeriodTextView.setText(model.getRecruitPeriod());
    }

    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item, parent, false);
        return new HomeItemHolder(v);
    }

    class HomeItemHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView recruitPeriodTextView;

        public HomeItemHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_home_item_title);
            recruitPeriodTextView = itemView.findViewById(R.id.text_view_home_item_date);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        Log.d(TAG, "Data changed");
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        super.onError(e);
        Log.e(TAG, "Error occurred: " + e.getMessage());
    }
}
