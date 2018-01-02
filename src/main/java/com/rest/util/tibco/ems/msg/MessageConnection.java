package com.rest.util.tibco.ems.msg;

import java.util.Map;

import com.rest.util.tibco.ems.msg.Destination.DestinationType;

/**
 * 
 * 
 * <p>
 * 
 * </p>
 * 
 * 
 * 
 * @author teamsun.com.cn
 * @see gov.chinapost.ems.msg.adapter.core.MessageConnectionFactory
 * @see gov.chinapost.ems.msg.adapter.PostMessageFaultService
 * @see gov.chinapost.ems.msg.adapter.ReceiveMessageService
 */
public interface MessageConnection {

	/**
	 * 
	 * 
	 * @param mail
	 * 
	 */
	void postMessage(Mail mail);

	/**
	 * 
	 * 
	 * @param destation
	 *            * @param mail
	 * 
	 */
	void postMessage(Destination destation, Mail mail);

	/**
	 * 
	 * 
	 * @param postMsg
	 */
	void postMessage(PostMessageService postMsg);

	/**
	 * 
	 * 
	 * @param destation
	 * @param postMsg
	 */
	void postMessage(Destination destation, PostMessageService postMsg);

	/**
	 * 
	 * 
	 * @param receMsg
	 * @see gov.chinapost.ems.msg.adapter.ReceiveMessageService
	 */
	void registerReceiveMessageService(ReceiveMessageService receMsg);

	/**
	 * 
	 * 
	 * @param destation
	 * @param receMsg
	 * @see gov.chinapost.ems.msg.adapter.ReceiveMessageService
	 */
	void registerReceiveMessageService(Destination destation, ReceiveMessageService receMsg);

	/**
	 * 
	 * @param destation
	 * @param receMsg
	 * @param charset
	 * 
	 */
	void registerReceiveMessageService(Destination destation, ReceiveMessageService receMsg, String charset);

	/**
	 * 
	 * @param faultService
	 * @param destation
	 * @see gov.chinapost.ems.msg.adapter.PostMessageFaultService
	 */
	void registerPostMessageFaultService(PostMessageFaultService faultService);

	/**
	 * 
	 * @param destation
	 * @return
	 */
	boolean registerDestination(Destination destation);

	/**
	 * 
	 * @param destationType
	 * @return
	 */
	Map<String, Destination> getDestinations(Destination.DestinationType destationType);

	/**
	 * 
	 * @param destationName
	 * @return
	 */
	Destination getDestinationByName(String destationName);

	/**
	 * 
	 * @param destationType
	 * @return
	 */
	Destination getDefaultDestination(Destination.DestinationType destationType);

	/**
	 * 
	 * @param destation
	 */
	void setDefaultDestination(Destination destation);

	/**
	 */
	void close();

}
