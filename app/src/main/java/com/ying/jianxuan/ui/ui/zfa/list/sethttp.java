package com.ying.jianxuan.ui.ui.zfa.list;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class sethttp extends Thread {

    public String ur="https://www.guofenzhuan.com";
    public Handler handler;
    public List<list> lists;
    public int i;
    public sethttp(Handler handler, List<list> lists, int i){
        this.handler=handler;
        this.lists=lists;
        this.i=i;
    }

    @Override
    public void run() {
        super.run();
        try {
            Document doc = Jsoup.connect("https://www.guofenzhuan.com/app/zfwz/"+i+".html").get();

            Element list=doc.getElementsByClass("app-list boutique-cnt").get(0);

            Elements me=list.getElementsByClass("app-data");

            Elements a=list.getElementsByClass("l app-ico");

            Elements src1=a.select("img[src]");
            Elements element=a.select("a[href]");

            Elements span=list.getElementsByClass("app-describe");
            for (int i=0;a.size()>i;i++){
                list list1=new list();
                String time=me.get(i).getElementsByTag("span").get(0).text();
                list1.setTime(time);
                //时间
               // System.out.print(me.get(i).getElementsByTag("span").get(0).text()+"\n");
                //标题
                 //System.out.print(src1.get(i).attr("alt")+"\n");
                   String tile=src1.get(i).attr("alt");
                   list1.setTile(tile);

                   String tile1=span.get(i).text();
                   list1.setTile1(tile1);

                   String urll=ur+element.get(i).attr("href");
                   list1.setUrl(urll);

                   String src=ur+src1.get(i).attr("src");
                   list1.setImageurl(src);
            //底部文字
          //  System.out.print(span.get(i).text()+"\n");
            //文章地址
          //  System.out.print(element.get(i).attr("href")+"\n");
            //图片
           // System.out.print(src1.get(i).attr("src")+"\n");
                lists.add(list1);
            }

            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);

        }catch (Exception e){

            Log.d("测试",""+e);
        }

    }
}
