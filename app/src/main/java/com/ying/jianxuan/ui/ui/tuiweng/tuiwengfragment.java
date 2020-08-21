package com.ying.jianxuan.ui.ui.tuiweng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import android.googd.View.SwipeRefreshView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ying.jianxuan.R;
import com.ying.jianxuan.activity.X5;
import com.ying.jianxuan.ui.ui.tuiweng.http.http;
import com.ying.jianxuan.ui.ui.tuiweng.http.item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.setfile;

@SuppressLint("HandlerLeak")
public class tuiwengfragment extends Fragment {

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    private List<com.ying.jianxuan.ui.ui.tuiweng.http.List> lists;
    private SwipeRefreshView swipeRefreshView;
    private item item;
    private Handler handler;
    public String cookie;
    public String httpurl;
    public WebView webView;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuwen, container, false);
        webView=view.findViewById(R.id.webvieww);
        X5(webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //不缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl("http://m.wufazhuce.com/one");
        webView.setVisibility(View.GONE);
        webView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLoadResource(WebView view, String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptThirdPartyCookies(webView,true);
                String CookieStr = cookieManager.getCookie(url);
                // http://m.wufazhuce.com/one/ajaxlist/0?_token=2519ffa1a2823f07aeae5b75fc4fb4bf22df949b
                String u=url.split("=")[0];
                if (u.equals("http://m.wufazhuce.com/one/ajaxlist/0?_token")) {
                    httpurl=url;
                    cookie=CookieStr;
                    File file=new File(sd+"cookie");
                    setfile(file,cookie);
                    webView.loadUrl("");
                   // Log.d("测试1", "" + url + "\ncookie:" + CookieStr);
                    swipeRefreshView(view);
                }
                super.onLoadResource(view, url);
            }
        });

        handler= handler();
        lists=new ArrayList<>();
        item=new item(lists,getActivity());
        listView=view.findViewById(R.id.tu_List);
        listView.setAdapter(item);
        swipeRefreshView = view.findViewById(R.id.tuiweng_swipe);
        swipeRefreshView(view);
        return view;
    }

    Handler handler(){
        Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what==0) {
                    item.notifyDataSetChanged();
                    swipeRefreshView.setRefreshing(false);
                }else if (msg.what==1){
                    swipeRefreshView.setRefreshing(false);
                    String text= (String) msg.obj;
                    Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();
                }
            }
        };
        return handler;
    }

    void swipeRefreshView(View view) {
        //进入初始化
        swipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                new http(handler,lists,httpurl,cookie).start();
                item.notifyDataSetChanged();
                swipeRefreshView.setRefreshing(false);
                swipeRefreshView.setRefreshing(true);
            }
        });


        swipeRefreshView.setOnLoadMoreListener(new SwipeRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (item.getCount()>1){
                Log.d("测试",""+item.getCount());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String dw[] = httpurl.split("=");
                            int dc = Integer.parseInt(lists.get(lists.size()-1).id);
                            String url = "http://m.wufazhuce.com/one/ajaxlist/" + dc + "?_token=" + dw[1];
                            new http(handler, lists, url, cookie).start();
                        }catch (Exception e){
                        }
                        swipeRefreshView.setLoading(false);
                    }
                }, 5000);
            }else {
                    swipeRefreshView.setLoading(false);
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
                        if (lists==null) {
                            webView.loadUrl("http://m.wufazhuce.com/one");
                        }
                        lists.clear();
                        new http(handler,lists,httpurl,cookie).start();
                        item.notifyDataSetChanged();
                        swipeRefreshView.setRefreshing(false);
                    }
                }, 5000);
            }
        });
    }

    public void X5(WebView webView) {
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowContentAccess(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        //webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
      //  webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
       // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        /*LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
        LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据*/
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
    }
}