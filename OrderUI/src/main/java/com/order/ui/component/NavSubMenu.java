package com.order.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.order.ui.R;
import com.order.ui.commom.Dimension;

import java.util.List;


/**
 * Created by LL on 13-12-31.
 */
public  class NavSubMenu {
    public enum Width{
        MATCH_PARENT,
        WRAP_CONTENT,
    }

    public enum Type{
        CENTER_POP,
        RIGHT_POP
    }

    private final int WIDTH = 200;
    private int  naviBarHeight = 0;
    private Context context;
    private View attachedView;
    private Width width;
    private PopupWindow popupWindow;
    private List<Option> options;
    private OnSubMenuOptionClickListener listener;
    private OnSubMenuOpenOrCloseListener  onSubMenuOpenOrCloseListener;
    private Type type;
    private int checkPosition  = -1 ;

    public NavSubMenu(View attachedView,
                      Width width,
                      int naviBarHeight,
                      List<Option> options,
                      OnSubMenuOptionClickListener listener,
                      OnSubMenuOpenOrCloseListener onSubMenuOpenOrCloseListener,
                      int checkPosition,
                      Type type){
        this.context                      = attachedView.getContext();
        this.attachedView                 = attachedView;
        this.width                        = width;
        this.options                      = options;
        this.type                         = type;
        this.naviBarHeight                = naviBarHeight;
        this.checkPosition                = checkPosition;
        this.popupWindow                  = initPopupWindow();
        this.listener                     = listener;
        this.onSubMenuOpenOrCloseListener = onSubMenuOpenOrCloseListener;
    }

    private PopupWindow initPopupWindow(){
        Resources res = context.getResources();


        ListView listView = new ListView(context);
        listView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new NavSubMenuAdapter());

        int w = width == Width.MATCH_PARENT ? WindowManager.LayoutParams.MATCH_PARENT : (int) (WIDTH * res.getDisplayMetrics().density);
        final PopupWindow window = new PopupWindow(context);

        if(type == Type.CENTER_POP){
/*
            LinearLayout linearLayout =new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            View bottomView =new View(context);
            bottomView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            bottomView.setFocusable(true);
            bottomView.requestFocus();

            linearLayout.addView(listView);
            linearLayout.addView(bottomView);

            bottomView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            window.setContentView(linearLayout);
            window.setBackgroundDrawable(res.getDrawable(R.color.gray_b2b2b2));
            listView.setBackgroundColor(context.getResources().getColor(R.color.white));
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int height = 0;
            if(Build.HARDWARE.contains("mx")){
                height = displayMetrics.heightPixels-Dimension.dip2px(84,context)-naviBarHeight-Dimension.dip2px(48,context);
            }else{
                height = displayMetrics.heightPixels-Dimension.dip2px(84,context)-naviBarHeight;
            }
            window.setHeight(height);*/
            window.setContentView(listView);
            window.setBackgroundDrawable(res.getDrawable(R.drawable.ui_jiaoyi_jilu_center_menu_bg));
            window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        }else if(type == Type.RIGHT_POP){
            window.setContentView(listView);
            window.setBackgroundDrawable(res.getDrawable(R.drawable.ui_jiaoyi_jilu_right_menu_bg));
            window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        }
        window.setWidth(w);
        window.setFocusable(true);              //设置PopupWindow可获得焦点
        window.setTouchable(true);              //设置PopupWindow可触摸
        window.setOutsideTouchable(true);       //设置非PopupWindow区域可触摸

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //PopupWindow关闭回调
                if(null != onSubMenuOpenOrCloseListener){
                    onSubMenuOpenOrCloseListener.close();
                }
            }
        });
        return window;
    }

    public static interface OnSubMenuOptionClickListener{
        void onSubMenuOptionClick(int position, Option option);
    }

    /**
     * PopWindow 打开或者关闭回调接口
     */
    public static interface OnSubMenuOpenOrCloseListener{
        public  void  open();
        public  void  close();
    }

    public void changeVisibility() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            if(type == Type.RIGHT_POP){
                int[] location = new int[2];
                attachedView.getLocationInWindow(location);
                popupWindow.showAsDropDown(attachedView, location[0]+attachedView.getWidth()/2, Dimension.dip2px(8,context));
                //2.3版本上需要刷新
                NavSubMenuAdapter adapter = (NavSubMenuAdapter)(((ListView)popupWindow.getContentView()).getAdapter());
                adapter.notifyDataSetChanged();
            }else if(type == Type.CENTER_POP){
              /*  DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                popupWindow.showAsDropDown(attachedView, 0,
                        -Dimension.dip2px(2, context));*/
                int x = (popupWindow.getWidth() - attachedView.getWidth()) / 2;
                popupWindow.showAsDropDown(attachedView,x,Dimension.dip2px(2, context));
                //2.3版本上需要刷新
                NavSubMenuAdapter adapter = (NavSubMenuAdapter)(((ListView)popupWindow.getContentView()).getAdapter());
                adapter.notifyDataSetChanged();
            }
            //PopupWindow打开回调
            if(null != onSubMenuOpenOrCloseListener){
                onSubMenuOpenOrCloseListener.open();
            }
        }
    }

    private class NavSubMenuAdapter extends BaseAdapter {
        private int checkedPosition = -1;

        public NavSubMenuAdapter(){
            this.checkedPosition = checkPosition;
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Option getItem(int position) {
            return options.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LinearLayout.LayoutParams p ;
            if (convertView == null){
                convertView         = LayoutInflater.from(context).inflate(R.layout.item_navsubmenu,null);
                holder              = new ViewHolder();
                holder.checkedFlag  = (ImageView) convertView.findViewById(R.id.ic_checked);
                holder.option       = (TextView) convertView.findViewById(R.id.tv_option);
                holder.root_linear  = (RelativeLayout)convertView.findViewById(R.id.item_navsubmenu_root_linear);
                p = (LinearLayout.LayoutParams) holder.root_linear.getLayoutParams();
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                p = (LinearLayout.LayoutParams) holder.root_linear.getLayoutParams();
            }
            final Option option = getItem(position);
            holder.checkedFlag.setVisibility(checkedPosition == position ? View.VISIBLE : View.INVISIBLE);
            holder.option.setText(option.description);
            int height = 0;
            RelativeLayout.LayoutParams checkParam = (RelativeLayout.LayoutParams) holder.checkedFlag.getLayoutParams();
            if(type == Type.RIGHT_POP){
                height = Dimension.dip2px(50, context);
                checkParam.setMargins(0,0,Dimension.dip2px(12,context),0);
            }else if(type == Type.CENTER_POP){
                height = Dimension.dip2px(45,context);
                checkParam.setMargins(0,0,Dimension.dip2px(24,context),0);
            }
            p.height = height;
            p.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            holder.root_linear.setLayoutParams(p);
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    checkedPosition = position;
                    popupWindow.dismiss();
                    listener.onSubMenuOptionClick(position,option);
                }
            });
            return convertView;
        }

        private class ViewHolder{
            ImageView icon;
            ImageView checkedFlag;
            TextView option;
            RelativeLayout root_linear;
        }
    }

    public static class Option {
        private String description;

        public String getDescription() {
            return description;
        }

        public Option(String description){
            this.description = description;
        }
    }
}
