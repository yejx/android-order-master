package com.order.ui.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.order.ui.R;


/**
 * Created by yjx on 14/11/30.
 */
public class ProgressDialog extends BaseDialog {

    private View me;

    private String   message;

    @Override
    int style() {
        return STYLE_NO_FRAME;
    }

    @Override
    View customView() {
        if (me != null){
            return me;
        }

        me = View.inflate(getActivity(), R.layout.ui_progress_dialog_layout, null);

        if (message != null && !message.equals("")) {
            TextView textView = (TextView) me.findViewById(R.id.progress_dialog_layout_message);
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
        }

        return me;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    /**
     * set progress message
     *
     * @param message  custom message
     * @return         me
     */
    public ProgressDialog setProgressMessage(String message){
        this.message = message;
        return this;
    }
}
