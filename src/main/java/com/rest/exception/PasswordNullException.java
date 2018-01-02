package com.rest.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;

/**
 * 自定义异常
 * @author 武庆超
 *
 */
public class PasswordNullException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordNullException() {
		super();
	}

	public PasswordNullException(String msg) {
		super(msg);
	}
}
