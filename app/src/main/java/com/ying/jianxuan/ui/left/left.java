package com.ying.jianxuan.ui.left;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.googd.View.Switch;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ying.jianxuan.CopyLinkTextHelper;
import com.ying.jianxuan.List.item;
import com.ying.jianxuan.List.list_item;
import com.ying.jianxuan.R;
import com.ying.jianxuan.activity.MainActivity;
import com.ying.jianxuan.activity.sz_activity;
import com.ying.jianxuan.ui.Right.Ydu;
import com.ying.jianxuan.ui.Right.main;
import com.ying.jianxuan.ui.Right.right;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tencent.smtt.sdk.QbSdk.checkApkExist;
import static com.ying.jianxuan.JNI.id.qun;
import static com.ying.jianxuan.StatusUtil.getfile;
import static com.ying.jianxuan.StatusUtil.sd;


//侧滑
public class left extends androidx.fragment.app.Fragment implements Switch.OnClickListener,AdapterView.OnItemClickListener {


    //更新右边界面接口
    public fragment Fragment;
    public ViewPager viewPager;
    public List<Fragment> fragmentList;
    public left(ViewPager viewPager, List<Fragment> fragmentList){
        this.fragmentList=fragmentList;
        //Fragment.getList();
        this.viewPager=viewPager;
    }

    void Activity() {
        Fragment=(fragment) getActivity();
        File file2=new File(sd+"fragment.txt");
        if (file2.exists()) {
            int position = Integer.parseInt(getfile(file2));
            if (position==0){
                //线报
                RighT(new right(viewPager));
            }else if (position==1){
                //浏览器
                RighT(new Ydu(viewPager));
            }else if (position==2){
                //v流量
                righT(2,"V流量","https://h5.16mnc.cn/");
            }else if (position==3){
                //四海big
                righT(3,"四海big","http://ja.bigliang.cn/wxTask/login/main.action");
            }else if (position==4){
                //红鸭
                righT(4,"红鸭","https://shimikj.com");
            }else if (position==5){
                //nb流量
                righT(5,"NB流量","https://service.nbliuliang.com");
            }else if (position==6){
                //电影
                righT(6,"电影","http://www.vipdyy.com");
            }else if (position==7){
                //漫画
                righT(7,"漫画","https://m.manhuadui.com/");
            }else if (position==8){
                righT(8,"熊猫代理","http://www.xiongmaodaili.com");
            }else if (position==9){
                righT(9,"讯代理","http://www.xdaili.cn");
            }else {
                //首页
                RighT(new right(viewPager));
            }
        }else {
            //首页
            RighT(new right(viewPager));
        }
    }

    //列表点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position==0){
            //浏览器
            Right(new Ydu(viewPager));
        }else if (position==1){
            //线报
            Right(new right(viewPager));
        }else if (position==2){
            //v流量
            right(2,"V流量","https://h5.16mnc.cn/");
        }else if (position==3){
            //四海big
            right(3,"四海big","http://ja.bigliang.cn/wxTask/login/main.action");
        }else if (position==4){
            //红鸭
            right(4,"红鸭","https://shimikj.com");
        }else if (position==5){
            //nb流量
            right(5,"NB流量","https://service.nbliuliang.com");
        }else if (position==6){
            //电影
            right(6,"电影","http://www.vipdyy.com");
        }else if (position==7){
            //漫画
            right(7,"漫画","https://m.manhuadui.com/");
        }else if (position==8){
            right(8,"熊猫代理","http://www.xiongmaodaili.com");
        }else if (position==9){
            right(9,"讯代理","http://www.xdaili.cn");
        }else if (10==position){
            joinQQGroup(getContext().getResources().getString(R.string.key));
        }else if (11==position){
            if (checkApkExist(getContext(), "com.tencent.mobileqq")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+getContext().getResources().getString(R.string.qq)+"&version=1")));
                new CopyLinkTextHelper(getContext()).CopyText(getContext().getResources().getString(R.string.qq));
                Toast.makeText(getContext(),"复制QQ号成功,如果未成功跳转请粘贴QQ号添加",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"本机未安装QQ应用",Toast.LENGTH_SHORT).show();
            }
        }
        viewPager.setCurrentItem(1);
    }

    public interface fragment{
        void getList(int i);
    }


    //底部按钮跳转
    @Override
    public void OnClick(View v) {
        switch (v.getId()){
            case R.id.x:
                Intent intent=new Intent(getActivity(),sz_activity.class);
                getActivity().startActivity(intent);
        }
    }

    public themem themem;
    public interface themem{
        void theme(int i);
    }

    public right right;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.left,container,false);
        Switch sw=view.findViewById(R.id.x);
        sw.setOnClickListene(this);
        Switch tui=view.findViewById(R.id.tui);
        tui.setOnClickListene(new Switch.OnClickListener() {
            @Override
            public void OnClick(View v) {
                getActivity().finish();
            }
        });
        //首次加载
        Activity();
        //侧滑栏
       // Animation(view);
        //切换1号界面
        ImageButton cuo=view.findViewById(R.id.cuo);
        cuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        //状态栏高度
        LinearLayout gao=view.findViewById(R.id.gao);
        gao.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,getStatusBarHeight(getContext())));
        //列表
        Lisview(view);

        themem= (left.themem) getActivity();
        final Switch theme=view.findViewById(R.id.theme);
        theme.setOnClickListene(new Switch.OnClickListener() {
            @Override
            public void OnClick(View v) {
                themem.theme(R.style.MD);
            }
        });
        return view;
    }

    void Lisview(View view){
        final ListView listView=view.findViewById(R.id.Listview);
        list_item list_item=new list_item();

        list_item.setList(R.color.colorAccent,"切换阅读模式",View.GONE,null);
        list_item.setList(R.color.colorAccent,"切换线报模式",View.GONE,null);

        list_item.setList(R.color.colorAccent,"v流量",View.VISIBLE,"微信流量:");
        list_item.setList(R.color.colorAccent,"四海(big)流量",View.GONE,null);
        list_item.setList(R.color.colorAccent,"红鸭",View.GONE,null);
        list_item.setList(R.color.colorAccent,"NB流量",View.GONE,null);

        list_item.setList(R.color.colorAccent,"电影网站",View.VISIBLE,"娱乐");
        list_item.setList(R.color.colorAccent,"动漫网站",View.GONE,null);

        list_item.setList(R.color.colorAccent,"熊猫代理",View.VISIBLE,"购买API");
        list_item.setList(R.color.colorAccent,"讯代理",View.GONE,null);

        list_item.setList(R.color.colorAccent,"官方群",View.VISIBLE,"关于与帮助");
        list_item.setList(R.color.colorAccent,"联系作者",View.GONE,null);

        List<item> itemList=list_item.getList();
        listView.setAdapter(new left_adapter(getActivity(),itemList));
        listView.setOnItemClickListener(this);
    }


    /**
     * 首次加载
     * @param fragment 界面
     */
    void RighT(Fragment fragment){
        fragmentList.add(fragment);
        Fragment.getList(0);
        viewPager.setCurrentItem(1);
    }

    /**
     *  点击加载页面
     * @param fragment 界面
     */
    void Right(Fragment fragment){
        fragmentList.remove(1);
        fragmentList.add(fragment);
        Fragment.getList(1);
    }

    /**
     * 更新界面
     * @param i 记录
     * @param tile 标题
     * @param url 链接
     */

    void right(int i,String tile,String url){
        Map<String,String> map=new HashMap<>();
        map.put("tile",tile);
        map.put("url",url);
        fragmentList.remove(1);
        fragmentList.add(new main(viewPager,map,i));
        Fragment.getList(1);
        viewPager.setCurrentItem(1);
    }

    /**
     * 首次加载
     * @param i 记录
     * @param tile 标题
     * @param url 链接
     */

    void righT(int i,String tile,String url){
        Map<String,String> map=new HashMap<>();
        map.put("tile",tile);
        map.put("url",url);
        fragmentList.add(new main(viewPager,map,i));
        Fragment.getList(1);
        viewPager.setCurrentItem(1);
    }

    //跳转QQ群
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(qun() + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

}
