package com.ying.jianxuan.File;


import com.ying.jianxuan.JNI.id;

import static com.ying.jianxuan.StatusUtil.setfile;

public class File {
    //初始化
    private id file;

    public File(){
        this.file=new id();
        setfragment();
    }

    private void setfragment(){
        java.io.File file2=new java.io.File(file.getfragment());
        if (!file2.exists()){
            setfile(file2,"0");
        }
    }
}
