package com.yjx.order.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yjx.order.R;

public class DateAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> lstDate;
	private TextView table_size;
	private TextView table_number;
	private TextView table_man_number;
	private TextView table_state;


	public DateAdapter(Context mContext, ArrayList<String> list) {
		this.context = mContext;
		lstDate = list;
	}

	@Override
	public int getCount() {
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		lstDate.add(startPosition, (String) endObject);
		lstDate.remove(startPosition + 1);
		lstDate.add(endPosition, (String) startObject);
		lstDate.remove(endPosition + 1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.activity_book_item, null);
		table_size = (TextView) convertView.findViewById(R.id.table_size);
		table_number = (TextView) convertView.findViewById(R.id.table_number);
		table_man_number = (TextView) convertView.findViewById(R.id.table_man_number);
		table_state = (TextView) convertView.findViewById(R.id.table_state);
//		if(lstDate.get(position)==null){
//			txtAge.setText("+");
//			txtAge.setBackgroundResource(R.drawable.mi_laucher_red);
//		}
//		else if(lstDate.get(position).equals("none")){
//			txtAge.setText("");
//			txtAge.setBackgroundDrawable(null);
//		}else

		table_number.setText(""+(Integer.parseInt(lstDate.get(position))+1));
		if(position%2==0){
			table_size.setText("小桌");
			table_man_number.setText("4人");

		}else{
			table_size.setText("中桌");
			table_man_number.setText("6人");
		}
		if(position%3==2){
			table_state.setText("忙碌");
			convertView.setBackgroundResource(R.color.table_state_busy);
		}else{
			table_state.setText("空闲");
		}

		return convertView;
	}

}
