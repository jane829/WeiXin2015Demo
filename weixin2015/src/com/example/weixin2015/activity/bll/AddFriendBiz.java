package com.example.weixin2015.activity.bll;

import org.jivesoftware.smack.Roster;

import android.content.Intent;

import com.example.weixin2015.activity.util.ExceptionUtil;
import com.example.weixin2015.activity.util.GloableConst;
import com.example.weixin2015.application.TApplication;

public class AddFriendBiz {
	public AddFriendBiz(	final String userName,
			final String nickName,
			final String[] group) {
		new Thread(){
			public void run() {
				try {
					//1,�ж������Ƿ����
					if(!TApplication.conn.isConnected()){
						TApplication.instance.reConnectChatServer();
						int count = 0;
						while(!TApplication.conn.isConnected()&&count<300){
							sleep(100);
							count++;
						}
					}
					if(TApplication.conn.isConnected()){
						//2,�жϵ�¼״̬�Ƿ����
						if(TApplication.conn.isAuthenticated()==false){
							//����Ѿ��˳���¼
							LoginBiz loginBiz = new LoginBiz();
							loginBiz.login(TApplication.userName,TApplication.pwd);
							int count =0;
							while(!TApplication.conn.isAuthenticated()&&count<300){
								sleep(100);
								count++;
							}
							
						}
						if(TApplication.conn.isAuthenticated()){
							//��Ӻ���
							//������ �� ��ź�������ѵ���Ϣ
							//userName  ȫ��
							//name  �ǳ�
							//user userName@
							Roster roster = TApplication.conn.getRoster();
							String user = userName+"@"+TApplication.serverName;
							roster.createEntry(user, nickName, group);
							
							//���͹㲥
							Intent intent = new Intent(GloableConst.ACTION_ADD_FRIEND);
							intent.putExtra(GloableConst.KEY_IS_LOGIN_SUCCESS, true);
							TApplication.instance.sendBroadcast(intent);
						}else{
							//TODO:
						}
						
					}
				} catch (Exception e) {
					ExceptionUtil.handle(e);
					Intent intent = new Intent(GloableConst.ACTION_ADD_FRIEND);
					intent.putExtra(GloableConst.KEY_IS_LOGIN_SUCCESS, false);
					TApplication.instance.sendBroadcast(intent);
				}
			};
		}.start();	
	}




}
