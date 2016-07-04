package com.xinran.qxviewslib;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by qixinh on 16/7/4.
 */
public class MoveSonLayoutActivity extends BaseActivity {
    LinearLayout layTop;
    Button btn;
    TextView tv;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movesonlayout);
        initViews();
    }

    private void initViews() {
        layTop=$(R.id.lay_top_move);
        btn=$(R.id.btn_top_move);
        tv=$(R.id.tv_move_bottom);
        final ObjectAnimator layAnimator=ObjectAnimator.ofFloat(layTop,"translationY",-300).setDuration(1000);//上移300
        final ObjectAnimator tvAnimator=ObjectAnimator.ofFloat(tv,"translationY",btn.getBottom()+300,btn.getBottom()).setDuration(1000);
        final ObjectAnimator layAnimator2=ObjectAnimator.ofFloat(layTop,"translationY",0).setDuration(1000);//哪来的回哪儿去
        final ObjectAnimator tvAnimator2=ObjectAnimator.ofFloat(tv,"translationY",300).setDuration(1000);//往下移动300
        tvAnimator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tv.setVisibility(View.GONE);//动画完成再消失，要不看不到动画效果
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    layAnimator.start();
                    tvAnimator.start();
                    tv.setVisibility(View.VISIBLE);
                    flag=true;
                }else{
                    layAnimator2.start();
                    tvAnimator2.start();
                    flag=false;
                }

            }
        });
    }
}
