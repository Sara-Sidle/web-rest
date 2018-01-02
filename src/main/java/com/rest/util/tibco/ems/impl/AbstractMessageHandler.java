package com.rest.util.tibco.ems.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import com.rest.util.tibco.ems.msg.Destination;

/**
 * 
 * @author teamsun.com.cn
 */
public abstract class AbstractMessageHandler {

	static Logger LOG = Logger.getLogger(AbstractMessageHandler.class);//
	
	/**
	 * 
	 */
	private JmsConfig jmsConfig;

	/**
	 * 
	 */
	private Connection connection;

	/**
	 * 
	 * 
	 * @throws JMSException
	 */
	public void connect() throws ConnectionFaultException {

		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				LOG.error(e.getMessage(), e);
			}
			connection = null;
		}
		if (jmsConfig.isUseJndi())
			connection = getConnectionUseJndi();
		else
			connection = getConnectionUseNative();
	}

	/**
	 * 
	 * @return
	 * @throws ConnectionFaultException
	 */
	@SuppressWarnings("unchecked")
	private Connection getConnectionUseNative() throws ConnectionFaultException {
		LOG.info(" ");
		Connection conn = null;
		try {
			Constructor<ConnectionFactory> ctor = (Constructor<ConnectionFactory>) Class.forName(getJmsConfig().getClassName()).getConstructor(String.class);
			ConnectionFactory factory = ctor.newInstance(getJmsConfig().getTcpUrl());

			// ConnectionFactory factory = new
			// com.tibco.tibjms.TibjmsConnectionFactory(
			// getJmsConfig().getTcpUrl());
			//
			conn = factory.createConnection(getJmsConfig().getUsername(), getJmsConfig().getPassword());

			return conn;
		} catch (SecurityException e) {
			LOG.error("" + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			LOG.error("" + e.getMessage(), e);
		} catch (JMSException e) {
			LOG.error("" + e.getMessage(), e);
			throw new ConnectionFaultException();
		} catch (NoSuchMethodException e) {
			LOG.error("" + e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			LOG.error("" + e.getMessage(), e);
		} catch (InstantiationException e) {
			LOG.error("" + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			LOG.error("" + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			LOG.error("" + e.getMessage(), e);
		}
		return conn;

	}

	private Connection getConnectionUseJndi() throws ConnectionFaultException {

		LOG.info("JNDI");
		Connection connection = null;
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("java.naming.factory.initial", getJmsConfig().getProvider());
		hashtable.put("java.naming.provider.url", getJmsConfig().getUrl());
		hashtable.put("java.naming.security.principal", getJmsConfig().getUsername());
		hashtable.put("java.naming.security.credentials", getJmsConfig().getPassword());

		Context context = null;
		try {
			context = new InitialContext(hashtable);
			ConnectionFactory factory = (ConnectionFactory) context.lookup(getJmsConfig().getLookupName());
			connection = factory.createConnection(getJmsConfig().getUsername(), getJmsConfig().getPassword());
			return connection;
		} catch (NamingException e) {
			LOG.error("NamingException\t" + e.getMessage(), e);
			throw new ConnectionFaultException();
		} catch (JMSException e) {
			LOG.error("JMSException\t" + e.getMessage(), e);
			throw new ConnectionFaultException();
		}
	}

	public abstract void close();

	/**
	 * JMS Destation
	 * 
	 * @param session
	 *            jms session
	 * @param destation
	 * @see gov.chinapost.ems.msg.adapter.Destination
	 * @return
	 * @throws JMSException
	 */
	protected javax.jms.Destination getJmsDestation(Session session, Destination destation) throws JMSException {

		javax.jms.Destination jmsDest = null;
		if (((JmsDestation) destation).isTopic()) {
			jmsDest = session.createTopic(destation.getDestationRealPath());
		} else {
			jmsDest = session.createQueue(destation.getDestationRealPath());
		}
		return jmsDest;
	}

	/**
	 * 
	 * 
	 * @throws ConnectionFaultException
	 */
	abstract public void init() throws ConnectionFaultException;

	public JmsConfig getJmsConfig() {
		return jmsConfig;
	}

	public void setJmsConfig(JmsConfig jmsConfig) {
		this.jmsConfig = jmsConfig;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
