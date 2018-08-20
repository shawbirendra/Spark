package com.kafka.day2;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer extends Thread {
	private final KafkaProducer<Integer, String> producer;
	private String topic;
	private boolean isAsync;
	private boolean interrupt;
	private String name;

	public Producer(String topic, Boolean isAsync, String name) {
		this.topic = topic;
		this.isAsync = isAsync;
		this.interrupt = false;
		this.name = name;
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers",
				KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);

		properties.setProperty("client.id", KafkaProperties.PRODUCER_CLIENT_ID);

		properties.setProperty("key.serializer", IntegerSerializer.class.getName());
		properties.setProperty("value.serializer", StringSerializer.class.getName());

		producer = new KafkaProducer<>(properties);

	}

	@Override
	public void run() {
		// produce either synch or asynch
		int messageNo = 1;
		while (!interrupt) {
			String messageString = "Message from Producer " + this.name + " Count : " + messageNo;

			if (isAsync) {
				// send asynchronously
				ProducerRecord<Integer, String> producerRecord = new ProducerRecord<Integer, String>(this.topic,
						messageNo, messageString);
				long startTime = System.currentTimeMillis();
				producer.send(producerRecord, new ProducerMesssageCallable(startTime, messageNo, messageString));
			} else {
				// synchronous
				try {
					ProducerRecord<Integer, String> producerRecord = new ProducerRecord<Integer, String>(this.topic,
							messageNo, messageString);
					producer.send(producerRecord).get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			messageNo++;
		}//end of while

	}

	public void setInterrupt(boolean interrupt) {
		this.interrupt = interrupt;
	}
}
