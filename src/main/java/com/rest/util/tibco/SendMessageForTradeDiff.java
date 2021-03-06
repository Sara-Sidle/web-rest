package com.rest.util.tibco;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.msg.Mail;
import com.rest.util.tibco.ems.msg.MessageConnection;
import com.rest.util.tibco.ems.msg.MessageConnectionFactory;

/**
 * 
 * @author
 * 
 */
public class SendMessageForTradeDiff {
	static Logger log = Logger.getLogger(SendMessageForTradeDiff.class);
	static Properties p = new Properties();
	static JmsConfig jmsConfig = new JmsConfig();
	private static SendMessageForTradeDiff sm = null;

	static {
	}

	public SendMessageForTradeDiff() {
	}

	public static SendMessageForTradeDiff getInstance() {
		if (sm == null) {
			sm = new SendMessageForTradeDiff();
		}
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
			log.info("-------tibcoURL-------" + tibcoURL);
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
		jmsConfig.setUrl("tibjmsnaming://" + tibcoURL);
		jmsConfig.setUseJndi(false);
		jmsConfig.setDefault_destation_post_name("SEND_TMS.EMS.CLT:queue");
		jmsConfig.setDefault_destation_post_realpath("BRZ.IN");
		jmsConfig.setDefault_destation_receive_name("RECV_TMS.EMS.CLT:queue");
		jmsConfig.setDefault_destation_receive_realpath("BRZ.IN");
		jmsConfig.setDestations_post_name("ule.in:queue");
		jmsConfig.setDestations_post_realpath("BRZ.IN");
		jmsConfig.setDestations_receive_name("SMS.RECV:queue|ule.in:queue");
		jmsConfig.setDestations_receive_realpath("TST.DW.13005|THTEST.IN");
	}

	public void toSend(String msg) {
		MessageConnection msgConn = null;
		try {
			log.info("补入账信息：" + msg);
			msgConn = MessageConnectionFactory.getMessageConnection(jmsConfig);
			Mail mail = new Mail(msg);
			mail.addStringProperty("MAIL_CONTENT_ENCODING", "GBK");
			msgConn.postMessage(mail);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			if (msgConn != null) {
				msgConn.close();
			}
		} finally {
			if (msgConn != null) {
				msgConn.close();
			}
		}
	}

	public static void main(String[] args) {
		SendMessageForTradeDiff.getInstance().toSend("1|2|3");
	}
}
