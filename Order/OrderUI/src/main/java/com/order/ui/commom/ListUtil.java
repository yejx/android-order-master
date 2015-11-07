package com.order.ui.commom;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by yjx on 14-1-17.
 */
public class ListUtil {

    /**
     * 在ScrollView中嵌套ListView
     * ListView的每个Item必须是LinearLayout，不能是其他的，因为其他的Layout(如RelativeLayout)没有重写onMeasure()，所以会在onMeasure()时抛出异常。
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        setHeightBaseOnDisplayChildren(listView, listAdapter.getCount());
    }

    /**
     * 根据需要显示的item个数，控制ListView的高度
     * @param listView
     * @param displayChildrenCount 需要显示的item个数，最后一个显示半个，其余未显示内容通过滑动显示
     */
    public static void setHeightBaseOnDisplayChildren(ListView listView,int displayChildrenCount){

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int itemCount = listAdapter.getCount();
        if (itemCount <= displayChildrenCount) return;

        View mView = listAdapter.getView(0,null,listView);
        mView.measure(0,0);

        int itemHeight = mView.getMeasuredHeight();
        int dividerHeight = listView.getDividerHeight();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (displayChildrenCount-1)*itemHeight + itemHeight/2 +displayChildrenCount*dividerHeight);

        listView.setLayoutParams(layoutParams);

    }
}
