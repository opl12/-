package com.ying.jianxuan.ui.Right;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.googd.View.X5WebView;
import android.googd.key.Key;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import com.tencent.smtt.sdk.WebViewClient;
import com.ying.jianxuan.CopyLinkTextHelper;
import com.ying.jianxuan.JNI.JNI;
import com.ying.jianxuan.R;
import com.ying.jianxuan.StatusUtil;
import com.ying.jianxuan.Ua.Ua;
import com.ying.jianxuan.activity.dibu.dibu;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ying.jianxuan.StatusUtil.getfile;
import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.setfile;
import static com.ying.jianxuan.StatusUtil.verifyStoragePermissions;
public class Ydu extends Fragment {

    public static boolean webyes=true;
    private ViewPager pager;
    public Ydu(ViewPager pager) {
        File file = new File(sd + "fragment.txt");
        setfile(file, "1");
        this.pager = pager;
    }

    private X5WebView x5WebView;
    private EditText url;
    private LinearLayout linearLayout_url;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ydu_activity,container,false);
        //状态栏高度
        LinearLayout gao=view.findViewById(R.id.gao);
        linearLayout_url=view.findViewById(R.id.webview);
        url1=view.findViewById(R.id.url1);
        url = view.findViewById(R.id.url);
        gao.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,getStatusBarHeight(getContext())));
        this.x5WebView=view.findViewById(R.id.webview_ydu);

        x5WebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && x5WebView.canGoBack()) {
                        //后退
                        x5WebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        x5(x5WebView,view);
        X5(x5WebView);

       // Xweb(x5WebView);
        x5WebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                if( s.startsWith("http:") || s.startsWith("https:") ) {
                    WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                    //hitTestResult==null解决重定向问题
                    if (!TextUtils.isEmpty(s) && hitTestResult == null) {
                        webView.loadUrl(s);
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(webView, s);
            }

        });

        x5WebView.setBackgroundColor(0);
        toolbar(view);
        dibu(view,x5WebView);


        webview(view);

        return view;
    }

    void webview(View view){
        final EditText webviw_url=view.findViewById(R.id.webview_url);
        final ImageView imageViewd=view.findViewById(R.id.webview_as);
        imageViewd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.setText(webviw_url.getText().toString());
                setloadUrl(url,x5WebView);
            }
        });

        webviw_url.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 获得焦点
                    imageViewd.setVisibility(VISIBLE);
                   // showKeyboard(getContext(),webviw_url);
                } else {
                    hideInput();
                    // 失去焦点
                  imageViewd.setVisibility(GONE);
                }
            }
        });

    }

    void X5(X5WebView x5WebView) {
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

    private TextView url1;

    void x5(X5WebView viewx5,View view){
        //webview进度条
        final ProgressBar progressBar=getProgressBar();
        viewx5.addView(progressBar);
        progressBar.setVisibility(GONE);
        viewx5.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title != null) {
                    if (title.contains("404")) {
                        url.setVisibility(VISIBLE);
                                                //加载错误显示的页面
                                                //showErrorPage();
                    } else {
                        if (title.equals("")){
                            url1.setText("青阅极致生活");
                          //  x5WebView.setVisibility(GONE);
                           // linearLayout_url.setVisibility(VISIBLE);
                        }else {
                            url1.setText(title);
                           // x5WebView.setVisibility(VISIBLE);
                            //linearLayout_url.setVisibility(GONE);
                        }
                        //title为webview标题内容
                        url.setVisibility(GONE);
                        url1.setVisibility(VISIBLE);
                    }
                }
                super.onReceivedTitle(view, title);
            }
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(view.getContext());
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // 在此处进行跳转URL的处理, 一般情况下_black需要重新打开一个页面,
                       view.loadUrl(url);
                        return true;
                    }

                });
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                return true;
            }
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
                    webyes=false;
                    progressBar.setVisibility(GONE);
                } else {
                    if (progressBar.getVisibility() == GONE)
                        progressBar.setVisibility(VISIBLE);
                    progressBar.setProgress(i);
                }
            }
        });
    }

    //访问网络
    public int i=0;
    String a[];
    public int uaid=0;
    void setloadUrl(EditText url,X5WebView x5WebView){
        x5WebView.setVisibility(VISIBLE);
        linearLayout_url.setVisibility(GONE);
        a=url.getText().toString().split("\n");
        if (a.length<=i){
            i=0;
        }
        if (a.length==1){
            url.setText(converKeywordLoadOrSearch(url.getText().toString()));
        }
        a=url.getText().toString().split("\n");
        if (i <= a.length) {
            i++;
            if (i-1==a.length) {
                i=1;
            }
        }
        File file = new File(sd + "qun.txt");
        if (file.exists()){ if (getfile(file).equals("1")){
          WebSettings webSettings=  x5WebView.getWebSettings();
          int f=0;
          File file1=new File(sd+"zdyua.txt");
          if (file1.exists()) {
              if (getfile(file1).equals("1")) {
                  f = 1;
              }
          }
          if (f==1){
              File file2=new File(sd+"UA.txt");
              String uaa=getfile(file2);
              File l = new File(uaa);
              String r = new Ua().getfile(l);
              String v[] = r.split("\n");
              File file3=new File(sd+"uaid.txt");
              if (file3.exists()){
                int ie=Integer.parseInt(new JNI().getFile(sd+"uaid.txt"));
                uaid=ie+1;
              }
              String ua = null;
              int fg=0;
              if (uaid>=v.length){
                  Toast.makeText(getContext(),"ua已经用完",Toast.LENGTH_LONG).show();
              }else {
                  for (int c = uaid; v.length > c; c++) {
                      fg = c;
                      ua = v[c];
                      if (ua.length() > 10) {
                          break;
                      }
                  }
                  setfile(file3, "" + fg);
                  webSettings.setUserAgentString(ua);
                  HashMap headers = new HashMap();
                  headers.put("User-Agent", ua);
                  headers.put("X-Requested-With", "com.tencent.mm");
                  x5WebView.loadUrl(a[i - 1], headers);
              }
          }else {
              File l = new File(sd + "UA");
                      String r = new Ua().getfile(l);
                      String v[] = r.split("\n");
                      String ua = new Ua().getua(v);
                      webSettings.setUserAgentString(ua);
                      HashMap headers = new HashMap();
                      headers.put("User-Agent", ua);
                      headers.put("X-Requested-With", "com.tencent.mm");
                      x5WebView.loadUrl(a[i - 1], headers);
                      // Toast.makeText(context,"卡密有效:"+key.getDate2String(Long.parseLong(time),"yyyy-MM-dd HH:mm:ss"),Toast.LENGTH_LONG).show();
          }
        }else {
            x5WebView.loadUrl(a[i-1]);
        } }
        else { x5WebView.loadUrl(a[i-1]); }
        File file1 = new File(sd + "url.txt");
        setfile(file1,url.getText().toString());
    }



    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    void dibu(final View view, final X5WebView x5WebView){
        final LinearLayout linearLayout=view.findViewById(R.id.dibu);
        final LinearLayout zhuu=view.findViewById(R.id.zhuu);
        ImageButton lbb=view.findViewById(R.id.lbb);
        lbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhuu.setVisibility(GONE);
                linearLayout.setVisibility(VISIBLE);
                new dibu(view,getActivity());
            }
        });

        ImageButton zhu=view.findViewById(R.id.image);
        zhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x5WebView.loadUrl("");
            }
        });
        ImageButton fui=view.findViewById(R.id.fui);

        fui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("测试",x5WebView.getUrl());
                x5WebView.goBack();
            }
        });

        ImageButton dfui=view.findViewById(R.id.dfui);
        dfui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x5WebView.goForward();
            }
        });

    }

    void toolbar(View view) {
        //底部状态栏操作
        final ImageView yes=view.findViewById(R.id.yes);


        url1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url1.setVisibility(GONE);
                url.setVisibility(VISIBLE);
                url.requestFocus();//请求焦点
                url.findFocus();//获取焦点
            }
        });

        url.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 获得焦点
                    yes.setImageDrawable(getResources().getDrawable(R.mipmap.a7));
                    yes.setRotation(180);
                    url.setVisibility(VISIBLE);
                    showKeyboard(getContext(),url);
                } else {
                    hideInput();
                    // 失去焦点
                    yes.setImageDrawable(getResources().getDrawable(R.mipmap.ao));
                    yes.setRotation(0);
                    if (url1.equals("")) {
                        url.setVisibility(VISIBLE);
                        url1.setVisibility(GONE);
                    }
                    else {
                        url.setVisibility(GONE);
                        url1.setVisibility(VISIBLE);
                    }
                }
            }
        });

        file(url);
        ImageButton left = view.findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        ImageButton jia = view.findViewById(R.id.jia);
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log(url);
            }
        });
       // ImageButton yes = view.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                    setloadUrl(url, x5WebView);
               }catch (Exception e){
                    Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //关闭键盘
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(INPUT_METHOD_SERVICE);        View v =getActivity(). getWindow().peekDecorView();        if (null != v) {            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);        }    }

    //弹出键盘
    private void showKeyboard(Context context, final View view) {

// 必须给控件加这个方法，否则无效
        view.requestFocus();
        Timer timer = new Timer(); //设置定时器
        timer.schedule(new TimerTask() {
            @Override
            public void run() { //弹出软键盘的代码
                InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }, 0); //设置1000毫秒的时长

    }

    void file(EditText url) {
        verifyStoragePermissions(getActivity());
        File file=new File(sd);
        if (!file.exists()){
            file.mkdirs();
        }else {
            File file1=new File(sd+"url.txt");
            if (!file1.exists()){
            }else {
                url.setText(new Ua().getfile(file1));
            }
        }
    }

    void km(){

    }

    void log(final EditText url){
        View view=getActivity().getLayoutInflater().inflate(R.layout.edtext,null,false);
        final EditText editText=view.findViewById(R.id.edit);
        editText.setText(url.getText());
        final Button zt=view.findViewById(R.id.zt);
        Button cqx=view.findViewById(R.id.cqx);
        Button cyes=view.findViewById(R.id.cyes);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
        alertDialog.setView(view);
        final AlertDialog log=alertDialog.show();
        zt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(new StatusUtil().getzt(getContext()));
            }
        });
        cqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.dismiss();
            }
        });
        cyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.setText(editText.getText());
                log.dismiss();
            }
        });
    }

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

    public ProgressBar getProgressBar(){
        ProgressBar mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setProgress(0);
        return mProgressBar;
    }

    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static final String FILE = "file://";
    /**
     * 将关键字转换成最后转换的url
     *
     * @param keyword
     * @return
     */
    private String converKeywordLoadOrSearch(String keyword) {
        keyword = keyword.trim();
        if (keyword.startsWith("www.")) {
            keyword = HTTP + keyword;
        } else if (keyword.startsWith("ftp.")) {
            keyword = "ftp://" + keyword;
        }
        boolean containsPeriod = keyword.contains(".");
        boolean isIPAddress = (TextUtils.isDigitsOnly(keyword.replace(".", ""))
                && (keyword.replace(".", "").length() >= 4) && keyword.contains("."));
        boolean aboutScheme = keyword.contains("about:");
        boolean validURL = (keyword.startsWith("ftp://") || keyword.startsWith(HTTP)
                || keyword.startsWith(FILE) || keyword.startsWith(HTTPS))
                || isIPAddress;
        boolean isSearch = ((keyword.contains(" ") || !containsPeriod) && !aboutScheme);

        if (isIPAddress
                && (!keyword.startsWith(HTTP) || !keyword.startsWith(HTTPS))) {
            keyword = HTTP + keyword;
        }

        String converUrl;
        if (isSearch) {
            try {
                keyword = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            converUrl = "https://m.baidu.com/s?wd=" + keyword + "&ie=UTF-8";
        } else if (!validURL) {
            converUrl = HTTP + keyword;
        } else {
            converUrl = keyword;
        }
        return converUrl;
    }
}
