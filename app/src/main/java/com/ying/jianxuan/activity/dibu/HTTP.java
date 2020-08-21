package com.ying.jianxuan.activity.dibu;

import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;

public class HTTP extends Thread {
    private String url;
    private Handler handler;
    public HTTP(String url, Handler handler){
        this.url=url;
        this.handler=handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL URL=new URL(url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) URL.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
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
            Message message=new Message();
            message.what=0;
            message.obj=text;
            handler.sendMessage(message);
        } catch (Exception e) {
            Message message=new Message();
            message.what=1;
            message.obj=""+e;
            handler.sendMessage(message);
        }
    }
}
