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
	 //���ӹ���
	  ConnectivityManager manager= (ConnectivityManager) 
			  context.getSystemService(Context.CONNECTIVITY_SERVICE);
	 NetworkInfo activeNetwork= manager.getActiveNetworkInfo();
	 //û������
	 if (activeNetwork==null)
	 {
		 //����Dialog,
		 AlertDialog.Builder dialog=new Builder(context);
		 dialog.setTitle("");
		 dialog.setMessage("��û�д�����");
		 dialog.setPositiveButton("������", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			//��ʾ�������ý���
				Intent intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				context.startActivity(intent);
			}
		});
		 dialog.setNeutralButton("ȡ��", new OnClickListener() {
			
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
