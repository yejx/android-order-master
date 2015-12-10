package com.yjx.order.activity.login;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yjx.order.activity.BaseActivity;
import com.yjx.order.activity.HomeActivity;
import com.yjx.order.R;
import com.yjx.order.bean.User;
import com.yjx.order.common.AppDoubleBackExit;
import com.yjx.order.common.Parameters;
import com.yjx.order.common.SoftKeyBoardSatusView;
import com.yjx.order.http.HttpResponseListener;
import com.yjx.order.http.ResponseHandler;
import com.yjx.order.http.request.CommonRequestFactory;
import com.order.ui.component.CircleImageView;
import com.order.ui.component.ClearEditText;
import com.order.ui.component.PhoneNumberInputWatcher;
import com.yjx.order.util.PhoneNumberUtil;
import com.yjx.order.util.RuleUtil;
import com.yjx.order.util.StringUtil;
import com.yjx.order.util.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements SoftKeyBoardSatusView.SoftkeyBoardListener {

    public static final String KEY_LOGIN_NAME = "loginName";
    public static final String KEY_ACTION_FLAG = "key_action_flag";
    public static final String ACTION_LOGINOUT = "loginOut";

    private final String securityName = "loginPass";
    /**
     * 同意拉卡拉钱包服务协议
     */
    private final int REQUEST_CODE_PROTOCOL = 1;
    /**
     * 注册拉卡拉账号
     */
    private final int REQUEST_CODE_REGISTER = 2;
    /**
     * 注册完成设置手势密码
     */
    private final int REQUEST_CODE_GESTURE_PWD = 3;
    /**
     * 忘记密码标识
     */
    private final int REQUEST_CODE_FORGET_PWD = 4;
    /**
     * 设备授信请求码
     */
    private final int REQUEST_CODE_DEVICE_AUTH = 5;
    private final String receiver_action = "com.sa.isecurity.plugin.sakbd";

    private RelativeLayout rootLayout;
    /**
     * 用户头像
     */
    private CircleImageView userPhoto;
    /**
     * 用户手机号吗登录EditText
     */
    private EditText mUserEdit;
    /**
     * 密码输入框
     */
    private ClearEditText mPassEdit;
    /**
     * 忘记密码
     */
    private TextView mForgetPassTextView;
    /**
     * 登录，注册button
     */
    private Button mLoginBtn, mRegisterBtn;
    /**
     * 用户名
     */
    private String mUserName;
    /**
     * 密码
     */
    private String mPassword;
    /**
     * 登录返回报文
     */
    private JSONObject loginResponse;
    private User user;
    /**
     * 页面交互元素rect
     */
    private Rect userRect,passRect,forgetPassRect,loginRect,registerRect;
    private ScrollView scrollView;
    private SoftKeyBoardSatusView satusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        initRectAndReceiver();
//        throw new RuntimeException("This is a crash");
    }

    private void initRectAndReceiver() {
        userRect = new Rect();
        passRect = new Rect();
        forgetPassRect = new Rect();
        loginRect = new Rect();
        registerRect = new Rect();
    }

    /**
     * 初始化View
     */
    private void initView() {
        hideNavigationBar();
        scrollView= (ScrollView) findViewById(R.id.login_scroller);
        userPhoto = (CircleImageView) findViewById(R.id.user_photo);
        mUserEdit = (EditText) findViewById(R.id.plat_activity_login_phone_edit);
        mUserEdit.setInputType(InputType.TYPE_CLASS_PHONE);
        mUserEdit.setFilters(RuleUtil.getLengthFilter(13));

        mPassEdit = (ClearEditText) findViewById(R.id.plat_activity_login_password_edit);
        mPassEdit.setFilters(RuleUtil.getLengthFilter(20));
//        SecurityKeyboardUtil.initWithPassword(mPassEdit, securityName);

        mLoginBtn = (Button) findViewById(R.id.plat_login_button);
        mRegisterBtn = (Button) findViewById(R.id.plat_regsiter_button);
        mForgetPassTextView = (TextView) findViewById(R.id.plat_activity_login_forget_password_textview);
        mForgetPassTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mForgetPassTextView.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        //提交按钮使用最小限度
        HashMap<EditText, Integer> map = new HashMap<EditText, Integer>();
        map.put(mUserEdit, 13);
        map.put(mPassEdit, 6);
        mUserEdit.addTextChangedListener(new PhoneNumberInputWatcher());

        //显示的loginName
        String loginName = getIntent().getStringExtra(KEY_LOGIN_NAME);
        if (StringUtil.isNotEmpty(loginName)) {
            mUserEdit.setText(loginName);
            mPassEdit.requestFocus();
        }

        rootLayout = (RelativeLayout)findViewById(R.id.login_root_layout);
        findViewById(R.id.login_root_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                double heightDiff = (rootLayout.getRootView().getHeight() - rootLayout.getHeight())/(double)(rootLayout.getRootView().getHeight());
                if (heightDiff > 0.13) { // if more than 0.13pixels, its probably a keyboard...
                    mForgetPassTextView.setVisibility(View.GONE);
                    mRegisterBtn.setVisibility(View.GONE);
                }
            }
        });

        LinearLayout layout=(LinearLayout)findViewById(R.id.login_layout);
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        layout.requestFocus();

        satusView=(SoftKeyBoardSatusView)findViewById(R.id.login_soft_status_view);
        satusView.setSoftKeyBoardListener(this);

        //设置用户头像
//        String mUsername = PreferencesUtil.getInstance().getString(PreferencesUtil.KEY_LOGIN_NAME);
//        Bitmap bitmap = UserPhotoManager.getInstance().getUserPhoto(mUsername);
//
//        if(bitmap != null){
//            userPhoto.setImageBitmap(bitmap);
//        }else{
//            userPhoto.setImageResource(R.drawable.default_head_set);
//        }
    }


    /**
     * 设置事件监听
     */
    private void setListener() {
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
        mForgetPassTextView.setOnClickListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //保存登录，注册，忘记密码，输入框的位置
        setRect();
        if (mUserEdit != null) {
            mPassEdit.requestFocus();
        }
    }

    private void setRect() {
        mUserEdit.getGlobalVisibleRect(userRect);
        mPassEdit.getGlobalVisibleRect(passRect);
        mForgetPassTextView.getGlobalVisibleRect(forgetPassRect);
        mLoginBtn.getGlobalVisibleRect(loginRect);
        mRegisterBtn.getGlobalVisibleRect(registerRect);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AppDoubleBackExit.onBack(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.plat_login_button && !isLoginInputVaild()) {
            CommonRequestFactory.login(mUserName, mPassword, new HttpResponseListener() {
                @Override
                public void onStart() {
                    showProgressDialog(getString(R.string.request_progress_info));
                }
                @Override
                public void onFinished(ResponseHandler response) {
                    hideProgressDialog();
                    if(response.retCode.equals(Parameters.successRetCode)){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(response.retCode.equals(Parameters.userOrPWError)){
                        ToastUtil.toast(mContext, R.string.user_or_pwd_error);
                    }else{
                        ToastUtil.toast(mContext, R.string.login_fail);
                    }
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgressDialog();
                    ToastUtil.toast(mContext, R.string.network_error);
                }
            });
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
//            UserLoginManager.getInstance().userLogin(this,true,mUserName,CommonEncrypt.loginEncrypt(mPassword));
        } else if (id == R.id.plat_regsiter_button) {
            register();
        } else if (id == R.id.plat_activity_login_forget_password_textview) {
            forgetPassword();
        }
    }

    /**
     * 效验手机号合法性
     */
    private boolean isMobileVaild() {
        mUserName = StringUtil.formatString(mUserEdit.getText().toString().trim());

        if (StringUtil.isEmpty(mUserName)) {
            return false;
        }

        if (!PhoneNumberUtil.checkPhoneNumber(mUserName)) {
            return false;
        }

        return true;
    }

    /**
     * 效验数据合法性
     */
    private boolean isLoginInputVaild() {
        mUserName = StringUtil.formatString(mUserEdit.getText().toString().trim());
        mPassword = mPassEdit.getText().toString().trim();


        if (StringUtil.isEmpty(mUserName)) {
            ToastUtil.toast(this, R.string.please_input_your_phonenumber);
            return false;
        }

        if (!PhoneNumberUtil.checkPhoneNumber(mUserName)) {
            ToastUtil.toast(this, R.string.please_input_your_phonenumber_error);
            return false;
        }

        if (StringUtil.isEmpty(mPassword)) {
            ToastUtil.toast(this, R.string.please_input_your_password);
            return false;
        }

        int len = mPassword.length();
        if (len < 6 || len > 20) {
            ToastUtil.toast(this, R.string.please_input_your_password_error);
            return false;
        }

        return true;
    }


    /**
     * 注册
     */
    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /**
     * 忘记密码
     */
    private void forgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /**
     * Activity回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UserLoginManager.getInstance().onResult(LoginActivity.this,requestCode,resultCode,data);
        switch (requestCode) {
            case REQUEST_CODE_FORGET_PWD:
                //忘记密码申请成功
                if (resultCode == RESULT_OK) {
                    loadUserName(data);
                }
                break;
        }
    }

    /**
     * 自动加载用户名
     */
    private void loadUserName(Intent data) {
        if (data == null) {
            return;
        }

        //获取注册的用户名
        String regiterUserName = data.getStringExtra(KEY_LOGIN_NAME);
        if (StringUtil.isNotEmpty(regiterUserName)) {
            mUserEdit.setText(regiterUserName);
        }
        mPassEdit.setText("");
    }

    @Override
    public void keyBoardStatus(int w, int h, int oldw, int oldh) {

    }

    @Override
    public void keyBoardVisable(int move) {
        mLoginBtn.getScrollY();
        Message message=new Message();
        message.what=WHAT_SCROLL;
        message.obj=move;
        handler.sendMessage(message);
    }

    @Override
    public void keyBoardInvisable(int move) {
        handler.sendEmptyMessageDelayed(WHAT_BTN_VISABEL, 200);
    }

    //滑动事件
    final int WHAT_SCROLL= 0;
    //view显示
    final int WHAT_BTN_VISABEL= 1;

    Handler handler=new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case WHAT_SCROLL://滚动view
                    int move=(Integer) msg.obj;
                    mForgetPassTextView.setVisibility(View.GONE);
                    mRegisterBtn.setVisibility(View.GONE);
                    scrollView.smoothScrollBy(0, move);
                    break;
                case WHAT_BTN_VISABEL://显示view
                    mForgetPassTextView.setVisibility(View.VISIBLE);
                    mRegisterBtn.setVisibility(View.VISIBLE);
                    break;
            }
        }

    };

}
