package com.ying.jianxuan.ui.ui.zfa.list;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.googd.View.X5WebView;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.ying.jianxuan.R;
import com.ying.jianxuan.StatusUtil;
import com.ying.jianxuan.theme.theme;
import com.ying.jianxuan.ui.ui.xbao.http.MyDownloadStart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static android.view.View.GONE;
import static com.ying.jianxuan.StatusUtil.getStatusBarHeight;

public class main extends AppCompatActivity {
    public LinearLayout linearLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        new StatusUtil().initStatusView(this);
        setContentView(R.layout.main);
        linearLayout=findViewById(R.id.colorn);

        //高度
        LinearLayout gao=findViewById(R.id.gao);
        gao.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,getStatusBarHeight(this)));
        new theme(this).setBackground(linearLayout);
        //权限申请以及状态栏配置
        //接收要显示的链接
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        final SwipeRefreshLayout swipeRefreshLayout=findViewById(R.id.qww);
        final X5WebView webView = findViewById(R.id.webview);

        webView.setBackgroundColor(0);
        //X5(webView);
        final ProgressBar mProgressBar = new ProgressBar(main.this, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setVisibility(GONE);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setProgress(0);
        webView.addView(mProgressBar);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(GONE);
                } else {
                    if (mProgressBar.getVisibility() == GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        // webView.setDownloadListener(new MyDownloadStart(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }});
        //初始化webview
        web(webView);
        //webview判断是不是webview刷新
        webView.setOnScrollListener(new X5WebView.IScrollListener() {
            @Override
            public void onScrollChanged(int scrollY) {
                if (scrollY == 0) {
                    //开启下拉刷新
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    //关闭下拉刷新
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        //加载webview数据
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                swipeRefreshLayout.setRefreshing(false);
                String html = (String) msg.obj;
                webView.loadDataWithBaseURL(null,
                        html, "text/html", "utf-8", null);
            }
        };

        //刷新事件
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Threa(url,handler);
                        swipeRefreshLayout.setRefreshing(true);
                    }
                }, 5000);
            }
        });

        //进入直接加载数据
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                Threa(url,handler);
            }
        });

        Log.d("测试", url);
        //顶部状态栏返回事件
        ImageButton fui=findViewById(R.id.fui);
        fui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });
    }


    public void web(final X5WebView webView){
        webView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        // 长按点击事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final X5WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == X5WebView.HitTestResult.IMAGE_TYPE ||
                        hitTestResult.getType() == X5WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // 弹出保存图片的对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                    builder.setTitle("提示");
                    builder.setMessage("保存图片到本地");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String url = hitTestResult.getExtra();
                            // 下载图片到本地
                            DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener() {
                                @Override
                                public void getDownPath(String s) {
                                    Toast.makeText(main.this, "下载完成", Toast.LENGTH_LONG).show();
                                }
                            });
                        }});

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // 自动dismiss
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return false;
            }
        });
    }
    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData (String bodyHTML){
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public String urll="https://www.guofenzhuan.com/";
    public void Threa(final String url, final Handler handler){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Document doc = Jsoup.connect(url).get();

                        Elements element = doc.getElementsByClass("bor-sty bg-fff bout-content app-info");
                        Elements d=doc.getElementsByClass("code-right");
                        Elements b=doc.getElementsByClass("comment");
                       // System.out.print(element.html().substring(d.html().length()+36,b.html().length()));

                        //content是后台返回的h5标签
                        Message message=new Message();
                        message.obj=getHtmlData(element.html().substring(d.html().length()+36,b.html().length()).replace("/app","https://www.guofenzhuan.com/app").replace("/upload","https://www.guofenzhuan.com/upload").replace("/download",urll+"download").replace("/getcode",urll+"/getcode").replace(d.html(),""));
                        handler.sendMessage(message);
                    }catch (Exception e){
                    }
                }
            }.start();
        }
}
