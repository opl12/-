package com.ying.jianxuan.ui.ui.zfa.list;

public class list {

   private String tile;
   private String tile1;
   private String time;
   private String imageurl;
   private String url;

   public String getUrl(){
       return url;
   }

   public String getTile(){
       return tile;
   }
   public String getTime(){
       return time;
   }
   public String getTile1(){
       return tile1;
   }
   public String getImageurl(){
       return imageurl;
   }
   public void setImageurl(String url){
       this.imageurl=url;
   }
   public void setTime(String time){
       this.time=time;
   }
   public void setTile1(String tile1){
       this.tile1=tile1;
   }
   public void setTile(String tile){
       this.tile=tile;
   }
   public void setUrl(String url){
       this.url=url;
   }

}
