package android.googd.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.googd.R;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Switch extends LinearLayout  {

    public Drawable src;
    public ImageView imageView;
    public TextView textView;
    public Switch(Context context){
        super(context);
    }

    public Switch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.Switch);
        src=typedArray.getDrawable(R.styleable.Switch_android_src);
        String text=typedArray.getString(R.styleable.Switch_android_text);

        int width=typedArray.getLayoutDimension(R.styleable.Switch_image_weight,-2);
        int height=typedArray.getLayoutDimension(R.styleable.Switch_image_height,-2);

       // int color=typedArray.getColor(R.styleable.Switch_android_tint,Color.BLACK);

        int size=typedArray.getLayoutDimension(R.styleable.Switch_android_textSize,10);

        setOrientation(VERTICAL);

        imageView=new ImageView(context);

        imageView.setLayoutParams(new LayoutParams(width,height));
        //imageView.setColorFilter(color);
        imageView.setImageDrawable(src);
        addView(imageView);

        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,10,0,0);
        textView=new TextView(context);
        textView.setText(text);

        //textView.setTextColor(color);
        textView.setTextSize(size);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        addView(textView);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdhua();
                if (onClickListener!=null) {
                    onClickListener.OnClick(v);
                }
            }
        });
    }

    public void setText(String y){
        textView.setText(y);
    }
    public CharSequence  getText(){
        return textView.getText();
    }

    public OnClickListener onClickListener;
    public void setOnClickListene(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }


   public interface OnClickListener{
        void OnClick(View v);
    }

    //setColorFilter("#ffffff");

    /**
     *
     * @param color16 16进制颜色
     */
    public void setColorFilter(String color16) {
        int color = Color.parseColor(color16);
        textView.setTextColor(color);
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     *
     * @param color 颜色
     */
    public void setTint(int color) {
        textView.setTextColor(color);
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * 鬼畜动画
     */
    public void setdhua(){
        textView.setVisibility(GONE);
        ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(imageView,"translationY",35F,0);//设置Y轴移动200像素
        objectAnimator.setDuration(1000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textView.setVisibility(VISIBLE);
            }
        });
        objectAnimator.start();
    }

    /**
     *
     * @param activity 初始ui
     * @param c 跳转ui
     */
    public void setIntent(Activity activity,Class c){
        Intent intent=new Intent(activity,c);
        activity.startActivity(intent);
    }

}

