package com.ying.jianxuan.ui.ui.tuiweng.http;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ying.jianxuan.R;

import java.util.List;

public class item extends BaseAdapter {

    public Activity activity;
    public List<com.ying.jianxuan.ui.ui.tuiweng.http.List> lists;
    public item(List<com.ying.jianxuan.ui.ui.tuiweng.http.List> lists,Activity activity){
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
        com.ying.jianxuan.ui.ui.tuiweng.http.List list=lists.get(position);
        View view= LayoutInflater.from(activity).inflate(R.layout.tuweng_item,parent,false);
        TextView textView=view.findViewById(R.id.time);
        textView.setText(list.date);
        TextView vol=view.findViewById(R.id.vol);
        vol.setText(list.title);
        TextView po=view.findViewById(R.id.tilte);
        po.setText(list.picture_author);
        TextView co=view.findViewById(R.id.tilte1);
        co.setText(list.content);
        TextView text=view.findViewById(R.id.tilte2);
        text.setText(list.text_authors);
        ImageView imageView=view.findViewById(R.id.image_tuweng);
        try {
            Glide.with(activity).load(list.img_url).placeholder(R.drawable.jiazai).error(R.drawable.jiazsbai).into(imageView);
            //Picasso.get().load(list.img_url).placeholder(R.drawable.jiazai).error(R.drawable.jiazsbai).into(imageView);
        }catch (Exception e){
        }

        return view;
    }
}
