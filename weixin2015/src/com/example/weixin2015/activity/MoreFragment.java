package com.example.weixin2015.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.weixin2015.R;
import com.example.weixin2015.activity.util.LogUtil;
import com.example.weixin2015.application.TApplication;

public class MoreFragment extends Fragment {
	private Button btn_detail;
	private Button btn_exit;
	private Button btn_set;
	private AlertDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.more, null);
		setupView(view);
		setListener();
		return view;
	}


	private void setListener() {
		btn_exit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					
					dialog.show();
				} catch (Exception e) {
					// TODO: handle exception
					LogUtil.i("info",e.getMessage());
				}
//				TApplication.instance.exit();
			}
		});
	}


	private void setupView(View view) {
		dialog = new AlertDialog.Builder(TApplication.instance)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setMessage("你确认要退出应用程序？")
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TApplication.exit();}
			})
			.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
		.create();
		btn_detail = (Button) view.findViewById(R.id.btn_more_detail);
		btn_exit = (Button) view.findViewById(R.id.btn_more_exit);
		btn_set = (Button) view.findViewById(R.id.btn_more_set);
	}


}
