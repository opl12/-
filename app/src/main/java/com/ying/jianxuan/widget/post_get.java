package com.ying.jianxuan.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class post_get {

    private String text;

    public String post_get(final String getpost, final String cookie, final String url, final String post) {
        Thread t= new Thread() {
            public void run(){
                try {
                    URL urlm = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlm.openConnection();
                    connection.setRequestMethod(getpost);
                    if (getpost.equals("POST")) {
                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        connection.setUseCaches(false);
                    }
                    cookie(connection, cookie);
                    connection.connect();
                    if (getpost.equals("POST")) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                        writer.write(post);
                        writer.close();
                    }
                    InputStream inputStream = connection.getInputStream();
                    Scanner scanner = new Scanner(inputStream, "UTF-8");
                    text = scanner.useDelimiter("\\A").next();
                    return ;
                }catch (Exception e) {
                    System.out.print("测试:"+e);
                }}
        };

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO: Implement this method
        return text;
    }


    public void cookie(URLConnection connection, String cookie) {
        if(cookie.equals(null))
        {} else {
            String[] hj =cookie.split("‖");
            for (int i = 0; i < hj.length; i++) {
                String[] j = hj[i].split(":");
                connection.setRequestProperty(j[0], j[1]);

            }
        }
    }

    private Bitmap bm;
    public Bitmap getBitmap(final String url) {
        Thread t= new Thread() {
            public void run() {
                try {
                    URL iconUrl = new URL(url);
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    int length = http.getContentLength();

                    conn.connect();
                    // 获得图像的字符流
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();// 关闭流
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }};
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bm;
    }

}


