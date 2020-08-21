package com.ying.jianxuan;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.googd.View.Switch;
import android.googd.View.X5WebView;
import android.googd.key.Key;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.textfield.TextInputEditText;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.ying.jianxuan.JNI.JNI;
import com.ying.jianxuan.Ua.Ua;
import com.ying.jianxuan.activity.dibu.HTTP;
import com.ying.jianxuan.activity.dibu.ip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;
import static com.tencent.smtt.sdk.QbSdk.checkApkExist;
import static com.ying.jianxuan.StatusUtil.getfile;
import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.setfile;
import static com.ying.jianxuan.ui.Right.Ydu.webyes;

public class XY {

    public int cs;
    public int huadocs;
    public int gg;
    public int yduzt;
    public int ip;

    public boolean b=true;
    public String api="/sdcard/JianXuan/api.txt";
    private Activity context;
    private X5WebView x5WebView;
    public Handler handler;
    public EditText url;
    public Switch huado;


    private View view;
    public XY(final Activity context, X5WebView x5WebView, EditText url,Switch huado,View view){
        this.huado=huado;
        this.view=view;
        this.x5WebView=x5WebView;
        this.context=context;
        this.handler=handler();
        this.url=url;
    }

    private void clearCache(){
        File file = context.getCacheDir().getAbsoluteFile();//删除缓存
        deleteFile(file);
    }

    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }

    //清空缓存
    void setqko(){
                try {
                    x5WebView.clearCache(true);
                    WebStorage.getInstance().deleteAllData();
                    x5WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    // 清空所有Cookie
                    CookieSyncManager.createInstance(context);
                    CookieManager cookieManager = CookieManager.getInstance();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        cookieManager.removeSessionCookies(null);
                        cookieManager.removeAllCookie();
                        cookieManager.flush();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            cookieManager.removeSessionCookies(null);
                        }
                        cookieManager.removeAllCookie();
                        CookieSyncManager.getInstance().sync();
                    }
                    clearCache();
                    try {
                        context.deleteDatabase("webview.db");
                        context.deleteDatabase("webviewCache.db");
                    } catch (Exception e) {
                    }
                    Toast.makeText(context, "删除缓存成功", Toast.LENGTH_LONG).show();
                } catch (
                        final Exception e) {
                            Toast.makeText(context, "删除缓存错误:"+e, Toast.LENGTH_LONG).show();
                }
    }

    int In(String string){
        return  Integer.parseInt(string);
    }

    //访问网络
    public int i=0;
    String a[];
    public int uaid=0;
    void setloadUrl(EditText url,X5WebView x5WebView){
        LinearLayout linearLayout=view.findViewById(R.id.webview);
        linearLayout.setVisibility(View.GONE);
        a=url.getText().toString().split("\n");
        if (a.length<=i){
            i=0;
        }
        if (a.length==1){
           // url.setText(converKeywordLoadOrSearch(url.getText().toString()));
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
                    Toast.makeText(context,"ua已经用完",Toast.LENGTH_LONG).show();
                }else {
                    for (int c=uaid;v.length>c;c++) {
                        fg=c;
                        ua = v[c];
                        if (ua.length() > 10) {
                            break;
                        }
                    }
                    setfile(file3,""+fg);
                    webSettings.setUserAgentString(ua);
                    HashMap headers = new HashMap();
                    headers.put("User-Agent", ua);
                    headers.put("X-Requested-With", "com.tencent.mm");
                    x5WebView.loadUrl(a[i-1], headers);
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
            }
        }else {
            x5WebView.loadUrl(a[i-1]);
        }
        }
        else { x5WebView.loadUrl(a[i-1]); }
        File file1 = new File(sd + "url.txt");
        setfile(file1,url.getText().toString());
    }





    private Handler handler(){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String text= (String) msg.obj;
                if (msg.what==0){
                    String ip[]=text.split(":");
                    new WebviewSettingProxy().setProxy(x5WebView,ip[0],Integer.parseInt(ip[1].replaceAll("\\s*", "")),"android.app.Application");
                    Toast.makeText(context, "开启成功:"+text, Toast.LENGTH_LONG).show();
                }else if (msg.what==1){
                    new WebviewSettingProxy().revertBackProxy(x5WebView, "android.app.Application");
                    Toast.makeText(context, "开启失败原因:"+text, Toast.LENGTH_LONG).show();
                }
            }
        };
        return handler;
    }

    private String app="android.app.Application";
    private boolean ipi=true;
    public void setyes(){
        final File file=new File(sd+"js.txt");
        if (file.exists()){
            JSONObject jsonObject= JSON.parseObject(getfile(file));
            String cs1=jsonObject.getString("cs");
            cs=In(cs1);
            String huadocs1=jsonObject.getString("huadocs");
            huadocs=Integer.parseInt(huadocs1);
            String gg1=jsonObject.getString("gg");
            gg=In(gg1);
            String yduzt1=jsonObject.getString("yduzt");
            yduzt=In(yduzt1);
            String ip1=jsonObject.getString("ip");
            ip=In(ip1);
        }
    }

    public Thread start(){
        final List<ip> list = new ArrayList<>();

        final Handler yt=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                x5WebView.loadUrl("");
                setqko();
            }
        };

        final Handler mip=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what==1){
                    int random=(int)(Math.random()*list.size()+1);
                    ip ip=list.get(random-1);
                    String i=ip.ip;
                    int h=Integer.parseInt(ip.host);
                    boolean iap=new WebviewSettingProxy().setProxy(x5WebView,i, h, app);
                    if (iap){
                        Toast.makeText(context, "代理成功:" + i+":"+h+"\n地址:"+ip.dz, Toast.LENGTH_LONG).show();
                    }
                    ipi=false;
                }else if (msg.what==0){
                    String d= (String) msg.obj;
                    Toast.makeText(context, "代理失败:"+d, Toast.LENGTH_LONG).show();
                }
            }
        };

        final Handler web=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d("测试","运行8");
                setloadUrl(url,x5WebView);
                Log.d("测试","运行9");
            }
        };


        final Handler han=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                try {
                    String text = (String) msg.obj;
                    Log.d("测试",text);
                    if (msg.what == 0) {
                        String ip[] = text.split(":");
                        boolean iap=new WebviewSettingProxy().setProxy(x5WebView, ip[0], Integer.parseInt(ip[1].replaceAll("\\s*", "")), app);
                        if (iap){
                            ipi=false;
                            Toast.makeText(context, "代理成功:" + text, Toast.LENGTH_LONG).show();
                        }
                    } else if (msg.what == 1) {
                        new WebviewSettingProxy().revertBackProxy(x5WebView, app);
                        Toast.makeText(context, "开启失败原因:" + text, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context, "开启失败原因:" + e, Toast.LENGTH_LONG).show();
                }
            }
        };

        Display defaultDisplay =context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        final int x2 = point.x;
        final int y2 = point.y;
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                for (int i=0;i<cs;i++) {

                    if (huado.getText().toString().contains("开启滑动")) {
                        break;
                    }

                    if (ip == 1) {
                        getmfip(mip, list);
                    }else {
                        File file=new File(sd+"api.txt");
                        if (file.exists()){
                            new HTTP(getfile(file),han).start();
                        }else {
                            Toast.makeText(context,"未设置API链接,请按左上角列表按钮",Toast.LENGTH_LONG).show();
                        }
                    }

                    while (ipi){
                       // Log.d("测试","运行77"+ipi);
                        if (!ipi){
                            break;
                        }
                    }
                  //  Log.d("测试","运行77"+ipi);
                    ipi=true;

                   // Log.d("测试","运行");
                    Message message=new Message();
                    message.what=0;
                    web.sendMessage(message);
                   // Log.d("测试","运行10");
                    while (webyes){
                        if (!webyes){
                            break;
                        }
                    }
                  //  Log.d("测试","运行11");
                    webyes=true;
                    Thread.sleep(1500);
                    //Log.d("测试8",x5WebView.getTitle());
                    for (int n = 0; n <= huadocs; n++) {
                        if (huado.getText().toString().contains("开启滑动")) {
                            break;
                        }
                        int x = (int) (50 + Math.random() * (x2 - 50 + 1));
                        int y = (int) (200 + Math.random() * (y2 - 200 + 1));
                        int yiup = (int) (2 + Math.random() * (5 - 2 + 1));
                        sendHover(x, y, yiup);
                        int millis = (int) (1000 + Math.random() * (5000 - 1000 + 1));
                        Thread.sleep(millis);
                    }

                    int x = (int) (100 + Math.random() * (x2 - 100 + 1));
                    int y = (int) (200 + Math.random() * (y2 - 200 + 1));
                    int mill = (int) (0 + Math.random() * (100 - 0 + 1));
                    if (mill <=gg) {
                        tap(x, y);
                        Thread.sleep(5000);
                        for (int w = 0; w <= 20; w++) {
                            int x1 = (int) (50 + Math.random() * (x2 - 50 + 1));
                            int y1 = (int) (200 + Math.random() * (y2 - 200 + 1));
                            int yiup = (int) (4 + Math.random() * (6 - 4 + 1));
                            sendHover(x1, y1, yiup);
                            int millis = (int) (1000 + Math.random() * (5000 - 1000 + 1));
                            Thread.sleep(millis);
                        }
                    }
                    Message message1=new Message();
                    message.what=0;
                    yt.sendMessage(message1);
                    Thread.sleep(yduzt);
                }}catch (Exception e){
                    Log.d("测试xy",""+e);
                } }};

        return thread;

    }

    void getmfip(final Handler handler, final List<ip> list){
        try{
                    for (int i = 1; i < 3; i++) {
                        Document doc = Jsoup.connect("https://ip.jiangxianli.com/?page=" + i).get();
                        Elements a = doc.getElementsByClass("layui-table");
                        Elements d = a.get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr");
                        for (int x = 0; x < d.size(); x++) {
                            String string[] = d.get(x).html().split("\n");
                            ip ip = new ip();
                            ip.host = string[1].substring(4, string[1].length() - 5);
                            ip.ip = string[0].substring(4, string[0].length() - 5);
                            ip.dz = string[4].substring(4, string[4].length() - 5);
                            list.add(ip);
                        }
                    }
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }catch (Exception e){
                    Message message = new Message();
                    message.what=0;
                    message.obj=""+e;
                    handler.sendMessage(message);
                }
    }

    private String s(String url) throws Exception {
        URL ur = new URL(url);
        HttpURLConnection httpURLConnection= (HttpURLConnection) ur.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(5000);
        //httpURLConnection.setRequestProperty("Cookie","Hm_lvt_c945a8c1ead2ed7539596f4ded541ec1=1593311272,1593312766,1593313459; PHPSESSID=0tahhe1nb0fh113dsc1j0pkkmj; _gid=GA1.2.1298140848.1593311272; _ga=GA1.2.1518271864.1593311272");
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        InputStream inputStream=httpURLConnection.getInputStream();
        byte[] by = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        int len=-1;
        while ((len=inputStream.read(by))!=-1){
            byteArrayOutputStream.write(by,0,len);
        }
        byteArrayOutputStream.close();
        String text = byteArrayOutputStream.toString("utf-8");
        return text;
    }

    private Point getpoint(){
        Display defaultDisplay =context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point;
    }

    //模拟点击
    //抢先赚点开全文532/765
    private void tap(int x,int y) {
            Log.d("测试",""+x+y);
            Instrumentation iso = new Instrumentation();
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));    //x,y 即是事件的坐标
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0));
    }

    //滑动
    private void sendHover(final int x, final int y, final int isup) {
            Instrumentation iso = new Instrumentation();
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, x, y, 0));
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 20, MotionEvent.ACTION_MOVE, x, y - 30 * isup, 0));
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 40, MotionEvent.ACTION_MOVE, x, y - 60 * isup, 0));
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 60, MotionEvent.ACTION_MOVE, x, y - 90 * isup, 0));
            iso.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis() + 60, MotionEvent.ACTION_UP, x, y - 90 * isup, 0));
    }

    private int getX(){
        Point point=getpoint();
        int x = point.x;
        int y2 = point.y;
        return x;
    }

    private int getY(){
        Point point=getpoint();
        int x = point.x;
        int y = point.y;
        return y;
    }


}
