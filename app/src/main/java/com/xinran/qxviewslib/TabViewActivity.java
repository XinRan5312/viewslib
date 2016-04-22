package com.xinran.qxviewslib;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.xinran.qxviewslib.constants.QxTapConstants;
import com.xinran.viewslib.qxtabview.QxTabView;
import com.xinran.viewslib.qxtabview.adapter.QxBaseFragmentAdapter;
import com.xinran.viewslib.qxtabview.fragment.QxTabFragmentFactory;
import com.xinran.viewslib.qxtabview.fragment.QxTapBaseFragment;

import java.util.ArrayList;

/***
 * Created by qixinh on 16/4/15.
 */
public class TabViewActivity extends FragmentActivity {

    private QxTabView tabs;
    private ViewPager pager;

    private final ArrayList<String> list = new ArrayList<>();
    private final ArrayList<QxTapBaseFragment> fragments = new ArrayList<>();
    private QxBaseFragmentAdapter qxBaseFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qx_main_fragment);
        pager = (ViewPager) findViewById(R.id.pager_code);
        tabs = (QxTabView) findViewById(R.id.tabs_code);
        for (int i = 0; i < 5; i++) {
            list.add("Tab " + i);
            Bundle bundle = new Bundle();
            bundle.putString(QxTapConstants.TAB_TITLE_NAME, "Dynamic" + i);
            try {
                fragments.add(QxTabFragmentFactory.getFragmentWithCls(QxWebFragment.class, bundle));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        qxBaseFragmentAdapter = new QxBaseFragmentAdapter(getSupportFragmentManager(), list, fragments);
        pager.setAdapter(qxBaseFragmentAdapter);
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);
        setTabsValue();

    }


    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);

        // 设置Tab的分割线的颜色
        tabs.setDividerColor(getResources().getColor(R.color.color_80cbc4));
        // 设置分割线的上下的间距,传入的是dp
        tabs.setDividerPaddingTopBottom(12);
        //设置Tab的分割线为背景颜色或者是透明的就代表不显示分割线
//        tabs.setDividerColor(Color.parseColor("#00000000"));
        // 设置Tab底部线的高度,传入的是dp
        tabs.setUnderlineHeight(1);
        //设置Tab底部线的颜色
        tabs.setUnderlineColor(getResources().getColor(R.color.color_1A000000));

        // 设置Tab 指示器Indicator的高度,传入的是dp
        tabs.setIndicatorHeight(4);
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(getResources().getColor(R.color.color_45c01a));

        // 设置Tab标题文字的大小,传入的是dp
        tabs.setTextSize(16);
        // 设置选中Tab文字的颜色
        tabs.setSelectedTextColor(getResources().getColor(R.color.color_45c01a));
        //设置正常Tab文字的颜色
        tabs.setTextColor(getResources().getColor(R.color.color_C231C7));

        //  设置点击Tab时的背景色
        tabs.setTabBackground(R.drawable.background_tab);

        //是否支持动画渐变(颜色渐变和文字大小渐变)
        tabs.setFadeEnabled(true);
        // 设置最大缩放,是正常状态的0.3倍
        tabs.setZoomMax(0.3F);
    }

}
