package com.xinran.viewslib.qxtabview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.xinran.viewslib.qxtabview.fragment.QxTapBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qixinh on 16/4/15.
 */
public class QxBaseFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;

    private List<QxTapBaseFragment> fragments;

    public QxBaseFragmentAdapter(FragmentManager fm, ArrayList<String> list, List<QxTapBaseFragment> fragments) {
        super(fm);
        this.titles = list;
        this.fragments = fragments;

    }

    public QxBaseFragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    public void addDates(ArrayList<String> moreTiles, List<QxTapBaseFragment> moreFragments) {
        if (titles == null || titles.isEmpty()) {
            initDatas(moreTiles, moreFragments);
        } else {
            titles.addAll(moreTiles);
            fragments.addAll(moreFragments);
            notifyDataSetChanged();
        }
    }

    public void initDatas(ArrayList<String> initTiles, List<QxTapBaseFragment> initFragments) {
        this.titles = initTiles;
        this.fragments = initFragments;
        notifyDataSetChanged();

    }

    public void addOneDate(String title, QxTapBaseFragment fragment) {
        if (titles == null || titles.isEmpty()) {
            titles = new ArrayList<String>();
            fragments = new ArrayList<QxTapBaseFragment>();
        }
        titles.add(title);
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles==null?"":titles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;//notify  管用
    }

    @Override
    public Object instantiateItem(View container, int position) {
        return super.instantiateItem(container, position);

    }

    @Override
    public int getCount() {
        return titles==null?0:titles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments==null?null:fragments.get(position);
    }

}
