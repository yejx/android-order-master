package com.yjx.order.activity;

import android.os.Bundle;
import android.view.View;

import com.yjx.order.R;
import com.order.ui.component.LabelItemView;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private LabelItemView user_guide;
    private LabelItemView feedback;
    private LabelItemView check_version;
    private LabelItemView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        user_guide = (LabelItemView) findViewById(R.id.user_guide);
        feedback = (LabelItemView) findViewById(R.id.feedback);
        check_version = (LabelItemView) findViewById(R.id.check_version);
        about = (LabelItemView) findViewById(R.id.about);

        user_guide.setOnClickListener(this);
        feedback.setOnClickListener(this);
        check_version.setOnClickListener(this);
        about.setOnClickListener(this);

        initUI();
    }

    private void initUI(){
        navigationBar.setTitle(R.string.settings);

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