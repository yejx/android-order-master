package com.order.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.order.ui.R;
import com.order.ui.commom.Dimension;

/**
 * 带图标Item基础类,横条样式的组件继承于此
 *
 *  +--------------------------------+
 *  |[icon] label         button [>] |
 *  +--------------------------------+
 */

public class IconItemView extends BaseItemView implements View.OnClickListener {
    private ImageView leftIcon;             //左侧图标
    private ViewGroup left_text_container;  //左侧文本容器

    private LineShape rightLine;       //用于显示竖线的view
    private TextView rightText;       //右侧文本
    private ImageView rightIcon;       //右侧图标
    private ImageView rightArrow;      //右侧箭头
    private boolean isVerticalLine;    //是否显示竖线
    private boolean enableOnClickItemEvents; //是否启用子项目单击事件
    private OnClickItemListener onClickItemListener;  //右图标单击事件

    public IconItemView(Context context) {
        super(context);
    }

    public IconItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View view) {
        if (onClickItemListener != null){
            ItemType type = ItemType.Unknown;

            if (view.equals(leftIcon)){
                type = ItemType.LeftIcon;
            }
            else if(view.equals(left_text_container)){
                type = ItemType.LeftContent;
            }
            else if(view.equals(rightText)){
                type = ItemType.RightText;
            }
            else if(view.equals(rightIcon)){
                type = ItemType.RightIcon;
            }
            else if(view.equals(rightArrow)){
                type = ItemType.RightArrow;
            }

            onClickItemListener.OnClickItem(this,type);
        }
    }

    /**
     * 设置左侧图标
     * @param drawable   Drawable 对象
     */
    public void setLeftIconDrawable(Drawable drawable){
        leftIcon.setImageDrawable(drawable);
    }

    /**
     * 设置左侧图标
     * @param bitmap   Bitmap 对象
     */
    public void setLeftIconBitmap(Bitmap bitmap){
        leftIcon.setImageBitmap(bitmap);
    }

    /**
     * 设置左侧图标资源 ID
     * @param resid   资源id
     */
    public void setLeftIconResource(int resid){
        leftIcon.setImageResource(resid);
    }

    /**
     * 设置左侧图标的右则的间隔值
     * @param margin  以像素为单位的间隔值。
     */
    public void setLeftIconMarginRight(int margin){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)leftIcon.getLayoutParams();
        params.rightMargin = margin;
        leftIcon.setLayoutParams(params);
    }

    /**
     * 设置左侧图标的显示属性
     * @param visibility 显示属性
     */
    public void setLeftIconVisibility(int visibility){
        leftIcon.setVisibility(visibility);
    }

    /**
     * 获取左侧图标的 ImageView 对象。
     * @return ImageView 对象。
     */
    public ImageView getLeftIconImageView(){
        return leftIcon;
    }

    /**
     * 设置右侧图标
     * @param drawable   Drawable 对象
     */
    public void setRightIconDrawable(Drawable drawable){
        rightIcon.setImageDrawable(drawable);
    }

    /**
     * 设置右侧图标
     * @param bitmap   Bitmap 对象
     */
    public void setRightIconBitmap(Bitmap bitmap){
        rightIcon.setImageBitmap(bitmap);
    }

    /**
     * 设置右侧图标资源 ID
     * @param resid   资源id
     */
    public void setRightIconResource(int resid){
        rightIcon.setImageResource(resid);
    }

    /**
     * 设置右侧图标的右则的间隔值
     * @param margin  以像素为单位的间隔值。
     */
    public void setRightIconMarginRight(int margin){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)rightIcon.getLayoutParams();
        params.rightMargin = margin;
        rightIcon.setLayoutParams(params);
    }

    /**
     * 设置左侧图标的显示属性
     * @param visibility 显示属性
     */
    public void setRightIconVisibility(int visibility){
        rightIcon.setVisibility(visibility);
    }

    /**
     * 获取右侧图标的 ImageView 对象。
     * @return ImageView 对象。
     */
    public ImageView getRightIconImageView(){
        return rightIcon;
    }

    /**
     * 获取右侧TextView对象
     * @return TextView对象
     */
    public TextView getRightText() {
        return rightText;
    }

    /**
     * 获取是否显示右侧的箭头
     * @return  显示返回 true。
     */
    public int getRightArrowVisibility() {
        return  rightArrow.getVisibility();
    }

    /**
     * 设置右侧箭头的显示属性
     * @param visibility  显示属性
     */
    public void setRightArrowVisibility(int visibility) {
        rightArrow.setVisibility(visibility);
    }

    /**
     * 是否显示右边的竖线
     * @return 显示返回true。
     */
    public boolean isVerticalLine() {
        return isVerticalLine;
    }

    /**
     * 设置是否显示右边的竖线
     * @param verticalLine 如果设置为 ture 则显示竖线。
     */
    public void setVerticalLine(boolean verticalLine) {
        isVerticalLine = verticalLine;
        if (verticalLine){
            //显示竖线
            rightLine.setVisibility(VISIBLE);
        }
        else{
            //不显示竖线
            rightLine.setVisibility(GONE);
        }
    }

    /**
     * 设置垂直线的右侧的间距
     * @param margin 像素为单位的间距
     */
    public void setVerticalLineMarginRight(int margin){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)rightLine.getLayoutParams();
        params.rightMargin = margin;
        rightLine.setLayoutParams(params);
    }

    /**
     * 获取是否显示右侧文字
     * @return  显示返回 true。
     */
    public int getRightTextVisibility() {
        return  rightText.getVisibility();
    }

    /**
     * 设置右侧文字的显示属性
     * @param visibility  显示属性
     */
    public void setRightTextVisibility(int visibility) {
        rightText.setVisibility(visibility);
    }

    /**
     * 设置右侧文本
     * @param text  文本
     */
    public void setRightText(CharSequence text){
        rightText.setText(text);
    }

    /**
     * 设置右侧文本
     * @param resid 文本资源 id。
     */
    public void setRightText(int resid){
        rightText.setText(resid);
    }

    /**
     * 设置第右侧 Hint
     * @param text  文本
     */
    public void setRightHint(CharSequence text){
        rightText.setHint(text);
    }

    /**
     * 设置第右侧 Hint
     * @param resid 文本资源 id。
     */
    public void setRightHint(int resid){
        rightText.setHint(resid);
    }

    /**
     * 设置第右侧字体大小
     * @param unit  单位
     * @param size  字体大小。
     * @see TextView#setTextSize(int, float)
     */
    public void setRightTextSize(int unit ,float size){
        rightText.setTextSize(unit,size);
    }

    /**
     * 设置第右侧字体颜色
     * @param color  颜色值。
     */
    public void setRightTextColor(int color){
        rightText.setTextColor(color);
    }

    /**
     * 设置第右侧 Hint 字体颜色
     * @param color  颜色值。
     */
    public void setRightHintTextColor(int color){
        rightText.setHintTextColor(color);
    }

    /**
     * 设置第右侧字体样式（正常、粗体、斜体 的组合值）
     * @param style  字体样式值。
     */
    public void setRightTextStyle(int style){
        TextPaint paint = rightText.getPaint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, style));
    }

    /**
     * 设置右侧文本的右侧间隔值。
     * @param margin  像素为单位的间隔值。
     */
    public void setRightTextMarginRight(int margin){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)rightText.getLayoutParams();
        params.rightMargin = margin;
        rightText.setLayoutParams(params);
    }

    /**
     * 设置右侧文本背景
     * @param drawable   Drawable 对象
     */
    public void setRightTextBackgroundDrawable(Drawable drawable){
        rightText.setBackgroundDrawable(drawable);
    }

    /**
     * 设置右侧文本背景资源 ID
     * @param resid   资源id
     */
    public void setRightTextBackgroundResource(int resid){
        rightText.setBackgroundResource(resid);
    }

    /**
     * 设置右侧文本的 padding 值。
     */
    public void setRightTextPadding(int left, int top, int right, int bottom){
        rightText.setPadding(left, top, right, bottom);
    }

    public void setOnClickItemListener(OnClickItemListener listener) {
        this.onClickItemListener = listener;
    }

    public boolean isEnableOnClickItemEvents() {
        return enableOnClickItemEvents;
    }

    public void setEnableOnClickItemEvents(boolean enableOnClickItemEvents) {
        this.enableOnClickItemEvents = enableOnClickItemEvents;
        if (enableOnClickItemEvents){
            leftIcon.setClickable(true);
            left_text_container.setClickable(true);
            rightText.setClickable(true);
            rightIcon.setClickable(true);
            rightArrow.setClickable(true);
        } else {
            leftIcon.setClickable(false);
            left_text_container.setClickable(false);
            rightText.setClickable(false);
            rightIcon.setClickable(false);
            rightArrow.setClickable(false);
        }
    }

    /**
     * 载入布局文件到当前控件、初始化变量。
     * @param attrs    AttributeSet 对象
     */
    @Override
    protected void init(AttributeSet attrs){
        //如果 rightArrow 为空表示目前处在Android Studio 的编辑模式中，Eclipse 的编辑模式是可以获取到元素对象的。
        if (attrs == null || rightArrow == null)
            return;

        setRightArrowVisibility(INVISIBLE);
        setVerticalLine(false);

        //从 xml 布局文件读取控件属性设置
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.IconItemView);

        int     iValue = 0;
        int     resid  = 0;
        int     color  = 0;
        int     style  = 0;
        float   margin = 0.0f;
        float   size   = 0.0f;
        boolean isTrue = false;
        String text   = "";

        int dp10 = Dimension.dip2px(10, getContext());
        int dp0  = 0;

        //属性 leftIconSrc
        resid = ta.getResourceId(R.styleable.IconItemView_leftIconSrc,0);
        setLeftIconResource(resid);

        //属性 leftIconMarginRight
        margin = ta.getDimension(R.styleable.IconItemView_leftIconMarginRight,dp10);
        setLeftIconMarginRight((int)margin);

        //属性 leftIconVisibility
        iValue = ta.getInt(R.styleable.IconItemView_leftIconVisibility,GONE);
        setLeftIconVisibility(iValue);

        //属性 rightIconSrc
        resid = ta.getResourceId(R.styleable.IconItemView_rightIconSrc, 0);
        setRightIconResource(resid);

        //属性 rightIconVisibility
        iValue = ta.getInt(R.styleable.IconItemView_rightIconVisibility,GONE);
        setRightIconVisibility(iValue);

        //属性 rightIconMarginRight
        margin = ta.getDimension(R.styleable.IconItemView_rightIconMarginRight,dp10);
        setRightIconMarginRight((int) margin);

        //属性 rightArrowVisibility
        iValue = ta.getInt(R.styleable.IconItemView_rightArrowVisibility,GONE);
        setRightArrowVisibility(iValue);

        //属性 isVerticalLine
        isTrue = ta.getBoolean(R.styleable.IconItemView_isVerticalLine,false);
        setVerticalLine(isTrue);

        //属性 verticalLineMarginRight
        margin = ta.getDimension(R.styleable.IconItemView_verticalLineMarginRight,dp10);
        setVerticalLineMarginRight((int)margin);

        //属性 rightText
        text = ta.getString(R.styleable.IconItemView_rightText);
        if (text != null){
            setRightText(text);
        }

        //属性 rightHint
        text = ta.getString(R.styleable.IconItemView_rightHint);
        if (text != null){
            setRightHint(text);
        }

        //属性 rightTextSize
        size = ta.getDimension(R.styleable.IconItemView_rightTextSize, 0.0f);
        if (size != 0.0f){
            setRightTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        }

        //属性 rightTextColor
        color = ta.getColor(R.styleable.IconItemView_rightTextColor,0);
        if (color != 0){
            setRightTextColor(color);
        }

        //属性 rightHintTextColor
        color = ta.getColor(R.styleable.IconItemView_rightHintTextColor,0);
        if (color != 0){
            setRightHintTextColor(color);
        }

        //属性 rightTextStyle
        style = ta.getInt(R.styleable.IconItemView_rightTextStyle,0);
        setRightTextStyle(style);

        //属性 rightTextMarginRight
        margin = ta.getDimension(R.styleable.IconItemView_rightTextMarginRight,dp10);
        setRightTextMarginRight((int) margin);

        //属性 rightIconSrc
        resid = ta.getResourceId(R.styleable.IconItemView_rightTextBackground, 0);
        setRightTextBackgroundResource(resid);

        //属性 rightTextVisibility
        iValue = ta.getInt(R.styleable.IconItemView_rightTextVisibility,GONE);
        setRightTextVisibility(iValue);

        //属性 enableOnClickItemEvents
        isTrue = ta.getBoolean(R.styleable.IconItemView_enableOnClickItemEvents,false);
        setEnableOnClickItemEvents(isTrue);

        ta.recycle();

        super.init(attrs);
    }

    /**
     * 载入UI布局，并返回左侧文本框容器(垂直分布)
     * @param  container  UI 元素的容器
     */
    @Override
    protected ViewGroup loadLayout(ViewGroup container){
        ViewGroup vg = super.loadLayout(container);
        if (vg == null) return null;

        //从布局文件 l_twolinetextview.xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.l_rowitemview,vg);

        leftIcon             = (ImageView) findViewById(R.id.left_icon);
        left_text_container  = (ViewGroup) findViewById(R.id.left_text_container);
        left_text_container.setClickable(true);

        rightLine      = (LineShape) findViewById(R.id.right_line);
        rightText      = (TextView)  findViewById(R.id.right_text);
        rightIcon      = (ImageView) findViewById(R.id.right_icon);
        rightArrow     = (ImageView) findViewById(R.id.right_arrow);

        leftIcon.setOnClickListener(this);
        left_text_container.setOnClickListener(this);
        rightText.setOnClickListener(this);
        rightIcon.setOnClickListener(this);
        rightArrow.setOnClickListener(this);

        //left_text_container 水平排列的LinearLayout.
        return left_text_container;
    }

    public enum ItemType {
        Unknown,
        LeftIcon,
        LeftContent,
        RightText,
        RightIcon,
        RightArrow
    }

    public interface OnClickItemListener {
        public void OnClickItem(View view, ItemType type);
    }
}
