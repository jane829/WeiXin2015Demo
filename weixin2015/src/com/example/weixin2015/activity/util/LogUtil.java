package com.example.weixin2015.activity.util;


import com.example.weixin2015.application.TApplication;

import android.util.Log;
/**
 * baidu log4j
 * baidu log4j android
 * ��־ͳһ����
 * @author pjy
 *
 */
public class LogUtil {
	public static void i(String tag, String msg) {
		//��Ӧ�ó����Ƿ��а౾ʱ�����log
		if (!TApplication.isRelease) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, Object msg) {
		i(tag, String.valueOf(msg));
	}
}
