package com.order.ui.commom;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class Dimension {
    /**
     * 将px值转换为 dip或dp值，保证尺寸大小不变
     * @param pxValue 像素值
     * @param context  Context 对象
     * @return dp值
     */
    public static int px2dip(float pxValue, Context context) {
        float scale = getDensity(context);
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * @param dipValue  dip数值
     * @param context  Context 对象
     * @return 像素值
     */
    public static int dip2px(float dipValue, Context context) {
        float scale = getDensity(context);
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * @param dipValue  dip数值
     * @param context  Context 对象
     * @return 像素值
     */
    public static float dip2pxF(float dipValue, Context context) {
        float scale = getDensity(context);
        return dipValue * scale + 0.5f;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue  像素值
     * @param context  Context 对象
     * @return 返回sp数值
     */
    public static int px2sp(float pxValue, Context context) {
        float scale = getDensity(context);

        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue sp数值
     * @param context  Context 对象
     * @return 返回像素值
     */
    public static int sp2px(float spValue, Context context) {
        float scale = getDensity(context);
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 取得手机屏幕的密度
     * @param context 上下文
     * @return 手机屏幕的密度
     */
    public static float getDensity(Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return scale;
    }

    /**
     * 获取手机分辨率，屏幕密度等屏幕属性
     * @param context
     * @return
     */
    public static DisplayMetrics getPhoneResolution(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        return  dm;
    }


    /**
     * 获取手机状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
