package com.yjx.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.yjx.order.R;
import com.yjx.order.common.Parameters;
import com.yjx.order.dao.DishDao;
import com.yjx.order.ui.adapter.DishAdapter;
import com.yjx.order.ui.adapter.MenuAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton ib_search;
    private ListView lvMenu,lvDish,lvOrderDetail;
    private Button btn_selete_finish;
    private SlidingDrawer drawer;
    private LinearLayout ll_order_detail;
    private TextView tvTotal;
    private ImageView iv_arrow;
    private List<String> lstMenu;
    private List<Map<String,String>> lstDish, lstDishDetail = new ArrayList<Map<String,String>>();
    private List<Map<String,String>> lst3103 = new ArrayList<Map<String,String>>();
    private List<Map<String,String>> lstChuan = new ArrayList<Map<String,String>>();
    private List<Map<String,String>> lstMin = new ArrayList<Map<String,String>>();
    private List<Map<String,String>> lstYue = new ArrayList<Map<String,String>>();
    private DishAdapter dishAdapter;
    private MenuAdapter menuAdapter;
    private SimpleAdapter orderDetailAdapter;
    private float totalMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvDish = (ListView) findViewById(R.id.lvDish);
        lvOrderDetail = (ListView) findViewById(R.id.lvOrder);
        ib_search = (ImageButton) findViewById(R.id.ib_search);
        btn_selete_finish = (Button) findViewById(R.id.btn_selete_finish);
        drawer = (SlidingDrawer) findViewById(R.id.drawer);
        ll_order_detail = (LinearLayout) findViewById(R.id.handle);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        ib_search.setOnClickListener(this);
        btn_selete_finish.setOnClickListener(this);
        ll_order_detail.setOnClickListener(this);
        drawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                iv_arrow.setImageResource(R.drawable.public_pulldown);
                orderDetailAdapter.notifyDataSetChanged();
            }
        });
        drawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                iv_arrow.setImageResource(R.drawable.public_upthrust);
            }
        });

        initData();
        initUI();
    }
    private void initData(){
        lstDish = DishDao.getInstance().findDishs();
        lstMenu = DishDao.getInstance().findMenus();
        for(int i=0;i<lstDish.size();i++){
            switch (lstDish.get(i).get("type")){
                case Parameters.MENU_3103_DISH:
                    lst3103.add(lstDish.get(i));
                    break;
                case Parameters.MENU_CHUAN_DISH:
                    lstChuan.add(lstDish.get(i));
                    break;
                case Parameters.MENU_MIN_DISH:
                    lstMin.add(lstDish.get(i));
                    break;
                case Parameters.MENU_YUE_DISH:
                    lstYue.add(lstDish.get(i));
                    break;
            }
        }
        getOrderDetail();
    }
    private void initUI(){
        if(getIntent().getStringExtra("type").equals("点菜")){
            navigationBar.setTitle(R.string.home_order);
        }else{
            navigationBar.setTitle(R.string.home_take_out);
        }

        dishAdapter = new DishAdapter(this, lstDish, lst3103, new DishAdapter.UpdateOrderDetailListener() {
            @Override
            public void update() {
                getOrderDetail();
                String format= "共计"+lstDishDetail.size()+"菜，"+"菜单金额：<font color='#d8000c'>%1$s</font>";
                String total = "¥"+totalMoney;
                tvTotal.setText(Html.fromHtml(String.format(format, total)));
            }
        });
        lvDish.setAdapter(dishAdapter);
        menuAdapter = new MenuAdapter(this, lstMenu);
        lvMenu.setAdapter(menuAdapter);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (lstMenu.get(position)) {
                    case Parameters.MENU_3103_DISH:
                        dishAdapter.updateDishType(lst3103);
                        break;
                    case Parameters.MENU_CHUAN_DISH:
                        dishAdapter.updateDishType(lstChuan);
                        break;
                    case Parameters.MENU_MIN_DISH:
                        dishAdapter.updateDishType(lstMin);
                        break;
                    case Parameters.MENU_YUE_DISH:
                        dishAdapter.updateDishType(lstYue);
                        break;
                }
                menuAdapter.setSeletePositon(position);
                menuAdapter.notifyDataSetChanged();
            }
        });
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
            case R.id.btn_selete_finish:
                Intent intent = new Intent(this, OrderDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("lstOrderDetail", (Serializable) lstDishDetail);
                intent.putExtra("totalMoney", totalMoney);
                startActivity(intent);
                break;
        }
    }

    private void getOrderDetail(){
        lstDishDetail.clear();
        totalMoney = 0;
        for(int i=0;i<lstDish.size();i++){
            if(Integer.parseInt(lstDish.get(i).get("number"))>0){
                Map<String,String> menuMap=new HashMap<String,String>();
                String name = lstDish.get(i).get("name");
                String number = lstDish.get(i).get("number")+"*"+lstDish.get(i).get("price");
                String total = "¥"+Integer.parseInt(lstDish.get(i).get("number"))*Float.parseFloat(lstDish.get(i).get("price"));
                totalMoney += Integer.parseInt(lstDish.get(i).get("number"))*Float.parseFloat(lstDish.get(i).get("price"));
                menuMap.put("name",name);
                menuMap.put("number",number);
                menuMap.put("total", total);
                lstDishDetail.add(menuMap);
            }
        }
    }
}