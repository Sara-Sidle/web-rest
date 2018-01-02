package com.rest.shiro.tokens;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 
 * @author
 * 
 * @date 2018-1-25
 */
public class MyExUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = -6791577552546396582L;

	/**
	 * 用户代码
	 */
	private String username;
	
	/**
	 * 公司id
	 */
	private Integer mobile;
	
	/**
	 * 机构部门ID
	 */
	private Integer agency_id;
	/**
	 * 登录类型<br>
	 * 手机号密码登录，手机号短信登录，代理商账户密码登录
	 */
	private String loginType;

	/**
	 * 
	 */
	private String captche;

	/**
	 * 各种构造函数
	 * 
	 * @param username
	 * @param password
	 */
	public MyExUsernamePasswordToken(String username, String password) {
		super(username, password);
		this.username = username;
	}

	/**
	 * 各种构造函数
	 * 
	 * @param username
	 * @param password
	 * @param userType
	 */
	public MyExUsernamePasswordToken(String username, String password, String loginType) {
		super(username, password);
		this.username = username;
		this.loginType = loginType;
	}

	/**
	 * 各种构造函数
	 * 
	 * @param username
	 * @param password
	 * @param captche
	 * @param userType
	 */
	public MyExUsernamePasswordToken(String username, String password, String captche, String loginType) {
		super(username, password);
		this.loginType = loginType;
		this.captche = captche;
	}


	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getCaptche() {
		return this.captche;
	}

	public void setCaptche(String captche) {
		this.captche = captche;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public Integer getAgency_id() {
		return agency_id;
	}

	public void setAgency_id(Integer agency_id) {
		this.agency_id = agency_id;
	}

}
