package com.rest.util.tibco.ems.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.rest.util.tibco.ems.msg.Destination;
import com.rest.util.tibco.ems.msg.Destination;
import org.apache.log4j.Logger;

import com.rest.util.tibco.ems.msg.Destination;
import com.rest.util.tibco.ems.msg.MessageConnection;
import com.rest.util.tibco.ems.msg.Destination.DestinationType;

/**
 * 
 * @author teamsun.com.cn
 * 
 */
public abstract class AbstractMessageConnection implements MessageConnection {

	/**
	 * 
	 */
	static Logger log = Logger.getLogger(AbstractMessageConnection.class);

	/**
	 * 目的集合
	 */
	private Map<String, Destination> destations = new HashMap<String, Destination>();

	/**
	 * 等级目的集合
	 */
	public boolean registerDestination(Destination destation) {
		synchronized (destations) {
			if (this.destations.containsKey(destation.getDestationName()))
				return false;
			else {
				this.destations.put(destation.getDestationName(), destation);
				return true;
			}
		}
	}

	/**
	 * 
	 */
	public Destination getDefaultDestination(Destination.DestinationType destationType) {
		for (Destination destation : this.destations.values()) {
			if (destation.getDestationType() == destationType && destation.isDefaultDestation())
				return destation;
		}
		return null;
	}

	/**
	 * 
	 */
	public Destination getDestinationByName(String destationName) {
		return this.destations.get(destationName);
	}

	/**
	 * 
	 */
	public Map<String, Destination> getDestinations(Destination.DestinationType destationType) {
		Map<String, Destination> maps = new HashMap<String, Destination>();
		synchronized (destations) {
			for (Destination destation : this.destations.values()) {
				if (destation.getDestationType() == destationType) {
					maps.put(destation.getDestationName(), destation);
				}
			}
		}
		return maps;
	}

	/**
	 */
	public void showAllDestations() {
		for (Destination destation : this.destations.values()) {
			log.debug(" " + destation);
		}
	}

	/**
	 */
	public void setDefaultDestination(Destination destation) {
		registerDestination(destation);
		synchronized (destations) {
			if (this.getDefaultDestination(destation.getDestationType()) == null && this.destations.containsKey(destation.getDestationName())) {
				try {
					Field field = Destination.class.getDeclaredField("defaultDestation");
					field.setAccessible(true);
					field.set(destation, true);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
