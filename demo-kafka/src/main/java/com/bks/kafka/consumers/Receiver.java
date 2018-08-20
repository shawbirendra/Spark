package com.bks.kafka.consumers;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;

public class Receiver {
	private CountDownLatch latch = new CountDownLatch(1);

	public CountDownLatch getLatch() {
		return latch;
	}

	@KafkaListener(topics = "${kafka.topic.test_topic}")
	public void receive(String payload) {
		latch.countDown();
	}
}
