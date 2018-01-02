/*
 * Decompiled with CFR 0_108.
 * 
 * Could not load the following classes:
 *  com.raising.system.utils.tibco.ems.msg.Mail
 *  com.raising.system.utils.tibco.ems.msg.ReceiveMessageService
 *  com.raising.system.utils.tibco.ems.msg.ReceiveMessageServiceImpl
 *  org.apache.log4j.Logger
 */
package com.rest.util.tibco.ems.msg;

import org.apache.log4j.Logger;

public class ReceiveMessageServiceImpl implements ReceiveMessageService {
	static Logger log = Logger.getLogger((Class) ReceiveMessageServiceImpl.class);
	private Mail mail;

	public Mail getMail() {
		return this.mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public boolean receiveMessage(Mail mail) {
		this.mail = mail;
		log.info((Object) ("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u03e2=========>" + mail.getMailText()));
		return true;
	}
}
