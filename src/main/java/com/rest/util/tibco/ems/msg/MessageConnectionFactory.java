package com.rest.util.tibco.ems.msg;

import java.util.Properties;

import com.rest.util.tibco.ems.exception.NotSuppoertException;
import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.impl.JmsDestation;
import com.rest.util.tibco.ems.impl.MessageConnectionJMS;
import com.rest.util.tibco.ems.exception.NotSuppoertException;
import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.impl.JmsDestation;
import com.rest.util.tibco.ems.impl.MessageConnectionJMS;
import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.exception.NotSuppoertException;
import com.rest.util.tibco.ems.impl.JmsConfig;
import com.rest.util.tibco.ems.impl.JmsDestation;
import com.rest.util.tibco.ems.impl.MessageConnectionJMS;
import com.rest.util.tibco.ems.msg.Destination.DestinationType;

/**
 * 
 * 
 * <code>getMessageConnection()</code>
 * 
 * 
 * @author teamsun.com.cn
 * @see gov.chinapost.ems.msg.adapter.jms.impl.JmsConfig
 */
public class MessageConnectionFactory {

	/**
	 *  
	 */
	private static final String CONFIG = "CONFIG";

	/**
	 * 
	 */
	private static final String ENCODING = "ENCODING";

	static Logger log = Logger.getLogger(MessageConnectionFactory.class);//  

	/**
	 * 
	 * @TODO
	 * @param prop
	 * @return
	 * 
	 */
	public static MessageConnection getMessageConnection(Properties prop) {

		String config = prop.getProperty(CONFIG);
		if (config.equals(MessageConnectionJMS.CONFIG_NAME)) {

			JmsConfig jmsConfig = new JmsConfig();
			jmsConfig.setEncoding(prop.getProperty(ENCODING));
			jmsConfig.setLookupName(prop.getProperty(MessageConnectionJMS.LOOKUPNAME));
			jmsConfig.setPassword(prop.getProperty(MessageConnectionJMS.PASSWORD));
			jmsConfig.setUrl(prop.getProperty(MessageConnectionJMS.URL));
			jmsConfig.setProvider(prop.getProperty(MessageConnectionJMS.PROVIDER));
			jmsConfig.setUsername(prop.getProperty(MessageConnectionJMS.USERNAME));
			jmsConfig.setReconnectTime(Integer.parseInt(prop.getProperty(MessageConnectionJMS.RECONNECT_TIME)));
			jmsConfig.setDebug(Boolean.parseBoolean(prop.getProperty(MessageConnectionJMS.DEBUG)));
			jmsConfig.setClassName(prop.getProperty(MessageConnectionJMS.CLASS_NAME));
			jmsConfig.setTcpUrl(prop.getProperty(MessageConnectionJMS.TCP_URL));
			jmsConfig.setUseJndi(Boolean.parseBoolean(prop.getProperty(MessageConnectionJMS.USE_JNDI)));

			MessageConnection msgConn = new MessageConnectionJMS();
			((MessageConnectionJMS) msgConn).setJmsConfig(jmsConfig);

			JmsDestation d_post = new JmsDestation();
			String d_post_s = prop.getProperty(MessageConnectionJMS.DEFAULT_DESTATION_POST_NAME);

			if (d_post_s != null && !"".equals(d_post_s)) {
				d_post.setDestationName(d_post_s.split("\\:")[0]);
				d_post.setDestationRealPath(prop.getProperty(MessageConnectionJMS.DEFAULT_DESTATION_POST_REALPATH));
				d_post.setDestationType(Destination.DestinationType.DESTINATION_POST);
				d_post.setTopic(d_post_s.split("\\:")[1].equals("topic"));

				msgConn.setDefaultDestination(d_post);
				log.info(" " + d_post);
			} else {
				log.debug(" ");
			}

			JmsDestation d_recv = new JmsDestation();
			String d_recv_s = prop.getProperty(MessageConnectionJMS.DEFAULT_DESTATION_RECEIVE_NAME);

			if (d_recv_s != null && !"".equals(d_recv_s)) {
				d_recv.setDestationName(d_recv_s.split("\\:")[0]);
				d_recv.setTopic(d_recv_s.split("\\:")[1].equals("topic"));
				d_recv.setDestationRealPath(prop.getProperty(MessageConnectionJMS.DEFAULT_DESTATION_RECEIVE_REALPATH));
				d_recv.setDestationType(Destination.DestinationType.DESTINATION_RECEIVE);
				msgConn.setDefaultDestination(d_recv);
				log.info(" " + d_recv);
			} else {
				log.debug(" ");
			}
			registDestation(msgConn, prop.getProperty(MessageConnectionJMS.DESTATIONS_POST_NAME), prop.getProperty(MessageConnectionJMS.DESTATIONS_POST_REALPATH), Destination.DestinationType.DESTINATION_POST);

			registDestation(msgConn, prop.getProperty(MessageConnectionJMS.DESTATIONS_RECEIVE_NAME), prop.getProperty(MessageConnectionJMS.DESTATIONS_RECEIVE_REALPATH),
					Destination.DestinationType.DESTINATION_RECEIVE);

			if (jmsConfig.isDebug())
				((MessageConnectionJMS) msgConn).showAllDestations();
			return msgConn;
		} else {
			throw new NotSuppoertException(" ");
		}

	}

	/**
	 * 
	 * 
	 * @TODO
	 * @param jmsConfig
	 * @return
	 * 
	 */
	public static MessageConnection getMessageConnection(JmsConfig jmsConfig) {

		MessageConnection msgConn = new MessageConnectionJMS();
		((MessageConnectionJMS) msgConn).setJmsConfig(jmsConfig);

		JmsDestation d_post = new JmsDestation();
		String d_post_s = jmsConfig.getDefault_destation_post_name();

		if (d_post_s != null && !"".equals(d_post_s)) {
			d_post.setDestationName(d_post_s.split("\\:")[0]);
			d_post.setDestationRealPath(jmsConfig.getDefault_destation_post_realpath());
			d_post.setDestationType(Destination.DestinationType.DESTINATION_POST);
			d_post.setTopic(d_post_s.split("\\:")[1].equals("topic"));

			msgConn.setDefaultDestination(d_post);
			log.info("  " + d_post);
		} else {
			log.debug("   ");
		}

		JmsDestation d_recv = new JmsDestation();
		String d_recv_s = jmsConfig.getDefault_destation_receive_name();

		if (d_recv_s != null && !"".equals(d_recv_s)) {
			d_recv.setDestationName(d_recv_s.split("\\:")[0]);
			d_recv.setTopic(d_recv_s.split("\\:")[1].equals("topic"));
			d_recv.setDestationRealPath(jmsConfig.getDefault_destation_receive_realpath());
			d_recv.setDestationType(Destination.DestinationType.DESTINATION_RECEIVE);
			msgConn.setDefaultDestination(d_recv);
			log.info(" " + d_recv);
		} else {
			log.debug("   ");
		}
		registDestation(msgConn, jmsConfig.getDestations_post_name(), jmsConfig.getDestations_post_realpath(), Destination.DestinationType.DESTINATION_POST);

		registDestation(msgConn, jmsConfig.getDestations_receive_name(), jmsConfig.getDestations_receive_realpath(), Destination.DestinationType.DESTINATION_RECEIVE);

		if (jmsConfig.isDebug())
			((MessageConnectionJMS) msgConn).showAllDestations();
		return msgConn;
	}

	/**
	 * 
	 * @param msgConn
	 * @param name
	 * @param realPath
	 * @param dtype
	 */
	private static void registDestation(MessageConnection msgConn, String name, String realPath, Destination.DestinationType dtype) {
		if (name == null || "".equals(name))
			return;
		int d = name.split("\\|").length;
		for (int i = 0; i < d; i++) {
			JmsDestation jd = new JmsDestation();
			jd.setDestationName(name.split("\\|")[i].split("\\:")[0]);
			jd.setTopic(name.split("\\|")[i].split("\\:")[1].equals("topic"));
			jd.setDestationRealPath(realPath.split("\\|")[i]);
			jd.setDestationType(dtype);
			msgConn.registerDestination(jd);
		}
	}

}
