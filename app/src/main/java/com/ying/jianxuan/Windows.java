package com.ying.jianxuan;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Windows implements View.OnTouchListener {
   public WindowManager mWindowManager;
   private  int canTouchFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
           | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

    private  int notTouchFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

    public Context context;
    public View view;
    public Windows(Context context){
        this.context=context;
        mWindowManager=getmWindowManagerm();
//        setTouchable(true);
        view=view();
        getView();
    }

    public void setTxt(String text){
        EditText tv_showinpop=view.findViewById(R.id.tv_showinpop);
        tv_showinpop.setText(text);
        tv_showinpop.setOnTouchListener(this);
        ScrollView scrollView=view.findViewById(R.id.sc);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public void getView(){
        mWindowManager.addView(view, params);
    }


    View view(){
        final View view= LayoutInflater.from(context).inflate(R.layout.xfc,null);
        ImageView imageButton=view.findViewById(R.id.guang);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });

      //  rl_drag_showinpp.setOnTouchListener(this);
        LinearLayout rl_drag_showinpop =view.findViewById(R.id.rl_drag_showinpop);
        rl_drag_showinpop.setOnTouchListener(this);

        return view;
    }

    /**
     * 设置是否可响应点击事件
     *
     * @param isTouchable
     */
    public void setTouchable(boolean isTouchable) {
        if (isTouchable) {
            params.flags = canTouchFlags;
        } else {
            params.flags = notTouchFlags;
        }
        mWindowManager.updateViewLayout(view, params);
    }

    public  WindowManager.LayoutParams params;
    WindowManager getmWindowManagerm(){
        WindowManager mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        params = new WindowManager.LayoutParams();
        // 类型，系统提示以及它总是出现在应用程序窗口之上。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        // 设置flag
        int flags = canTouchFlags;

        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.TOP;
        return mWindowManager;
    }

    private float lastX; //上一次位置的X.Y坐标
    private float lastY;
    private float nowX;  //当前移动位置的X.Y坐标
    private float nowY;
    private float tranX; //悬浮窗移动位置的相对值
    private float tranY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // 获取按下时的X，Y坐标
                lastX = event.getRawX();
                lastY = event.getRawY();
                ret = true;

                break;
            case MotionEvent.ACTION_MOVE:
                // 获取移动时的X，Y坐标
                nowX = event.getRawX();
                nowY = event.getRawY();
                // 计算XY坐标偏移量
                tranX = nowX - lastX;
                tranY = nowY - lastY;
                params.x += tranX;
                params.y += tranY;

                //更新悬浮窗位置
                mWindowManager.updateViewLayout(view, params);
                //记录当前坐标作为下一次计算的上一次移动的位置坐标
                lastX = nowX;
                lastY = nowY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return ret;
    }
}
