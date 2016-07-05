package com.xinran.qxviewslib.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xinran.qxviewslib.R;

/**
 * Created by qixinh on 16/7/4.
 */
public class CustomTitleBarView extends LinearLayout{
    private EditText edit;
    private ImageView img;
    public CustomTitleBarView(Context context) {
        this(context,null);

    }

    public CustomTitleBarView(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }

    public CustomTitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        View.inflate(context, R.layout.view_customtitlebar,this);
        edit= (EditText) findViewById(R.id.custom_edit);
        img= (ImageView) findViewById(R.id.custom_img);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    img.setVisibility(VISIBLE);
                }else{
                    img.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
