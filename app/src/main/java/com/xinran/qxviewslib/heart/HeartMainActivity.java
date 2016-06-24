package com.xinran.qxviewslib.heart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xinran.qxviewslib.heart.view.CustomHeartContainer;

/**
 * Created by qixinh on 16/6/24.
 * 一个狂赞时的心形动画
 * 来自：http://www.jianshu.com/p/9423ca99c303
 */
public class HeartMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button button=new Button(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margen=dp2px(this, 100f);
        params.setMargins(margen, margen, margen, margen);
        button.setLayoutParams(params);
        button.setText("来颗心");
        linearLayout.addView(button);
        final CustomHeartContainer heart = new CustomHeartContainer(this);
        linearLayout.addView(heart);
        setContentView(linearLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.addHeart();
            }
        });
    }
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
