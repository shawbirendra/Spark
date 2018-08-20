package com.kafka.day2;

public class ClientConsumer {
	public static void main(String[] args) {
		Consumer consumer = new Consumer(KafkaProperties.TOPIC_1, false);
		consumer.start();
	}
}
