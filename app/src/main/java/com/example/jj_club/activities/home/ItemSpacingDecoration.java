package com.example.jj_club.activities.home;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemSpacingDecoration extends RecyclerView.ItemDecoration {
    private int spacing;

    public ItemSpacingDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = spacing;
        outRect.right = spacing;
    }
}
