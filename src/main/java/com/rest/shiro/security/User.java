package com.rest.shiro.security;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = -6791577552546396582L;
	/**
	 * 基本属性<br>
	 * 系统用户ID
	 **/
	private Integer id; // int(11) NOT NULL AUTO_INCREMENT // COMMENT '系统用户ID',
	/**
	 * 属于哪个顶级机构的代码
	 */
	private String branchId; //
	/** 属于哪个顶级机构的名称 **/
	private String branchName; //
	/** 属于哪个直屬机构的代码 **/
	private String preBranchid; //

	/** 属于哪个直屬机构的名称 **/
	private String preBranchName; //

	/** 用户登陆名 **/
	private String loginName; // varchar(40) NOT NULL COMMENT '用户登陆名',
	/** 修改信息用的 **/
	private String oldloginname; // 修改信息用的
	/** 真实姓名 **/
	private String realName; // 真实姓名
	/** 登陆密码 **/
	private String password; // varchar(40) DEFAULT NULL COMMENT
	/** 手机号码 **/
	private String mobile; //

	/** 省份 **/
	private String province; // varchar(20) DEFAULT NULL COMMENT
	/** '城市' **/
	private String city; // varchar(20) DEFAULT NULL COMMENT
	/** 是否锁定 **/
	private Integer locked;
	/** 登录错误次数 **/
	private Integer loginerrors; //
	/** 是否管理员 **/
	private String isadmin; //
	// 20140904应翔
	/** 机构级别 **/
	private String branchlevel; // ;

	/** 角色列表 **/
	private List<String> group;
	/** 主题 **/
	private String theme; //
	/**  **/
	private String pid;
	/**  **/
	private String name;
	/** 用于脱敏处理的标识字段，如果为1，不脱敏；其他--脱敏 **/
	private String qtflag;
	/** 登录ip地址 **/
	private String ip;

	/** 密码设置日期 **/
	private String setpw_date;
	/** 失效日期 DISABLE_DATE **/
	private String disable_date;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	/** 用户类型 **/
	private UserType userType; //
	/** 用户类型字符 **/
	@SuppressWarnings("unused")
	private String userTypeChar; //
	/** 用户本身的代码，如果是代理商，则是机构代码，如果是商户，则是的商户代码 **/
	private String usercode; //
	/** 修改信息用的 **/
	private String oldbranchid; //

	/**  **/
	private String userBranchName; // 用户本身的名称，如果是代理商登录，则是代理商名称，如果是有了登录，则显示的"优乐"
	// 管理员用户可以有多个角色组，外网普通用户只能有一个组。而且，不管组是什么，都是定死的。

	// 如果是内部管理用户，branchId，preBranchid，usercode 全部的是8个0，即00000000

	/** 角色组ID **/
	private Integer groupid; //

	/** 角色组ID **/
	private String groupName; //

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOldbranchid() {
		return oldbranchid;
	}

	public void setOldbranchid(String oldbranchid) {
		this.oldbranchid = oldbranchid;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getPreBranchid() {
		return preBranchid;
	}

	public void setPreBranchid(String preBranchid) {
		this.preBranchid = preBranchid;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	/**
	 * 获得用户所属类别
	 * 
	 * @return
	 */
	public String getUserTypeChar() {
		String myuserTypeChar = "";
		if (this.userType != null)
			switch (this.userType) {
			case MANAGER:
				myuserTypeChar = "youlink";
				break;
			case AGENT:
				myuserTypeChar = "agent";
				break;
			case CLIENT:
				myuserTypeChar = "client";
				break;
			default:
				break;
			}
		return myuserTypeChar;
	}

	public void setUserTypeChar(String userTypeChar) {
		this.userTypeChar = userTypeChar;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPreBranchName() {
		return preBranchName;
	}

	public void setPreBranchName(String preBranchName) {
		this.preBranchName = preBranchName;
	}

	public String getUserBranchName() {
		return userBranchName;
	}

	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Integer getLoginerrors() {
		return loginerrors;
	}

	public void setLoginerrors(Integer loginerrors) {
		this.loginerrors = loginerrors;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBranchlevel() {
		return branchlevel;
	}

	public void setBranchlevel(String branchlevel) {
		this.branchlevel = branchlevel;
	}

	public String getOldloginname() {
		return oldloginname;
	}

	public void setOldloginname(String oldloginname) {
		this.oldloginname = oldloginname;
	}

	public List<String> getGroup() {
		return group;
	}

	public void setGroup(List<String> group) {
		this.group = group;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQtflag() {
		return qtflag;
	}

	public void setQtflag(String qtflag) {
		this.qtflag = qtflag;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSetpw_date() {
		return setpw_date;
	}

	public void setSetpw_date(String setpw_date) {
		this.setpw_date = setpw_date;
	}

	public String getDisable_date() {
		return disable_date;
	}

	public void setDisable_date(String disable_date) {
		this.disable_date = disable_date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", branchId=" + branchId + ", branchName=" + branchName + ", preBranchid=" + preBranchid + ", preBranchName=" + preBranchName + ", loginName=" + loginName
				+ ", oldloginname=" + oldloginname + ", realName=" + realName + ", password=" + password + ", mobile=" + mobile + ", province=" + province + ", city=" + city + ", locked=" + locked
				+ ", loginerrors=" + loginerrors + ", isadmin=" + isadmin + ", branchlevel=" + branchlevel + ", group=" + group + ", theme=" + theme + ", pid=" + pid + ", name=" + name + ", qtflag="
				+ qtflag + ", ip=" + ip + ", userType=" + userType + ", userTypeChar=" + userTypeChar + ", usercode=" + usercode + ", oldbranchid=" + oldbranchid + ", userBranchName="
				+ userBranchName + ", groupid=" + groupid + ", groupName=" + groupName + "]";
	}

}