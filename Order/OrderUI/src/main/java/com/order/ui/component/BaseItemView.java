package com.order.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.order.ui.R;

/**
 * 组件的基础类
 */
public class BaseItemView extends LinearLayout {
    private ImageView topColorBar;    //上方颜色条
    private ImageView bottomColorBar; //下方颜色条
    private LinearLayout centerContainer;

    public BaseItemView(Context context) {
        super(context);
        loadLayout(this);
        init(null);
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadLayout(this);
        init(attrs);
    }

    /**
     * 设置顶部条的颜色
     * @param color 颜色值
     */
    public void setTopBarColor(int color){
        topColorBar.setBackgroundColor(color);
    }

    /**s
     * 设置顶部条的高度，如果高度 <=0 则隐藏（不占位）顶部条。
     * @param height
     */
    public void setTopBarHeight(int height){
        if (height <= 0){
            //不显示顶部颜色条
            topColorBar.setVisibility(GONE);
        }
        else{
            //显示顶部颜色条
            ViewGroup.LayoutParams params = topColorBar.getLayoutParams();
            params.height = height;
            topColorBar.setLayoutParams(params);
            topColorBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置底部条的颜色
     * @param color 颜色值
     */
    public void setBottomBarColor(int color){
        bottomColorBar.setBackgroundColor(color);
    }

    /**s
     * 设置底部条的高度，如果高度 <=0 则隐藏（不占位）顶部条。
     * @param height
     */
    public void setBottomBarHeight(int height){
        if (height <= 0){
            //不显示顶部颜色条
            bottomColorBar.setVisibility(GONE);
        }
        else{
            //显示顶部颜色条
            ViewGroup.LayoutParams params = bottomColorBar.getLayoutParams();
            params.height = height;
            bottomColorBar.setLayoutParams(params);
            bottomColorBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 载入布局文件到当前控件、初始化变量。
     * @param attrs    AttributeSet 对象
     */
    protected void init(AttributeSet attrs){
        //如果 topColorBar 为空表示目前处在Android Studio 的编辑模式中，Eclipse 的编辑模式是可以获取到元素对象的。
        if (attrs == null || topColorBar == null)
            return;

        //从 xml 布局文件读取控件属性设置
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BaseItemView);

        int     resid  = 0;
        int     color  = 0;
        float   size   = 0.0f;

        //属性 topBarHeight
        size = ta.getDimension(R.styleable.BaseItemView_topBarHeight, 0.0f);
        setTopBarHeight((int)size);

        //属性 topBarColor
        color = ta.getColor(R.styleable.BaseItemView_topBarColor,0);
        setTopBarColor(color);

        //属性 bottomBarHeight
        size = ta.getDimension(R.styleable.BaseItemView_bottomBarHeight, 0.0f);
        setBottomBarHeight((int)size);

        //属性  bottomBarColor
        color = ta.getColor(R.styleable.BaseItemView_bottomBarColor,0);
        setBottomBarColor(color);

        ta.recycle();
    }

    /**
     * 载入 ui 布局，并返回控件内部的容器对象，如果控件不是一个容器则返回 null。
     * BaseItemView 内部的容器是一个垂直排列的 LinearLayout。
     * @param  container  UI元素的容器
     * @return 控件的容器对象,可以向其添加子视图，如果控件不是一个容器则返回null。
     */
    protected ViewGroup loadLayout(ViewGroup container){
        //从布局文件 l_baseitemview.xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.l_baseitemview,container);

        topColorBar     = (ImageView)    findViewById(R.id.top_color_bar);
        bottomColorBar  = (ImageView)    findViewById(R.id.bottom_color_bar);
        centerContainer = (LinearLayout) findViewById(R.id.center_container);

        return centerContainer;
    }
}
