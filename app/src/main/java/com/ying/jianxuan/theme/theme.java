package com.ying.jianxuan.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;

import com.ying.jianxuan.JNI.id;
import com.ying.jianxuan.JNI.JNI;
import com.ying.jianxuan.R;

import static com.ying.jianxuan.StatusUtil.getfile;
import static com.ying.jianxuan.StatusUtil.sd;

public class theme {
    private id file;
    private Resources resources;
    private JNI jni;

    public theme(Context context){
        //cpp
        this.jni=new JNI();
        this.file=new id();
        this.resources=context.getResources();
    }

    public void setBackground(LinearLayout view){
        java.io.File bj = new java.io.File(sd + "bj.txt");
        if (bj.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(getfile(bj));
            BitmapDrawable drawable = new BitmapDrawable(resources,
                    bitmap);
            view.setBackground(drawable);
        }else {
            view.setBackgroundColor(resources.getColor(R.color.tmbai));
        }
    }

}
