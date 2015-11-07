package com.order.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.order.ui.R;
import com.order.ui.commom.CommmonSelectData;
import com.order.ui.component.TwoLineSingleLineTextView;

import java.util.ArrayList;

/**
 * 通用选择列表，左边(可含图标)文字，右边选中图片标识
 * @author SeaZhang
 *
 */
public class CommonSelectListAdapter extends BaseAdapter {

	private int selectposition = -1;
	private ArrayList<CommmonSelectData> datas = new ArrayList<CommmonSelectData>();
    private ArrayList<Integer> drawableIds = new ArrayList<Integer>();
    private int rightDrawableId = 0;
	private Context mContext;
	private boolean isShowRirhtArrow = false;
    private boolean isShowRightSelectImg =false;
    private boolean isOnlyShowSelectedImage =false;

	public void setSelectposition(int selectposition) {
		this.selectposition = selectposition;
		notifyDataSetChanged();
	}

    public int getSelectposition() {
        return selectposition;
    }

    /**
     *
     * @param context
     * @param dataLists          列表数据
     * */
     public CommonSelectListAdapter(Context context,ArrayList<CommmonSelectData> dataLists) {
		this.mContext = context;
		this.datas = dataLists;
        this.isShowRirhtArrow = false;
        this.isShowRightSelectImg =false;
	}

    /**
     *选中列表
     * @param context
     * @param dataLists          列表数据
     * @param leftIconIds     左侧图标资源id列表
     * @param isShowSelectImg            是否显示右侧选中图片
     * @param isShowRightArrow          是否显示右侧箭头
     */
	public CommonSelectListAdapter(Context context,ArrayList<CommmonSelectData> dataLists,ArrayList<Integer> leftIconIds,boolean isShowRightArrow,boolean isShowSelectImg,boolean isOnlyShowSelectedImage) {
        this(context,dataLists,leftIconIds,0,isShowRightArrow,isShowSelectImg,isOnlyShowSelectedImage);
	}

    /**
     *选中列表
     * @param context
     * @param dataLists          列表数据
     * @param leftIconIds     左侧图标资源id列表
     * @param rightDrawableId  右侧图片资源id
     * @param isShowSelectImg            是否显示右侧选中图片
     * @param isShowRightArrow          是否显示右侧箭头
     */
	public CommonSelectListAdapter(Context context,ArrayList<CommmonSelectData> dataLists,ArrayList<Integer> leftIconIds,int rightDrawableId,boolean isShowRightArrow,boolean isShowSelectImg,boolean isOnlyShowSelectedImage) {
		this.mContext = context;
		this.datas = dataLists;
        this.drawableIds = leftIconIds;
        this.rightDrawableId = rightDrawableId;
        this.isShowRightSelectImg = isShowSelectImg;
        this.isOnlyShowSelectedImage = isOnlyShowSelectedImage;
        this.isShowRirhtArrow = isShowRightArrow;
        if (datas!=null){
            for (int i =0;i<datas.size();i++){
                if (datas.get(i).isSelected()){
                    setSelectposition(i);
                }
            }
        }
	}

	@Override
	public int getCount() {
		
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		
		return position;
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView ==null) {
			convertView = LinearLayout.inflate(mContext, R.layout.ui_common_select_list_item, null);
			holder = new Holder();
            holder.item = (TwoLineSingleLineTextView)convertView.findViewById(R.id.id_item);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
        if (drawableIds!=null && drawableIds.size()>0){
            holder.item.setLeftIconDrawable(mContext.getResources().getDrawable(drawableIds.get(position)));
        }
        holder.item.setId(position);
        holder.item.setFirstLineText(datas.get(position).getLeftTopText());
        if (datas.get(position).getLeftBottomText().equals("")){
            holder.item.setSecondLineVisibility(View.GONE);
        }
        holder.item.setSecondLineText(datas.get(position).getLeftBottomText());
        holder.item.setSecondLineTextColor(mContext.getResources().getColor(R.color.l_gray));
        holder.item.setRightText(datas.get(position).getRightText());
        holder.item.setCenterText(datas.get(position).getCenterText());

		if (isShowRightSelectImg){
            holder.item.setRightIconVisibility(View.VISIBLE);
            if (position == selectposition) {
                if (rightDrawableId!=0){
                    holder.item.setRightIconDrawable(mContext.getResources().getDrawable(rightDrawableId));

                }else {
                    holder.item.setRightIconDrawable(mContext.getResources().getDrawable(R.drawable.ui_choose_done));
                }
            }else {
                if (isOnlyShowSelectedImage){
                    holder.item.setRightIconDrawable(null);
                }else {
                    if (rightDrawableId !=0){
                        holder.item.setRightIconDrawable(null);
                    }else {
                        holder.item.setRightIconDrawable(mContext.getResources().getDrawable(R.drawable.ui_choose_cancel));
                    }
                }
            }
        }else {
            holder.item.setRightIconVisibility(View.GONE);
        }
        if (isShowRirhtArrow){
            holder.item.setRightArrowVisibility(View.VISIBLE);
        }else {
            holder.item.setRightArrowVisibility(View.GONE);
        }

		return convertView;
	}

	public class Holder {
        private TwoLineSingleLineTextView item;
	}
}
