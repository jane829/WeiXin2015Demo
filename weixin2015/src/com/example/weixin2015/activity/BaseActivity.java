package com.example.weixin2015.activity;
/**
 * ����Activity�ĸ��࣬�������Activity��Ҫ��ɵ�һЩ����
 */
import com.example.weixin2015.activity.util.NetWorkUtil;
import com.example.weixin2015.application.TApplication;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ÿ����һ��Activity,�������һ��List����;
		TApplication.add(this);
		//�ж���������ӵ�״̬
		NetWorkUtil networkUtil = new NetWorkUtil();
		networkUtil.checkNetworkState(this);
		
	}


}
