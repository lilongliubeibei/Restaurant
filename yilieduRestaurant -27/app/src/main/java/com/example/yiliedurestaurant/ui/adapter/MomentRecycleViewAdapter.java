package com.example.yiliedurestaurant.ui.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiliedurestaurant.R;
import com.example.yiliedurestaurant.model.Moments;
import com.example.yiliedurestaurant.ui.customizedView.MyGridView;

import java.util.List;

public class MomentRecycleViewAdapter extends RecyclerView.Adapter<MomentRecycleViewAdapter.ViewHolder> {

    List<Moments> moments_List;
    Context context;

    public MomentRecycleViewAdapter(List<Moments> moments_List, Context context) {
        this.moments_List = moments_List;
        this.context = context;

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;
        TextView tv_shareCount;
        TextView tv_comment;
        TextView tv_like;
        MyGridView gridView;

        public ViewHolder(@NonNull View view) {
            super(view);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_shareCount = (TextView) view.findViewById(R.id.tv_shareCount);
            tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            tv_like = (TextView) view.findViewById(R.id.tv_like);
            gridView = (MyGridView) view.findViewById(R.id.gridView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MomentRecycleViewAdapter.ViewHolder viewHolder, int position) {
        final Moments moments = moments_List.get(position);
        viewHolder.tv_content.setText(moments.getContent());
        viewHolder.tv_shareCount.setText(String.valueOf(moments.getShareCount()));
        viewHolder.tv_like.setText(String.valueOf(moments.getLikeCount()));
        viewHolder.tv_comment.setText(String.valueOf(moments.getCommentCount()));
        GridViewAdapter gridViewAdapter = new GridViewAdapter(context, moments.getImgUrls());

        if (moments.getImgUrls().size() > 4||moments.getImgUrls().size() == 3) {
            viewHolder.gridView.setNumColumns(3);
        } else if (moments.getImgUrls().size() > 1) {
            viewHolder.gridView.setNumColumns(2);
        } else if (moments.getImgUrls().size() == 1) {
            viewHolder.gridView.setNumColumns(1);
        }

        viewHolder.gridView.setAdapter(gridViewAdapter);
    }

    @Override
    public int getItemCount() {
        return moments_List.size();
    }
}
