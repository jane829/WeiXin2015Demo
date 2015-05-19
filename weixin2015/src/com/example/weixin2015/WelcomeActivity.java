package com.example.weixin2015;

import com.example.weixin2015.activity.BaseActivity;
import com.example.weixin2015.activity.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//启动进入欢迎页面，停留2秒进入登录页面
		new Thread(){
			public void run() {
				try {
					Log.i("info","WelcomeActivity thread="+Thread.currentThread().getId());
					sleep(2000);
					Intent intent = new Intent();
					intent.setClass(WelcomeActivity.this,LoginActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
