package com.order.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.order.ui.R;
import com.order.ui.commom.Dimension;

/**
 *  左侧label ,右侧为viewGroup容器
 *  +---------------------------+
 *  |[icon]lable viewGroup  [>] |
 *  +---------------------------+
 */
public class LabelItemView extends IconItemView{
    //标签文本
    private TextView label;

    public LabelItemView(Context context) {
        super(context);
    }

    public LabelItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置标签文本
     * @param text  文本
     */
    public void setLabelText(CharSequence text){
        label.setText(text);
    }

    /**
     * 设置标签文本
     * @param resid 文本资源 id。
     */
    public void setLabelText(int resid){
        label.setText(resid);
    }

    /**
     * 设置第标签字体大小
     * @param unit  单位
     * @param size  字体大小。
     * @see TextView#setTextSize(int, float)
     */
    public void setLabelTextSize(int unit,float size){
        label.setTextSize(unit,size);
    }

    /**
     * 设置第标签字体颜色
     * @param color  颜色值。
     */
    public void setLabelTextColor(int color){
        label.setTextColor(color);
    }

    /**
     * 设置第标签字体样式（正常、粗体、斜体 的组合值）
     * @param style  字体样式值。
     */
    public void setLabelTextStyle(int style){
        TextPaint paint = label.getPaint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, style));
    }

    /**
     * 设置标签文本的标签间隔值。
     * @param margin  像素为单位的间隔值。
     */
    public void setLabelTextMarginRight(int margin){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)label.getLayoutParams();
        params.rightMargin = margin;
        label.setLayoutParams(params);
    }

    public void setMinEms(int ems){
        label.setMinEms(ems);
    }

    public void setLabelMaxEms(int ems){
        label.setMaxEms(ems);
    }

    /**
     * 设置标签文本的对齐方式
     * @param gravity 对齐方式
     */
    public void setLabelGravity(int gravity){
        label.setGravity(gravity);
    }

    @Override
    protected void init(AttributeSet attrs) {
        if(attrs == null)
            return;

        int     color  = 0;
        int     style  = 0;
        int     iValue = 0;
        float   margin = 0.0f;
        float   size   = 0.0f;
        String text   = "";
        int dp10 = Dimension.dip2px(10, getContext());

        //从 xml 布局文件读取控件属性设置
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LabelItemView);

        //属性 labelText
        text = ta.getString(R.styleable.LabelItemView_labelText);
        if (text != null){
            setLabelText(text);
        }

        //属性 labelTextSize
        size = ta.getDimension(R.styleable.LabelItemView_labelTextSize, 0.0f);
        if (size != 0.0f){
            setLabelTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        }

        //属性 labelTextColor
        color = ta.getColor(R.styleable.LabelItemView_labelTextColor,0);
        if (color != 0){
            setLabelTextColor(color);
        }

        //属性 labelTextStyle
        style = ta.getInt(R.styleable.LabelItemView_labelTextStyle,0);
        setLabelTextStyle(style);

        //属性 labelTextMarginRight
        margin = ta.getDimension(R.styleable.LabelItemView_labelTextMarginRight,dp10);
        setLabelTextMarginRight((int) margin);

        //属性 minEms
        iValue = ta.getInt(R.styleable.LabelItemView_minEms, 0);
        setMinEms(iValue);

        //属性 maxEms
        iValue = ta.getInt(R.styleable.LabelItemView_maxEms,100);
        setLabelMaxEms(iValue);

        //属性 gravity, 默认左侧垂直居中对齐。
        iValue = ta.getInt(R.styleable.LabelItemView_gravity,0x13);
        setLabelGravity(iValue);

        ta.recycle();

        super.init(attrs);
    }

    /**
     * 载入标签布局
     * @param  container  UI元素的容器
     * @return 标签标签文本框的容器 (水平分布)
     */
    @Override
    protected ViewGroup loadLayout(ViewGroup container) {
        ViewGroup vg = super.loadLayout(container);
        label        = new TextView(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
        label.setLayoutParams(params);
        vg.addView(label);

        return vg;
    }
}
