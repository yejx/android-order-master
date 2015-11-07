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
 * 左边两行，右边一行view类，具体布局如下面示意
 * 继承自IconItemView，扩展left_text_container布局
 *
 *  +--------------------------+
 *  | label1                   |
 *  |               label      |
 *  | label2                   |
 *  +--------------------------+
 */
public class TwoLineSingleLineTextView extends TwoLineTextView {
  private TextView centerTextView;  //中间文本


  public TwoLineSingleLineTextView(Context context) {
      super(context);
  }

  public TwoLineSingleLineTextView(Context context, AttributeSet attrs) {
      super(context, attrs);
  }

    /**
     * 设置中间文本
     * @param text  文本
     */
    public void setCenterText(CharSequence text){
        centerTextView.setText(text);
    }

    /**
     * 设置中间文本文本
     * @param resid 文本资源 id。
     */
    public void setCenterText(int resid){
        centerTextView.setText(resid);
    }

    /**
     * 设置中间文本 Hint
     * @param text  文本
     */
    public void setCenterHint(CharSequence text){
        centerTextView.setHint(text);
    }

    /**
     * 设置中间文本 Hint
     * @param resid 文本资源 id。
     */
    public void setCenterHint(int resid){
        centerTextView.setHint(resid);
    }

    /**
     * 设置中间文本字体大小
     * @param unit  单位
     * @param size  字体大小。
     * @see TextView#setTextSize(int, float)
     */
    public void setCenterTextSize(int unit,float size){
        centerTextView.setTextSize(unit,size);
    }

    /**
     * 设置中间文本字体颜色
     * @param color  颜色值。
     */
    public void setCenterTextColor(int color){
        centerTextView.setTextColor(color);
    }

    /**
     * 设置中间文本 Hint 字体颜色
     * @param color  颜色值。
     */
    public void setCenterHintTextColor(int color){
        centerTextView.setHintTextColor(color);
    }

    /**
     * 设置中间文本字体样式（正常、粗体、斜体 的组合值）
     * @param style  字体样式值。
     */
    public void setCenterTextStyle(int style){
        TextPaint paint = centerTextView.getPaint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, style));
    }

    /**
     * 设置中间文本下方的间隔值
     * @param margin
     */
    public void setCenterTextMarginLeft(int margin){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)centerTextView.getLayoutParams();
        params.leftMargin = margin;
        centerTextView.setLayoutParams(params);
    }

  /**
   * 载入布局文件到当前控件、初始化变量。
   * @param attrs    AttributeSet 对象
   */
  @Override
  protected void init(AttributeSet attrs){
      if (attrs == null)
          return;

      //从 xml 布局文件读取控件属性设置
      TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TwoLineSingleLineTextView);

      int     color  = 0;
      int     style  = 0;
      float   margin = 0.0f;
      float   size   = 0.0f;
      String text   = "";

      int dp10 = Dimension.dip2px(10, getContext());
      int dp0  = 0;

      //属性 centerText
      text = ta.getString(R.styleable.TwoLineSingleLineTextView_centerText);
      if (text != null){
          setCenterText(text);
      }

      //属性 centerHint
      text = ta.getString(R.styleable.TwoLineSingleLineTextView_centerHint);
      if (text != null){
          setCenterHint(text);
      }

      //属性 centerTextSize
      size = ta.getDimension(R.styleable.TwoLineSingleLineTextView_centerTextSize, 0.0f);
      if (size != 0.0f){
          setCenterTextSize(TypedValue.COMPLEX_UNIT_PX,size);
      }

      //属性 centerTextColor
      color = ta.getColor(R.styleable.TwoLineSingleLineTextView_centerTextColor,0);
      if (color != 0){
          setCenterTextColor(color);
      }

      //属性 centerHintTextColor
      color = ta.getColor(R.styleable.TwoLineSingleLineTextView_centerHintTextColor,0);
      if (color != 0){
          setCenterHintTextColor(color);
      }

      //属性 centerTextStyle
      style = ta.getInt(R.styleable.TwoLineSingleLineTextView_centerTextStyle,0);
      setCenterTextStyle(style);

      //属性 firstLineTextMarginBottom
      margin = ta.getDimension(R.styleable.TwoLineSingleLineTextView_centerTextMarginLeft,dp0);
      setCenterTextMarginLeft((int)margin);

      ta.recycle();

      super.init(attrs);
  }

  @Override
  protected ViewGroup loadLayout(ViewGroup container) {
      ViewGroup vg = super.loadLayout(container);
      if (vg == null) return null;

      centerTextView = new TextView(getContext());
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      centerTextView.setLayoutParams(params);

      vg.addView(centerTextView);

      return vg;
  }
}
