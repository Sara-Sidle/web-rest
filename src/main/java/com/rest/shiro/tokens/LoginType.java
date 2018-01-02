package com.rest.shiro.tokens;

/**
 * 登录类型<br>
 * 手机号密码登录，手机号短信登录，代理商账户密码登录
 * 
 * @author
 * 
 */

public enum LoginType {

	PHONESMS("PhoneSMS"), PHONEPWD("PhonePwd"), ACCOUNTPWD("AccountPwd");

	private String type;

	private LoginType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type.toString();
	}
	
	
	public static void main(String[] args) {
		 System.out.println(LoginType.PHONEPWD.toString());
		 
	}
}