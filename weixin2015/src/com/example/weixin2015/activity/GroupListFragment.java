package com.example.weixin2015.activity;

import java.util.zip.Inflater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.weixin2015.R;
import com.example.weixin2015.activity.util.LogUtil;
import com.example.weixin2015.application.TApplication;

public class GroupListFragment extends Fragment{
	private Button btn_add;
	private PopupWindow popupWindow;
	private RelativeLayout top;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.group_list, null);
		setupView(view);
		setListener();
		
		return view;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.i("info","onPause");
		//取消popWindow的显示
		popupWindow.dismiss();
	}
	private void setListener() {
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//				//跳转到添加好友界面
				View viewMore = View.inflate(getActivity(), R.layout.showmore, null);
				popupWindow =  new PopupWindow(viewMore, ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);

				popupWindow.showAtLocation(top, Gravity.RIGHT|Gravity.BOTTOM, 0, top.getHeight()+30);
				Button add = (Button) viewMore.findViewById(R.id.button1_show);
				add.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//给popupWindow的控件添加监听事件
						LogUtil.i("info", "单击添加好友");
						Intent intent = new Intent();
						intent.setClass(TApplication.instance, AddFriendActivity.class);
						startActivity(intent);
					}
				});
			}
		});

	}

	
	
	private void setupView(View view) {
		btn_add =(Button) view.findViewById(R.id.btn_group_add);
		top = (RelativeLayout) view.findViewById(R.id.top_group);
	}
}
