package com.yjx.order.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.order.ui.component.NavigationBar;
import com.yjx.order.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener{
    private Button ib_search;
    private ListView lvOrderDetail;
    private Button btn_confirm_order;
    private TextView tvTotal;
    private SimpleAdapter orderDetailAdapter;
    private List<Map<String,String>> lstDishDetail = new ArrayList<Map<String,String>>();
    private float totalMoney = 0;
    private NavigationBar.OnNavBarClickListener navBarClickListener = new NavigationBar.OnNavBarClickListener() {
        @Override
        public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
            switch (navBarItem) {
                case back:
                    finish();
                    break;
                case action:
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        lvOrderDetail = (ListView) findViewById(R.id.lvOrderDetail);
        ib_search = (Button) findViewById(R.id.ib_search);
        btn_confirm_order = (Button) findViewById(R.id.btn_confirm_order);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        ib_search.setOnClickListener(this);
        btn_confirm_order.setOnClickListener(this);

        initData();
        initUI();
    }
    private void initData(){
        lstDishDetail = (List<Map<String, String>>) getIntent().getSerializableExtra("lstOrderDetail");
        totalMoney = getIntent().getFloatExtra("totalMoney", 0);
    }

    private void initUI(){
        navigationBar.setTitle(R.string.order_confirm);
        navigationBar.setActionBtnText(R.string.modify);
        navigationBar.setOnNavBarClickListener(navBarClickListener);

        orderDetailAdapter = new SimpleAdapter(this, lstDishDetail, R.layout.listview_order_detail_item,
                new String[]{"name","number","total"},new int[]{R.id.tvDishName,R.id.tvDishNum,R.id.tvDishPrice});
        lvOrderDetail.setAdapter(orderDetailAdapter);
        String format= "共计"+lstDishDetail.size()+"菜，"+"菜单金额：<font color='#d8000c'>%1$s</font>";
        String total = "¥"+totalMoney;
        tvTotal.setText(Html.fromHtml(String.format(format, total)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_search:

                break;
            case R.id.btn_confirm_order:

                break;
        }
    }
}