package com.ying.jianxuan.ui.left;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ying.jianxuan.List.item;
import com.ying.jianxuan.R;

import java.util.List;

public class left_adapter extends BaseAdapter {

    List<item> list;
    Activity activity;

    public left_adapter(Activity activity,List<item> list){
        this.list=list;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=activity.getLayoutInflater().inflate(R.layout.item,parent,false);
        item item=list.get(position);
        LinearLayout linearLayout=view.findViewById(R.id.dblb);
        linearLayout.setVisibility(item.Visibility);
        TextView title1=view.findViewById(R.id.lb);
        title1.setText(item.title1);
        final TextView title=view.findViewById(R.id.title);
        title.setText(item.title);
        return view;
    }}