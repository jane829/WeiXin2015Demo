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
				//����asmack�ķ������е�¼
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
						//��¼
						Log.i("info", "��ʼ��¼.....");
						TApplication.conn.login(user, pwd);
						Log.i("info", "��¼�����"+TApplication.conn.isAuthenticated());
						//����֪ͨ�������¼�Ľ��
						Intent intent =  new Intent(GloableConst.ACTION_LOGIN);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra(GloableConst.KEY_IS_LOGIN_SUCCESS, TApplication.conn.isAuthenticated());
						TApplication.instance.sendBroadcast(intent);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
				}else{
					//����û�������ϣ���¼ʧ��,����֪ͨ
					Intent intent =  new Intent(GloableConst.ACTION_LOGIN);
					intent.putExtra(GloableConst.KEY_IS_LOGIN_SUCCESS, false);
					TApplication.instance.sendBroadcast(intent);
				}
			};
		}.start();
	}
}
