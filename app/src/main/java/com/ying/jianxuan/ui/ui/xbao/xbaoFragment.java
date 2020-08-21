package com.ying.jianxuan.ui.ui.xbao;

import android.content.Intent;
import android.googd.View.SwipeRefreshView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ying.jianxuan.R;
import com.ying.jianxuan.ui.ui.xbao.http.main;
import com.ying.jianxuan.ui.ui.xbao.http.itme;
import com.ying.jianxuan.ui.ui.xbao.http.list;
import com.ying.jianxuan.ui.ui.xbao.http.sethttp;

import java.util.ArrayList;
import java.util.List;

public class xbaoFragment extends Fragment {

    public int i=1;
    private SwipeRefreshView swipeRefreshView;
    private Handler handler;
    private itme itme;
    private List<list> lists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.xbao,container,false);

        ListView listView=view.findViewById(R.id.list);
        lists=new ArrayList<>();
        itme=new itme(lists,getActivity());
        listView.setAdapter(itme);

        handler=handler();
        swipeRefreshView=view.findViewById(R.id.swip);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), main.class);
                intent.putExtra("url",lists.get(position).getUrl());
                getActivity().startActivity(intent);
            }
        });
        swipeRefreshView(view);
        return view;
    }

    void swipeRefreshView(View view){
        //进入初始化
        swipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                new sethttp(handler,lists,i).start();
                itme.notifyDataSetChanged();
                swipeRefreshView.setRefreshing(false);
                swipeRefreshView.setRefreshing(true);
            }
        });

        //添加列表数据
        swipeRefreshView.setOnLoadMoreListener(new SwipeRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (itme.getCount()>1) {
                    i++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new sethttp(handler, lists, i).start();
                            itme.notifyDataSetChanged();
                            swipeRefreshView.setLoading(false);
                        }
                    }, 3000);
                }
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
                        i=1;
                        lists.clear();
                        new sethttp(handler,lists,i).start();
                        itme.notifyDataSetChanged();
                        swipeRefreshView.setRefreshing(false);
                    }
                }, 5000);
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
