package com.example.jj_club.adapters;
// BoardAdapter.java 파일

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jj_club.R;
import com.example.jj_club.models.request.BoardRequest;

import java.util.List;

public class BoardAdapter extends BaseAdapter {
    private Context context;
    private List<BoardRequest> boardItems;

    public BoardAdapter(Context context, List<BoardRequest> boardItems) {
        this.context = context;
        this.boardItems = boardItems;
    }

    @Override
    public int getCount() {
        return boardItems.size();
    }

    @Override
    public Object getItem(int position) {
        return boardItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_board, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.profileImage = convertView.findViewById(R.id.profile_image);
            viewHolder.idTextView = convertView.findViewById(R.id.id);
            viewHolder.titleTextView = convertView.findViewById(R.id.title);
            viewHolder.contentTextView = convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BoardRequest boardItem = boardItems.get(position);
        // boardItem에서 필요한 데이터를 가져와서 해당 뷰에 설정
        viewHolder.profileImage.setImageResource(boardItem.getProfileImage());
        viewHolder.idTextView.setText(boardItem.getId());
        viewHolder.titleTextView.setText(boardItem.getTitle());
        viewHolder.contentTextView.setText(boardItem.getContent());

        return convertView;
    }

    private static class ViewHolder {
        ImageView profileImage;
        TextView idTextView;
        TextView titleTextView;
        TextView contentTextView;
    }
}