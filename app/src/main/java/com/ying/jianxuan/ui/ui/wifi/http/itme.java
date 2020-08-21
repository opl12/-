package com.ying.jianxuan.ui.ui.wifi.http;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import com.ying.jianxuan.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class itme extends BaseAdapter {

    public List<com.ying.jianxuan.ui.ui.wifi.http.List> lists;
    public Activity activity;
    public itme(List<com.ying.jianxuan.ui.ui.wifi.http.List> lists, Activity activity){
        this.lists=lists;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private  com.ying.jianxuan.ui.ui.wifi.http.List list;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(activity).inflate(R.layout.wifi_item,parent,false);
        list=lists.get(position);
        TextView textView=convertView.findViewById(R.id.sj);
        textView.setText(list.getDeviceRename());
        ImageView imageView=convertView.findViewById(R.id.cahua);
        if (list.getSrc()==null){
            imageView.setBackgroundResource(R.drawable.chahua);
        }else {
            Glide.with(activity).load(list.getSrc()).placeholder(R.drawable.jiazai).error(R.drawable.jiazsbai).into(imageView);
        }

        //ip
        TextView ip=convertView.findViewById(R.id.ip);
        ip.setText("IP|"+list.getIP());
        //mac
        TextView mac=convertView.findViewById(R.id.mac);
        mac.setText("MAC|"+list.getMAC());
        //拉黑
        TextView w=convertView.findViewById(R.id.w);
        if (Integer.parseInt(list.getBlockUser())==0){
            w.setText("是否允许上网|允许");
        }else {
            w.setText("是否允许上网|禁止");
        }
        //下载速度
        TextView xz=convertView.findViewById(R.id.xz);
        xz.setText("下载数据|"+list.getDownRate()+"k");
        //上传速度
        TextView sc=convertView.findViewById(R.id.sc);
        sc.setText("上传数据|"+list.getUpRate()+"k");
        //限制上传速度
        TextView xzs=convertView.findViewById(R.id.xzs);
        xzs.setText("限制上传数据|"+list.getUpMax());
        //限制下载速度
        TextView xzx=convertView.findViewById(R.id.xzx);
        xzx.setText("限制下载数据|"+list.getDownMax());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alog(lists.get(position));
            }
        });
        return convertView;
    }

    private void alog(final com.ying.jianxuan.ui.ui.wifi.http.List list) {
        View view=activity.getLayoutInflater().inflate(R.layout.wifi_log,null,false);
        EditText DeviceRename=view.findViewById(R.id.DeviceRename);
        Button iphttp=view.findViewById(R.id.http);
        Button qxiao=view.findViewById(R.id.qxiao);
        ImageView imageView=view.findViewById(R.id.srclog);
        Glide.with(activity).load(list.getSrc()).placeholder(R.drawable.jiazai).error(R.drawable.jiazsbai).into(imageView);

        DeviceRename.setText(list.getDeviceRename());
        final EditText isBind=view.findViewById(R.id.ifType);
        isBind.setText(list.getIsBind());
        final EditText BlockUser=view.findViewById(R.id.BlockUser);
        BlockUser.setText(list.getBlockUser());
        final EditText UpMax=view.findViewById(R.id.UpMax);
        UpMax.setText(list.getUpMax());
        final EditText DownMax=view.findViewById(R.id.DownMax);
        DownMax.setText(list.getDownMax());
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog dialog = builder.show();

        qxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String d= (String) msg.obj;
                Toast.makeText(activity,d,Toast.LENGTH_LONG).show();
            }
        };

        iphttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                          //  Document document= Jsoup.connect("http://192.168.2.1/LocalMACConfig.asp?action=set&MAC="+list.getMAC()+"&IP="+list.getIP()+"&DeviceRename="+list.getDeviceRename()+"&isBind=1&ifType="+ifType.getText()+"&BlockUser="+BlockUser.getText()+"&UpMax="+UpMax.getText()+"&DownMax="+DownMax.getText()+"&_="+System.currentTimeMillis()).get();
                            URL url = new URL("http://192.168.2.1/LocalMACConfig.asp?action=set&MAC="+list.getMAC()+"&IP="+list.getIP()+"&DeviceRename="+list.getDeviceRename()+"&isBind="+isBind.getText()+"&ifType=1&BlockUser="+BlockUser.getText()+"&UpMax="+UpMax.getText()+"&DownMax="+DownMax.getText()+"&_="+System.currentTimeMillis());
                            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setConnectTimeout(3000);
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
                            Message message=new Message();
                            message.obj=text;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            Message message=new Message();
                            message.obj=""+e;
                            handler.sendMessage(message);
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });


    }


}
