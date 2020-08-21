package com.ying.jianxuan.ui.ui.tuiweng.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class http extends Thread {

    public Handler handler;
    public List<com.ying.jianxuan.ui.ui.tuiweng.http.List> lists;
    public String url;
    public String cookie;
    public http(Handler handler, List<com.ying.jianxuan.ui.ui.tuiweng.http.List> lists,String url,String cookie){
        this.handler=handler;
        this.url=url;
        this.cookie=cookie;
        this.lists=lists;
    }
    @Override
    public void run() {
        super.run();
        if (cookie!=null) {
            try {
                String c = s(url);
                JSONObject jsonObject1 = JSON.parseObject(c);
                String d = jsonObject1.getString("data");
                JSONArray v = JSON.parseArray(d);
                for (int i = 0; v.size() > i; i++) {
                    com.ying.jianxuan.ui.ui.tuiweng.http.List list = new com.ying.jianxuan.ui.ui.tuiweng.http.List();
                    String dc = v.getString(i);
                    JSONObject jsonObject2 = JSON.parseObject(dc);
                    list.date = jsonObject2.getString("date");
                    list.id = jsonObject2.getString("id");
                    list.img_url = jsonObject2.getString("img_url");
                    list.text_authors = jsonObject2.getString("text_authors");
                    list.picture_author = jsonObject2.getString("picture_author");
                    list.title = jsonObject2.getString("title");
                    list.content = jsonObject2.getString("content");
                    lists.add(list);
                }
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            } catch (Exception e) {

                Message message = new Message();
                message.what = 1;
                message.obj = "" + e;
                handler.sendMessage(message);
            }
        }
    }

    public  String s(String url1) throws Exception {
        URL url = new URL(url1);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        httpURLConnection.setConnectTimeout(10000); // 5秒 连接主机的超时时间（单位：毫秒）
        httpURLConnection.setReadTimeout(10000); // 5秒 从主机读取数据的超时时间（单位：毫秒）
        httpURLConnection.setRequestProperty("Cookie",cookie);
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
}
