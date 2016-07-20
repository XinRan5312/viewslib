package com.xinran.qxviewslib.customviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinran.qxviewslib.BaseActivity;
import com.xinran.qxviewslib.R;

/**
 * Created by qixinh on 16/7/16.
 */
public class TestCustomViewPagerActvity extends BaseActivity{
    private QxCustomViewpager mJazzy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qx_custom_viewpager);
        setupJazziness(QxCustomViewpager.TransitionEffect.Tablet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_custom_viewpager, menu);
        return true;
//        menu.add("Toggle Fade");
//        String[] effects = this.getResources().getStringArray(R.array.qx_effects);
//        for (String effect : effects)
//            menu.add(effect);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Toggle Fade")) {
            mJazzy.setFadeEnabled(!mJazzy.getFadeEnabled());
        } else {
            QxCustomViewpager.TransitionEffect effect = QxCustomViewpager.TransitionEffect.valueOf(item.getTitle().toString());
            setupJazziness(effect);
        }
        return true;
    }

    private void setupJazziness(QxCustomViewpager.TransitionEffect effect) {
        mJazzy = (QxCustomViewpager) findViewById(R.id.jazzy_pager);
        mJazzy.setTransitionEffect(effect);
        mJazzy.setAdapter(new MainAdapter());
        mJazzy.setPageMargin(30);
    }

    private class MainAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            TextView text = new TextView(TestCustomViewPagerActvity.this);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(30);
            text.setTextColor(Color.WHITE);
            text.setText("Page " + position);
            text.setPadding(30, 30, 30, 30);
            int bg = Color.rgb((int) Math.floor(Math.random()*128)+64,
                    (int) Math.floor(Math.random()*128)+64,
                    (int) Math.floor(Math.random()*128)+64);
            text.setBackgroundColor(bg);
            container.addView(text, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mJazzy.setObjectForPosition(text, position);
            return text;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mJazzy.findViewFromObject(position));
        }
        @Override
        public int getCount() {
            return 10;
        }
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }
    }
}

