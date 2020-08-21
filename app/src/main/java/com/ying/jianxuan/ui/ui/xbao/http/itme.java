package com.ying.jianxuan.ui.ui.xbao.http;

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
        convertView= LayoutInflater.from(activity).inflate(R.layout.xbao_item,parent,false);
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
        String tex[]=list.getTime().split("-");
        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;tex.length>i;i++){
            stringBuffer.append(filterNumber(tex[i])+"-");
        }
        time.setText(stringBuffer.toString().substring(0,stringBuffer.toString().length()-1));

        return convertView;
    }

    public  String filterNumber(String number) {
        number = number.replaceAll("[^(0-9)]", "");
        return number;
    }

}
