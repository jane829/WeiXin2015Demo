package com.example.weixin2015.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weixin2015.R;
import com.example.weixin2015.activity.bll.LoginBiz;
import com.example.weixin2015.activity.util.GloableConst;
import com.example.weixin2015.application.TApplication;

public class LoginActivity extends BaseActivity {
	private Button register;
	private Button login;
	private TextView userName;
	private TextView password;

	private LoginReceiver receiver;

	class LoginReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//获取登录的信息
			
			
			boolean isSuccess = intent.getBooleanExtra(GloableConst.KEY_IS_LOGIN_SUCCESS,
					false);
			if(isSuccess){
				Toast.makeText(context, "登录成功",Toast.LENGTH_SHORT).show();
				//跳转至主framement界面
				Intent intent2 = new Intent();
				intent2.setClass(context,MainFragmentActivity.class ); 
				startActivity(intent2);
			}else{
				Toast.makeText(context, "登录失败",Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setupView();
		addListener();
		//注册广播
		receiver = new LoginReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(GloableConst.ACTION_LOGIN);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//注销广播
		unregisterReceiver(receiver);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		//适配屏幕的横竖屏的切换
	}

	private void setupView() {
		register = (Button) findViewById(R.id.btn_login_register);
		login = (Button) findViewById(R.id.btn_login_submit);
		userName = (TextView) findViewById(R.id.et_login_username);
		password = (TextView) findViewById(R.id.et_login_password);
	}

	private void addListener() {
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取输入的用户名密码
				Intent intent2 = new Intent();
				intent2.setClass(LoginActivity.this,MainFragmentActivity.class ); 
				startActivity(intent2);
				String user = userName.getText().toString();
				String pwd = password.getText().toString();
				TApplication.userName = user;
				TApplication.pwd = pwd;
				//调用LoginBiz进行登录
				LoginBiz.login(user,pwd);
				
			}
		});
	}

}
