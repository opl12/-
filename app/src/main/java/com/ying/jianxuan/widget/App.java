package com.ying.jianxuan.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.googd.http.http;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ying.jianxuan.R;

import java.net.URL;

public class App extends Service {

    private String json;
        @Override
        public IBinder onBind(Intent p1) {
            // TODO: Implement this method
            return null;
        }

        BroadcastReceiver batteryReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context p1, Intent p2) {
                AppWidgetManager awm = AppWidgetManager.getInstance(p1);
                ComponentName cn = new ComponentName(p1, Appwidget.class);
                RemoteViews rv = new RemoteViews(p1.getPackageName(), R.layout.widget);
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        http http=new http();
                        try {
                            http.http(new URL("http://api.youngam.cn/api/one.php"));
                            json=http.get();
                        }catch (Exception e){
                        }
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //  String json = new post_get().post_get("GET", "", "http://api.youngam.cn/api/one.php", "");
                if (json!=null) {
                  JSONObject jsonObject = JSON.parseObject(json);
                  if (Integer.parseInt(jsonObject.getString("code")) == 200) {
                      JSONArray jsonArray = JSON.parseArray(jsonObject.getString("data"));
                      JSONObject json1 = (JSONObject) jsonArray.get(0);
                      String src = json1.getString("src");
                      String text = json1.getString("text");
                      String day = json1.getString("day").replace(" ", "");
                      Bitmap bitmap = new post_get().getBitmap(src);
                      if (bitmap != null) {
                          rv.setImageViewBitmap(R.id.i, bitmap);
                      } else {
                          rv.setImageViewResource(R.id.i, R.drawable.jiazsbai);
                      }
                      rv.setTextViewText(R.id.time, day);
                      rv.setTextViewText(R.id.tilte, text);
                  }
              }
                awm.updateAppWidget(cn, rv);
            }
        };

   /* @Override
    public void onStart(Intent intent, int startId) {
        // TODO: Implement this method
        super.onStart(intent, startId);
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }*/

   @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return super.onStartCommand(intent, flags, startId);
    }

}
