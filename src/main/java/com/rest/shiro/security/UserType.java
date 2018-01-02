package com.rest.shiro.security;

/**
 * 用户登录类型的枚举
 * 
 * @author <a href="mailto:zhangzhilin.r@gmail.com">d407</a>
 * 
 */
public enum UserType {
	MANAGER("管理员"), AGENT("代理商"), CLIENT("商户"), OTHER("其他");

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private UserType(String desc) {
		this.desc = desc;
	}
}
