package com.rest.publish.event;

import org.springframework.context.ApplicationEvent;

public class TradeEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TradeEvent(Object source) {
		super(source);
		msgString = source.toString();
		System.out.println("事件：TradeEvent event !!source=" + source.toString());
		// TODO Auto-generated constructor stub
	}

	private String msgString = null;

	public String getMsgString() {
		return msgString;
	}

	public void setMsgString(String msgString) {
		this.msgString = msgString;
	}

}
