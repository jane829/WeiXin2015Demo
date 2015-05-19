package com.example.weixin2015.application;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

import com.example.weixin2015.R;
import com.example.weixin2015.activity.util.LogUtil;

import android.app.Activity;
import android.app.Application;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class TApplication extends Application {
	public static  List<Activity> listActivity;
	public static TApplication instance;
	private ConnectionConfiguration configuration;
	public static XMPPConnection conn;
	public static boolean isRelease = false;
	private int port;
	public static String serverName;
	private String host;
	
	public static String userName;
	public static String  pwd;

	/**
	 * ����������������
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		listActivity = new ArrayList<Activity>();
		instance = this;
		try {
			//Ӧ������ʱ��������������
			getHostPortServerName();
			connectChatServer();

			//ע������
			registerInterceptor();
			registerListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerListener() {
		AllPacketListener myPacketListener = new AllPacketListener();
		conn.addPacketListener(myPacketListener, null);
	}

	private void registerInterceptor() {
		AllPacketInterceptor myPacketInterceptor = new AllPacketInterceptor();
		//����������Ϣ�������ء�
		conn.addPacketInterceptor(myPacketInterceptor,null);
	}

	class AllPacketInterceptor implements PacketInterceptor{

		@Override
		public void interceptPacket(Packet packet) {
			// TODO Auto-generated method stub
			//packet ����ȥ���ս������Ķ���packet
			LogUtil.i("application", "Interceptor:"+packet.toXML());
		}

	}
	class AllPacketListener implements PacketListener{
		//openfire���ص���Ϣ
		@Override
		public void processPacket(Packet packet) {
			// TODO Auto-generated method stub
			LogUtil.i("application", "Listen:"+packet.toXML());
		}}


	private void connectChatServer() {	
		configuration = new ConnectionConfiguration(host, port,serverName);
		configuration.setSASLAuthenticationEnabled(false);
		configuration.setSelfSignedCertificateEnabled(false);
		configuration.setCompressionEnabled(false);
		configuration.setExpiredCertificatesCheckEnabled(false);
		conn = new XMPPConnection(configuration);
		new Thread(){
			public void run() {
				try {
					conn.connect();
					LogUtil.i("application", "���ӳɹ�");
					
				} catch (XMPPException e) {
					e.printStackTrace();
					LogUtil.i("application", "����ʧ��:"+e.getMessage());
				}
			};
		}.start();
	}


	private void getHostPortServerName() {
		try {
			XmlResourceParser parser = this.getResources().getXml(R.xml.config);
			int enventType = parser.getEventType();
			while(enventType!=parser.END_DOCUMENT){
				if(enventType==parser.START_TAG){
					String tagName = parser.getName().trim();
					if("host".equals(tagName)){
						host  = parser.nextText();
					}else if("port".equals(tagName)){
						port = Integer.parseInt(parser.nextText());
					}else if("serviceName".equals(tagName)){
						serverName = parser.nextText();
					}
				}
				enventType = parser.next();
			}
			LogUtil.i("application","xml�����Ľ��Ϊ��\nhost:"+host+",port:"+port+"serverName:"+serverName);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}


	public static void add(Activity activity){//ÿ������һ��Activity,�������list����
		listActivity.add(activity);
		Log.i("add", "activity"+activity);
	}



	public static void exit(){//�˳�Ӧ�ó���
		for(int i=0;i<listActivity.size();i++){
			listActivity.get(i).finish();
			Log.i("exit", "activity"+listActivity.get(i));
		}
		System.exit(0);//�˳�application,��֤�´�����Ӧ��ʱ��ִ��application��onCreate����
	}

	public void reConnectChatServer() {
		// TODO Auto-generated method stub
		
	}
}
