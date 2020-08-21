package com.ying.jianxuan.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class Appwidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: Implement this method
        super.onReceive(context, intent);
    }

    /**
     * 每次桌面小部件更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        context.startService(new Intent(context, App.class));
    }
}