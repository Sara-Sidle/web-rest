package com.rest.util.tibco.ems.exception;

public class ConnectionFaultException extends Exception {
	private static final long serialVersionUID = 1;

	public ConnectionFaultException() {
	}

	public ConnectionFaultException(String object) {
		super(object);
	}
}
