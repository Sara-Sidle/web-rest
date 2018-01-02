package com.rest.util.tibco.ems.impl;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;

/**
 * 
 * @author
 * @version pm
 * @date 2016-4-5
 */
public class ExceptionListenerThread extends Thread {
	
	private AbstractMessageHandler abstractMessageHandler;
	
	static Logger LOG = Logger.getLogger(ExceptionListenerThread.class);

	
	
	@Override
	public void run() {
		LOG.info("start re-connect....");
		this.abstractMessageHandler.close();
		do {
			try {
				Thread.sleep(this.abstractMessageHandler.getJmsConfig().getReconnectTime());
			} catch (InterruptedException e) {
				LOG.error(e.getMessage(), e);
				// empty catch block
			}
			try {
				this.abstractMessageHandler.init();
				continue;
			} catch (ConnectionFaultException e) {
				LOG.error(e.getMessage(), e);
			}
		} while (this.abstractMessageHandler.getConnection() == null);
	}

	public AbstractMessageHandler getAbstractMessageHandler() {
		return this.abstractMessageHandler;
	}

	public void setAbstractMessageHandler(AbstractMessageHandler abstractMessageHandler) {
		this.abstractMessageHandler = abstractMessageHandler;
	}
}
