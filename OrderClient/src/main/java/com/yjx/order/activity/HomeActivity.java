package com.yjx.order.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.yjx.order.R;
import com.yjx.order.common.AppDoubleBackExit;
import com.order.ui.component.CircleItemView;
import com.order.ui.component.CircleLayout;
import com.order.ui.component.NavigationBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity implements View.OnClickListener{
//        CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener{
    private CircleLayout circleMenu;
    private CircleItemView ll_order;
    private CircleItemView ll_book;
    private CircleItemView ll_grouppurchase;
    private CircleItemView ll_take_out;
    private TextView tv_home_tips;
    private TextView selectedTextView;
    private List<CircleItemView> lstCircleItemView = new ArrayList<CircleItemView>();
    private NavigationBar.OnNavBarClickListener navBarClickListener = new NavigationBar.OnNavBarClickListener() {
        @Override
        public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
            switch (navBarItem) {
                case back:
                    Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    break;
                case action:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);
        ll_order = (CircleItemView) findViewById(R.id.ll_order);
        ll_book = (CircleItemView) findViewById(R.id.ll_book);
        ll_grouppurchase = (CircleItemView) findViewById(R.id.ll_grouppurchase);
        ll_take_out = (CircleItemView) findViewById(R.id.ll_take_out);
        tv_home_tips = (TextView)findViewById(R.id.tv_home_tips);
        selectedTextView = (TextView)findViewById(R.id.main_selected_textView);

        // 将字体文件保存在assets/fonts/目录下
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/huawenxingkai.ttf");
        // 应用字体
        tv_home_tips.setTypeface(typeFace);
        selectedTextView.setText(((CircleItemView)circleMenu.getSelectedItem()).getName());
//        circleMenu.setOnItemSelectedListener(this);
//        circleMenu.setOnItemClickListener(this);
        ll_order.setOnClickListener(this);
        ll_book.setOnClickListener(this);
        ll_grouppurchase.setOnClickListener(this);
        ll_take_out.setOnClickListener(this);

        navigationBar.setTitle(R.string.app_name);
        navigationBar.setBackButtonBackground(R.drawable.sy_mine);
        navigationBar.setActionBtnBackground(R.drawable.sy_subbranch);
        navigationBar.setOnNavBarClickListener(navBarClickListener);

        lstCircleItemView.add(ll_order);
        lstCircleItemView.add(ll_book);
        lstCircleItemView.add(ll_grouppurchase);
        lstCircleItemView.add(ll_take_out);

    }

    private void initUI(){
        dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        order_curX = ll_order.getX();
        order_curY = ll_order.getY();
        book_curX = ll_book.getX();
        book_curY = ll_book.getY();
        grouppurchase_curX = ll_grouppurchase.getX();
        grouppurchase_curY = ll_grouppurchase.getY();
        take_out_curX = ll_take_out.getX();
        take_out_curY = ll_take_out.getY();

//        final AnimationDrawable ad_order = (AnimationDrawable) ll_order.getBackground();
//        // 开始逐帧动画
//        ad_order.start();
        // 通过定制器控制每0.2秒运行一次TranslateAnimation动画
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MESSAGE_ORDER);
                handler.sendEmptyMessage(MESSAGE_BOOK);
                handler.sendEmptyMessage(MESSAGE_GROUPPURCHASE);
                handler.sendEmptyMessage(MESSAGE_TAKE_OUT);
            }

        }, 0, 400);

    }


    @Override
    public void onClick(View view) {
        startIntent(view);
    }

    private void startIntent(View view) {
        switch (view.getId()){
            case R.id.ll_order:
                selectedTextView.setText(R.string.home_order);
                Intent orderIntent = new Intent(HomeActivity.this, OrderActivity.class);
                orderIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                orderIntent.putExtra("type","点菜");
                startActivity(orderIntent);
                break;
            case R.id.ll_book:
                selectedTextView.setText(R.string.home_book);
                Intent bookIntent = new Intent(HomeActivity.this, BookActivity.class);
                bookIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(bookIntent);
                break;
            case R.id.ll_grouppurchase:
                selectedTextView.setText(R.string.home_grouppurchase);
                Intent groupPurchaseIntent = new Intent(HomeActivity.this, GroupPurchaseActivity.class);
                groupPurchaseIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(groupPurchaseIntent);
                break;
            case R.id.ll_take_out:
                selectedTextView.setText(R.string.home_take_out);
                Intent takeOutIntent = new Intent(HomeActivity.this, OrderActivity.class);
                takeOutIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                takeOutIntent.putExtra("type", "外卖");
                startActivity(takeOutIntent);
                break;
        }
    }

//    @Override
//    public void onItemSelected(View view, int position, long id, String name) {
////        for (int i = 0;i<lstCircleItemView.size(); i++){
////            if(i != position){
////                lstCircleItemView.get(i).setBackgroundResource(R.drawable.home_button_white_bg);
////            }
////        }
//        selectedTextView.setText(name);
////        startIntent(view);
//    }

//    @Override
//    public void onItemClick(View view, int position, long id, String name) {
////        startIntent(view);
//        Toast.makeText(getApplicationContext(), " " + name, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onBackPressed() {
        AppDoubleBackExit.onBack(this);
    }


    //记录当前的位置
    private float order_curX = 0;
    private float order_curY = 0;
    //记录下一个位置的座标
    private float order_nextX = 0;
    private float order_nextY = 0;

    //记录当前的位置
    private float book_curX = 0;
    private float book_curY = 0;
    //记录下一个位置的座标
    private float book_nextX = 0;
    private float book_nextY = 0;

    //记录当前的位置
    private float grouppurchase_curX = 0;
    private float grouppurchase_curY = 0;
    //记录下一个位置的座标
    private float grouppurchase_nextX = 0;
    private float grouppurchase_nextY = 0;

    //记录当前的位置
    private float take_out_curX = 0;
    private float take_out_curY = 0;
    //记录下一个位置的座标
    private float take_out_nextX = 0;
    private float take_out_nextY = 0;

    private static final int MESSAGE_ORDER = 1;
    private static final int MESSAGE_BOOK = 2;
    private static final int MESSAGE_GROUPPURCHASE =3;
    private static final int MESSAGE_TAKE_OUT = 4;

    private DisplayMetrics dm;
    private int width = 0;
    private int height = 0;

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_ORDER:
                    order_translateAnimation();
                    break;
                case MESSAGE_BOOK:
                    book_translateAnimation();
                    break;
                case MESSAGE_GROUPPURCHASE:
                    grouppurchase_translateAnimation();
                    break;
                case MESSAGE_TAKE_OUT:
                    take_out_translateAnimation();
                    break;
            }
        }
    };

    private void order_translateAnimation() {
        if (order_curX > width) {
            order_curX = width;
        } else if(order_curX < 0){
            order_curX = 50;
        }
        if(order_curY > height){
            order_curY = height;
        }else if(order_curY < dm.density*56){
            order_curY = dm.density*56;
        }
        order_nextX = order_curX + (float) (Math.random() * 30 - 15);
        order_nextY = order_curY + (float) (Math.random() * 30 - 15);
        TranslateAnimation anim = new TranslateAnimation(order_curX, order_nextX, order_curY, order_nextY);
        order_curX = order_nextX;
        order_curY = order_nextY;
        anim.setDuration(400);
        // 开始位移动画
        ll_order.startAnimation(anim);
    }

    private void book_translateAnimation() {
        if (book_curX > width) {
            book_curX = width;
        } else if(book_curX < 0){
            book_curX = 50;
        }
        if(book_curY > height){
            book_curY = height;
        }else if(book_curY < dm.density*56){
            book_curY = dm.density*56;
        }
        book_nextX = book_curX + (float) (Math.random() * 30 - 15);
        book_nextY = book_curY + (float) (Math.random() * 30 - 15);
        TranslateAnimation anim = new TranslateAnimation(book_curX, book_nextX, book_curY, book_nextY);
        book_curX = book_nextX;
        book_curY = book_nextY;
        anim.setDuration(400);
        // 开始位移动画
        ll_book.startAnimation(anim);
    }

    private void grouppurchase_translateAnimation() {
        if (grouppurchase_curX > width) {
            grouppurchase_curX = width;
        } else if(grouppurchase_curX < 0){
            grouppurchase_curX = 50;
        }
        if(grouppurchase_curY > height){
            grouppurchase_curY = height;
        }else if(grouppurchase_curY < dm.density*56){
            grouppurchase_curY = dm.density*56;
        }
        grouppurchase_nextX = grouppurchase_curX + (float) (Math.random() * 30 - 15);
        grouppurchase_nextY = grouppurchase_curY + (float) (Math.random() * 30 - 15);
        TranslateAnimation anim = new TranslateAnimation(grouppurchase_curX, grouppurchase_nextX, grouppurchase_curY, grouppurchase_nextY);
        grouppurchase_curX = grouppurchase_nextX;
        grouppurchase_curY = grouppurchase_nextY;
        anim.setDuration(400);
        // 开始位移动画
        ll_grouppurchase.startAnimation(anim);
    }

    private void take_out_translateAnimation() {
        if (take_out_curX > width) {
            take_out_curX = width;
        } else if(take_out_curX < 0){
            take_out_curX = 0;
        }
        if(take_out_curY > height){
            take_out_curY = height;
        }else if(take_out_curY < dm.density*56){
            take_out_curY = dm.density*56;
        }
        take_out_nextX = take_out_curX + (float) (Math.random() * 30 - 15);
        take_out_nextY = take_out_curY + (float) (Math.random() * 30 - 15);
        TranslateAnimation anim = new TranslateAnimation(take_out_curX, take_out_nextX, take_out_curY, take_out_nextY);
        Log.d("yjx", take_out_nextY+"");
        take_out_curX = take_out_nextX;
        take_out_curY = take_out_nextY;
        anim.setDuration(400);
        // 开始位移动画
        ll_take_out.startAnimation(anim);
    }

}
