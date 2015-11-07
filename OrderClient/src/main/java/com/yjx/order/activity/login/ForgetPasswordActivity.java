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
import com.yjx.order.util.ToastUtil;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    private LabelEditText item_mobile;
    private LabelEditText item_new_password;
    private LabelEditText item_confirm_password;
    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        item_mobile = (LabelEditText) findViewById(R.id.mobile);
        item_new_password = (LabelEditText) findViewById(R.id.new_pwd);
        item_confirm_password = (LabelEditText) findViewById(R.id.confirm_pwd);
        btnOK = (Button) findViewById(R.id.ok);

        btnOK.setOnClickListener(this);

        initUI();
    }

    private void initUI(){
        navigationBar.setTitle(R.string.modify_pwd);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                updatePwd();
                break;
        }
    }

    private void updatePwd() {
        String mobile = item_mobile.getEditText().getText().toString();
        String pwd = item_new_password.getEditText().getText().toString();

        CommonRequestFactory.updatePwd(mobile, pwd, new HttpResponseListener() {
            @Override
            public void onStart() {
                showProgressDialog(getString(R.string.request_progress_info));
            }

            @Override
            public void onFinished(ResponseHandler response) {
                hideProgressDialog();
                if (response.retCode.equals(Parameters.successRetCode)) {
                    ToastUtil.toast(mContext, R.string.update_success);
                    finish();
                } else {
                    ToastUtil.toast(mContext, R.string.update_fail);
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