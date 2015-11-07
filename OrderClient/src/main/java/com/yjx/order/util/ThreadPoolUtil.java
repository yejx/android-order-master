package com.yjx.order.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yjx on 2015/11/6.
 */
public class ThreadPoolUtil {
    private static ExecutorService pool;
    private static int THREAD_NUM = 20;//线程池的线程数量

    public static ExecutorService getInstance(){
        if(pool == null){
             pool = Executors.newFixedThreadPool(THREAD_NUM);//创建一个可重用的、具有固定线程数的线程池
        }

        return pool;
    }

    public void runThread(Runnable runnable){
        pool.submit(runnable);
//        pool.shutdown();
    }
}
