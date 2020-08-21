package com.ying.jianxuan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.googd.View.X5WebView;
import android.googd.http.http;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ying.jianxuan.JNI.id;
import com.ying.jianxuan.activity.Logo;
import com.ying.jianxuan.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m);
        //获取收集UA接口
        id id=new id();
        //沉浮状态栏
        StatusUtil statusUtil=new StatusUtil();
        statusUtil.initStatusView(this);
        //控件
        X5WebView x5WebView=findViewById(R.id.x5);
        LinearLayout view=findViewById(R.id.view);
        //ua收集
        x5WebView.loadUrl(id.ua());
        //申请权限
        initPermissions();
        //AlphaAnimation 动画构造器参数：起始alpha值的动画 结束alpha值的动画
        AlphaAnimation aa = new AlphaAnimation(0.1f,1.0f);
        //动画持续时间
        aa.setDuration(5000);
        //使用View的startAnimation开始执行动画
        view.startAnimation(aa);
        //给这个动画绑定动画监听器,监听动画结束或重复的动画事件
        aa.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation arg0) {
                startActivity(new Intent(Main.this, MainActivity.class));
                finish();
            }
            public void onAnimationRepeat(Animation animation) {

            }
            public void onAnimationStart(Animation animation) {

            }
        });
    }

    //权限数组（申请定位）
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};
    //返回code
    private static final int OPEN_SET_REQUEST_CODE = 100;
    //调用此方法判断是否拥有权限
    private void initPermissions() {
        if (lacksPermission()) {//判断是否拥有权限
            //请求权限，第二参数权限String数据，第三个参数是请求码便于在onRequestPermissionsResult 方法中根据code进行判断
            ActivityCompat.requestPermissions(this, permissions, OPEN_SET_REQUEST_CODE);
        } else {
            //拥有权限执行操作
        }
    }
    //如果返回true表示缺少权限
    public boolean lacksPermission() {
        for (String permission : permissions) {
            //判断是否缺少权限，true=缺少权限
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){//响应Code
            case OPEN_SET_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"未拥有相应权限"+grantResults[i],Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    //拥有权限执行操作
                } else {
                    Toast.makeText(this,"未拥有相应权限",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}
