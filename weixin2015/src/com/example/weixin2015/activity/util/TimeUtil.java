package com.example.weixin2015.activity.util;
/**
 * ����
 * ��λ�����о���ĳ���Աд�����ࣨ���ߣ�������������ʵ���࣬��ȫ�����ܣ���ռ80%
 * ��һ�δ����õ�ʱ��
 * @author pjy
 *
 */
public class TimeUtil {
	long startTime;
	public TimeUtil() {
		// TODO Auto-generated constructor stub
	startTime=System.currentTimeMillis();
	}
	
	public void showTime(String tag)
	{
		LogUtil.i(tag, System.currentTimeMillis()-startTime);
	}
	
	

}
