package com.rest.util.tibco;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.msg.Mail;
import com.rest.util.tibco.ems.msg.MessageConnection;
import com.rest.util.tibco.ems.msg.MessageConnectionFactory;
import com.ryx.framework.utils.DateUtil;

/**
 * 短信发送接口
 * 
 * @author
 * @version pm
 * @date 2016-4-1
 */
public class MySendMessage {
	
	//TODO 该为 weblogic 配置文件地址
	static String path = "E:/weblogic/etc/ryx_app.properties";
 
	/**
	 * 日志
	 */
	static Logger LOG = Logger.getLogger(MySendMessage.class);

	/**
	 * 配置文件
	 */
	// static Properties properties = new Properties();

	/**
	 * JMS配置
	 */
	static JmsConfig jmsConfig = new JmsConfig();

	/**
	 * 单例模式
	 */
	private static MySendMessage sm = null;
	

	/**
	 * 获得短信发送 实例
	 * 
	 * @return
	 */
	public static MySendMessage getInstance() {
		if (sm == null) {
			synchronized (LOG) {
				if (sm == null) {
					sm = new MySendMessage();
					sm.init();
				}
			}
			
		}
		return sm;
	}

	
	/**
	 * 参数配置，初始化
	 */
	private void init() {

		Properties p = new Properties();
		String tibcoURL = "";
		try {
			p.load(new FileInputStream("E:/weblogic/etc/ryx_app.properties"));
			tibcoURL = p.getProperty("tibcoUrl");
			//release : tibcoUrl=10.3.10.218:7222
			if (tibcoURL == null || "".equals(tibcoURL)) {
				throw new RuntimeException("tibco IP is not exist");
			}
			LOG.warn("-------tibcoURL-------" + tibcoURL);
		} catch (Exception e1) {
			LOG.error(e1.getMessage(), e1);
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
		jmsConfig.setDefault_destation_post_realpath("QTPAYSMS.OUT");
		jmsConfig.setDefault_destation_receive_name("RECV_TMS.EMS.CLT:queue");
		jmsConfig.setDefault_destation_receive_realpath("THTEST.IN");
		jmsConfig.setDestations_post_name("ule.in:queue");
		jmsConfig.setDestations_post_realpath("QTPAYSMS.OUT");
		jmsConfig.setDestations_receive_name("SMS.RECV:queue|ule.in:queue");
		jmsConfig.setDestations_receive_realpath("TST.DW.13005|THTEST.IN");
	}

	public static void main(String[] args) {
		// 恭喜小主，您提交的高级认证已通过，感谢您的使用。
		// 20160401|130954|616978|13057638343|2货|00800012"
		// Format.formatDate() + "|" + Format.formatTime() + "|11111|" + userId
		// + "|尊敬的用户您好，密码重置成功，新密码：" + password + " 。|" + branchId;
		String msg = DateUtil.getToday() + "|" + DateUtil.getTime() + "|111111|18668917331|恭喜小主，您提交的高级认证已通过，感谢您的使用。 |00800012";

		MySendMessage.getInstance().toSend(msg);
	}

	/**
	 * 发送短信息<br>
	 * 格式： 日期|时间|111111|手机号|短信内容|机构号码<br>
	 * 格式： YYYY|HHMMSS|111111|186********|恭喜小主，****|branchId<br>
	 * 范例：20160401|130954|616978|13057638343|小主，你好|00800012"<br>
	 * 
	 * @param msg
	 *            短信内容
	 */
	public static void toSend(String msg) {
		System.err.println("msg==>" + msg); 
		MessageConnection msgConn = null;
		try {
			try {
				LOG.warn("短信内容：" + msg);
				msgConn = MessageConnectionFactory.getMessageConnection((JmsConfig) jmsConfig);
				Mail mail = new Mail(msg);
				mail.addStringProperty("MAIL_CONTENT_ENCODING", "GBK");
				mail.addStringProperty("JMSCorrelationID", "Zc19_Send_SMS");
				msgConn.postMessage(mail);
			} catch (Exception ex) {
				ex.printStackTrace();
				LOG.error(ex.getMessage(), ex);
				if (msgConn != null) {
					msgConn.close();
				}
			}
		} finally {
			/*if (msgConn != null) {
				msgConn.close();
			}*/
		}
	}
}
