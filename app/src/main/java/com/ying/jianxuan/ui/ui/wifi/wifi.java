package com.ying.jianxuan.ui.ui.wifi;

import android.app.AlertDialog;
import android.googd.Adapter.fragmnet_adapter;
import android.googd.View.SwipeRefreshView;
import android.googd.View.Switch;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ying.jianxuan.R;
import com.ying.jianxuan.ui.ui.wifi.http.http;

import java.util.ArrayList;
import java.util.List;

public class wifi extends Fragment {

    private SwipeRefreshView swipeRefreshView;
    private int i=1;
    private com.ying.jianxuan.ui.ui.wifi.http.itme itme;
    private Handler handler;
    private  List<com.ying.jianxuan.ui.ui.wifi.http.List> lists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wifi, container, false);
        handler=handler();
        ListView listView = view.findViewById(R.id.wifilist);
        swipeRefreshView = view.findViewById(R.id.swipe);
        lists= new ArrayList<>();
        itme= new com.ying.jianxuan.ui.ui.wifi.http.itme(lists, getActivity());
        listView.setAdapter(itme);
        swipeRefreshView(view);
        return view;
    }

        void swipeRefreshView(View view) {
        //进入初始化
        swipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                new http(handler, lists, i).start();
                itme.notifyDataSetChanged();
                swipeRefreshView.setRefreshing(true);
            }
        });

        //下拉刷新
        swipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        i = 1;
                        lists.clear();
                        new http(handler, lists, i).start();
                        itme.notifyDataSetChanged();
                        swipeRefreshView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    Handler handler(){
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what==0){
                    itme.notifyDataSetChanged();
                }
                else if (msg.what==1){

                    Toast.makeText(getContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                }
                swipeRefreshView.setRefreshing(false);
            }
        };
        return handler;
    }

}
