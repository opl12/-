package com.ying.jianxuan.ui.ui.xbao.http;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class sethttp extends Thread {

    public Handler handler;
    public List<list> lists;
    public int i;
    public sethttp(Handler handler, List<list> lists,int i){
        this.handler=handler;
        this.lists=lists;
        this.i=i;
    }

    @Override
    public void run() {
        super.run();
        try {
            String url="https://www.lantianla.cn/news/?list_12_"+i+".html";
            Document doc = Jsoup.connect(url).get();
            Element list = doc.getElementsByClass("listUl").get(0);
            Elements c=list.getElementsByClass("mark");
            Elements s=list.getElementsByClass("thumbBox");
            Elements elements = s.select("img[src]");
            if (i==1){
                lists.clear();
            }
            for (int i=0;i<c.size();i++){

                list li=new list();
                Element u=c.get(i);

                Elements w=u.getElementsByTag("a");
                String e=w.attr("href").replace("..","https://www.lantianla.cn");
                li.setUrl(e);

                Element src=elements.get(i);
                String links = src.attr("src");
                li.setImageurl(links);

                Elements n=u.getElementsByTag("h3");
                String tile=n.text();
                li.setTile(tile);

                Element k=u.getElementsByTag("p").get(1);
                String p=k.text();
                li.setTile1(p);

                Element o=u.getElementsByTag("p").get(0);
                Elements list1 = o.getElementsByClass("info listInfo");
                String r=list1.text().replace(" ","");
                li.setTime(r);
                lists.add(li);

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
