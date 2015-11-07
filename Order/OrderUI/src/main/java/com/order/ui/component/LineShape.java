package com.order.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.order.ui.R;
import com.order.ui.commom.Dimension;

/**
 * 画线控件
 * \     |     /
 *   \   |   /
 *     \ | /
 * ------+------
 *     / | \
 *   /   |   \
 * /     |     \
 *
 */

public class LineShape extends View {
    private float lineWidth;    //线宽度
    private int orientation;    //画线方向：0 默认（水平），1 水平（-），2 垂直(|），4 左斜线（\),8 右斜线(/)
    private int type;           //线类型： 0 实线，1 虚线
    private int lineColor;
    private float dashEffect[]; //虚线线型
    private boolean antiAliased; //是否启用反锯齿

    public LineShape(Context context) {
        super(context);
        init(context,null);
    }

    public LineShape(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public LineShape(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        //取画布宽高
        float height = getHeight();
        float width  = getWidth();
        float horizontalCenter = width / 2.0f;
        float verticalCenter   = height / 2.0f;
        float lineWidth =  getLineWidth();

        //当线必须大于于1.0，否则可能画不出线。
        if (lineWidth < 1.0f) lineWidth = 1.0f;

        //根所 antiAliased值决定是否使用反锯齿画刷
        Paint paint = antiAliased ?
                new Paint(Paint.ANTI_ALIAS_FLAG) :
                new Paint() ;

        paint.setStyle(Paint.Style.STROKE);//画线

        paint.setStrokeWidth(lineWidth);

        //设置线颜色，如果为0 表示使用系统颜色。
        if (getLineColor() == 0){
            paint.setColor(getSolidColor());
        }
        else{
            paint.setColor(getLineColor());
        }

        if(getType() == 1){
            //设置虚线效果
            PathEffect effects = new DashPathEffect(dashEffect,1);
            paint.setPathEffect(effects);
        }
        
        int ori = getOrientation();
        //水平线
        if ((ori & 1) != 0)
            canvas.drawLine(0, verticalCenter, width, verticalCenter, paint);

        //垂直线
        if ((ori & 2) != 0)
            canvas.drawLine(horizontalCenter, 0, horizontalCenter, height, paint);

        //左斜线
        if ((ori & 4) != 0)
            canvas.drawLine(0, 0, width, height, paint);

        //右斜线
        if ((ori & 8) != 0)
            canvas.drawLine(width, 0, 0, height, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getMeasureSize(getLayoutParams().width,widthMeasureSpec, Dimension.dip2px(20, getContext()));
        int measuredHeight = getMeasureSize(getLayoutParams().height,heightMeasureSpec,Dimension.dip2px(10,getContext()));

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public boolean isAntiAliased() {
        return antiAliased;
    }

    public void setAntiAliased(boolean antiAliased) {
        this.antiAliased = antiAliased;

        //改变了反锯齿选项，需要重新绘制线段。
        invalidate();
    }

    /**
     * 获取虚线线型
     * @return 虚线线型
     * @see LineShape#setDashEffect(float[])
     */
    public float[] getDashEffect() {
        return dashEffect;
    }

    /**
     * 设置虚线线型，dashEffect 参数必须是一个2倍长度的数组，数组的第一个奇数元素表示实线长度，
     * 偶数元表示空白的长度。例如虚线线型“- - - -",对应的线型数组可以是 [5.0f,5.0f]。
     * @param dashEffect 线型数组
     */
    public void setDashEffect(float[] dashEffect) {
        this.dashEffect = dashEffect;
    }

    private void init(Context context, AttributeSet attrs){
        //默认线宽 1
        setLineWidth(1);

        //默认方向 水平
        setOrientation(1);

        //默认线型 实线
        setType(0);

        //设置默认颜色
        setLineColor(0);

        float dashedSolidLength = 5; //虚线的实线长，仅在 type = dashed 有效
        float dashedSpaceLength = 5; //虚线的空白长，仅在 type = dashed 有效

        dashEffect = new float[]{dashedSolidLength,dashedSpaceLength};

        if (attrs == null)
            return;

        //从 xml 布局文件读取控件属性设置
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineShape);

        int     iValue = 0;
        float   fValue = 0.0f;
        boolean bValue = false;

        //属性 width
        fValue = ta.getDimension(R.styleable.LineShape_lineWidth,1.0f);
        setLineWidth(fValue);

        //属性 orientation
        iValue = ta.getInt(R.styleable.LineShape_lineShapeOrientation,1);
        setOrientation(iValue);

        //属性 type
        iValue = ta.getInt(R.styleable.LineShape_type,0);
        setType(iValue);

        //属性 lineColor
        iValue = ta.getColor(R.styleable.LineShape_lineColor,0);
        setLineColor(iValue);

        //属性 dashedSolidLength
        fValue = ta.getDimension(R.styleable.LineShape_dashedSolidLength, 5.0f);
        dashedSolidLength = fValue;

        //属性 dashedSpaceLength
        fValue = ta.getDimension(R.styleable.LineShape_dashedSpaceLength,5.0f);
        dashedSpaceLength = fValue;

        //属性 antiAliased
        bValue = ta.getBoolean(R.styleable.LineShape_antiAliased,false);
        setAntiAliased(bValue);

        dashEffect = new float[]{dashedSolidLength,dashedSpaceLength};

        ta.recycle();
    }

    /**
     * 获取 Veiw 期望的大小
     * @param layoutSize      布局参数中的高或宽
     * @param MeasureSpecSize OnMeasure 事件中的高或宽
     * @param defaultSize     缺省大小
     * @return  测量结果
     */
    private int getViewDesiredSize(int layoutSize,int MeasureSpecSize,int defaultSize){
        int result;

        switch(layoutSize){
            case ViewGroup.LayoutParams.FILL_PARENT:
                //充满父大小，直接将宽度设为父充许的最大宽度
                result = MeasureSpec.getSize(MeasureSpecSize);
                break;

            case ViewGroup.LayoutParams.WRAP_CONTENT:
                //按内容大小，则设置成默认的 20dp 大小。
                result = Dimension.dip2px(defaultSize,getContext());
                break;

            default:
                //按布局参数设定的大小
                result = layoutSize;
                break;
        }

        return result;
    }

    /**
     * 获取测量结果
     * @param layoutSize      布局参数中的高或宽
     * @param MeasureSpecSize OnMeasure 事件中的高或宽
     * @param defaultSize     缺省大小
     * @return
     */
    private int getMeasureSize(int layoutSize, int MeasureSpecSize, int defaultSize){
        //测量模式
        int mode  = MeasureSpec.getMode(MeasureSpecSize);
        //view期望的大小
        int desiredSize = getViewDesiredSize(layoutSize, MeasureSpecSize, defaultSize);
        //缺省大小
        int result = defaultSize;
        //父指定的大小
        int specifiedSize = MeasureSpec.getSize(MeasureSpecSize);

        //按布局参数设定的大小
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                //父元素不对子元素施加任何约束约束，子元素可以得到任意想要的大小。
                result = desiredSize;
                break;

            case MeasureSpec.AT_MOST:
                //父元素决定子元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
                result = specifiedSize;
                break;

            case MeasureSpec.EXACTLY:
                //子元素至多达到指定大小的值。
                if (desiredSize <= specifiedSize ){
                    result = desiredSize;
                }
                else{
                    result = specifiedSize;
                }

                break;
        }

        return result;
    }
}
