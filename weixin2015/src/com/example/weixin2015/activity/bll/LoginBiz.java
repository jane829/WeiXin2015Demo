package com.example.weixin2015.activity.bll;

import org.jivesoftware.smack.XMPPException;

import android.content.Intent;
import android.util.Log;

import com.example.weixin2015.activity.util.GloableConst;
import com.example.weixin2015.application.TApplication;

public class LoginBiz {

	public static void login(final String user, final String pwd) {
		new Thread(){
			public void run() {
				//调用asmack的方法进行登录
				int count = 0;
				while(count<300&&TApplication.conn.isConnected()==false){
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count++;
					Log.i("info", "Login:"+Thread.currentThread().getId()+"----"+count);
				
				}
				if(TApplication.conn.isConnected()){
					try {
						//登录
						Log.i("info", "开始登录.....");
						TApplication.conn.login(user, pwd);
						Log.i("info", "登录结果："+TApplication.conn.isAuthenticated());
						//发送通知，处理登录的结果
						Intent intent =  new Intent(GloableConst.ACTION_LOGIN);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra(GloableConst.KEY_IS_LOGIN_SUCCESS, TApplication.conn.isAuthenticated());
						TApplication.instance.sendBroadcast(intent);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
				}else{
					//网络没有连接上，登录失败,发送通知
					Intent intent =  new Intent(GloableConst.ACTION_LOGIN);
					intent.putExtra(GloableConst.KEY_IS_LOGIN_SUCCESS, false);
					TApplication.instance.sendBroadcast(intent);
				}
			};
		}.start();
	}
}
