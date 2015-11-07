package com.order.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.order.ui.R;
import com.order.ui.commom.Dimension;

/**
 *  双行text,图标可选
 *  +--------------------------+
 *  |       label1             |
 *  |[icon]                [>] |
 *  |       label2             |
 *  +--------------------------+
 */

public class TwoLineTextView extends IconItemView {
    private TextView firstLineText;  //第一行文本
    private TextView secondLineText; //第二行文本


    public TwoLineTextView(Context context) {
        super(context);
    }

    public TwoLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置第一行文本的显示
     * @param visibility visibility
     */
    public void setFirstLineVisibility(int visibility){
        firstLineText.setVisibility(visibility);
    }

    /**
     * 设置第一行文本
     * @param text  文本
     */
    public void setFirstLineText(CharSequence text){
        firstLineText.setText(text);
    }

    /**
     * 设置第一行文本
     * @param resid 文本资源 id。
     */
    public void setFirstLineText(int resid){
        firstLineText.setText(resid);
    }

    public String getFirstLineText() {
        return firstLineText.getText().toString();
    }

    /**
     * 设置第一行 Hint
     * @param text  文本
     */
    public void setFirstLineHint(CharSequence text){
        firstLineText.setHint(text);
    }

    /**
     * 设置第一行 Hint
     * @param resid 文本资源 id。
     */
    public void setFirstLineHint(int resid){
        firstLineText.setHint(resid);
    }

    /**
     * 设置第一行字体大小
     * @param unit  单位
     * @param size  字体大小。
     * @see TextView#setTextSize(int, float)
     */
    public void setFirstLineTextSize(int unit,float size){
        firstLineText.setTextSize(unit, size);
    }

    /**
     * 设置第一行字体颜色
     * @param color  颜色值。
     */
    public void setFirstLineTextColor(int color){
        firstLineText.setTextColor(color);
    }

    /**
     * 设置第一行 Hint 字体颜色
     * @param color  颜色值。
     */
    public void setFirstLineHintTextColor(int color){
        firstLineText.setHintTextColor(color);
    }

    /**
     * 设置第一行字体样式（正常、粗体、斜体 的组合值）
     * @param style  字体样式值。
     */
    public void setFirstLineTextStyle(int style){
        TextPaint paint = firstLineText.getPaint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, style));
    }

    /**
     * 设置第一行文本下方的间隔值
     * @param margin
     */
    public void setFirstLineTextMarginBottom(int margin){
        MarginLayoutParams params = (MarginLayoutParams)firstLineText.getLayoutParams();
        params.bottomMargin = margin;
        firstLineText.setLayoutParams(params);
    }

    /**
     *设置第二行文本的显示
     */
    public void setSecondLineVisibility(int visibility){
        secondLineText.setVisibility(visibility);
    }
    /**
     * 设置第二行文本
     * @param text  文本
     */
    public void setSecondLineText(CharSequence text){
        secondLineText.setText(text);
    }

    /**
     * 设置第二行文本
     * @param resid 文本资源 id。
     */
    public void setSecondLineText(int resid){
        secondLineText.setText(resid);
    }

    /**
     * 获取第二行文本
     * @return
     */
    public String getSecondLineTextStr(){
        return secondLineText.getText().toString();
    }

    /**
     * 获取第二行文本textview
     * @return
     */
    public TextView getSecondLineText(){
        return secondLineText;
    }

    /**
     * 设置第二行 Hint
     * @param text  文本
     */
    public void setSecondLineHint(CharSequence text){
        secondLineText.setHint(text);
    }

    /**
     * 设置第二行 Hint
     * @param resid 文本资源 id。
     */
    public void setSecondLineHint(int resid){
        secondLineText.setHint(resid);
    }

    /**
     * 设置第二行字体大小
     * @param unit  单位
     * @param size  字体大小。
     * @see TextView#setTextSize(int, float)
     */
    public void setSecondLineTextSize(int unit,float size){
        secondLineText.setTextSize(unit, size);
    }

    /**
     * 设置第二行字体颜色
     * @param color  颜色值。
     */
    public void setSecondLineTextColor(int color){
        secondLineText.setTextColor(color);
    }

    /**
     * 设置第二行 Hint 字体颜色
     * @param color  颜色值。
     */
    public void setSecondLineHintTextColor(int color){
        secondLineText.setHintTextColor(color);
    }

    /**
     * 设置第二行字体样式（正常、粗体、斜体 的组合值）
     * @param style  字体样式值。
     */
    public void setSecondLineTextStyle(int style){
        TextPaint paint = secondLineText.getPaint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, style));
    }

    /**
     * 设置第一行文本内容位置
     * @param gravity
     */
    public void setFirstLineTextGravity(int gravity) {

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //此处相当于布局文件中的Android:layout_gravity属性
        lp.gravity = gravity;
        firstLineText.setLayoutParams(lp);
        firstLineText.setGravity(gravity);
    }

    /**
     * 设置第二行文本内容位置
     * @param gravity
     */
    public void setSecondLineTextGravity(int gravity) {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity = gravity;
        secondLineText.setLayoutParams(lp);
        secondLineText.setGravity(gravity);
    }

    /**
     * 载入布局文件到当前控件、初始化变量。
     * @param attrs    AttributeSet 对象
     */
    @Override
    protected void init(AttributeSet attrs){
        //如果 rightArrow 为空表示目前处在Android Studio 的编辑模式中，Eclipse 的编辑模式是可以获取到元素对象的。
        if (attrs == null || firstLineText == null)
            return;

        //从 xml 布局文件读取控件属性设置
        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.TwoLineTextView);

        int     resid  = 0;
        int     color  = 0;
        int     style  = 0;
        float   margin = 0.0f;
        float   size   = 0.0f;
        boolean isTrue = false;
        String text   = "";
        int     firstLineGravity;
        int     secondLineGravity;

        int dp10 = Dimension.dip2px(10, getContext());
        int dp0  = 0;

        //属性 firstLineText
        text = ta.getString(R.styleable.TwoLineTextView_firstLineText);
        if (text != null){
        	setFirstLineText(text);
        }

        //属性 firstLineHint
        text = ta.getString(R.styleable.TwoLineTextView_firstLineHint);
        if (text != null){
            setFirstLineHint(text);
        }

        //属性 firstLineTextSize
        size = ta.getDimension(R.styleable.TwoLineTextView_firstLineTextSize, 0.0f);
        if (size != 0.0f){
            setFirstLineTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        }

        //属性 firstLineTextColor
        color = ta.getColor(R.styleable.TwoLineTextView_firstLineTextColor,0);
        if (color != 0){
            setFirstLineTextColor(color);
        }

        //属性 firstLineHintTextColor
        color = ta.getColor(R.styleable.TwoLineTextView_firstLineHintTextColor,0);
        if (color != 0){
            setFirstLineHintTextColor(color);
        }

        //属性 firstLineTextStyle
        style = ta.getInt(R.styleable.TwoLineTextView_firstLineTextStyle,0);
        setFirstLineTextStyle(style);

        //属性 firstLineTextMarginBottom
        margin = ta.getDimension(R.styleable.TwoLineTextView_firstLineTextMarginBottom,dp0);
        setFirstLineTextMarginBottom((int) margin);

        //属性 secondLineText
        text = ta.getString(R.styleable.TwoLineTextView_secondLineText);
        if (text != null){
            setSecondLineText(text);
        }

        //属性 secondLineHint
        text = ta.getString(R.styleable.TwoLineTextView_secondLineHint);
        if (text != null){
            setSecondLineHint(text);
        }
   
        //属性 secondLineTextSize
        size = ta.getDimension(R.styleable.TwoLineTextView_secondLineTextSize, 0.0f);
        if (size != 0.0f){
            setSecondLineTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        }

        //属性 secondLineTextColor
        color = ta.getColor(R.styleable.TwoLineTextView_secondLineTextColor,0);
        if (color != 0){
            setSecondLineTextColor(color);
        }

        //属性 secondLineHintTextColor
        color = ta.getColor(R.styleable.TwoLineTextView_secondLineHintTextColor,0);
        if (color != 0){
            setSecondLineHintTextColor(color);
        }

        //属性 secondLineTextStyle
        style = ta.getInt(R.styleable.TwoLineTextView_secondLineTextStyle,0);
        setSecondLineTextStyle(style);

        //设置第一行文字对齐方式
        firstLineGravity  = ta.getInt(R.styleable.TwoLineTextView_firstLineTextGravity, Gravity.LEFT);
        setFirstLineTextGravity(firstLineGravity);

        //设置第二行文字对齐方式
        secondLineGravity = ta.getInt(R.styleable.TwoLineTextView_secondLineTextGravity, Gravity.LEFT);
        setSecondLineTextGravity(secondLineGravity);


        ta.recycle();

        super.init(attrs);
    }

    @Override
    protected ViewGroup loadLayout(ViewGroup container) {
        ViewGroup vg = super.loadLayout(container);
        if (vg == null) return null;

        //从布局文件 l_twolinetextview.xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.l_twolinetextview,vg);

        firstLineText  = (TextView)  findViewById(R.id.first_line_text);
        secondLineText = (TextView)  findViewById(R.id.second_line_text);

        //继续返回  vg 充许在两行文本左侧添加其它组件
        return vg;
    }
}
