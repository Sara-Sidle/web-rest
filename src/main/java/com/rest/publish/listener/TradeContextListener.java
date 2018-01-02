package com.rest.publish.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Repository;

import com.rest.publish.event.TradeEvent;

/**
 * 
 * @author
 * 
 */
@Repository
public class TradeContextListener implements ApplicationListener<TradeEvent> {

	@Override
	public void onApplicationEvent(TradeEvent e) {

//		System.out.println(e.getClass().toString());
//		// TODO Auto-generated method stub
//
//		if (e instanceof TradeEvent) {
//			
//			System.out.println("================AAAAA=========================");
//			System.out.println("TradeContextListener==e" + e.getMsgString());
//			System.out.println("================BBBBB=========================");
//		}
	}
}