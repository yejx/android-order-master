package com.order.ui.component;

import android.content.Context;
import android.widget.TextView;
import android.util.AttributeSet;

public class MyTextView extends TextView{       
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {    
        super(context, attrs, defStyle);    
    }    
     
    public MyTextView(Context context, AttributeSet attrs) {    
        super(context, attrs);    
    }    
     
    public MyTextView(Context context) {    
        super(context);    
    }    
      
    @Override    
    public boolean isFocused() {    
        return true;    
    }       
}  