package com.xinran.qxviewslib.fragments.statefragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinran.qxviewslib.R;

public class ArrayListFragment extends ListFragment {
	private int mNum;
	public ArrayList<String> list = new ArrayList<String>();

	public static ArrayListFragment newInstance(int num) {

		ArrayListFragment f = new ArrayListFragment();
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pager_list, container,
				false);
		TextView tv = (TextView) view.findViewById(R.id.text);
		tv.setText("Fragment #" + mNum);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getData()));
	}

	private ArrayList<String> getData() {
		for (int i = 0; i < 20; i++) {
			list.add("nihao" + i);
		}
		return list;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(getActivity(), "ddd"+position, Toast.LENGTH_SHORT).show();
	}

}
