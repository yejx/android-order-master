package com.order.ui.commom;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.order.ui.R;

public class Animations{
	Animation DelDown,DelUp;
	public Animation getDownAnimation(Context context){
		return AnimationUtils.loadAnimation(context, R.anim.mi_laucher_del_down);
	}
}