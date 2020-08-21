package android.googd.http;

import android.os.Handler;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Map;

public class http {

    public Builder with(){
        return new Builder();
    }

    private int anInt=0;
    private int max=0;

    private String POST="POST";
    private String GET="GET";

    private HttpURLConnection httpURLConnection;
    //初始化
    public void http(URL url) throws IOException {
        httpURLConnection= (HttpURLConnection) url.openConnection();
        setRequestMethod(GET);
    }

    public void setTime(int time){
        httpURLConnection.setReadTimeout(time);
        httpURLConnection.setConnectTimeout(time);
    }

    //数据流转换
    public String get() throws IOException {
        InputStream inputStream=httpURLConnection.getInputStream();
        max+=httpURLConnection.getContentLength();
        byte[] by = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        int len=-1;
        while ((len=inputStream.read(by))!=-1){
            anInt+=len;
            byteArrayOutputStream.write(by,0,len);
           if (progressBar!=null){
               // 创建一个数值格式化对象
               NumberFormat numberFormat = NumberFormat.getInstance();
               // 设置精确到小数点后2位
               numberFormat.setMaximumFractionDigits(0);
               Log.d("测试",""+anInt+"|"+max);
               String result= numberFormat.format((float) anInt / (float) max * 100);
               //返回进度
               progressBar.MAX(max,anInt,result);
           }
        }
        byteArrayOutputStream.close();
        String text = byteArrayOutputStream.toString("utf-8");
        return text;
    }

    public void setRequestProperty(Map<String,String> map){
        for (Map.Entry<String,String>entry:map.entrySet()){
            String mapkey=entry.getKey();
            String Value=entry.getValue();
            httpURLConnection.setRequestProperty(mapkey,Value);
        }
    }

    public void setReadTimeout(int Time){
        httpURLConnection.setReadTimeout(Time);
    }

    public void setConnectTimeout(int Time){
        httpURLConnection.setConnectTimeout(Time);
    }

    public void setRequestMethod(String Method) throws IOException {
        httpURLConnection.setRequestMethod(Method);
        httpURLConnection.setDoInput(true);
        if (Method.contains(POST)){
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
// http正文内，因此需要设为true, 默认情况下是false;
            httpURLConnection.setDoOutput(true);
// Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
        }
        httpURLConnection.connect();
    }

    //进度条
    public Interface progressBar;
    public void setProgressBar(Interface progressBar) {
        this.progressBar = progressBar;
    }

    public static class Builder{
        private Map<String,String> map;

        private int anInt=0;
        private int max=0;

        private String POST="POST";
        private String GET="GET";
        private String Method="GET";

        private int Time;
        private int Time1;
        private int Time2;

        //进度条
        public Interface progressBar;
        public Builder setProgressBar(Interface progressBar) {
            this.progressBar = progressBar;
            return this;
        }

        private HttpURLConnection httpURLConnection;
        public Builder setURL(String url) throws Exception {
            URL url1=new URL(url);
            httpURLConnection= (HttpURLConnection) url1.openConnection();

            if (Time!=0){
                httpURLConnection.setReadTimeout(Time);
                httpURLConnection.setConnectTimeout(Time);
            }
            if (Time1!=0){
                httpURLConnection.setReadTimeout(Time1);
            }
            if (Time2!=0){
                httpURLConnection.setConnectTimeout(Time2);
            }

            httpURLConnection.setRequestMethod(Method);
            httpURLConnection.setDoInput(true);
            if (Method.contains(POST)){
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
            }

            httpURLConnection.setRequestProperty("Connection","keep-alive");
            httpURLConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpURLConnection.setRequestProperty("Upgrade-Insecure-Requests","1");

            if (map!=null){
                setRequestProperty1(map);
            }

            httpURLConnection.connect();

            return this;
        }

        public Builder setRequestProperty(Map<String,String> map){
            this.map=map;
            return this;
        }

        private void setRequestProperty1(Map<String,String> map){
            for (Map.Entry<String,String>entry:map.entrySet()){
                String mapkey=entry.getKey();
                String Value=entry.getValue();
                httpURLConnection.setRequestProperty(mapkey,Value);
            }
        }

        public Builder setRequestMethod(String Method) {
            this.Method=Method;
            return this;
        }

        public Builder setTime(int Time){
            this.Time=Time;
            return this;
        }

        public Builder setReadTimeout(int Time){
           this.Time1=Time;
           return this;
        }

        public Builder setConnectTimeout(int Time){
            this.Time2=Time;
            return this;
        }

        //数据流转换
        public String get() throws IOException {
            InputStream inputStream=httpURLConnection.getInputStream();
            max+=httpURLConnection.getContentLength();
            byte[] by = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            int len=-1;
            while ((len=inputStream.read(by))!=-1){
                anInt+=len;
                byteArrayOutputStream.write(by,0,len);
                if (progressBar!=null){
                    // 创建一个数值格式化对象
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    // 设置精确到小数点后2位
                    numberFormat.setMaximumFractionDigits(0);
                    Log.d("测试",""+anInt+"|"+max);
                    String md = numberFormat.format((float) anInt / (float) max * 100);
                    //返回进度
                    progressBar.MAX(max,anInt,md);
                }
            }

            byteArrayOutputStream.close();
            String text = byteArrayOutputStream.toString("utf-8");
            return text;
        }

    }

}

