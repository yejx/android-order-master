package com.order.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.order.ui.R;

/**
 * Created by yjx on 14/11/29.
 */
public class BaseDialog extends DialogFragment {
    //Base dialog tag
    public static final String BASE_DIALOG = "base_dialog";
    //代理
    BaseDialogDelegate dialogDelegate;
    //根视图
    private View rootView;

    public BaseDialog(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(style(), theme());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return dialogDelegate != null && dialogDelegate.onKeyEvent(dialog, keyCode, event);
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (dialogDelegate != null) {
                    dialogDelegate.onShow(dialog);
                }
            }
        });
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.ui_base_dialog, container, false);
            if (customView() != null){
                if(((ViewGroup)rootView).getChildCount() != 0){
                    ((ViewGroup)rootView).removeAllViews();
                }
                ((ViewGroup)rootView).addView(customView());
            }
        }else {
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        if (dialogDelegate != null){
            dialogDelegate.onDestroy();
        }
        super.onDestroy();
    }

    /**
     * dialog style
     *
     * @return  style
     */
    int style() {
        return STYLE_NO_TITLE;
    }

    /**
     * dialog theme
     *
     * @return  theme
     */
    int theme() {
        return 0;
    }

    /**
     * custom view
     *
     * @return           view
     */
    View customView(){
        return null;
    }

    /**
     * show dialog
     *
     * @param manager   FragmentManager
     * @return          me
     */
    public BaseDialog show(FragmentManager manager){
        FragmentTransaction transaction = manager.beginTransaction();
        try {
            show(transaction, BASE_DIALOG);
        } catch (Exception ignored){
            //在show之前点击home键返回桌面会抛出异常
            //http://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html
        }
        return this;
    }

    /**
     * show dialog
     *
     * @param manager   FragmentManager
     * @param tag       dialog tag
     * @return          me
     */
    public void show(FragmentManager manager,String tag){
        FragmentTransaction transaction = manager.beginTransaction();
        try {
            show(transaction, tag);
        } catch (Exception ignored){
        }
    }

    /**
     * override DialogFragment#show(FragmentTransaction transaction,String tag)
     * replace commit() to commitAllowingStateLoss(can not set mDismissed,mShownByMe,mViewDestroyed,maybe have some problem ).
     * @param transaction
     * @param tag
     * @return
     */
    @Override
    public int show(FragmentTransaction transaction,String tag){

        transaction.add(this, tag);
        int mBackStackId = transaction.commitAllowingStateLoss();

        return mBackStackId;
    }

    @Override
    public void dismiss() {
        try {

            dismissAllowingStateLoss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * set dialog delegate
     *
     * @param dialogDelegate    BaseDialogDelegate
     * @return                  me
     */
    public BaseDialog setDialogDelegate(BaseDialogDelegate dialogDelegate){
        this.dialogDelegate = dialogDelegate;
        return this;
    }

    //Base Dialog Delegate
    public static class BaseDialogDelegate {

        /**
         * Called when a key is dispatched to a dialog. This allows listeners to
         * get a chance to respond before the dialog.
         *
         * @param dialog The dialog the key has been dispatched to.
         * @param keyCode The code for the physical key that was pressed
         * @param keyEvent The KeyEvent object containing full information about
         *            the event.
         * @return True if the listener has consumed the event, false otherwise.
         */
        public boolean onKeyEvent(DialogInterface dialog, int keyCode, KeyEvent keyEvent){
            return false;
        }

        /**
         * This method will be invoked when the dialog is shown.
         *
         * @param dialog The dialog that was shown will be passed into the
         *               method.
         */
        public void onShow(DialogInterface dialog) {}

        /**
         * This method will be invoked when the dialog is destroy
         */
        public void onDestroy (){}
    }
}
