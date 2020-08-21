package com.ying.jianxuan.ui.Right;

import android.googd.View.X5WebView;
import android.googd.x5webview.WebViewClient;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.ying.jianxuan.JNI.id;
import com.ying.jianxuan.R;
import com.ying.jianxuan.theme.theme;

import java.io.File;
import java.util.Map;

import static android.view.View.GONE;
import static com.ying.jianxuan.StatusUtil.getStatusBarHeight;
import static com.ying.jianxuan.StatusUtil.setfile;

public class main extends Fragment {

    private SwipeRefreshLayout swipeRefreshView;
    private ViewPager pager;
    private Map<String,String> map;

    public main(ViewPager pager, Map<String,String> map,int i) {
        this.pager=pager;
        this.map=map;
        id file1=new id();
        File file = new File(file1.getfragment());
        setfile(file, ""+i);
    }

    public X5WebView x5WebView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.big,container,false);
        TextView tile=view.findViewById(R.id.tile);

        tile.setText(map.get("tile"));
        //状态栏高度
        LinearLayout gao=view.findViewById(R.id.gao);
        gao.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,getStatusBarHeight(getContext())));
        LinearLayout linearLayout=view.findViewById(R.id.color);
        new theme(getContext()).setBackground(linearLayout);

        ImageButton left = view.findViewById(R.id.lbio);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });

        x5WebView=view.findViewById(R.id.webview);
        x5WebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x5WebView.requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        X5(x5WebView);
        x5WebView.setBackgroundColor(0);
        swipeRefreshView=view.findViewById(R.id.swip);
        x5WebView.setOnScrollListener(new X5WebView.IScrollListener() {
            @Override
            public void onScrollChanged(int scrollY) {
                if (scrollY == 0) {
                    //开启下拉刷新
                    swipeRefreshView.setEnabled(true);
                } else {
                    //关闭下拉刷新
                    swipeRefreshView.setEnabled(false);
                }
            }
        });

        final ProgressBar mProgressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setVisibility(GONE);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setProgress(0);
        x5WebView.addView(mProgressBar);

        x5WebView.setWebViewClient(new WebViewClient());

        x5WebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView =getActivity(). findViewById(R.id.frame_web_video);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }
            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    mProgressBar.setVisibility(GONE);
                } else {
                    if (mProgressBar.getVisibility() == GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(i);
                }
            }
        });

        menu();

        swipeRefreshView(x5WebView);
        return view;
    }

    void menu() {
        final ImageButton cd=view.findViewById(R.id.cd);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 初始化菜单
                PopupMenu popupMenu = new PopupMenu(getContext(), cd);

                // 将菜单视图文件绑定到popupMenu的menu对象上
                popupMenu.getMenuInflater().inflate(R.menu.web, popupMenu.getMenu());

                // 注册菜单项监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.hui:
                                x5WebView.goForward();
                                Snackbar.make(view, "网页成功回调", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                break;
                            case R.id.fhui:
                                x5WebView.goBack();
                                Snackbar.make(view, "网页成功返回", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                break;
                        }
                        return false;
                    }
                });

                // 显示菜单
                popupMenu.show();
            }
        });
    }


    void swipeRefreshView(final X5WebView x5WebView) {
        //进入初始化
        swipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                x5WebView.loadUrl(map.get("url"));

                swipeRefreshView.setRefreshing(false);
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
                        x5WebView.loadUrl(x5WebView.getUrl());
                        swipeRefreshView.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    public void X5(X5WebView x5WebView) {
        WebSettings webSetting = x5WebView.getSettings();
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
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        // 开启支持视频
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setGeolocationEnabled(true);

        // 开启DOM缓存。
        webSetting.setDomStorageEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDatabasePath(getActivity().getApplicationContext().getCacheDir()
                .getAbsolutePath());
        webSetting.setAppCacheEnabled(true);
        webSetting.setAppCachePath(getActivity().getApplicationContext().getCacheDir()
                .getAbsolutePath());
        webSetting.setAppCacheMaxSize(Integer.MAX_VALUE);

        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

        /*LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
        LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据*/
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
    }
}
