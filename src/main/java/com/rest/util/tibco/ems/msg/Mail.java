package com.rest.util.tibco.ems.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author teamsun.com.cn
 * 
 */
public class Mail {

	/**
	 * mail.addStringProperty(...)
	 */
	public static final String MAIL_ENCODING = "MAIL_CONTENT_ENCODING";

	private long timeStamp;

	private String mailText = "";

	private String bodyName = "";

	private Map<String, PropertyPair> customerProperties = new HashMap<String, PropertyPair>();

	public Mail() {

	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void addStringProperty(String name, String value) {
		PropertyPair pair = new PropertyPair();
		pair.setName(name);
		pair.setValue(value);

		this.customerProperties.put(name, pair);
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getStringPropertyNames() {
		List<String> list = new ArrayList<String>();

		for (PropertyPair pair : this.customerProperties.values()) {
			if (pair.getValue() instanceof String) {
				list.add(pair.getName());
			}
		}

		return list;
	}

	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public String getStringProperty(String name) {
		PropertyPair pair = this.customerProperties.get(name);

		if (pair != null && pair.getValue() instanceof String) {
			return (String) pair.getValue();
		}

		return null;
	}

	public Mail(String text) {
		this.mailText = text;
	}

	public String getMailText() {
		return mailText;
	}

	public void setMailText(String mailText) {
		this.mailText = mailText;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * 
	 * @author teamsun.com.cn
	 * 
	 */
	private static class PropertyPair {

		private String name;

		private Object value;

		/**
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 
		 * 
		 * @return
		 */
		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" " + name + " " + value + "\n");
			return buffer.toString();
		}

	}

	public String getBodyName() {
		return bodyName;
	}

	public void setBodyName(String bodyName) {
		this.bodyName = bodyName;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("------ -----\n");
		buffer.append(" " + this.getBodyName() + "\n");
		buffer.append(" " + this.getMailText() + "\n");
		if (this.customerProperties.size() > 0) {
			buffer.append("  \n");
			for (String name : this.customerProperties.keySet()) {
				buffer.append("  " + this.customerProperties.get(name));
			}
		} else {
			buffer.append(" ");
		}
		return buffer.toString();
	}
}
