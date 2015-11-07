package com.yjx.order.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yjx.order.R;
import com.yjx.order.common.ApplicationEx;
import com.order.ui.component.NavigationBar;
import com.order.ui.dialog.ProgressDialog;

public class BaseActivity extends ActionBarActivity implements NavigationBar.OnNavBarClickListener ,View.OnClickListener{
    /** 页面的 action 参数 Key*/
    public static final String INTENT_PARAM_KEY_ACTION = "acAction";
    /** 标面参数 key */
    public static final String INTENT_PARAM_KEY_TITLE= "acTitle";
    /**业务requestCode */
    public static final String INTENT_PARAM_KEY_REQUEST_CODE = "acRequestCode";
    /** URL */
    public static final String INTENT_PARAM_KEY_URL = "acURL";
    /** Activity Key */
    public static final String INTENT_PARAM_KEY_ACTIVITYKEY = "acActivityKey";
    /** hideBack Key */
    public static final String INTENT_PARAM_KEY_HIDEBACKKEY = "acHideBack";

    //导航栏
    protected NavigationBar navigationBar;
    //view容器，所有子类的根布局都会添加到此容器中
    private FrameLayout baseContainer;

    private FragmentManager fragmentManager;
    protected FragmentActivity mContext;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        fragmentManager = mContext.getSupportFragmentManager();
    }

    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        //初始化导航栏
        navigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
        navigationBar.setOnNavBarClickListener(this);
        if (getIntent() != null) {
            String title = getIntent().getStringExtra(INTENT_PARAM_KEY_TITLE);
            if (title != null) {
                navigationBar.setTitle(title);
            }

            Boolean hideBack = getIntent().getBooleanExtra(INTENT_PARAM_KEY_HIDEBACKKEY, false);
            if (hideBack){
                navigationBar.setBackBtnVisibility(View.INVISIBLE);
            }
        }
        //初始化根布局容器
        baseContainer = (FrameLayout) findViewById(R.id.base_container);
        if (layoutResID != 0) {
            ViewGroup.inflate(this, layoutResID, baseContainer);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消这个队列里的所有请求
        ApplicationEx.getInstance().getmRequestQueue().cancelAll(this);
    }

    /**
     * 隐藏导航栏，splash页面，主页面会用到
     */
    protected void hideNavigationBar() {
        navigationBar.setVisibility(View.GONE);
        navigationBar.setOnClickListener(this);
    }

    /**
     * 显示导航栏
     */
    protected void showNavigationBar() {
        navigationBar.setVisibility(View.VISIBLE);
    }

    /**
     * 导航栏点击事件回调
     *
     * @param navBarItem
     */
    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        if (navBarItem == NavigationBar.NavigationBarItem.back) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 显示加载对话框
     */
    public void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog();
        progressDialog.setProgressMessage(msg);
        progressDialog.show(fragmentManager);
    }

    /**
     * 隐藏加载对话框
     */
    public void hideProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

}
