package com.bks.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

	private KafkaTemplate<String, String> kt;

	@Autowired
	public void send(String topic, String payLoad) {
		kt.send(topic, payLoad);
	}
}
