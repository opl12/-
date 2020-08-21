package com.ying.jianxuan.ui.ui.zfa;

import android.googd.View.SwipeRefreshView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ying.jianxuan.R;
import com.ying.jianxuan.ui.ui.zfa.list.itme;
import com.ying.jianxuan.ui.ui.zfa.list.list;
import com.ying.jianxuan.ui.ui.zfa.list.sethttp;

import java.util.ArrayList;
import java.util.List;

public class zfa extends Fragment  {
    public int i=1;
    private itme itme;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.zfa,container,false);
        final ListView listView=root.findViewById(R.id.zfa);
        final List<list> lists=new ArrayList<>();
        itme=new itme(lists,getActivity());
        listView.setAdapter(itme);

        final SwipeRefreshView swiperereshlayout=root.findViewById(R.id.swipe);
        //隐藏刷新
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d("测试",""+lists.size());
                itme.notifyDataSetChanged();
                swiperereshlayout.setRefreshing(false);
                swiperereshlayout.setLoading(false);
            }
        };

        //进入初始化
        swiperereshlayout.post(new Runnable() {

            @Override
            public void run() {
                Log.i("getMeasuredHeight",swiperereshlayout.getMeasuredHeight()+"");
                new sethttp(handler,lists,i).start();
                itme.notifyDataSetChanged();
                swiperereshlayout.setRefreshing(false);
                swiperereshlayout.setRefreshing(true);
            }
        });
        //添加列表数据
        swiperereshlayout.setOnLoadMoreListener(new SwipeRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (itme.getCount()>1) {
                    i++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new sethttp(handler, lists, i).start();
                            itme.notifyDataSetChanged();
                            swiperereshlayout.setLoading(false);
                        }
                    }, 5000);
                }
            }
        });
        //下拉刷新
        swiperereshlayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        swiperereshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        i=1;
                        lists.clear();
                        new sethttp(handler,lists,i).start();
                        itme.notifyDataSetChanged();
                        swiperereshlayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });

        return root;
}
}
