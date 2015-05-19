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
					//1,判断连接是否存在
					if(!TApplication.conn.isConnected()){
						TApplication.instance.reConnectChatServer();
						int count = 0;
						while(!TApplication.conn.isConnected()&&count<300){
							sleep(100);
							count++;
						}
					}
					if(TApplication.conn.isConnected()){
						//2,判断登录状态是否存在
						if(TApplication.conn.isAuthenticated()==false){
							//如果已经退出登录
							LoginBiz loginBiz = new LoginBiz();
							loginBiz.login(TApplication.userName,TApplication.pwd);
							int count =0;
							while(!TApplication.conn.isAuthenticated()&&count<300){
								sleep(100);
								count++;
							}
							
						}
						if(TApplication.conn.isAuthenticated()){
							//添加好友
							//花名册 ： 存放好友与好友的信息
							//userName  全名
							//name  昵称
							//user userName@
							Roster roster = TApplication.conn.getRoster();
							String user = userName+"@"+TApplication.serverName;
							roster.createEntry(user, nickName, group);
							
							//发送广播
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
