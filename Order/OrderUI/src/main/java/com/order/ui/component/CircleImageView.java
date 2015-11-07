package com.order.ui.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 圆形背景ImageView
 */
public class CircleImageView extends ImageView {
    private BitmapShader bitmapShader = null;
    private Bitmap bitmap = null;
    private ShapeDrawable shapeDrawable = null;
    private int width = 0;
    private int height = 0;


    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();


        bitmap = ((BitmapDrawable) getDrawable()).getBitmap();

        if (bitmap == null) return;

        if (width <= 0 || height <= 0) return;
        // 重新生成指定大小的图片
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height,
                true);

        if (bitmap == null) return;

        // 构造渲染器BitmapShader
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR,
                Shader.TileMode.REPEAT);

        // 将图片裁剪为椭圆形
        // 构建ShapeDrawable对象并定义形状为椭圆
        shapeDrawable = new ShapeDrawable(new OvalShape());
        // 得到画笔并设置渲染器
        shapeDrawable.getPaint().setShader(bitmapShader);
        // 设置显示区域
        shapeDrawable.setBounds(0, 0, width, height);
        // 绘制shapeDrawable
        shapeDrawable.draw(canvas);
    }


}