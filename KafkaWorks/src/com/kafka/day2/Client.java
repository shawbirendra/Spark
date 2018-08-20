package com.kafka.day2;

public class Client {
	public static void main(String[] args) {
		Boolean isAsync = true;
		Producer producer = new Producer(KafkaProperties.TOPIC_1, isAsync, "FirstTest");
		producer.start();

		System.out.println("Started Producer ");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		producer.setInterrupt(true);
	}
}
