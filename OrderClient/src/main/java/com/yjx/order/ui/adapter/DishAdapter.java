package com.yjx.order.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjx.order.R;
import com.yjx.order.util.ImageUtil;

public class DishAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String,String>> lstDish,lstDishType;
	int num = 0;
	private UpdateOrderDetailListener updateOrderDetailListener;
	public class ViewHolder{
		private ImageView ivDishPhoto;
		private TextView tvDishName;
		private TextView tvOldDishPrice;
		private TextView tvDishPrice;
		private ImageView btnAddDish;
		private TextView etDishNumber;
		private ImageView btnDeleteDish;
	}
	
	public DishAdapter(Context context, List<Map<String, String>> lstDish,List<Map<String, String>> lstDishType,
					   UpdateOrderDetailListener updateOrderDetailListener) {
		super();
		this.context = context;
		this.lstDish = lstDish;
		this.lstDishType = lstDishType;
		this.updateOrderDetailListener = updateOrderDetailListener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstDishType.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lstDishType.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder vh = new ViewHolder();
//		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_dish_item, null);
			vh.ivDishPhoto = (ImageView) convertView.findViewById(R.id.ivDishPhoto);
			vh.tvDishName = (TextView) convertView.findViewById(R.id.tvDishName);
		    vh.tvOldDishPrice = (TextView) convertView.findViewById(R.id.tvOldDishPrice);
			vh.tvDishPrice = (TextView) convertView.findViewById(R.id.tvDishPrice);
			vh.btnAddDish = (ImageView) convertView.findViewById(R.id.btnAddDish);
			vh.etDishNumber = (TextView) convertView.findViewById(R.id.etDishNumber);
			vh.btnDeleteDish = (ImageView) convertView.findViewById(R.id.btnDeleteDish);
			convertView.setTag(vh);
//		}else{
//			vh = (ViewHolder)convertView.getTag();
//		}
		final Map<String, String> map = lstDishType.get(position);
		String url = map.get("photo").toString();
		vh.ivDishPhoto.setImageBitmap(ImageUtil.getBitmapInAssets(context, url));
		vh.tvDishName.setText(map.get("name"));
		vh.tvOldDishPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);//中间加下划线
		String format= "<font color='#d8000c'>%1$s</font>"+"/"+map.get("unit");
		String price = "¥"+map.get("price")+".00";
		vh.tvDishPrice.setText(Html.fromHtml(String.format(format,price)));
		vh.etDishNumber.setText(map.get("number"));
		vh.btnAddDish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				num = Integer.parseInt(vh.etDishNumber.getText().toString()) + 1;
				lstDishType.get(position).put("number", String.valueOf(num));
				lstDishType.get(position).put("order", map.get("name") + "\n" + num + "*" + map.get("price") + ".0" + map.get("currency"));
				int id = Integer.parseInt(lstDishType.get(position).get("id").toString()) - 1;
				lstDish.get(id).put("number", String.valueOf(num));
				lstDish.get(id).put("order", map.get("name") + "\n" + num + "*" + map.get("price") + ".0" + map.get("currency"));
				vh.etDishNumber.setText("" + num);
				updateOrderDetailListener.update();
			}
		});
        vh.btnDeleteDish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Integer.parseInt(vh.etDishNumber.getText().toString())>0) {
					num = Integer.parseInt(vh.etDishNumber.getText().toString()) - 1;
					lstDishType.get(position).put("number", String.valueOf(num));
					lstDishType.get(position).put("order", map.get("name") + "\n" + num + "*" + map.get("price") + ".0元");
					int id = Integer.parseInt(lstDishType.get(position).get("id").toString()) - 1;
					lstDish.get(id).put("number", String.valueOf(num));
					lstDish.get(position).put("order", map.get("name") + "\n" + num + "*" + map.get("price") + ".0元");
					vh.etDishNumber.setText("" + num);
					updateOrderDetailListener.update();
				}

			}
		});
		return convertView;
	}

	/**
	 * 更新消息数据
	 */
	public void updateDishType(List<Map<String,String>> lstDishType) {
		this.lstDishType = new ArrayList<>(lstDishType);
		notifyDataSetChanged();
	}

	public interface UpdateOrderDetailListener{
		public void update();
	}
}
