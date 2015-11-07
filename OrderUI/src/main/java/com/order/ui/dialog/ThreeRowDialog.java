package com.order.ui.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.order.ui.R;

/**
 * Created by yjx on 14/11/30.
 */
public class ThreeRowDialog extends BaseDialog {

    private View me;

    private FrameLayout top,
                        middle,
                        bottom;

    @Override
    View customView() {

        if (me != null){
            return me;
        }
        me      = View.inflate(getActivity(), R.layout.ui_threerow_dialog, null);

        top     = (FrameLayout) me.findViewById(R.id.top_container);
        middle  = (FrameLayout) me.findViewById(R.id.middle_container);
        bottom  = (FrameLayout) me.findViewById(R.id.bottom_container);
        View topDivider = me.findViewById(R.id.ui_dialog_top_divider);

        if (background() != 0){
            me.setBackgroundResource(background());
        }

        if (topView() != null){
            top.setVisibility(View.VISIBLE);
            if (top.getChildCount() != 0){
                top.removeAllViews();
            }
            top.addView(topView());
        } else {
            top.setVisibility(View.GONE);
            topDivider.setVisibility(View.GONE);
        }

        if (middleView() != null){
            middle.setVisibility(View.VISIBLE);
            if (middle.getChildCount() != 0){
                middle.removeAllViews();
            }
            middle.addView(middleView());
        } else {
            middle.setVisibility(View.GONE);
        }

        if (bottomView() != null){
            bottom.setVisibility(View.VISIBLE);
            if (bottom.getChildCount() != 0){
                bottom.removeAllViews();
            }
            bottom.addView(bottomView());
        } else {
            bottom.setVisibility(View.GONE);
        }

        return me;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogDelegate != null && dialogDelegate instanceof ThreeRowDialogDelegate){
                    ((ThreeRowDialogDelegate) dialogDelegate).onTopClick(v);
                }
            }
        });

        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogDelegate != null && dialogDelegate instanceof ThreeRowDialogDelegate){
                    ((ThreeRowDialogDelegate) dialogDelegate).onMiddleClick(v);
                }
            }
        });

        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogDelegate != null && dialogDelegate instanceof ThreeRowDialogDelegate){
                    ((ThreeRowDialogDelegate) dialogDelegate).onBottomClick(v);
                }
            }
        });
    }

    /**
     * background
     *
     * @return  image id
     */
    int background(){
        return 0;
    }

    /**
     * top view
     *
     * @return view
     */
    View topView(){
        return null;
    }

    /**
     * middle view
     *
     * @return view
     */
    View middleView(){
        return null;
    }

    /**
     * bottom view
     *
     * @return view
     */
    View bottomView(){
        return null;
    }

    //Three row dialog delegate
    public static class ThreeRowDialogDelegate extends BaseDialogDelegate {

        /**
         * This method will be invoked when the top view clicked
         *
         * @param view click view
         */
        public void onTopClick(View view){}

        /**
         * This method will be invoked when the middle view clicked
         *
         * @param view click view
         */
        public void onMiddleClick(View view){}

        /**
         * This method will be invoked when the bottom view clicked
         *
         * @param view click view
         */
        public void onBottomClick(View view){}
    }
}
