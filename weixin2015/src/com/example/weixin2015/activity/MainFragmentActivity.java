package com.example.weixin2015.activity;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.example.weixin2015.R;
import com.example.weixin2015.adapter.MainFragmentAdapter;

public class MainFragmentActivity extends FragmentActivity implements OnClickListener{
	private ViewPager pager;
	private List<Fragment> list;
	private int currentIndex = 0;
	private Button[] btnAry;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main);
		setupView();
		setListener();
		setData();
	}

	private void setData() {
		list = new ArrayList<Fragment>();
		list.add(new GroupListFragment());
		list.add(new InputRoomFragment());
		list.add(new MessageFragment());
		list.add(new MoreFragment());
		MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), list);
		pager.setAdapter(adapter);
		updateBtnColor();
	}



	private void updateBtnColor() {
		for(int i=0;i<btnAry.length;i++)
		{
			if (i==this.currentIndex)
			{
				btnAry[i].setTextColor(0xFFFFFFFF);
			}else
			{
				btnAry[i].setTextColor(0xFF000000);
			}
		}
	}

	private void setListener() {
		for(int i=0;i<btnAry.length;i++){
			btnAry[i].setOnClickListener(this);
		}
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				//字体变色
				//界面显示发生变化
				currentIndex = index;
				updateBtnColor();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}

			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}

	private void setupView() {
		pager = (ViewPager) findViewById(R.id.viewPager_main);
		btnAry = new Button[4];
		btnAry[0] = (Button) findViewById(R.id.btn_main_groupList);
		btnAry[1] = (Button) findViewById(R.id.btn_main_groupChat);
		btnAry[2] = (Button) findViewById(R.id.btn_main_message);
		btnAry[3] = (Button) findViewById(R.id.btn_main_more);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_main_groupChat://群聊
			this.currentIndex = 0;
			break;
		case R.id.btn_main_groupList://好友
			this.currentIndex = 1;
			break;
		case R.id.btn_main_message://消息
			this.currentIndex = 2;
			break;
		case R.id.btn_main_more://更多
			this.currentIndex = 3;
			break;
		}
		pager.setCurrentItem(currentIndex);
		updateBtnColor();
	}

}
