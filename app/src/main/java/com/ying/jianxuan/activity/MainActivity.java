package com.ying.jianxuan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.googd.Adapter.fragmnet_adapter;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ying.jianxuan.R;
import com.ying.jianxuan.StatusUtil;
import com.ying.jianxuan.theme.theme;
import com.ying.jianxuan.ui.left.left;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ying.jianxuan.Application.m;
import static com.ying.jianxuan.JNI.id.app;
import static com.ying.jianxuan.JNI.id.key;
import static com.ying.jianxuan.JNI.id.qq;
import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.verifyStoragePermissions;

public class MainActivity extends AppCompatActivity implements left.fragment,left.themem{

    public static Handler handler;
    public LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(m);
        super.onCreate(savedInstanceState);
       //getSupportActionBar().hide();
        //方式二：这句代码必须写在setContentView()方法的前面
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        linearLayout=findViewById(R.id.color);
        StatusUtil statusUtil=new StatusUtil();

        //权限申请
        statusUtil.verifyStoragePermissions(this);
        //标题栏
        statusUtil.initStatusView(this);
        //侧滑
        CeHua();

        try {
            //初始化数据
            file();
        }catch (Exception e){
            Toast.makeText(this,"错误原因:"+e,Toast.LENGTH_LONG).show();
        }

        //设置背景
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                new theme(MainActivity.this).setBackground(linearLayout);
            }
        };

    }

    void jc() {
        if (!getResources().getString(R.string.app_name).equals(app())) {
            finish();
        }
        if (!getResources().getString(R.string.qq).equals(qq())){
            finish();
        }
        if (!getResources().getString(R.string.key).equals(key())){
            finish();
        }
    }

    void file() {
        verifyStoragePermissions(this);
        File file=new File(sd);
        if (!file.exists()){
            file.mkdirs();
        }else {
            //设置主背景
            LinearLayout color1 = findViewById(R.id.color);
            new theme(this).setBackground(color1);
        }
       new com.ying.jianxuan.File.File();
    }

    private List<Fragment> fragmentList;
    private android.googd.Adapter.fragmnet_adapter fragmnet_adapter;
    private ViewPager viewPager;
     void CeHua() {
         viewPager=findViewById(R.id.viewpager);
        viewPager.setVisibility(View.VISIBLE);
         jc();
         fragmentList=new ArrayList<>();
        fragmentList.add(new left(viewPager,fragmentList));
        fragmnet_adapter=new fragmnet_adapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(fragmnet_adapter);
    }

    @Override
    public void getList(int i) {
         if (i==0){
             fragmnet_adapter.notifyDataSetChanged();
         }else if (i==1){
         fragmnet_adapter.notifyDataSetChanged();
         }
     }

    @Override
    public void theme(int i) {
        if (m==R.style.MD){
            m=R.style.AppTheme;
            //recreate();
        }else {
            m=R.style.MD;
         //recreate();
        }
        Intent mIntent = getIntent();
        finish();
        overridePendingTransition(0,0);
        startActivity(mIntent);
    }

}
