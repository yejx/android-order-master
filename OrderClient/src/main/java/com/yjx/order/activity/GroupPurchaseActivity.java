package com.yjx.order.activity;

import android.os.Bundle;
import android.view.View;

import com.yjx.order.R;

public class GroupPurchaseActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_purchase);
        initUI();
    }

    private void initUI(){
        navigationBar.setTitle(R.string.home_grouppurchase);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_guide:

                break;
            case R.id.feedback:

                break;
            case R.id.check_version:

                break;
            case R.id.about:

                break;
        }
    }
}