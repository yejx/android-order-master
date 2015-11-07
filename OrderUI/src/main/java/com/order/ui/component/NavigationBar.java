package com.order.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.order.ui.R;
import com.order.ui.commom.Dimension;

import java.util.List;

/**
 * 导航栏
 * <p/>
 * +--------------------------+
 * |  back    title     action|
 * +--------------------------+
 */
public class NavigationBar extends LinearLayout implements View.OnClickListener {

    /**
     * 间隔时间击时间
     */
    private final long INTERVAL_TIME = 800;

    /**
     * 点击时间记录tag
     */
    private final int TAG_FORMER_CLICK_TIME = "TAG_FORMER_CLICK_TIME".hashCode();



    private ViewGroup mRootLayout; //根布局
    private FrameLayout navBackLayout;
    private int                   background;         //整体背景
    private int                   bottomBg;           //底部背景条
    private int                   backBg;             //返回按钮背景
    private String backText;        //返回按钮文字
    private String actionText;      //操作按钮文字
    private int                   actionBg;           //操作按钮背景
    private int                   textColor;          //文字颜色，包括title和操作按钮的文字颜色
    private float                 btnTextSize;      //按钮文字大小
    private float                 titleTextSize;    //title文字大小
    private int                   titleMaxLength;    //title最大长度
    private TextView backButton;     //返回按钮
    private TextView actionButton;        //操作按钮
    private TextView titleText;         //title文本
    private ImageView bottomImage;      //底部imageview，导航栏下边的灰色条
    private ProgressBar mProgress;      //右侧进度提示bar
    private OnNavBarClickListener onNavBarClickListener;    //导航栏点击事件监听器

    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initAttrs(context, attrs);
        initView();
    }

    /**
     * 设置导航栏事件监听器
     *
     * @param onNavBarClickListener 点击监听器
     */
    public void setOnNavBarClickListener(OnNavBarClickListener onNavBarClickListener) {
        this.onNavBarClickListener = onNavBarClickListener;
    }

    /**
     * 获取标题对象titleText
     *
     * @return
     */
    public TextView getNavbarTitleTextView() {
        return titleText;
    }

    /**
     * 配置事件监听器
     *
     * @param view 被点击的view
     */
    @Override
    public void onClick(View view) {
        if (isFastDoubleClick(view)) {
            return;
        }

        NavSubMenu subMenu = (NavSubMenu) view.getTag();
        if (subMenu != null) {
            subMenu.changeVisibility();
            return;
        }

        if (onNavBarClickListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.nav_box_back && backButton.isShown()) {
            onNavBarClickListener.onNavItemClick(NavigationBarItem.back);
        } else if (id == R.id.nav_center_text) {
            onNavBarClickListener.onNavItemClick(NavigationBarItem.title);
        } else if (id == R.id.nav_box_action && actionButton.isShown()) {
            onNavBarClickListener.onNavItemClick(NavigationBarItem.action);
        }
    }

    /**
     * 是否重复点击
     *
     * @return
     * @view 被点击view，如果前后是同一个view，则进行双击校验
     */
    private boolean isFastDoubleClick(View view) {
        boolean isFastDoubleClick = false;
        long now = System.currentTimeMillis();

        Object lastClickTime = view.getTag(TAG_FORMER_CLICK_TIME);
        if (lastClickTime != null && lastClickTime instanceof Long && (now - (long) lastClickTime) < INTERVAL_TIME){
            isFastDoubleClick = true;
        }
        view.setTag(TAG_FORMER_CLICK_TIME, now);
        return isFastDoubleClick;
    }


    /**
     * 设置返回按钮文字
     *
     * @param backText 按钮文字
     */
    public void setBackText(String backText) {
        backButton.setText(backText);
        backButton.setBackgroundResource(0);
        navBackLayout.setPadding(Dimension.dip2px(5, getContext()), 0, 0, 0);
    }

    /**
     * 设置返回按钮文字
     *
     * @param resId 按钮文字id
     */
    public void setBackText(int resId) {
        String text = getContext().getString(resId);
        setBackText(text);
    }


    /**
     * @param color 设置返回按钮文字颜色
     */
    public void setBackBtnTextColor(int color) {
        backButton.setTextColor(color);
    }

    /**
     * 设置标题
     *
     * @param title 标题文字
     */
    public void setTitle(String title) {
        titleText.setText(title);
    }

    /**
     * 获取标题名称
     *
     * @return 标题字符串
     */
    public String getTitle() {
        return titleText.getText().toString();
    }

    /**
     * 设置标题
     *
     * @param resId 标题文字id
     */
    public void setTitle(int resId) {
        String text = getContext().getString(resId);
        setTitle(text);
    }

    /**
     * 设置标题文字大小
     *
     * @param sizePx
     */
    public void setTitleTextSize(int sizePx) {
        int sizeSp = Dimension.px2sp(sizePx, getContext());
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
    }

    /**
     * 给textview添加图标
     *
     * @param left   左侧图标id
     * @param top    上侧图标id
     * @param right  右侧图标id
     * @param bottom 底侧图标id
     */
    public void setTitleCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        titleText.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        titleText.setCompoundDrawablePadding(Dimension.dip2px(5, getContext()));
    }

    /**
     * 设置操作按钮文字
     *
     * @param actionText
     */
    public void setActionBtnText(String actionText) {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setText(actionText);
    }

    /**
     * 获取操作按钮的文字
     *
     * @return
     */
    public String getActionBtnText() {
        return actionButton.getText().toString();
    }

    /**
     * 设置操作按钮文字
     *
     * @param resId 操作按钮文字id
     */
    public void setActionBtnText(int resId) {
        setActionBtnText(getContext().getString(resId));
    }

    /**
     * 设置操作按钮字体颜色
     *
     * @param color
     */
    public void setActionBtnTextColor(int color) {
        actionButton.setTextColor(color);
    }

    /**
     * 设置操作按钮是否可用
     *
     * @param isEnable
     */
    public void setActionBtnEnabled(boolean isEnable) {
        actionButton.setEnabled(isEnable);
    }

    /**
     * 设置返回按钮是否可见
     *
     * @param visibility
     */
    public void setBackBtnVisibility(int visibility) {
        backButton.setVisibility(visibility);
    }

    /**
     * 设置操作按钮是否可见
     *
     * @param visibility
     */
    public void setActionBtnVisibility(int visibility) {
        actionButton.setVisibility(visibility);
    }

    /**
     * 设置操作按钮背景
     *
     * @param resourceId 资源id
     */
    public void setActionBtnBackground(int resourceId) {
        actionButton.setBackgroundResource(resourceId);
        actionButton.setVisibility(VISIBLE);
    }

    /**
     * 设置操作按钮背景
     *
     * @param drawable
     */
    public void setActionBtnBackground(Drawable drawable) {
        actionButton.setBackgroundDrawable(drawable);
        actionButton.setVisibility(VISIBLE);
    }

    /**
     * 设置返回按钮背景
     *
     * @param resourceId 资源id
     */
    public void setBackButtonBackground(int resourceId) {
        backButton.setText("");
        if (resourceId == R.drawable.nav_back) {
            navBackLayout.setPadding(Dimension.dip2px(5, getContext()), 0, 0, 0);
        } else {
            navBackLayout.setPadding(Dimension.dip2px(15, getContext()), 0, 0, 0);
        }
        backButton.setBackgroundResource(resourceId);
    }

    /**
     * 设置标题背景
     *
     * @param resourceId 资源id
     */
    public void setTitleBackground(int resourceId) {
        titleText.setBackgroundResource(resourceId);
    }

    /**
     * 设置标题背景
     *
     * @param resourceId 资源id
     */
    public void setNavBackground(int resourceId) {
        mRootLayout.setBackgroundResource(resourceId);
    }

    /**
     * 设置标题背景色
     *
     * @param color
     */
    public void setNavBackgroundColor(int color) {
        mRootLayout.setBackgroundColor(color);
    }

    /**
     * 显示右侧进度提示bar
     */
    public void showRightProgress() {
        actionButton.setVisibility(GONE);
        mProgress.setVisibility(VISIBLE);
    }

    /**
     * 隐藏右侧进度提示bar
     */
    public void hideRightProgress() {
        actionButton.setVisibility(VISIBLE);
        mProgress.setVisibility(GONE);
    }

    /**
     * 删掉所有元素
     */
    public void clear() {
        removeSubMenu();
        mProgress.setVisibility(GONE);
        titleText.setText(null);
        titleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        titleText.setBackgroundResource(0);
        backButton.setText(null);
        backButton.setBackgroundResource(0);
        actionButton.setText(null);
        actionButton.setBackgroundResource(0);
    }

    /**
     * 初始化属性值
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //获取xml中配置的属性资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        try {
            background      = typedArray.getResourceId(R.styleable.NavigationBar_navBg, 0);
            bottomBg        = typedArray.getResourceId(R.styleable.NavigationBar_bottomBg, 0);
            backBg          = typedArray.getResourceId(R.styleable.NavigationBar_backBg, 0);
            actionText      = typedArray.getString(R.styleable.NavigationBar_actionText);
            backText        = typedArray.getString(R.styleable.NavigationBar_backText);
            actionBg        = typedArray.getResourceId(R.styleable.NavigationBar_actionBg, 0);
            textColor       = typedArray.getColor(R.styleable.NavigationBar_textColor, 0xFFFFFFFF);
            btnTextSize     = typedArray.getDimension(R.styleable.NavigationBar_btnTextSize, 0.0f);
            titleTextSize   = typedArray.getDimension(R.styleable.NavigationBar_titleTextSize, 0.0f);
            titleMaxLength  = typedArray.getInt(R.styleable.NavigationBar_titleMaxLength, 9);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * 初始化导航条中的相关元素
     */
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.l_navigation_bar, this, true);

        backButton      = (TextView) findViewById(R.id.nav_back);
        actionButton    = (TextView) findViewById(R.id.nav_right_btn);
        titleText       = (TextView) findViewById(R.id.nav_center_text);
        bottomImage     = (ImageView) findViewById(R.id.nav_bottom_image);
        mProgress       = (ProgressBar) findViewById(R.id.nav_right_progress);
        navBackLayout   = (FrameLayout) findViewById(R.id.nav_box_back);

        //背景
        mRootLayout = (ViewGroup) findViewById(R.id.nav_bar_root);
        mRootLayout.setBackgroundResource(background);
        //底部灰色横条
        bottomImage.setBackgroundResource(bottomBg);

        //返回按钮
        backButton.setText(backText);
        backButton.setTextColor(textColor);
        backButton.setBackgroundResource(backBg);
        navBackLayout.setPadding(Dimension.dip2px(5, getContext()), 0, 0, 0);

        //操作按钮
        actionButton.setText(actionText);
        actionButton.setTextColor(textColor);
        actionButton.setBackgroundResource(actionBg);

        //标题文字
        titleText.setTextColor(textColor);
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(titleMaxLength);
        InputFilter[]            filters      = {lengthFilter};
        titleText.setFilters(filters);

        //字体大小
        if (Float.compare(btnTextSize, 0.0f) > 0) {
            backButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            actionButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        if (Float.compare(titleTextSize, 0.0f) > 0) {
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        }

        //设置事件监听器
        findViewById(R.id.nav_box_back).setOnClickListener(this);
        titleText.setOnClickListener(this);
        findViewById(R.id.nav_box_action).setOnClickListener(this);
    }

    /**
     * 给按钮添加SubMenu
     *
     * @param item                         按钮标示
     * @param options                      SubMenu菜单
     * @param checkPosition                默认选择的item项的position
     * @param onSubMenuOptionClickListener SubMenu菜单点击监听
     */
    public void addSubMenu(NavigationBarItem item,
                           List<NavSubMenu.Option> options,
                           int checkPosition,
                           NavSubMenu.OnSubMenuOptionClickListener onSubMenuOptionClickListener,
                           NavSubMenu.OnSubMenuOpenOrCloseListener onSubMenuOpenOrCloseListener)
    {
        View attachedView = null;
        NavSubMenu.Width width        = NavSubMenu.Width.WRAP_CONTENT;
        NavSubMenu.Type  type         = null;
        switch (item) {
            case back:
                attachedView = backButton;
                break;
            case title:
                attachedView = titleText;
                width = NavSubMenu.Width.MATCH_PARENT;
                type = NavSubMenu.Type.CENTER_POP;
                break;
            case action:
                attachedView = actionButton;
                type = NavSubMenu.Type.RIGHT_POP;
                break;
        }
        this.measure(0, 0);
        attachedView.setTag(new NavSubMenu(attachedView,
                                           width,
                                           this.getMeasuredHeight(),
                                           options,
                                           onSubMenuOptionClickListener,
                                           onSubMenuOpenOrCloseListener,
                                           checkPosition,
                                           type));
    }

    /**
     * 去掉SubMenu
     */
    public void removeSubMenu() {
        backButton.setTag(null);
        titleText.setTag(null);
        actionButton.setTag(null);
    }

    public enum NavigationBarItem {
        back,
        //返回按钮
        title,
        //title文字
        action  //操作按钮，即最右边的按钮
    }

    /**
     * 导航点击事件监听接口
     */
    public interface OnNavBarClickListener {
        public void onNavItemClick(NavigationBarItem navBarItem);
    }

}
