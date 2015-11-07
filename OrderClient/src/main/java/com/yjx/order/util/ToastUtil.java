package com.yjx.order.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast显示工具类，可以取消显示，防止多个Toast同时出现的问题
 * Created by andy_lv on 14-1-8.
 */
public class ToastUtil {

    /**
     * 默认显示时间Toast.LENGTH_SHORT
     * @param ctx      上下文
     * @param stringid 文字id
     * @return
     */
    public static void toast(Context ctx,int stringid){
        toast(ctx,stringid, Toast.LENGTH_SHORT);
    }

    /**
     * 显示的可以及时清除
     * @param ctx       上下文
     * @param stringid  文字资源id
     * @param lastTime  显示时间
     * @return
     */
    public static void  toast(Context ctx, int stringid, int lastTime) {
         toast(ctx,ctx.getString(stringid),lastTime);
    }

    /**
     * 默认显示时间用Toast.LENGTH_LONG
     * @param ctx
     * @param tips
     * @return
     */
    public static void toast(Context ctx,String tips){
        toast(ctx,tips, Toast.LENGTH_SHORT);
    }

    /**
     * 显示的可以及时清除
     * @param ctx      上下文
     * @param tips     显示文字
     * @param lastTime 显示时间
     * @return
     */
    public static void toast(Context ctx, String tips, int lastTime) {
        toast(ctx,tips,lastTime, Gravity.CENTER);
    }

    /**
     * 显示的可以及时清除
     * @param ctx      上下文
     * @param tips     显示文字
     * @param lastTime 显示时间
     * @param gravity  .屏幕显示位置
     * @return
     */
    public static void toast(Context ctx, String tips, int lastTime,int gravity) {
        if(null == ctx){
            return ;
        }
        //xyz fixbug
        //Toast对象在底层不实用静态变量管理，每次显示toast都是一个新的对象,避免不同线程造成的crash问题
        Toast toast= Toast.makeText(ctx, tips, lastTime);
        toast.setGravity(gravity,0,0);
        toast.show();
    }
}
