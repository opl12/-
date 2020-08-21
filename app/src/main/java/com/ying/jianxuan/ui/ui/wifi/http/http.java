package com.ying.jianxuan.ui.ui.wifi.http;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

public class http extends Thread {

    public Handler handler;
    public List<com.ying.jianxuan.ui.ui.wifi.http.List> lists;
    public int i;
    public http(Handler handler, List<com.ying.jianxuan.ui.ui.wifi.http.List> lists, int i){
        this.handler=handler;
        this.lists=lists;
        this.i=i;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL("http://192.168.2.1/LocalClientList.asp?action=get");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Referer","http://192.168.2.1/cgi-bin/luci/;stok=11b426598243ed420d017a3753c7ff8e/admin/device_manage");
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
            JSONObject jsonObject= JSON.parseObject(text);
            String retClientInfo=jsonObject.getString("retClientInfo");
            JSONObject jsonObject1= JSON.parseObject(retClientInfo);
            String retClientInfo1=jsonObject1.getString("Clientlist");
            JSONArray jsonArray=JSONArray.parseArray(retClientInfo1);
            for (int i=0;i<jsonArray.size();i++){
                com.ying.jianxuan.ui.ui.wifi.http.List list=new com.ying.jianxuan.ui.ui.wifi.http.List();
                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                String IP=jsonObject2.getString("IP");
                list.setIP(IP);
                String MAC=jsonObject2.getString("MAC");
                list.setMAC(MAC);
                String DeviceRename=jsonObject2.getString("DeviceRename");
                list.setDeviceRename(DeviceRename);
                String DownMax=jsonObject2.getString("DownMax");
                list.setDownMax(DownMax);
                String UpMax=jsonObject2.getString("UpMax");
                list.setUpMax(UpMax);
                String downRate=jsonObject2.getString("downRate");
                list.setDownRate(downRate);
                String ifType=jsonObject2.getString("ifType");
                list.setIfType(ifType);
                String upRate=jsonObject2.getString("upRate");
                list.setUpRate(upRate);
                String BlockUser=jsonObject2.getString("BlockUser");
                list.setBlockUser(BlockUser);
                String IsBind=jsonObject2.getString("isBind");
                list.setIsBind(IsBind);
                try {
                    Document document = Jsoup.connect("https://img.xjh.me/random_img.php?return=json").get();
                    JSONObject jsonObject3 = JSON.parseObject(document.text());
                    list.setSrc("https:" + jsonObject3.getString("img"));
                }catch (Exception e){

                }

                lists.add(list);
            }
            Message message=new Message();
            message.what=0;
            handler.sendMessage(message);
        }catch (Exception e){
            Message message=new Message();
            message.what=1;
            message.obj=""+e;
            handler.sendMessage(message);
        }

    }
}
