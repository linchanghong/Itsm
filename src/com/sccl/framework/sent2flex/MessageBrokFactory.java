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
 * ��Ϣ��������
 * ���ܣ�����mq,������mq������Ϣ,��Ϣ���͵�flex. �����ܡ�
 * **/
public class MessageBrokFactory {
	
	/** * flex��Ϣ��� */
	private MessageBroker msgBroker;
	
	/*** flex uuid  * */
	private String clientId ;
	
	/*-******************����Ϊmq��ʼ������**************************-*/
	/**�˿�*/
	private  static int port=61616;
	
	/**mq����������ip/������*/
	private static String hostname = "localhost";
	
	/**activemq�û���*/
	private  static String user = "";
	
	/**activeMq����*/
	private  static String password= "";
	
	/**  * �������� */
	private String queuename="sendToFlex";
	
	/**
	 * ���õĽ��ܷ�ʽ Ĭ����Queue
	 * һ����Ϣ�����ͻ��˷���ʹ��topic
	 * һ����Ϣֻ���͵�һ���ͻ���ʹ��Queue
	 * */
	private static boolean  pattern;
	
	/**
	 * �Ƿ������Ϣ�־û�
	 * ��ʱ����
	 * */
	private  static String durable="��";
	
	/**
	 * ˯��ʱ��
	 * ��ʱ����
	 * */
	private  static String sleepTime="��";
	
	/**
	 * ��ʱʱ��   
	 * ��ʱ����
	 */
	private  static String receiveTimeOut="��";
	/*-*********************��ʼ����������**********************************-*/
	
	private Logger logger = Logger.getLogger(MessageBrokFactory.class);

	public boolean sedMessage(String sub, Object message) {
		try {
			AsyncMessage msg = new AsyncMessage();
			msg.setDestination("serverpush");
			msg.setClientId(clientId);
			msg.setMessageId(UUIDUtils.createUUID());
			msg.setTimestamp(System.currentTimeMillis());
			/* ������Ϣ��Ϣ���������ͻ���ȥ */
			msg.setBody(message);
			msgBroker.routeMessageToService(msg, null);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return true;
		}
		
	}
	
	/** ����activeMq�ɼ� */
	public void start() {
		//try {
			//��ʼ��flex��Ϣ�齨
			msgBroker = MessageBroker.getMessageBroker(null);
			clientId = UUIDUtils.createUUID();
			//����mq��Ϣ����
			//amqAdapter = new ActiveMQAdapter();
			//�汾1.0ֻ��Ҫ��hostname  port ������ �и��������������
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
	
	/** ֹͣactiveMq�ɼ�*/
	public void stop() {
		//amqAdapter.close();
	}
}
