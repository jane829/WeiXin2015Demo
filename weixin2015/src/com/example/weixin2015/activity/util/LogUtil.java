package com.example.weixin2015.activity.util;


import com.example.weixin2015.application.TApplication;

import android.util.Log;
/**
 * baidu log4j
 * baidu log4j android
 * 日志统一处理
 * @author pjy
 *
 */
public class LogUtil {
	public static void i(String tag, String msg) {
		//当应用程序不是发行班本时，打出log
		if (!TApplication.isRelease) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, Object msg) {
		i(tag, String.valueOf(msg));
	}
}
