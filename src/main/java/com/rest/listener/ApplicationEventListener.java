package com.rest.listener;

import com.rest.publish.event.TradeEvent;
import com.rest.publish.event.TradeEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Repository;

import com.rest.publish.event.TradeEvent;

/**
 * 
 * @author
 * 
 */
@Repository
public class ApplicationEventListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		// 如果是容器刷新事件
		if (event instanceof ContextClosedEvent) {
			System.out.println(event.getClass().getSimpleName() + " 事件已发生！");
		} else if (event instanceof ContextRefreshedEvent) {// 如果是容器关闭事件
			System.out.println(event.getClass().getSimpleName() + " 事件已发生！");
		} else if (event instanceof ContextStartedEvent) {
			System.out.println(event.getClass().getSimpleName() + " 事件已发生！");
		} else if (event instanceof ContextStoppedEvent) {
			System.out.println(event.getClass().getSimpleName() + " 事件已发生！");
		} else if (event instanceof TradeEvent) {
			System.out.println(event.getClass().getSimpleName() + " 事件已发生！");
			System.out.println(((TradeEvent) event).getMsgString() + " 值==");
		} else {
			System.out.println("有其它事件发生:" + event.getClass().getName());
		}

	}

}
