package com.xinran.qxviewslib.fragments.statefragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.xinran.qxviewslib.R;

public class StateMainActivity extends FragmentActivity {
	public static final int NUM_ITEMS = 10;
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private Button button_first, button_last;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_pager);

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		button_first = (Button) findViewById(R.id.goto_first);
		button_first.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPager.setCurrentItem(0);
			}
		});

		button_last = (Button) findViewById(R.id.goto_last);
		button_last.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPager.setCurrentItem(NUM_ITEMS - 1);
			}
		});

	}

}
