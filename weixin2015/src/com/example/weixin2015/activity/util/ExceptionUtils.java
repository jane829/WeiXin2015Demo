package com.example.weixin2015.activity.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;

public class ExceptionUtils {
	
	public static void handleMessage(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String msg = e.getMessage();
		Log.i("info", msg);
		e.printStackTrace(pw);
	}

}
