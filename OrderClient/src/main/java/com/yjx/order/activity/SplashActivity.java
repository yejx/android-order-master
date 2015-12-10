package com.yjx.order.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yjx.order.R;
import com.yjx.order.util.PreferencesUtil;

/**
 * 应用程序启动时的第一个页面
 * 在此页面进行一些必要的网络请求，获取配置或更新的信息
 */
public class SplashActivity extends BaseActivity{
    /**
     * 表示是否是第一次打开app,如果是，则开启引导页面
     */
    private final static String FIRST_OPEN_APP = "first_open_app";

    private boolean isFirst = false;
    private Activity context;

    /**
     * 初始化方法,所有子类不写在onCreate方法里面
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        initUI();
        startDelayed(2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initUI(){
        hideNavigationBar();

        TextView tv_splash = (TextView) findViewById(R.id.tv_splash);
        tv_splash.setText("\n星星点餐\n\n快捷  便利  时尚\n\n订餐热线：18259060689");
        // 将字体文件保存在assets/fonts/目录下
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/huawenxingkai.ttf");
        // 应用字体
        tv_splash.setTypeface(typeFace);
    }

    /**
     * 延迟启动
     */
    private void startDelayed(final long time) {
        isFirst = PreferencesUtil.getInstance().getBoolean(FIRST_OPEN_APP, true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                if (isFirst) {
                    intent.setComponent(new ComponentName("com.yjx.order", "com.yjx.order.activity.GuideActivity"));
                    PreferencesUtil.getInstance().putBoolean(FIRST_OPEN_APP, false);
                    startActivity(intent);
                } else {
                    intent.setComponent(new ComponentName("com.yjx.order", "com.yjx.order.activity.login.LoginActivity"));
                    startActivity(intent);
                }

                finish();
            }
        }, time);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}