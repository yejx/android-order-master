package com.order.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.order.ui.R;

/**
 * Created by wangchao on 14/11/30.
 */
public class AlertDialog extends ThreeRowDialog {

    private View        top,middle,bottom,underMessage;
    private ViewGroup   underMessageContainer;
    private ImageView   imageView;
    private TextView    titleView,messageView;
    private String      title,message;
    private int         topIcon;

    private Button[]  buttons;
    private boolean[] disabled;

    private String[] buttonsText;
    private int[]    buttonsResid;
    private float density;

    public static AlertDialog dialogWithOnlyOk(String title,String message){
        AlertDialog dlg = new AlertDialog();
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.setButtons(R.string.ui_certain);

        return dlg;
    }

    public static AlertDialog dialogWithOnlyCancel(String title,String message){
        AlertDialog dlg = new AlertDialog();
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.setButtons(R.string.ui_cancel);

        return dlg;
    }

    public static AlertDialog dialogWithCancelAndOk(String title,String message){
        AlertDialog dlg = new AlertDialog();
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.setButtons(R.string.ui_cancel,R.string.ui_certain);

        return dlg;
    }

    public static AlertDialog dialogWithCancelAndRetry(String title,String message){
        AlertDialog dlg = new AlertDialog();
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.setButtons(R.string.ui_cancel,R.string.ui_retry);

        return dlg;
    }

    public static AlertDialog dialogWithNoAndYes(String title,String message){
        AlertDialog dlg = new AlertDialog();
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.setButtons(R.string.ui_no, R.string.ui_yes);

        return dlg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        density =  getResources().getDisplayMetrics().density;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog()==null) return;

        int width =  getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int)(width * 0.85f);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    View topView() {
        if (TextUtils.isEmpty(title) && topIcon == 0) {
            return null;
        }

        if (top != null) {
            return top;
        }

        top = View.inflate(getActivity(), R.layout.ui_alert_top_dialog, null);

        imageView = (ImageView) top.findViewById(R.id.dialog_top_image);
        titleView = (TextView) top.findViewById(R.id.dialog_top_text);

        if (topIcon != 0) {
            imageView.setBackgroundResource(topIcon);
            imageView.setVisibility(View.VISIBLE);
        }

        if (title != null && !title.equals("")) {
            titleView.setText(title);
            if(topIcon != 0){
                titleView.setPadding((int) (density * 10), 0, 0, 0);
            }
        }

        return top;
    }

    @Override
    int theme() {
        return R.style.dialog_normal;
    }

    @Override
    View middleView() {
        if (middle != null) {
            return middle;
        }

        middle = View.inflate(getActivity(), R.layout.ui_alert_middle_dialog, null);
        messageView = (TextView) middle.findViewById(R.id.dialog_middle_text);
        if (message != null && !message.equals("")) {
            messageView.setText(message);
        }
        else{
            messageView.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(title) && topIcon == 0) {
            messageView.setPadding(0, (int)(density*30), 0, (int)(density*30));
        }else{
            messageView.setPadding(0, 0, 0, (int)(density*20));
        }

        underMessageContainer = (ViewGroup)middle.findViewById(R.id.dialog_middel_undermsg);
        if (underMessage != null){
            underMessageContainer.addView(underMessage);
        }

        return middle;
    }

    @Override
    View bottomView() {
        if (bottom != null) {
            return bottom;
        }

        bottom = View.inflate(getActivity(), R.layout.ui_alert_bottom_dialog, null);

        if (buttons != null && buttons.length != 0) {
            if (((ViewGroup) bottom).getChildCount() == buttons.length) {
                return bottom;
            }
            for (int i = 0; i < buttons.length; i++) {
                View btn = createButton(i);
                ((ViewGroup) bottom).addView(btn);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btn.getLayoutParams();
                params.width = 0;
                params.weight = 1;
                btn.setLayoutParams(params);
                View view = View.inflate(getActivity(), R.layout.ui_vertical_divider_line, null);
                ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params1.width  = 1;
                view.setLayoutParams(params1);
                if (i < buttons.length-1) ((ViewGroup) bottom).addView(view);
            }
        }
        return bottom;
    }

    /**
     * get button with text
     *
     * @param index index
     * @return me
     */
    private View createButton(final int index) {
        View view = View.inflate(getActivity(), R.layout.ui_alert_bottom_button_dialog, null);

        final AlertDialog dialog = this;
        buttons[index] = (Button) view.findViewById(R.id.dialog_bottom_button);

        if (this.buttonsResid != null){
            buttons[index].setText(this.buttonsResid[index]);
        }
        else{
            buttons[index].setText(this.buttonsText[index]);
        }

        buttons[index].setEnabled(!disabled[index]);

        buttons[index].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismissAllowingStateLoss();
                if (dialogDelegate != null && dialogDelegate instanceof AlertDialogDelegate) {
                    ((AlertDialogDelegate) dialogDelegate).onButtonClick(dialog, v, index);
                }
            }
        });

        return view;
    }

    /**
     * set title
     *
     * @param title title
     * @return me
     */
    public AlertDialog setTitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
        } else {
            this.title = title;
        }
        return this;
    }

    /**
     * set icon
     *
     * @param resourceId id
     * @return me
     */
    public AlertDialog setIcon(int resourceId) {
        if (imageView != null) {
            imageView.setBackgroundResource(resourceId);
        } else {
            this.topIcon = resourceId;
        }
        return this;
    }

    /**
     * set middle message
     *
     * @param message message
     * @return me
     */
    public AlertDialog setMessage(String message) {
        if (messageView != null){
            if (message == null || "".equals(message)){
                messageView.setText(message);
            }
            else{
                messageView.setText("");
            }
        }
        else {
            this.message = message;
        }
        return this;
    }

    /**
     * set buttons
     *
     * @param btnText button array
     * @return me
     */
    public AlertDialog setButtons(String ...btnText) {
        this.buttonsText  = btnText;
        this.buttonsResid = null;
        this.buttons = new Button[buttonsText.length];
        this.disabled = new boolean[buttonsText.length];
        return this;
    }

    /**
     * set buttons
     *
     * @param resid button array
     * @return me
     */
    public AlertDialog setButtons(int ...resid) {
        this.buttonsResid = resid;
        this.buttonsText  = null;
        this.buttons = new Button[buttonsResid.length];
        this.disabled = new boolean[buttonsResid.length];
        return this;
    }

    /**
     * 设置索引为 index 的 button 的文本
     *
     * @param text  文本
     * @param index 索引
     * @return me
     */
    public AlertDialog setButtonText(String text, int index) {
        if (buttons != null && buttons.length > index) {
            buttons[index].setText(text);
        }
        else if (dialogDelegate != null && dialogDelegate instanceof AlertDialogDelegate) {
            ((AlertDialogDelegate) dialogDelegate).onError(index + "", "您设置的按钮文本，索引越界!");
        }
        return this;
    }

    /**
     * 设置消息本下方的自定义 View，默认为 null。
     * @param view 自义 View
     * @return me
     */
    public AlertDialog setViewUnderMessage(View view){
        underMessage = view;
        if (underMessageContainer != null){
            underMessageContainer.removeAllViews();
            underMessageContainer.addView(underMessage);
        }
        return this;
    }

    /**
     * 设置按钮的启用状态
     * @param index 按钮索引（最左边按钮的索引值为 0）
     * @param enabled 是否启用
     * @return me
     */
    public AlertDialog setButtonEnable(int index,boolean enabled){
        if (buttons == null || index < 0 || index >= buttons.length){
            //索引越界
            return this;
        }

        if (buttons[index] != null){
            buttons[index].setEnabled(enabled);
        }

        disabled[index] = !enabled;

        return this;
    }

    /**
     * 获取按钮的启用状态
     * @param index 按钮索引（最左边按钮的索引值为 0）
     * @return 启用状态
     */
    public boolean buttonIsEnable(int index){
        if (buttons == null || index < 0 || index >= buttons.length){
            //索引越界
            return false;
        }

        if (buttons[index] == null){
            return !disabled[index];
        }
        else{
            return buttons[index].isEnabled();
        }
    }

    //alert dialog delegate
    public static class AlertDialogDelegate extends ThreeRowDialogDelegate {
        @Override
        public boolean onKeyEvent(DialogInterface dialog, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss();
                return false;
            }
            return super.onKeyEvent(dialog, keyCode, keyEvent);
        }

        /**
         * button click
         *
         * @param dialog 对话框实例
         * @param view   按钮视图
         * @param index  （最左边按钮的索引值为 0）
         */
        public void onButtonClick(AlertDialog dialog, View view, int index) {

        }

        /**
         * 发生异常时候会调用这个方法
         *
         * @param id   异常 id
         * @param data 异常数据
         */
        public void onError(String id, Object data) {
        }
    }
}
