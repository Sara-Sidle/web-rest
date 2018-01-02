package com.rest.publish.pulisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Repository;

import com.rest.publish.event.TradeEvent;

/**
 * 
 * @author zq
 * 
 */
@Repository
public class AgentMsgPublisher implements ApplicationEventPublisherAware {
	private String word;
	private ApplicationEventPublisher tradeEventPublisher;

	public void setWord(String w) {
		this.word = w;
	}

	public void say(String msg) {
		System.out.println(msg+" say : " + msg);
		// construct a TradeEvent instance and publish it
		TradeEvent tradeEvent = new TradeEvent(msg);
		this.tradeEventPublisher.publishEvent(tradeEvent);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// TODO Auto-generated method stub
		this.tradeEventPublisher = applicationEventPublisher;
	}
}