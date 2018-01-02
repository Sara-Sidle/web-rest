package com.rest.util.tibco.ems.msg;

/**
 * </p>目的
 * 
 * @see gov.chinapost.ems.msg.adapter.Destination
 * @author teamsun.com.cn
 */
public class Destination {

	/**
	 * 发送目的
	 * <br>
	 * 接收目的
	 * @author teamsun.com.cn
	 * 
	 */
	static public enum DestinationType {
		DESTINATION_POST, DESTINATION_RECEIVE
	}

	/**
	 * 
	 */
	private String destationName;

	/**
	 * 
	 */
	private String destationRealPath;

	/**
	 * 
	 */
	private DestinationType destationType;

	/**
	 * 是否默认缺省
	 */
	private boolean defaultDestation;

	/**
	 * 
	 */
	public Destination() {
	}

	/**
	 * 
	 * @param destationType
	 */
	public Destination(DestinationType destationType) {
		super();
		this.destationType = destationType;
	}

	/**
	 * 
	 * @param destationName
	 * @param destationRealPath
	 * @param destationType
	 */
	public Destination(String destationName, String destationRealPath, DestinationType destationType) {
		super();
		this.destationName = destationName;
		this.destationRealPath = destationRealPath;
		this.destationType = destationType;
	}

	/**
	 * 
	 */
	public String getDestationName() {
		return destationName;
	}

	/**
	 * @param destationName
	 */
	public void setDestationName(String destationName) {
		this.destationName = destationName;
	}

	/**
	 * 
	 * @return
	 */
	public String getDestationRealPath() {
		return destationRealPath;
	}

	/**
	 * 
	 * @param destationRealPath
	 */
	public void setDestationRealPath(String destationRealPath) {
		this.destationRealPath = destationRealPath;
	}

	/**
	 * 
	 * @return
	 */
	public DestinationType getDestationType() {
		return destationType;
	}

	/**
	 * 
	 * @param destationType
	 */
	public void setDestationType(DestinationType destationType) {
		this.destationType = destationType;
	}

	/**
	 * 
	 */
	public boolean isDefaultDestation() {
		return defaultDestation;
	}

	@Override
	public String toString() {
		return "�Ѻ����[" + this.destationName + "] ʵ�ʵ�ַ��[" + this.getDestationRealPath() + "] �շ����[" + this.destationType + "] �Ƿ�Ĭ��:[" + this.isDefaultDestation() + "]";
	}

}
