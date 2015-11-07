package com.order.ui.dialog;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.order.ui.R;

/**
 * Created by yjx on 15/7/16.
 */
public class ProgressBarDialog extends BaseDialog {

    private View me;
    //刷新进度条子线程控制标记，每个数字控制一个子线程的开关
    private int startflag=0;

    private String titleText;

    private String   messageText;

    private ProgressBar progressBar;

    private Button btn;

    private String btnText;
    //进度条文本
    private TextView middle;

    private ButtonCallBack listener;

    double start= 0,end= 0,second= 1000;

    @Override
    int theme() {
        return R.style.dialog_normal;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    View customView() {
        if (me != null){
            return me;
        }
        final ProgressBarDialog dialog = this;

        me = View.inflate(getActivity(), R.layout.ui_progressbar_dialog_layout, null);

        TextView title = (TextView) me.findViewById(R.id.progressbar_dialog_layout_title);
        middle = (TextView) me.findViewById(R.id.dialog_middle_text);
        progressBar = (ProgressBar) me.findViewById(R.id.progressBar);
        btn= (Button) me.findViewById(R.id.button);
        View ls_bottom= (View) me.findViewById(R.id.ui_dialog_bottom_divider);
        View ls_top= (View) me.findViewById(R.id.ui_dialog_top_divider);

        if (titleText!=null&&titleText!=""){
            title.setVisibility(View.VISIBLE);
            title.setText(titleText);
        }

        if (messageText!=null&&messageText!=""){
            middle.setVisibility(View.VISIBLE);
            middle.setText(messageText);
        }

        if (btnText!=null&&btnText!=""){
            btn.setVisibility(View.VISIBLE);
            ls_bottom.setVisibility(View.VISIBLE);
            btn.setText(btnText);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener!=null){
                        listener.onClick();
                    }
                    dialog.dismissAllowingStateLoss();
                }
            });
        }

        progressBar.setMax(1000);
        int pg= (int) (start*1000);
        progressBar.setProgress(pg);
//        progreText.setText((pg / 10) + "%");
        start(start, end, second);

        return me;
    }


    /**
     *
     * @param s 开始进度
     * @param e 结束进度
     * @param sen 动画时间
     */
    private void start(double s, final double e ,double sen) {

        if (s>=e){
            return;
        }

        startflag++;
        //每100毫秒的进度值
        final double increase= (double) ((e-s)*1000/sen*50);
        final double ss=s;

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=1 ,stopflag=startflag;
                while(stopflag ==startflag ) {
                    int current= (int) (ss*1000+i*increase);
                    if (current>start*1000){
                        progressBar.setProgress(current);
                    }
                    //执行完成后将完成值设为初始值
                    if (start<((double)current)/1000.0){
                        start=((double)current)/1000.0;
                    }
                    i++;
                    SystemClock.sleep(50);
                    if(progressBar.getProgress() >= (int)(e*1000)) {
                        break;
                    }

                }
            }
        }).start();
    }

    /**
     * 设置进度条消息
     * @param message 进度条文本信息
     * @return
     */
    public ProgressBarDialog setMessage(String message){
        if (me!=null){
            if (middle!=null){
                middle.setText(message);
            }
        }else {
            this.messageText = message;
        }
        return this;
    }

    /**
     * 设置对话框标题
     * @param title
     * @return
     */
    public ProgressBarDialog setTitle(String title){
        this.titleText = title;
        return this;
    }

    /**
     * 设置进度值
     * @param progress 取值0-1.0
     */
    public ProgressBarDialog setProgress(double progress){
        if (me!=null){
            int pro= (int) (progress*1000);
            if (progressBar!=null&&pro>=start){
                startflag++;
                progressBar.setProgress(pro);
            }
        }else {
            this.end=progress;
        }
        return this;
    }

    /**
     * 设置进度及动画完成时间
     * @param start 开始进度 取值0-1.0
     * @param end 结束进度 取值0-1.0
     * @param second 进度完成时间(豪秒)
     */
    public ProgressBarDialog setProgress(double start, double end, double second){
        setProgress(start);
        this.start=start;
        this.end=end;
        this.second=second;
        return this;
    }

    /**
     * 设置动画时间,单位：毫秒
     * @param second
     * @return
     */
    public ProgressBarDialog setTime(double second){
        this.second=second;
        return this;
    }

    /**
     * 设置底部按钮
     * @param text 按钮文本
     */
    public void setButton(String text){
        if(!(text=="")){
            this.btnText=text;
        }
    }

    /**
     * 刷新进度条从当前位置到指定位置用的毫秒数
     * @param progress 指定位置
     * @param second 所用时间，单位：毫秒
     */
    public void setProgressWithSmooth(double progress,double second){
        if (me!=null){
            start(start, progress, second);
        }else {
            setProgress(start,progress,second);
        }
    }

    /**
     * 设置按钮监听
     * @param listener
     */
    public void setListener(ButtonCallBack listener) {
        this.listener = listener;
    }

    /**
     * 按钮回调
     */
    public interface ButtonCallBack{
        public void onClick();
    }


}
