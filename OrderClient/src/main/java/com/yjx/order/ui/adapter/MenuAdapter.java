package com.yjx.order.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjx.order.R;
import com.order.ui.commom.Dimension;

import java.util.List;

public class MenuAdapter extends BaseAdapter{
	private Context context;
	private List<String> lstMenu;
	private int seletePositon = 0;

	public MenuAdapter(Context context, List<String> lstMenu) {
		super();
		this.context = context;
		this.lstMenu = lstMenu;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstMenu.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lstMenu.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LinearLayout.inflate(context, R.layout.listview_menu_item, null);
		TextView tvMenu= (TextView) convertView.findViewById(R.id.tvMenu);
		tvMenu.setText(lstMenu.get(position));
		tvMenu.setHeight(Dimension.dip2px(60,context));
		if(position==seletePositon){
			tvMenu.setBackgroundResource(R.color.white);
			tvMenu.setTextColor(context.getResources().getColor(R.color.nav_bg));
		}else{
			tvMenu.setBackgroundResource(R.color.gray_e6e7e9);
			tvMenu.setTextColor(context.getResources().getColor(R.color.font_gray));
		}

		return convertView;
	}

	public int getSeletePositon() {
		return seletePositon;
	}

	public void setSeletePositon(int seletePositon) {
		this.seletePositon = seletePositon;
	}
}
