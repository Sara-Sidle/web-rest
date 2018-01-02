package com.rest.util.tibco.ems.impl;

import com.rest.util.tibco.ems.msg.Destination;

public class JmsDestation extends Destination {
	private boolean topic;

	public boolean isTopic() {
		return this.topic;
	}

	public void setTopic(boolean topic) {
		this.topic = topic;
	}
}
