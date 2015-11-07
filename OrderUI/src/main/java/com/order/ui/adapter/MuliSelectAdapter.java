package com.order.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.order.ui.R;
import com.order.ui.commom.CommmonSelectData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Blues on 14-1-17.
 */
public class MuliSelectAdapter extends BaseAdapter{

    Map<Integer, Boolean> map;

    LayoutInflater mInflater;

    private ArrayList<CommmonSelectData> commmonSelectDatas = new ArrayList<CommmonSelectData>();

    public void setMap(Map<Integer, Boolean> map) {
        this.map = map;
    }

    public Map<Integer, Boolean> getMap() {
        return map;
    }

    public MuliSelectAdapter(Context context, ArrayList<CommmonSelectData> datas) {
        map = new HashMap<Integer, Boolean>();
        mInflater = LayoutInflater.from(context);
        if(datas != null && datas.size() > 0){
            commmonSelectDatas = datas;
            for(int i = 0; i < datas.size(); i++) {
                map.put(i,false);
            }
            for(int i = 0; i < datas.size(); i++) {
                if (datas.get(i).isSelected()){
                    map.put(i,true);
                }
            }
        }
    }

    @Override
    public int getCount() {
        if(null == commmonSelectDatas){
            return 0;
        }
        return commmonSelectDatas.size();
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
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.ui_muti_select_item, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.multiple_title);
        title.setText((String) commmonSelectDatas.get(position).getLeftTopText());

        TextView summary = (TextView) convertView.findViewById(R.id.multiple_summary);
        summary.setText((String) commmonSelectDatas.get(position).getLeftBottomText());

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.multiple_checkbox);
        checkBox.setChecked(map.get(position));

        return convertView;
    }

}