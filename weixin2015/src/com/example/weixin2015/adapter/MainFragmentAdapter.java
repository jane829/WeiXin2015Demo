package com.example.weixin2015.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> list ;
	public MainFragmentAdapter(FragmentManager manager,List<Fragment> list) {
		super(manager);
		if(list!=null){
			this.list =list;
		}else{
			list = new ArrayList<Fragment>();
		}
	}

	@Override
	public Fragment getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

}
