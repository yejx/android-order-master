package com.yjx.order.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yjx.order.activity.login.LoginActivity;
import com.yjx.order.R;
import com.order.ui.commom.Dimension;
import com.yjx.order.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 * Created by yjx on 2014/6/18.
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    /**
     * 存放下一级页面,不要在这里写死啊!!!!!
     */
    public static final String KEY_NEXT_PAGE = "KEY_NEXT_PAGE";

    /**
     * 引导文字图片id
     */
    private static final int[] TEXT_IDS = {
//            R.drawable.guide_text_1,
//            R.drawable.guide_text_2,
//            R.drawable.guide_text_3,
//            R.drawable.guide_text_4
    };

    /**
     * 引导图片id
     */
    private static final int[] IMAGE_IDS = {
            R.drawable.guide_image_1,
            R.drawable.guide_image_2,
            R.drawable.guide_image_3,
    };

    /**
     * 下一个页面,要求调用本类的页面传递过来
     */
    private String next;

    private Activity self;
    private ViewPager viewPager;
    private GuideAdapter guideAdapter;
    private LinearLayout mDotContainer;

    private int displayWidth = 0,displayHeight = 0 ;
    private DisplayMetrics displayMetrics;
    private float density;
    private Animation alphaAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        displayMetrics = getResources().getDisplayMetrics();
        density = displayMetrics.density;
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.activity_guide_viewpager);
        mDotContainer   = (LinearLayout) findViewById(R.id.dotContainer);

        hideNavigationBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == guideAdapter) {
            initView();
        }
    }

    /**
     * 初始化UI
     */
    private void initView(){
        Rect outRect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        displayHeight = outRect.height();
        displayWidth = outRect.width();

        alphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        guideAdapter = new GuideAdapter();
        viewPager.setAdapter(guideAdapter);
        viewPager.setOnPageChangeListener(this);

        setBottom(0);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setBottom(position);
        View itemView = guideAdapter.getView(position);
        ImageView image = (ImageView) itemView.findViewById(R.id.activity_guide_imageview);
        image.startAnimation(alphaAnim);
        if(position==IMAGE_IDS.length-1){
            Button btn_open_app = (Button) itemView.findViewById(R.id.activity_guide_open_app_button);
            btn_open_app.startAnimation(alphaAnim);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 设置底部index
     * @param position
     */
    private void setBottom(int position) {
        position %= IMAGE_IDS.length;
        if (position < 0) {
            position = IMAGE_IDS.length + position;
        }
        if (IMAGE_IDS.length < 2) {
            return;
        }
        if (mDotContainer.getChildCount() == 0) {
            int density = (int) getResources().getDisplayMetrics().density;
            LinearLayout.LayoutParams lpDot = new LinearLayout.LayoutParams(density*13, density*13);
            for (int i = 0; i < IMAGE_IDS.length; i++) {
                ImageView v = new ImageView(mContext);
                v.setLayoutParams(lpDot);
                v.setPadding(density*3, 0, density*3, 0);
                mDotContainer.addView(v);
            }
        }
        for (int i = 0; i < IMAGE_IDS.length; i++) {
            ImageView v = (ImageView) mDotContainer.getChildAt(i);
            v.setImageResource(position == i ? R.drawable.dot_white_2 : R.drawable.dot_white_1);
        }
        mDotContainer.setVisibility(position == IMAGE_IDS.length - 1 ? View.GONE : View.VISIBLE);
    }

    /**
     * 适配器
     */
    private class GuideAdapter extends PagerAdapter implements View.OnClickListener {

        private List<View> lstImageView = new ArrayList<View>();

        private GuideAdapter(){
            super();
            setItemView();
        }
        private View getView(int position){
            return lstImageView.get(position);
        }

        private void setItemView(){
            for(int position = 0; position < IMAGE_IDS.length; position++){
                View itemView = getLayoutInflater().inflate(R.layout.activity_guide_item,null);
//                ImageView text = (ImageView) itemView.findViewById(R.id.activity_guide_text);
                ImageView image = (ImageView) itemView.findViewById(R.id.activity_guide_imageview);

//                text.setImageResource(TEXT_IDS[position]);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setImageResource(IMAGE_IDS[position]);

                final Button btnOpen  = (Button) itemView.findViewById(R.id.activity_guide_open_app_button);
                btnOpen.setVisibility(position == IMAGE_IDS.length - 1 ? View.VISIBLE : View.GONE);
                btnOpen.setOnClickListener(this);
                setJoinButton(btnOpen);

                if(position==0) image.startAnimation(alphaAnim);
                lstImageView.add(itemView);
            }
        }

        /**
         * 设置手机使用系统自带虚拟按键时按钮的边距
         * @param btnOpen
         */
        private void setJoinButton(Button btnOpen) {
            try {
                if (btnOpen == null) return;
                if (isVirtualKeyShow(GuideActivity.this)) {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(0, Dimension.dip2px(50, GuideActivity.this), 0, Dimension.dip2px(80, GuideActivity.this));
                    params.height = Dimension.dip2px(55, GuideActivity.this);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    btnOpen.setLayoutParams(params);
                }
            } catch (Exception e) {
                LogUtil.print(e.getMessage());
            }
        }

        /**
         * 手机是使用了虚拟按键
         * @param context
         * @return
         */
        public boolean isVirtualKeyShow(Activity context){

            if(Build.VERSION.SDK_INT >= 14){
                Resources resources = context.getResources();
                int resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
                if (resourceId > 0) {
                    return resources.getBoolean(resourceId);
                }
            }
            return false;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

        @Override
        public int getCount() {
            return IMAGE_IDS.length;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            if (object != null) {
                ((ViewGroup) container).removeView((View) object);
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = lstImageView.get(position);
            container.addView(itemView);
            return itemView;
        }

        /**
         * 图片等比例缩放
         * @param drawbale
         * @return
         */
        private Drawable getScaleDrawable(int drawbale){
            Drawable returnDrawable  = null;
            Bitmap originalBitmap = BitmapFactory.decodeResource(self.getResources(), drawbale);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, displayWidth, displayHeight, false);

            returnDrawable = new BitmapDrawable(scaledBitmap);
            return returnDrawable;
        }

    }
}
