package com.rest.util.tibco;

import java.io.FileInputStream;
import java.util.Properties;

import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.msg.Mail;
import com.rest.util.tibco.ems.msg.MessageConnection;
import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.msg.Mail;
import com.rest.util.tibco.ems.msg.MessageConnection;
import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.msg.Mail;
import com.rest.util.tibco.ems.msg.MessageConnection;
import com.rest.util.tibco.ems.msg.MessageConnectionFactory;

/**
 * 高级认证队列
 * 
 * @author
 * @version pm
 * @date 2016-2-24
 */
public class SendMessageForAdvancedVip {

	static Logger LOG = Logger.getLogger(SendMessageForAdvancedVip.class);// 日志对象

	static Properties p = new Properties();

	static JmsConfig jmsConfig = new JmsConfig();

	private static SendMessageForAdvancedVip sm = null;

	public static SendMessageForAdvancedVip getInstance() {
		if (null == sm)
			sm = new SendMessageForAdvancedVip();
		sm.init();
		return sm;
	}

	private void init() {

		String tibcoURL = "";
		try {
			p.load(new FileInputStream("/home/weblogic/etc/pm.properties"));
			tibcoURL = p.getProperty("tibcoURL");
			if (tibcoURL == null || "".equals(tibcoURL)) {
				tibcoURL = "10.3.10.221:7222";
			}
			LOG.info("-------tibcoURL-------" + tibcoURL);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		jmsConfig.setEncoding("utf-8");
		jmsConfig.setLookupName("QueueConnectionFactory");
		jmsConfig.setPassword("");
		jmsConfig.setUrl("tibjmsnaming://" + tibcoURL);
		jmsConfig.setProvider("com.tibco.tibjms.naming.TibjmsInitialContextFactory");
		jmsConfig.setUsername("admin");
		jmsConfig.setReconnectTime(30000);
		jmsConfig.setDebug(true);
		jmsConfig.setClassName("com.tibco.tibjms.TibjmsConnectionFactory");
		jmsConfig.setTcpUrl("tcp://" + tibcoURL);
		jmsConfig.setUseJndi(false);

		jmsConfig.setDefault_destation_post_name("SEND_TMS.EMS.CLT:queue");
		jmsConfig.setDefault_destation_post_realpath("MFB.IN");
		// jmsConfig.setDefault_destation_post_realpath("THTEST.OUT");

		jmsConfig.setDefault_destation_receive_name("RECV_TMS.EMS.CLT:queue");
		jmsConfig.setDefault_destation_receive_realpath("MFB.IN");

		jmsConfig.setDestations_post_name("ule.in:queue");
		jmsConfig.setDestations_post_realpath("MFB.IN");
		// jmsConfig.setDestations_post_realpath("THTEST.OUT");

		jmsConfig.setDestations_receive_name("SMS.RECV:queue|ule.in:queue");
		jmsConfig.setDestations_receive_realpath("TST.DW.13005|THTEST.IN");
	}

	public void toSend(String msg) {
		MessageConnection msgConn = null;
		try {
			LOG.info("高级认证退款：" + msg);
			msgConn = MessageConnectionFactory.getMessageConnection(jmsConfig);
			Mail mail = new Mail(msg);
			mail.addStringProperty(Mail.MAIL_ENCODING, "GBK");
			msgConn.postMessage(mail);
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			if (msgConn != null) {
				msgConn.close();
			}
		} finally {
			if (msgConn != null) {
				msgConn.close();
			}
		}
	}
}
