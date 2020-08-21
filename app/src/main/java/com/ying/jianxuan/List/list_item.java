package com.ying.jianxuan.List;

import java.util.ArrayList;
import java.util.List;

public class list_item {

    private List<item> lists=new ArrayList<>();
    public List<item> getList(){
        return lists;
    }

    public void setList(int R,String title,int Visibility,String title1){
        item item=new item();
        item.R=R;
        item.title1=title1;
        item.Visibility=Visibility;
        item.title=title;
        lists.add(item);
    }

}
