package com.ying.jianxuan.ui.ui.wifi.http;

public class List {
    private String src;
    public void setSrc(String url){
        this.src=url;
    }
    public String getSrc(){
        return src;
    }
    //mac
    private String MAC;
    //ip
    private String IP;

    //上传数据
    private String upRate;
    //下载数据
    private String downRate;

    ////不知道什么鬼数据

    private  String ifType;
    //主机绑定
    private String isBind;

    //限制上传数据
    private String UpMax;
    //限制下载数据
    private String DownMax;

    //1=拉黑
    private String BlockUser;

    //不认识的数据
    private String ONLINE;
    private String HOSTNAME;

    //设备信息
    private String DeviceRename;

    public void setDeviceRename(String deviceRename){
        this.DeviceRename=deviceRename;
    }
    public String getDeviceRename(){
        return DeviceRename;
    }
    public void setHOSTNAME(String HOSTNAME){
        this.HOSTNAME=HOSTNAME;
    }
    public String getHOSTNAME(){
        return HOSTNAME;
    }
    public void setONLINE(String ONLINE){
        this.ONLINE=ONLINE;
    }
    public String getONLINE(){
        return ONLINE;
    }
    public void setBlockUser(String blockUser){
        this.BlockUser=blockUser;
    }
    public String getBlockUser(){
        return BlockUser;
    }
    public void setDownMax(String downMax){
        this.DownMax=downMax;
    }
    public String getDownMax(){
        return DownMax;
    }
    public void setUpMax(String upMax){
        this.UpMax=upMax;
    }
    public String getUpMax(){
        return UpMax;
    }
    public String getIsBind(){
        return isBind;
    }
    public void setIsBind(String isBind){
        this.isBind=isBind;
    }
    public void setIfType(String ifType){
        this.ifType=ifType;
    }
    public String getIfType(){
        return ifType;
    }
    public void setDownRate(String downRate){
        this.downRate=downRate;
    }
    public String getDownRate(){
        return downRate;
    }
    public void setUpRate(String upRate){
        this.upRate=upRate;
    }
    public String getUpRate(){
        return upRate;
    }
    public void setMAC(String mac){
        this.MAC=mac;
    }
    public String getMAC(){
        return MAC;
    }
    public void setIP(String ip){
        this.IP=ip;
    }
    public String getIP(){
        return IP;
    }

}
