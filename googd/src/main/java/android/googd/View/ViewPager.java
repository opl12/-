package android.googd.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPager extends androidx.viewpager.widget.ViewPager {
    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        //Log.e("分发事件", "dispatch");
        return super.dispatchTouchEvent(ev);
    }

    private float mLastMotionX;
    private float mLastMotionY;
    static boolean isClickedTag = true;
    int interceptAction,touchAction;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        interceptAction = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        switch (interceptAction & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
            {
                //Log.e("拦截事件——down", "intercept");
                mLastMotionX = x;
                mLastMotionY = y;
                isClickedTag = true;
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                //Log.e("拦截事件——move", "intercept");
                int deltalX = (int)(mLastMotionX - x);
                int deltalY = (int)(mLastMotionY - y);
                if((deltalX*deltalX + deltalY*deltalY)>100)
                {
                    isClickedTag = false;
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                //Log.e("拦截事件——up", "intercept");
                if(isClickedTag)
                {
                    return false;
                }
                return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                //Log.e("触摸事件——down", "touch");
                return true;
            case MotionEvent.ACTION_MOVE:
                //Log.e("触摸事件——move", "touch");
                break;
            case MotionEvent.ACTION_UP:
                //Log.e("触摸事件——up", "touch");
                break;
        }
        return super.onTouchEvent(event);
    }


}
