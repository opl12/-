package com.ying.jianxuan.ui.Right;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import android.googd.Adapter.fragmnet_adapter;
import android.googd.View.Switch;
import android.googd.http.http;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ying.jianxuan.CopyLinkTextHelper;
import com.ying.jianxuan.R;
import com.ying.jianxuan.ui.ui.tuiweng.tuiwengfragment;
import com.ying.jianxuan.ui.ui.wifi.wifi;
import com.ying.jianxuan.ui.ui.xbao.xbaoFragment;
import com.ying.jianxuan.ui.ui.zfa.zfa;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.ying.jianxuan.StatusUtil.getStatusBarHeight;
import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.setfile;

public class right extends Fragment implements Switch.OnClickListener,ViewPager.OnPageChangeListener{

    @Override
    public void OnClick(View v) {
        switch (v.getId()){
            case R.id.d:
                getViewPager.setCurrentItem(0);
                break;
            case R.id.x:
                getViewPager.setCurrentItem(1);
                break;
            case R.id.b:
                getViewPager.setCurrentItem(2);
                break;
/*            case R.id.wifi:
                getViewPager.setCurrentItem(3);
                break;*/
        }
    }

    void color(int i){
        List<Switch> switches=new ArrayList<>();
        switches.add((Switch)view.findViewById(R.id.d));
        switches.add((Switch)view.findViewById(R.id.x));
        switches.add((Switch)view.findViewById(R.id.b));
        //switches.add((Switch)view.findViewById(R.id.wifi));
        for (int e=0;e<switches.size();e++){
            Switch s=switches.get(e);
            if (i==e){
                s.setTint(getResources().getColor(R.color.colorAccent));
            }else {
                s.setTint(getResources().getColor(R.color.hei));
            }
        }
    }

    private ViewPager viewPager;
    public right(ViewPager viewPager){
        File file=new File(sd+"fragment.txt");
        setfile(file,"0");
        this.viewPager=viewPager;
    }

    //private Switch wifi;
    void Switch(View view){
        Switch s=view.findViewById(R.id.d);
        Switch d=view.findViewById(R.id.x);
        Switch f=view.findViewById(R.id.b);
       // wifi=view.findViewById(R.id.wifi);
       //wifi.setOnClickListene(this);
        s.setOnClickListene(this);
        d.setOnClickListene(this);
        f.setOnClickListene(this);
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private View view;
    private LinearLayout toolber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.right,container,false);
        toolber=view.findViewById(R.id.toolbar);
        toolber.setVisibility(View.VISIBLE);
        menu();

        //状态栏搜索图标颜色
        //侧滑显示
        ImageButton imageButton=view.findViewById(R.id.lbio);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        //高度
        LinearLayout gao=view.findViewById(R.id.gao);
        gao.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,getStatusBarHeight(getContext())));

        //底部操作栏
        fragment(view);

        //点击事件
        Switch(view);

        return view;

    }

    void menu() {

        final ImageButton cd=view.findViewById(R.id.cd);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 初始化菜单
                PopupMenu popupMenu = new PopupMenu(getContext(), cd);

                // 将菜单视图文件绑定到popupMenu的menu对象上
                popupMenu.getMenuInflater().inflate(R.menu.mian, popupMenu.getMenu());

                // 注册菜单项监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.qq:
                                if (checkApkExist(getContext(), "com.tencent.mobileqq")){
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+getContext().getResources().getString(R.string.qq)+"&version=1")));
                                    new CopyLinkTextHelper(getContext()).CopyText(getContext().getResources().getString(R.string.qq));
                                    Toast.makeText(getContext(),"复制QQ号成功,如果未成功跳转请粘贴QQ号添加",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getContext(),"本机未安装QQ应用",Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.qun:
                                joinQQGroup(getContext().getResources().getString(R.string.key));
                                break;
                        }
                        return false;
                    }
                });
                // 显示菜单
                popupMenu.show();
            }
        });

    }

    //跳转QQ群
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    private ViewPager getViewPager;

    void fragment(View view) {
        try {
            //操作侧滑栏
            getViewPager = view.findViewById(R.id.vie);
            getViewPager.addOnPageChangeListener(this);
            getViewPager.setOffscreenPageLimit(2);
            final List<Fragment> fragmentList = new ArrayList<>();
            fragmentList.add(new tuiwengfragment());
            fragmentList.add(new zfa());
            fragmentList.add(new xbaoFragment());
            fragmnet_adapter fragmnet_adapter=new fragmnet_adapter(getChildFragmentManager(), fragmentList);
          //  fragmentList.add(new wifi());
            getViewPager.setAdapter(fragmnet_adapter);
            getViewPager.setCurrentItem(0);
            color(0);
        }catch (Exception e){
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                color(0);
                toolber.setVisibility(View.VISIBLE);
                getViewPager.setCurrentItem(0);
                break;
            case 1:
                color(1);
                toolber.setVisibility(View.VISIBLE);
                getViewPager.setCurrentItem(1);
                break;
            case 2:
                color(2);
                toolber.setVisibility(View.VISIBLE);
                getViewPager.setCurrentItem(2);
                break;
            /*case 3:
                color(3);
                toolber.setVisibility(View.VISIBLE);
                getViewPager.setCurrentItem(3);
                break;*/
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
