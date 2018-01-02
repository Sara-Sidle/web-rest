package com.rest.util.tibco.ems.impl;

import com.rest.util.tibco.ems.core.ConfigBean;

/**
 * 配置
 * 
 * @author
 * 
 */
public class JmsConfig extends ConfigBean implements Cloneable {
	/**
	 * 
	 */
	private String tcpUrl;
	/**
	 * 
	 */
	private String className;
	/**
	 * 
	 */
	private boolean useJndi;
	/**
	 * 
	 */
	private String provider;
	private String url;
	private String username;
	private String password;
	private String lookupName;
	private int reconnectTime;
	private String default_destation_post_name = "";
	private String default_destation_post_realpath = "";
	private String default_destation_receive_name = "";
	private String default_destation_receive_realpath = "";
	private String destations_post_name = "";
	private String destations_post_realpath = "";
	private String destations_receive_name = "";
	private String destations_receive_realpath = "";

	public String getDefault_destation_post_name() {
		return this.default_destation_post_name;
	}

	public void setDefault_destation_post_name(String defaultDestationPostName) {
		this.default_destation_post_name = defaultDestationPostName;
	}

	public String getDefault_destation_post_realpath() {
		return this.default_destation_post_realpath;
	}

	public void setDefault_destation_post_realpath(String defaultDestationPostRealpath) {
		this.default_destation_post_realpath = defaultDestationPostRealpath;
	}

	public String getDefault_destation_receive_name() {
		return this.default_destation_receive_name;
	}

	public void setDefault_destation_receive_name(String defaultDestationReceiveName) {
		this.default_destation_receive_name = defaultDestationReceiveName;
	}

	public String getDefault_destation_receive_realpath() {
		return this.default_destation_receive_realpath;
	}

	public void setDefault_destation_receive_realpath(String defaultDestationReceiveRealpath) {
		this.default_destation_receive_realpath = defaultDestationReceiveRealpath;
	}

	public String getDestations_receive_namesOfSplit(String regex, int index) {
		return this.destations_receive_name.split(regex)[index];
	}

	public String getDestations_receive_realpathsOfSplit(String regex, int index) {
		return this.destations_receive_realpath.split(regex)[index];
	}

	public String getProvider() {
		return this.provider;
	}

	public String getDestations_post_name() {
		return this.destations_post_name;
	}

	public void setDestations_post_name(String destationsPostName) {
		this.destations_post_name = destationsPostName;
	}

	public String getDestations_post_realpath() {
		return this.destations_post_realpath;
	}

	public void setDestations_post_realpath(String destationsPostRealpath) {
		this.destations_post_realpath = destationsPostRealpath;
	}

	public String getDestations_receive_name() {
		return this.destations_receive_name;
	}

	public void setDestations_receive_name(String destationsReceiveName) {
		this.destations_receive_name = destationsReceiveName;
	}

	public String getDestations_receive_realpath() {
		return this.destations_receive_realpath;
	}

	public void setDestations_receive_realpath(String destationsReceiveRealpath) {
		this.destations_receive_realpath = destationsReceiveRealpath;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLookupName() {
		return this.lookupName;
	}

	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}

	public int getReconnectTime() {
		return this.reconnectTime;
	}

	public void setReconnectTime(int reconnectTime) {
		this.reconnectTime = reconnectTime;
	}

	public String getTcpUrl() {
		return this.tcpUrl;
	}

	public void setTcpUrl(String tcpUrl) {
		this.tcpUrl = tcpUrl;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isUseJndi() {
		return this.useJndi;
	}

	public void setUseJndi(boolean useJndi) {
		this.useJndi = useJndi;
	}

	public JmsConfig clone() {
		try {
			return (JmsConfig) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
