package com.rest.util.tibco.ems.impl;

import java.util.ArrayList;
import java.util.List;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import com.rest.util.tibco.ems.exception.ConnectionFaultException;
import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.exception.ConnectionFaultException;

/**
 * 
 * @author 
 *
 */
public class Reconnectable extends Thread {
	
	
	private static final int SLEEP_TIME = 10000;
	private List<MessageReceiver> messageReceivers = new ArrayList<MessageReceiver>();
	static Logger log = Logger.getLogger( Reconnectable.class);
	private long delay;
	private boolean close = false;

	public void setMessageReceivers(List<MessageReceiver> messageReceivers) {
		this.messageReceivers = messageReceivers;
	}

	public void addMessageReceiver(MessageReceiver msgRecv) {
		this.messageReceivers.add(msgRecv);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log.error((Object) e.getMessage(), (Throwable) e);
		}
		while (!this.close) {
			try {
				Thread.sleep(this.delay);
			} catch (InterruptedException e1) {
				log.error((Object) e1.getMessage(), (Throwable) e1);
			}
			for (MessageReceiver messageReceiver : this.messageReceivers) {
				if (messageReceiver.isStarted())
					continue;
				try {
					messageReceiver.init();
					if (messageReceiver.getDestination() != null && messageReceiver.getReceiveMessageService() != null) {
						messageReceiver.registerReceiveMessageService(messageReceiver.getDestination(), messageReceiver.getReceiveMessageService(), messageReceiver.getCharset());
					}
					messageReceiver.setStarted(true);
					log.info((Object) (String.valueOf(messageReceiver.getDestination().getDestationName()) + " "));
					continue;
				} catch (ConnectionFaultException e) {
					log.debug((Object) (String.valueOf(messageReceiver.getDestination().getDestationName()) + " " + e.getMessage()));
					continue;
				} catch (Exception e) {
					log.debug((Object) " ");
				}
			}
		}
		this.stopReceive();
	}

	public void stopReceive() {
		for (MessageReceiver messageReceiver : this.messageReceivers) {
			messageReceiver.close();
		}
	}

	public long getDelay() {
		return this.delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public boolean isClose() {
		return this.close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}
}
