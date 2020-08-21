package com.ying.jianxuan.activity.dibu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.googd.View.Switch;
import android.googd.View.X5WebView;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.textfield.TextInputEditText;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebStorage;
import com.ying.jianxuan.CopyLinkTextHelper;
import com.ying.jianxuan.R;
import com.ying.jianxuan.WebviewSettingProxy;
import com.ying.jianxuan.XY;
import com.ying.jianxuan.activity.X5;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ying.jianxuan.StatusUtil.deleteFile;
import static com.ying.jianxuan.StatusUtil.getfile;
import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.setfile;

public class dibu implements View.OnClickListener, Switch.OnClickListener {

    private  ImageButton dibu;
    private  View view;
    private Switch qun;
    //View点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dibu_GNNE:
                view.findViewById(R.id.dibu).setVisibility(View.GONE);
                view.findViewById(R.id.zhuu).setVisibility(View.VISIBLE);
                break;
        }
    }

    //Swich点击事件
    @Override
    public void OnClick(View v) {
        switch (v.getId()){
            //删除数据
            case R.id.qdooo:
                try {
                    DeleteFile(new File("data/data/" + context.getPackageName()));
                    Toast.makeText(context,"删除成功",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(context,"删除失败"+e,Toast.LENGTH_LONG).show();
                }
                break;
                //复制链接
            case R.id.fztext:
                new CopyLinkTextHelper(context).CopyText( x5WebView.getUrl().toString());
                Toast.makeText(context,"复制链接:"+ x5WebView.getUrl().toString(),Toast.LENGTH_LONG).show();
                break;
                //跳转x5设置
            case R.id.webvieww:
                Intent intent=new Intent(context, X5.class);
                context.startActivity(intent);
                break;
                //邮链
            case R.id.yl:
                alert_edit();
                break;
                //清空缓存
            case R.id.shangchu:
                x5WebView.loadUrl("");
                setqko();
                break;
            //弹窗ip设置
            case R.id.ippp:
                ip();
                break;
                //启动自定义ua
            case R.id.zdyua:
                File fe=new File(sd+"UA.txt");
                if (!fe.exists()){
                    Toast.makeText(context,"未自定义ua",Toast.LENGTH_LONG).show();
                }else {
                    File file=new File(sd+"zdyua.txt");
                    if (file.exists()){
                        if (getfile(file).contains("0")) {
                            setfile(file, "1");
                            zdyua.setTint(context.getResources().getColor(R.color.colorAccent));
                        }else {
                            setfile(file, "0");
                            zdyua.setTint(context.getResources().getColor(R.color.hei));
                        }
                    }else {
                        setfile(file, "1");
                        zdyua.setTint(context.getResources().getColor(R.color.colorAccent));
                    }}
                break;
                //启动阅读
            case R.id.qun:
                File fel=new File(sd+"UA");
                if (!fel.exists()){
                    Toast.makeText(context,"未下载ua",Toast.LENGTH_LONG).show();
                }else {
                    File file = new File(sd + "qun.txt");
                    if (file.exists()) {
                        if (getfile(file).equals("0")) {
                            setfile(file, "1");
                            qun.setTint(context.getResources().getColor(R.color.colorAccent));
                        } else {
                            //x5WebView.getWebSetting().setUserAgentString(null);
                            setfile(file, "0");
                            qun.setTint(context.getResources().getColor(R.color.hei));
                        }
                    } else {
                        setfile(file, "1");
                        qun.setTint(context.getResources().getColor(R.color.colorAccent));

                    }}
                break;
                //自定义ip
            case R.id.zdyip:
                setzdyip();
                break;
        }
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

    void setSwitch(){
        //删除数据
        Switch qdo=view.findViewById(R.id.qdooo);
        qdo.setOnClickListene(this);
        //复制链接
        Switch fztext=view.findViewById(R.id.fztext);
        fztext.setOnClickListene(this);
        //跳转x5内核设置
        Switch web=view.findViewById(R.id.webvieww);
        web.setOnClickListene(this);
        //邮链
        Switch yl=view.findViewById(R.id.yl);
        yl.setOnClickListene(this);
        //删除缓存
        Switch shangchu=view.findViewById(R.id.shangchu);
        shangchu.setOnClickListene(this);
        //弹窗ip设置
        Switch ipp=view.findViewById(R.id.ippp);
        ipp.setOnClickListene(this);
        //自定义ua
        zdyua=view.findViewById(R.id.zdyua);
        zdyua.setOnClickListene(this);
        //启动阅读模式
        qun=view.findViewById(R.id.qun);
        qun.setOnClickListene(this);
        //自定义ip
        Switch zdyip=view.findViewById(R.id.zdyip);
        zdyip.setOnClickListene(this);
    }

    private Activity context;

    private X5WebView x5WebView;
    public dibu(final View view, final Activity context){
        this.context=context;
        this.view=view;
        x5WebView=view.findViewById(R.id.webview_ydu);
        dibu=view.findViewById(R.id.dibu_GNNE);
        dibu.setOnClickListener(this);
        setSwitch();
        file();
        final EditText url = view.findViewById(R.id.url);
        final Switch huado=view.findViewById(R.id.huado);

        final XY xy=new XY(context,x5WebView,url,huado,view);
        huado.setOnClickListene(new Switch.OnClickListener() {
            @Override
            public void OnClick(View v) {
                Log.d("测试",""+xy.b);
                if (huado.getText().toString().contains("开启滑动")) {
                    huado(xy,huado);
                    huado.setText("关闭滑动");
                }else if (huado.getText().toString().contains("关闭滑动")){
                    huado.setText("开启滑动");
                    huado.setTint(context.getResources().getColor(R.color.hei));
                    xy.b=true;
                }
            }}); }

        void huado(final XY xy, final Switch huado){
        View view=context.getLayoutInflater().inflate(R.layout.zdjs,null,false);
        final TextInputEditText cs=view.findViewById(R.id.cs);
        final TextInputEditText huadocs=view.findViewById(R.id.huadocs);
        final TextInputEditText gg=view.findViewById(R.id.gg);
        final TextInputEditText yduzt=view.findViewById(R.id.yduzt);
        final TextInputEditText ip=view.findViewById(R.id.ip);
        final File file=new File(sd+"js.txt");
        if (file.exists()){
            JSONObject jsonObject= JSON.parseObject(getfile(file));
            String cs1=jsonObject.getString("cs");
            cs.setText(cs1);
            String huadocs1=jsonObject.getString("huadocs");
            huadocs.setText(huadocs1);
            String gg1=jsonObject.getString("gg");
            gg.setText(gg1);
            String yduzt1=jsonObject.getString("yduzt");
            yduzt.setText(yduzt1);
            String ip1=jsonObject.getString("ip");
            ip.setText(ip1);
        }

        Button yes=view.findViewById(R.id.yes);
        Button no=view.findViewById(R.id.no);
        Button gb=view.findViewById(R.id.gb);

        AlertDialog.Builder a=new AlertDialog.Builder(context,R.style.AlertDialog);
        a.setView(view);
        final AlertDialog alertDialog=a.show();

            gb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    huado.setTint(context.getResources().getColor(R.color.hei));
                    xy.b=true;
                    huado.setText("开启滑动");
                    alertDialog.dismiss();
                }
            });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                if (cs.getText()!=null){ if (huadocs.getText()!=null){ if (gg.getText()!=null){ if (yduzt!=null) { if (ip != null) {
                        jsonObject.put("cs", cs.getText().toString());
                        jsonObject.put("huadocs", huadocs.getText().toString());
                        jsonObject.put("gg", gg.getText().toString());
                        jsonObject.put("yduzt", yduzt.getText().toString());
                        jsonObject.put("ip", ip.getText().toString());
                        setfile(file, jsonObject.toString());
                        xy.b=false;
                        Toast.makeText(context, jsonObject.toString(), Toast.LENGTH_LONG).show();
                        xy.setyes();
                        xy.start().start();
                    alertDialog.dismiss();
                    huado.setTint(context.getResources().getColor(R.color.colorAccent));
                    }else { Toast.makeText(context, "某个属性设置为空", Toast.LENGTH_LONG).show(); } }else {  Toast.makeText(context, "某个属性设置为空", Toast.LENGTH_LONG).show();}}else {  Toast.makeText(context, "某个属性设置为空", Toast.LENGTH_LONG).show();}}else{  Toast.makeText(context, "某个属性设置为空", Toast.LENGTH_LONG).show();}}else {  Toast.makeText(context, "某个属性设置为空", Toast.LENGTH_LONG).show();} }});
    }

    public int y=0;
    private String app="android.app.Application";
    //ip设置
    void ip(){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                try {
                    String text = (String) msg.obj;
                    if (msg.what == 0) {
                        String ip[] = text.split(":");
                        boolean iap=new WebviewSettingProxy().setProxy(x5WebView, ip[0], Integer.parseInt(ip[1].replaceAll("\\s*", "")), app);
                        if (iap){
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

            View view=context.getLayoutInflater().inflate(R.layout.ipview,null,false);
            Button mfip=view.findViewById(R.id.mfip);
            Button dlip=view.findViewById(R.id.dlip);
            Button gbip=view.findViewById(R.id.gbip);
            AlertDialog.Builder a=new AlertDialog.Builder(context,R.style.AlertDialog);
            a.setView(view);
            final AlertDialog alertDialog=a.show();
            //关闭代理
            gbip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(new WebviewSettingProxy().revertBackProxy(x5WebView, "android.app.Application")){
                        Toast.makeText(context, "关闭成功", Toast.LENGTH_LONG).show();
                    }
                    alertDialog.dismiss();
                }
            });

            //设置apiip
        dlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(sd+"api.txt");
                if (file.exists()){
                    new HTTP(getfile(file),handler).start();
                }else {
                    Toast.makeText(context,"未设置API链接,请按左上角列表按钮",Toast.LENGTH_LONG).show();
                }
                alertDialog.dismiss();
            }
        });

        final List<ip> list = new ArrayList<>();
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
                }else if (msg.what==0){
                    String d= (String) msg.obj;
                    Toast.makeText(context, "代理失败:"+d, Toast.LENGTH_LONG).show();
                }
            }
        };

        //免费ip
        mfip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmfip(mip,list);
                alertDialog.dismiss();
            }
        });

    }

    void getmfip(final Handler handler, final List<ip> list){
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
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
        }};
        thread.start();
    }

    void setzdyip(){
        View view=context.getLayoutInflater().inflate(R.layout.zdyip,null,false);
        final TextInputEditText textInputEditText=view.findViewById(R.id.a);
        Button yes=view.findViewById(R.id.yes);
        Button no=view.findViewById(R.id.no);
        AlertDialog.Builder a=new AlertDialog.Builder(context,R.style.AlertDialog);
        a.setView(view);
        final AlertDialog alertDialog=a.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ip[] = textInputEditText.getText().toString().split(":");
                    boolean iap = new WebviewSettingProxy().setProxy(x5WebView, ip[0], Integer.parseInt(ip[1].replaceAll("\\s*", "")), app);
                    if (iap) {
                        Toast.makeText(context, "代理成功:" + textInputEditText.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "代理原因:" + textInputEditText.getText().toString(), Toast.LENGTH_LONG).show();
                }
                alertDialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
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
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "删除缓存成功", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "删除缓存错误:" + e, Toast.LENGTH_LONG).show();
                        }
                    });
                }

    }

    //设置背景
 Switch zdyua;
    private void file() {
        File zdyuafile=new File(sd+"zdyua.txt");
        if (zdyuafile.exists()){
            if (getfile(zdyuafile).contains("1")){
                zdyua.setTint(context.getResources().getColor(R.color.colorAccent));
            }
        }
        File file=new File(sd+"qun.txt");
        if (file.exists()){
            if (getfile(file).contains("1")){
                    qun.setTint(context.getResources().getColor(R.color.colorAccent));
        }}
    }


    private  String stringTextCut(String s) {
        String afterString = s;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (String.valueOf(chars[i]).getBytes().length < 2) {
                continue;
            } else {
                afterString = afterString.replaceAll(String.valueOf(chars[i]), "");
            }
        }
        return afterString;
    }

    public void alert_edit() {
        final EditText et = new EditText(context);
        new AlertDialog.Builder(context)
                .setTitle("请输入要提取链接的邮箱内容")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            //按下确定键后的事件
                            StringBuffer text=new StringBuffer();
                            String urlm=et.getText().toString();
                            String mui = stringTextCut(urlm);
                            String mum=mui.replaceAll("\\s*", "").replace("\n","");
                            String mu[]=mum.split("]");
                            for (int e=0;e<mu.length;e++) {
                                String rfr=mu[e];
                                String s =rfr.substring(rfr.indexOf("http"),rfr.length());
                                Log.d("测试",s);
                                text.append(s);
                                text.append("\n");
                            }
                            EditText url=view.findViewById(R.id.url);
                            url.setText(text.substring(0,text.length()-1));
                            Toast.makeText(context,"解析成功",Toast.LENGTH_LONG).show();
                        }catch (Exception e)
                        {
                            Toast.makeText(context,"解析失败原因:"+e,Toast.LENGTH_LONG).show();

                            Log.d("测试",""+e);
                        }
                        //  Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消", null).show();

    }

    private static final String APP_CACAHE_DIRNAME = "/webcache";
    /**
     * 清除WebView缓存  在onDestroy调用这个方法就可以了
     */
    public void clearWebViewCache(){
        CookieSyncManager.createInstance(context.getApplicationContext());
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
        //清理Webview缓存数据库
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WebView 缓存文件
        File appCacheDir = new File(context.getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
        //Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());
        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath()+"/webviewCache");
        // Log.e("path===", "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());
        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    private void clearCache(){
        File file = context.getCacheDir().getAbsoluteFile();//删除缓存
        deleteFile(file);
    }

    public  void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    DeleteFile(f);
                }
                file.delete();
            }
        }
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

}
