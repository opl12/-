package com.ying.jianxuan.ui.ui.zfa.list;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.ying.jianxuan.R;

import java.util.List;

public class itme extends BaseAdapter {

    public List<list> lists;
    public Activity activity;
    public itme(List<list> lists, Activity activity){
        this.lists=lists;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(activity).inflate(R.layout.zfa_item,parent,false);
        final list list=lists.get(position);
        ImageView imageView=convertView.findViewById(R.id.image);
        try {
            Glide.with(activity).load(list.getImageurl()).placeholder(R.drawable.jiazai).error(R.drawable.jiazsbai).into(imageView);
        }catch (Exception e){
        }
        TextView tile=convertView.findViewById(R.id.tile);
        tile.setText(list.getTile());
        TextView tile1=convertView.findViewById(R.id.tile1);
        tile1.setText(list.getTile1());
        TextView time=convertView.findViewById(R.id.time);
        time.setText(list.getTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, main.class);
                intent.putExtra("url",list.getUrl());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }
}
