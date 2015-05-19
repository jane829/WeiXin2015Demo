package com.example.weixin2015.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.weixin2015.R;
import com.example.weixin2015.activity.bll.AddFriendBiz;
import com.example.weixin2015.activity.util.ExceptionUtil;
import com.example.weixin2015.activity.util.GloableConst;
import com.example.weixin2015.activity.util.Tools;

public class AddFriendActivity extends BaseActivity {
	private Button btn_back;
	private Button btn_submit;
	private EditText et_userName;
	private EditText et_nickName;
	private EditText et_gruop;

	private AddFriendReceiver receiver;


	class AddFriendReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			boolean is_success = false;
			is_success = intent.getBooleanExtra(GloableConst.KEY_IS_LOGIN_SUCCESS, false);
			if(is_success){
				Tools.showInfo(AddFriendActivity.this, "添加好友成功");
				AddFriendActivity.this.finish();
			}else{
				Tools.showInfo(AddFriendActivity.this, "添加好友失败,请重试。。。");
				AddFriendActivity.this.finish();
			}
		}

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfriend);
		setupView();
		setListener();

		receiver = new AddFriendReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(GloableConst.ACTION_ADD_FRIEND);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	private void setupView(){

		btn_back = (Button) findViewById(R.id.btn_add_friend_back);
		btn_submit = (Button) findViewById(R.id.btn_add_friend_submit1);

		et_userName =(EditText) findViewById(R.id.et_add_friend_userName);
		et_nickName =(EditText) findViewById(R.id.et_add_friend_nickName);
		et_gruop =(EditText) findViewById(R.id.et_add_friend_group);
	}

	private void setListener(){
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddFriendActivity.this.finish();
			}
		});
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取添加好友的具体信息
				//调用AddFriendBiz方法进行添加好友的操作
				String userName = et_userName.getText().toString().trim();
				String nickName = et_nickName.getText().toString().trim();
				String group = et_gruop.getText().toString().trim();
				//添加好友
				try {
					AddFriendBiz addFriendBiz = new AddFriendBiz(userName, nickName,
							new String[]{ group } );
				} catch (Exception e) {
					ExceptionUtil.handle(e);
				}
			}
		});
	}

}
