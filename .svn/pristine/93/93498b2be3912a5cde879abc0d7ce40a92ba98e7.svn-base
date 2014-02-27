package com.sccl.framework.sent2flex;

import javax.jms.JMSException;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import flex.messaging.MessageBroker;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.util.UUIDUtils;


/**
 * 消息处理事务
 * 功能：启动mq,并监听mq接收消息,消息推送到flex. 三大功能。
 * **/
public class MessageBrokFactory {
	
	/** * flex消息组件 */
	private MessageBroker msgBroker;
	
	/*** flex uuid  * */
	private String clientId ;
	
	/*-******************以下为mq初始化变量**************************-*/
	/**端口*/
	private  static int port=61616;
	
	/**mq服务器主机ip/主机名*/
	private static String hostname = "localhost";
	
	/**activemq用户名*/
	private  static String user = "";
	
	/**activeMq密码*/
	private  static String password= "";
	
	/**  * 队列名称 */
	private String queuename="sendToFlex";
	
	/**
	 * 采用的接受方式 默认是Queue
	 * 一个消息向多个客户端发送使用topic
	 * 一个消息只发送到一个客户端使用Queue
	 * */
	private static boolean  pattern;
	
	/**
	 * 是否采用消息持久化
	 * 暂时不用
	 * */
	private  static String durable="是";
	
	/**
	 * 睡眠时间
	 * 暂时不用
	 * */
	private  static String sleepTime="否";
	
	/**
	 * 超时时间   
	 * 暂时不用
	 */
	private  static String receiveTimeOut="否";
	/*-*********************初始化变量结束**********************************-*/
	
	private Logger logger = Logger.getLogger(MessageBrokFactory.class);

	public boolean sedMessage(String sub, Object message) {
		try {
			AsyncMessage msg = new AsyncMessage();
			msg.setDestination("serverpush");
			msg.setClientId(clientId);
			msg.setMessageId(UUIDUtils.createUUID());
			msg.setTimestamp(System.currentTimeMillis());
			/* 设置消息信息，发布到客户端去 */
			msg.setBody(message);
			msgBroker.routeMessageToService(msg, null);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return true;
		}
		
	}
	
	/** 启动activeMq采集 */
	public void start() {
		//try {
			//初始化flex消息组建
			msgBroker = MessageBroker.getMessageBroker(null);
			clientId = UUIDUtils.createUUID();
			//启动mq消息接收
			//amqAdapter = new ActiveMQAdapter();
			//版本1.0只需要对hostname  port 起作用 有更高需求再另添加
			//amqAdapter.setUrl("failover:(tcp://"+hostname+":"+port+"?wireFormat.maxInactivityDuration=0)");
		
			//amqAdapter.addMessageListener(queuename, pattern, new MyTestMessageListener());
	/*} /*	catch (JMSException e) {
			String msg = "Cannot start AlarmCollectQueue.";
			logger.error(msg, e);
			System.out.println("set config ex"+e);
			e.printStackTrace();
 	}	catch (NamingException e) {
*///			String msg = "Naming context error.";
//			logger.error(msg, e);
//			System.out.println("set config ex"+e);
//			e.printStackTrace();
//		}
	}
	
	/** 停止activeMq采集*/
	public void stop() {
		//amqAdapter.close();
	}
}
