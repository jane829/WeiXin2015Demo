package com.example.weixin2015.activity;
/**
 * 所有Activity的父类，完成所有Activity都要完成的一些任务
 */
import com.example.weixin2015.activity.util.NetWorkUtil;
import com.example.weixin2015.application.TApplication;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//每启动一个Activity,将其加入一个List集合;
		TApplication.add(this);
		//判断网络的连接的状态
		NetWorkUtil networkUtil = new NetWorkUtil();
		networkUtil.checkNetworkState(this);
		
	}


}
