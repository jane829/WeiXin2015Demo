package com.example.weixin2015.activity.util;

import android.R.anim;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {
  public static void checkNetworkState(final Context context)
  {
	 //连接管理
	  ConnectivityManager manager= (ConnectivityManager) 
			  context.getSystemService(Context.CONNECTIVITY_SERVICE);
	 NetworkInfo activeNetwork= manager.getActiveNetworkInfo();
	 //没打开网络
	 if (activeNetwork==null)
	 {
		 //弹出Dialog,
		 AlertDialog.Builder dialog=new Builder(context);
		 dialog.setTitle("");
		 dialog.setMessage("你没有打开网络");
		 dialog.setPositiveButton("打开网络", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			//显示网络设置界面
				Intent intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				context.startActivity(intent);
			}
		});
		 dialog.setNeutralButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		 dialog.show();
	 }
  }
}
