package com.yjx.order.util;

import com.yjx.order.DebugConfig;

import java.io.IOException;

/**
 * Created by LL on 13-12-12.
 */
public class LogUtil {
    /** TAG */
    private static final String TAG = "OrderDebug";
    /** debug开关 */
    private static final boolean DEBUG = DebugConfig.DEBUG;

    /**
     * 一个参数的打印方法
     *
     * @param msg 需要打印的信息
     */
    public static void print(String msg) {
        print(null, msg, null);
    }

    /**
     * 打印异常
     * @param throwable 异常信息
     */
    public static void print(Throwable throwable){
        print(null,null,throwable);
    }

    /**
     * 自定义TAG打印
     * @param tag  自定义tag,为null则默认为 TAG
     * @param msg  需要打印的信息
     */
    public static void print(String tag,String msg){
        print(tag,msg,null);
    }

    /**
     * 自定义TAG打印
     * @param tag 自定义tag,为null则默认为 TAG
     * @param throwable 异常信息
     */
    public static void print(String tag,Throwable throwable){
        print(tag,null,throwable);
    }


    /**
     *  String.format(pattern,args);
     * @param pattern  pattern
     * @param args     args
     */
    public static void print(String pattern,Object...args){
        print(null, String.format(pattern, args),null);
    }

    /**
     * 三个参数的打印方法
     *
     * @param tag       自定义tag,为null则默认为 TAG
     * @param msg       打印信息
     * @param throwable 异常信息
     */
    public static void print(String tag, String msg, Throwable throwable) {
        if (!DEBUG)
            return;
        //设置TAG
        String t = TAG;
        if (tag != null){
            t = tag;
        }
        //打印输出
        if (msg != null){
            android.util.Log.d(t, msg);
        }
        if (throwable != null){
            android.util.Log.e(t, throwable.toString());
        }
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void logWithDivider(String tag,String message){

        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("                 ");
        LogUtil.print(tag +"=!=",message);
        LogUtil.print("                 ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
        LogUtil.print("         |        ");
    }

    /**
     * 写日志
     * @param filePath 日志文件路径
     * @param msg      日志内容
     * @return
     */
    public synchronized static boolean write(String filePath, String msg){
        return write(filePath, msg,false);
    }

    /**
     * 写日志
     * @param filePath 日志文件路径
     * @param msg      日志内容
     * @param isAsync   是否需要异步写
     *
     * @return 写入成功 or 失败,异步写文件视为成功
     */
    public synchronized static boolean write(String filePath, String msg, boolean isAsync) {
        if (isAsync) {
            writeAsync(filePath, msg);
            return true;
        }

        try {
            return FileUtil.appendConentFile(msg, filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 异步写日志
     *
     * @param filePath 日志文件名
     * @param msg      日志路径
     */
    private static void writeAsync(final String filePath, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileUtil.appendConentFile(msg, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
