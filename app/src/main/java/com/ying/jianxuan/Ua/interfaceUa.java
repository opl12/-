package com.ying.jianxuan.Ua;
public interface interfaceUa {
    //获取网络状态
    String getwifi();
    //获取微信版本
    String getMicroMessenger();
    //微信16进制
    String get16();
    //输入ua转换微信ua
    String getua(String[] a);
    //ua尾部
    String getweibu();
}
