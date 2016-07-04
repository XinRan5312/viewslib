package com.xinran.qxviewslib;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by qixinh on 16/6/29.
 */
public class TestSeekBarActivity extends Activity {
    RelativeLayout relativeLayout;
    LinearLayout layoutOne;
    LinearLayout layout2;
    boolean b = true;
    TextView num;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        relativeLayout = (RelativeLayout) findViewById(R.id.view_container);
        layoutOne = (LinearLayout) findViewById(R.id.lay_one);
        layout2 = (LinearLayout) findViewById(R.id.lay_two);
        num = (TextView) findViewById(R.id.tv_num);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                num.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                num.setVisibility(View.VISIBLE);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                num.setVisibility(View.GONE);
            }
        });
        float ll = layout2.getLayoutParams().height;
        final ObjectAnimator out = ObjectAnimator.ofFloat(layout2, "translationY", ll, 0f);
        final ObjectAnimator in = ObjectAnimator.ofFloat(layout2, "translationY", 0f, ll);
        out.setDuration(200);
        in.setDuration(200);
        findViewById(R.id.btn_seekbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet intoAdjustModeAnimatorSet = new AnimatorSet();
                if (b) {

                    b = false;
                    intoAdjustModeAnimatorSet.play(out);
                    layout2.setVisibility(View.VISIBLE);
                    num.setVisibility(View.VISIBLE);
//                    int h = relativeLayout.getLayoutParams().height;
//
//                    relativeLayout.getLayoutParams().height = h + h * 2;
                } else {
//                    int h = relativeLayout.getLayoutParams().height;
//
//                    relativeLayout.getLayoutParams().height = h - h * 2/3;
                    b = true;
                    intoAdjustModeAnimatorSet.play(in);
                    layout2.setVisibility(View.GONE);
                    num.setVisibility(View.GONE);
                }
                intoAdjustModeAnimatorSet.start();


            }
        });
    }
}
