package com.xinran.qxviewslib.fragments.statefragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class MyAdapter extends FragmentStatePagerAdapter {

	public MyAdapter(FragmentManager fm) {
		super(fm);
	}
	@Override
	public Fragment getItem(int position) {
		return ArrayListFragment.newInstance(position);
	}

	@Override
	public int getCount() {
		return StateMainActivity.NUM_ITEMS;
	}

}
