package com.rest.util.tibco.ems.impl;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import com.rest.util.tibco.ems.msg.Destination;
import com.rest.util.tibco.ems.msg.Mail;
import com.rest.util.tibco.ems.msg.MessageConnectionFactory;
import com.rest.util.tibco.ems.msg.PostMessageFaultService;

/**
 * 短信发送
 * 
 * @author teamsun.com.cn
 * 
 */
public class MessageSender extends AbstractMessageHandler {

	private PostMessageFaultService postMessageFaultService;

	private ExceptionListenerThread elt;

	static Logger log = Logger.getLogger(MessageConnectionFactory.class);

	/**
	 * 
	 * 
	 * @param destation
	 * @param mail
	 */
	public void postMessage(Destination destination, Mail mail) {
		javax.jms.Session session = null;
		MessageProducer mp = null;
		try {
			if (getJmsConfig().isDebug())
				log.info("connectoin:" + getConnection() + " " + this);
			session = this.getConnection().createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			javax.jms.BytesMessage bm = session.createBytesMessage();

			for (String name : mail.getStringPropertyNames()) {
				if (name.equals(Mail.MAIL_ENCODING))
					continue;
				bm.setStringProperty(name, mail.getStringProperty(name));
			}
			if (mail.getStringProperty("JMSCorrelationID") != null) {
				bm.setJMSCorrelationID(mail.getStringProperty("JMSCorrelationID"));
			}

			String encoding = mail.getStringProperty(Mail.MAIL_ENCODING);
			if (encoding == null) {
				encoding = getJmsConfig().getEncoding();
				log.info("Encoding=" + encoding);
				bm.writeBytes(mail.getMailText().getBytes(encoding));
			} else if (encoding.equals("")) {
				log.info("encoding为空了 ");
				bm.writeBytes(mail.getMailText().getBytes());
			} else {
				log.info(" Encoding=" + encoding);
				bm.writeBytes(mail.getMailText().getBytes(encoding));
			}

			mp = session.createProducer(this.getJmsDestation(session, destination));
			mp.send(bm);

			if (getJmsConfig().isDebug())
				log.info(" " + destination + "\n" + mail);
			else
				log.info("  " + destination + "  ");
		} catch (JMSException e) {
			log.error(" 短信发送出错了：" + e.getMessage() + " " + e.getLocalizedMessage());
			if (this.postMessageFaultService != null)
				this.postMessageFaultService.postMessageFault(destination, mail);
			startReconnect();
		} catch (Exception e) {
			log.error(" 短信发送出错了2：" + e.getMessage());
			e.printStackTrace();
			if (this.postMessageFaultService != null)
				this.postMessageFaultService.postMessageFault(destination, mail);
		} finally {
			if (null != mp)
				try {
					mp.close();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * 设置监听
	 */
	public void setMessageConnectExceptionListener() {
		try {
			if (this.getConnection() != null)
				this.getConnection().setExceptionListener(new ExceptionListener() {

					public void onException(JMSException jmsException) {
						log.info("JMS异常:" + jmsException.getMessage());
						if (!elt.isAlive())
							elt.start();

					}

				});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ConnectionFaultException {
		elt = new ExceptionListenerThread();
		elt.setAbstractMessageHandler(MessageSender.this);
		elt.setDaemon(true);

		this.connect();

		this.setMessageConnectExceptionListener();
	}

	@Override
	public void close() {
		if (this.getConnection() != null) {
			try {
				this.getConnection().close();
			} catch (JMSException e) {
				log.error(" " + e.getMessage(), e);
			}
			this.setConnection(null);

		}
	}

	/**
	 * 
	 */
	public void startReconnect() {
		if (getJmsConfig().isDebug())
			log.info(" ");
		if (elt == null) {
			elt = new ExceptionListenerThread();
			elt.setAbstractMessageHandler(MessageSender.this);
			elt.setDaemon(true);
		}
		if (!elt.isAlive())
			elt.start();
	}

	/**
	 * 
	 * @return
	 */
	public PostMessageFaultService getPostMessageFaultService() {
		return postMessageFaultService;
	}

	/**
	 * 
	 * @param postMessageFaultService
	 */
	public void setPostMessageFaultService(PostMessageFaultService postMessageFaultService) {
		this.postMessageFaultService = postMessageFaultService;
	}

}
