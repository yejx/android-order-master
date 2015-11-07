package com.yjx.order.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义全局程序
 * @author bob
 * 
 *
 */
public class ApplicationEx extends Application {
	
	private static ApplicationEx instance;
    private boolean hasMsg = false;

	private List<Activity> activities = new LinkedList<Activity>();

	public static ApplicationEx getInstance() {
        return instance;
    }
    private RequestQueue mRequestQueue;
    
    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity) {
		activities.add(activity);
	}
    
    /**
     * 移除activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
    	if (activities.contains(activity)) {
    		activities.remove(activity);
		}
	}

    public void setHasMsg(boolean hasMsg){
        this.hasMsg = hasMsg;
    }
    public boolean hasMsg(){
        return this.hasMsg;
    }
    	
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mRequestQueue = Volley.newRequestQueue(this);
    }

    /**
     * 退出应用，关闭堆栈中Activity
     */
    public void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }

        System.exit(0);
    }

    /**
     * 当系统内存过低时的事件
     */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/**
	 * 程序退出时的事件
	 */
	@Override
	public void onTerminate() {		
		super.onTerminate();
	}

    public boolean isRunningForeground(){
        String packageName=getPackageName(instance);
        String topActivityClassName=getTopActivityName(instance);
        System.out.println("packageName="+packageName+",topActivityClassName="+topActivityClassName);
        if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
            System.out.println("---> isRunningForeGround");
            return true;
        } else {
            System.out.println("---> isRunningBackGround");
            return false;
        }
    }


    private String getTopActivityName(Context context){
        String topActivityClassName=null;
        ActivityManager activityManager =
                (ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
        if(runningTaskInfos != null){
            ComponentName f=runningTaskInfos.get(0).topActivity;
            topActivityClassName=f.getClassName();
        }
        return topActivityClassName;
    }

    private String getPackageName(Context context){
        String packageName = context.getPackageName();
        return packageName;
    }


    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public void setmRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }
}
