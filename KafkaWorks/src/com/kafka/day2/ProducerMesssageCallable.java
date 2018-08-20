package com.kafka.day2;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerMesssageCallable implements Callback {

	private long startTime;
	private int key;
	private String message;

	public ProducerMesssageCallable(long startTime, int key, String message) {
		super();
		this.startTime = startTime;
		this.key = key;
		this.message = message;
	}

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		// when the message is sent
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			System.out.println("Message : " + message + " with key : " + key + ", sent to " + metadata.partition()
					+ ", offset : " + metadata.offset() + ", Time Taken : " + elapsedTime + " ms");
		} else {
			exception.printStackTrace();
		}

	}

}
