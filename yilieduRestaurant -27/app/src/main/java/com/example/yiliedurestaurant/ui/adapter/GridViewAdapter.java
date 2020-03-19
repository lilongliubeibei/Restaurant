package com.example.yiliedurestaurant.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yiliedurestaurant.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> urls = new ArrayList<>();

    public GridViewAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int i) {
        return urls.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(urls.size()>4){
            return 3;
        }else if(urls.size()>1){
            return 2;
        }
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        int itemSize=0 ;
        if(view == null){
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            float density = dm.density;
            int width = dm.widthPixels;
            if(urls.size()>4|urls.size() == 3){
                itemSize=(width-dip2px(context,34))/3;
            }else if(urls.size()>1){
                itemSize=(width-dip2px(context,32))/2;
            }else if(urls.size()==1){
                itemSize=width-dip2px(context,30);
            }
            //设置item高度

            view = LayoutInflater.from(context).inflate(R.layout.item_image,null);
            vh = new ViewHolder();
            vh.imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setLayoutParams(new GridView.LayoutParams(itemSize,itemSize));
            view.setTag(vh);
        }
        vh = (ViewHolder) view.getTag();
        if(urls!=null && urls.size()>0){
            Glide.with(context).load(urls.get(i)).centerCrop().into(vh.imageView);
            Log.d("asd", urls.get(i).toString());
        }
        return view;
    }

    class ViewHolder{
        ImageView imageView;
    }
    public static int dip2px(Context context,float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}