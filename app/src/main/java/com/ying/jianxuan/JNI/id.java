package com.ying.jianxuan.JNI;

public class id {

    static {
        System.loadLibrary("id");
    }

    //文件目录
    public native String getBJ();
    public native String getfragment();

    //链接
    //熊猫
    public native String xmao();

    //UA域名
    public native String ua();

    //检测数据
    public static native String app();
    public static native String key();
    public static native String qq();
    public static native String qun();
}
