package com.ying.jianxuan;

import android.content.Context;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import vip.ruoyun.webkit.x5.WeBer;

public class Application extends android.app.Application {
    public static int m=R.style.AppTheme;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        MultiDex.install(this);
        WeBer.with()
                .multiProcessOptimize(true)//可选,接⼊TBS SDK后，解决⾸次启动卡顿问题
                .interceptor(new WeBer.Interceptor() { //在初始化之前做一些配置

                    @Override
                    public void beforeInit(final Context context) {
                        //QbSdk 设置
                        QbSdk.setDownloadWithoutWifi(true);
                    QbSdk.setTbsListener(new TbsListener() {
                            @Override
                            public void onDownloadFinish(int i) {
                                //tbs内核下载完成回调
                            }

                            @Override
                            public void onInstallFinish(int i) {
                                //内核安装完成回调，
                            }

                            @Override
                            public void onDownloadProgress(int i) {
                                //下载进度监听
                            }
                        });
                    }
                })
                .preInitCallBack(new QbSdk.PreInitCallback() {
                    @Override
                    public void onCoreInitFinished() {

                    }

                    @Override
                    public void onViewInitFinished(final boolean b) {
                        if (b){
                          //  Toast.makeText(getApplicationContext(),"腾讯X5内核安装成功",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"请安装X5内核",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .authority("provider")
                .build(this);
    }

}
