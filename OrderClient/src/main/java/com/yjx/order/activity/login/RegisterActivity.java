package com.yjx.order.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.yjx.order.R;
import com.yjx.order.activity.BaseActivity;
import com.yjx.order.common.Parameters;
import com.yjx.order.http.HttpResponseListener;
import com.yjx.order.http.ResponseHandler;
import com.yjx.order.http.request.CommonRequestFactory;
import com.yjx.order.ui.component.LabelEditText;
import com.yjx.order.util.StringUtil;
import com.yjx.order.util.ToastUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private LabelEditText item_mobile;
    private LabelEditText item_password;
    private LabelEditText item_confirm_password;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        item_mobile = (LabelEditText) findViewById(R.id.mobile);
        item_password = (LabelEditText) findViewById(R.id.pwd);
        item_confirm_password = (LabelEditText) findViewById(R.id.confirm_pwd);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(this);
        initUI();
    }

    private void initUI(){
        navigationBar.setTitle(R.string.register);
        item_mobile.setLabelText(StringUtil.addSpaceToStringFront(getString(R.string.mobile), 4));
        item_password.setLabelText(StringUtil.addSpaceToStringFront(getString(R.string.pwd), 4));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                register();
                break;
        }
    }

    private void register() {
        String mobile = item_mobile.getEditText().getText().toString();
        String pwd = item_password.getEditText().getText().toString();

        CommonRequestFactory.register(mobile, pwd, new HttpResponseListener() {
            @Override
            public void onStart() {
                showProgressDialog(getString(R.string.request_progress_info));
            }

            @Override
            public void onFinished(ResponseHandler response) {
                hideProgressDialog();
                if (response.retCode.equals(Parameters.successRetCode)) {
                    ToastUtil.toast(mContext, R.string.register_success);
                    finish();
                } else {
                    ToastUtil.toast(mContext, R.string.register_fail);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                ToastUtil.toast(mContext, R.string.network_error);
            }
        });
    }
}