package com.rest.util.tibco.ems.core;

public class ConfigBean {
	private String encoding;
	private String config;
	private boolean debug;

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public String getConfig() {
		return this.config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public boolean isDebug() {
		return this.debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
