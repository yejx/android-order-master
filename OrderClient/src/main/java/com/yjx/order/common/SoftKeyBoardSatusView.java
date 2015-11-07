package com.yjx.order.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 *
 * 键盘监听类
 *
 * 该监听在类容不能滚动的情况下无效
 *
 */
public class SoftKeyBoardSatusView extends LinearLayout {

	private final int  CHANGE_SIZE=100;
	public SoftKeyBoardSatusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SoftKeyBoardSatusView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.i("demo", "w :" + w);
		Log.i("demo", "h :" + h);
		Log.i("demo", "oldw :" + oldw);
		Log.i("demo", "oldh :" + oldh);
		
		if(oldw==0||oldh==0)
			return;

        //根据布局尺寸大小变化进行相应的事件分发
		if(boardListener!=null)
		{
			boardListener.keyBoardStatus(w, h, oldw, oldh);
			if(oldw != 0 && h-oldh < -CHANGE_SIZE)
			{
				boardListener.keyBoardVisable(Math.abs(h - oldh));
			}
			
			if(oldw != 0 && h-oldh > CHANGE_SIZE)
			{
				boardListener.keyBoardInvisable(Math.abs(h - oldh));
			}
		}
	}

    /**
     * 键盘状态监听器
     */
	public interface SoftkeyBoardListener{
        /**
         * 键盘状态变化
         * @param w
         * @param h
         * @param oldw
         * @param oldh
         */
		public void keyBoardStatus(int w, int h, int oldw, int oldh);

        /**
         * 键盘显示回调
         * @param move
         */
		public void keyBoardVisable(int move);

        /**
         * 键盘隐藏回调
         * @param move
         */
		public void keyBoardInvisable(int move);
	}
	
	SoftkeyBoardListener boardListener;

    /**
     * 设置键盘监听器
     * @param boardListener
     */
	public void setSoftKeyBoardListener(SoftkeyBoardListener boardListener)
	{
		this.boardListener=boardListener;
	}
}
