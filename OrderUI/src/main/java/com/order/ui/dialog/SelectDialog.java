package com.order.ui.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.order.ui.R;
import com.order.ui.adapter.CommonSelectListAdapter;
import com.order.ui.adapter.MuliSelectAdapter;
import com.order.ui.commom.CommmonSelectData;
import com.order.ui.commom.ListUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Michael on 15-4-3.
 */
public class SelectDialog extends AlertDialog{
    private boolean     mMultiSelect;
    private ListView    mListView;
    private ArrayList<CommmonSelectData> mData;
    private final static int maxLines = 6;
    private int seleteIndex = 0;//单选时设置选中的位置

    /**
     * 选择对话框
     *
     * 多选框拥有两个按钮“取消”，”确定“，调用者需要在”确定“（索引为1）按钮回调中处理选择数据。
     * 单选框拥有一个“取消”按钮，当用户选择了列表中的项时，会触发 onButtonClick 回调，
     * 其中 view 参数为空，index 值为 1，调用者需要在回调中处理选择数据。
     *
     * @param multiSelect  是否是多选
     * @param data  选择项数据
     */
    public SelectDialog(boolean multiSelect,ArrayList<CommmonSelectData> data){
        super();
        mData = data;
        mMultiSelect = multiSelect;
    }
    /**
    * @param multiSelect  是否是多选
    * @param data  选择项数据
    */
    public SelectDialog(boolean multiSelect,ArrayList<CommmonSelectData> data,int seleteIndex){
        super();
        mData = data;
        mMultiSelect = multiSelect;
        this.seleteIndex = seleteIndex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mMultiSelect){
            initMultiSelect(mData);
        }
        else{
            initSingleSelect(mData);
        }

        setViewUnderMessage(mListView);
    }

    public ArrayList<Integer> getSelected(){
        ArrayList<Integer> selectIndexs = new ArrayList<Integer>();

        if (mMultiSelect){
            Map<Integer,Boolean> map = ((MuliSelectAdapter) mListView.getAdapter()).getMap();

            Set<Integer> keys = map.keySet();
            for (int key : keys){
                if(map.get(key)) selectIndexs.add(key);
            }

        }
        else{
            int position = ((CommonSelectListAdapter) mListView.getAdapter()).getSelectposition();
            if ( position!= -1)
                selectIndexs.add(position);
        }

        return selectIndexs;
    }

    private void initMultiSelect(final ArrayList<CommmonSelectData> data){
        LinearLayout layout =(LinearLayout) LinearLayout.inflate(getActivity(), R.layout.ui_select_list_dialog, null);
        mListView = (ListView)layout.findViewById(R.id.id_data_list);
        layout.removeAllViews();

        final MuliSelectAdapter adapter = new MuliSelectAdapter(getActivity(),data);
        mListView.setAdapter(adapter);

        int lines = data.size();
        if (lines > maxLines){
            lines = maxLines;
        }
        ListUtil.setHeightBaseOnDisplayChildren(mListView, lines);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.multiple_checkbox);
                checkBox.toggle();
                String mergeFlag = data.get(i).getMergeFlag();
                if (mergeFlag != null) {
                    if (mergeFlag.equals("0")) {
                        //不可合并，单选
                        for (int j = 0; j < data.size(); j++) {
                            adapter.getMap().put(j, false);
                        }
                    }
                    else {//可合并项目，可多选，不能和不可合并选项同时使用
                        for (int j = 0; j < data.size(); j++) {
                            if (data.get(j).getMergeFlag().equals("0"))
                                adapter.getMap().put(j, false);
                        }
                    }
                }
                adapter.getMap().put(i, checkBox.isChecked());
                adapter.notifyDataSetChanged();
            }
        });

        setButtons(R.string.ui_cancel,R.string.ui_certain);
    }

    private void initSingleSelect(ArrayList<CommmonSelectData> data) {
        LinearLayout layout =(LinearLayout) LinearLayout.inflate(getActivity(), R.layout.ui_select_list_dialog, null);
        mListView = (ListView)layout.findViewById(R.id.id_data_list);
        layout.removeAllViews();
        final CommonSelectListAdapter adapter = new CommonSelectListAdapter(getActivity(),data,null,false,true,false);
        mListView.setAdapter(adapter);

        int lines = data.size();
        if (lines > maxLines){
            lines = maxLines;
        }

        ListUtil.setHeightBaseOnDisplayChildren(mListView, lines);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectposition(i);
                if (dialogDelegate != null) {
                    ((AlertDialogDelegate) dialogDelegate).onButtonClick(SelectDialog.this, null, 1);
                }
            }
        });

        setButtons(R.string.ui_cancel);
        View v = mListView.getChildAt(seleteIndex);
        int top = (v == null) ? 0 : v.getTop();
        mListView.setSelectionFromTop(seleteIndex, top);
    }
}
