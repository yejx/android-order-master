package com.yjx.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yjx.order.R;
import com.order.ui.component.LabelItemView;
import com.order.ui.component.NavigationBar;

public class UserActivity extends BaseActivity implements View.OnClickListener{
    private ImageView user_photo;
    private EditText user_name;
    private Button btn_fast_pay;
    private LabelItemView bookup_manager;
    private LabelItemView orderfood_manager;
    private LabelItemView takeout_manager;
    private LabelItemView oto_manager;
    private LabelItemView fast_pay_record;
    private LabelItemView pay_record;
    private LabelItemView refund_manager;
    private LabelItemView my_voucher;
    private LabelItemView address_manage;
    private LabelItemView msg_manager;
    private LabelItemView public_phone;
    private NavigationBar.OnNavBarClickListener navBarClickListener = new NavigationBar.OnNavBarClickListener() {
        @Override
        public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
            switch (navBarItem) {
                case back:
                    finish();
                    break;
                case action:
                    Intent intent = new Intent(UserActivity.this, SettingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        user_photo = (ImageView) findViewById(R.id.user_photo);
        user_name = (EditText) findViewById(R.id.user_name);
        btn_fast_pay = (Button) findViewById(R.id.btn_fast_pay);
        bookup_manager = (LabelItemView) findViewById(R.id.user_bookup_manager);
        orderfood_manager = (LabelItemView) findViewById(R.id.user_orderfood_manager);
        takeout_manager = (LabelItemView) findViewById(R.id.user_takeout_manager);
        oto_manager = (LabelItemView) findViewById(R.id.user_oto_manager);
        fast_pay_record = (LabelItemView) findViewById(R.id.user_fast_pay_record);
        pay_record = (LabelItemView) findViewById(R.id.user_pay_record);
        refund_manager = (LabelItemView) findViewById(R.id.user_refund_manager);
        my_voucher = (LabelItemView) findViewById(R.id.user_my_voucher);
        address_manage = (LabelItemView) findViewById(R.id.user_address_manage);
        msg_manager = (LabelItemView) findViewById(R.id.user_msg_manager);
        public_phone = (LabelItemView) findViewById(R.id.user_public_phone);

        user_photo.setOnClickListener(this);
        user_name.setOnClickListener(this);
        btn_fast_pay.setOnClickListener(this);
        bookup_manager.setOnClickListener(this);
        orderfood_manager.setOnClickListener(this);
        takeout_manager.setOnClickListener(this);
        oto_manager.setOnClickListener(this);
        fast_pay_record.setOnClickListener(this);
        pay_record.setOnClickListener(this);
        refund_manager.setOnClickListener(this);
        my_voucher.setOnClickListener(this);
        address_manage.setOnClickListener(this);
        msg_manager.setOnClickListener(this);
        public_phone.setOnClickListener(this);

        initUI();
    }

    private void initUI(){
        navigationBar.setTitle(R.string.user_title);
        navigationBar.setActionBtnBackground(R.drawable.user_setting);
        navigationBar.setOnNavBarClickListener(navBarClickListener);

        user_name.setSelection(user_name.getText().length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_photo:

                break;
            case R.id.user_name:

                break;
            case R.id.btn_fast_pay:

                break;
            case R.id.user_bookup_manager:

                break;
            case R.id.user_orderfood_manager:

                break;
            case R.id.user_takeout_manager:

                break;
            case R.id.user_oto_manager:

                break;
            case R.id.user_fast_pay_record:

                break;
            case R.id.user_pay_record:

                break;
            case R.id.user_refund_manager:

                break;
            case R.id.user_my_voucher:

                break;
            case R.id.user_address_manage:

                break;
            case R.id.user_msg_manager:

                break;
            case R.id.user_public_phone:

                break;
        }
    }
}