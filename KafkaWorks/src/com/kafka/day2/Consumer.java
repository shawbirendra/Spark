package com.kafka.day2;

import java.util.Collections;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import kafka.utils.ShutdownableThread;

public class Consumer extends ShutdownableThread {
	private KafkaConsumer<Integer, String> consumer;
	private String topic;

	public Consumer(String name, boolean isInterruptible) {
		super(name, isInterruptible);

		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers",
				KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);

		properties.setProperty("key.deserializer", IntegerDeserializer.class.getName());
		properties.setProperty("value.deserializer", StringDeserializer.class.getName());
		//properties.setProperty("group.id", "MyGroup");
		properties.setProperty("session.timeout.ms", String.valueOf(10000));

		consumer = new KafkaConsumer<>(properties);
		this.topic = name;

	}

	@Override
	public void doWork() {
		System.out.println(this.topic);
		consumer.subscribe(Collections.singleton(this.topic));
		System.out.println("after subscribe");
		System.out.println();
		System.out.println(consumer.listTopics().entrySet());
		
		ConsumerRecords<Integer, String> records = consumer.poll(1000);
		
		System.out.println("after poll");
		System.out.println("records:" + records.count());
		for (ConsumerRecord<Integer, String> temp : records) {
			System.out.println("Received Messaghe Key : " + temp.key() + ", Message : " + temp.value()
					+ ", Partition : " + temp.partition() + ", Topic : " + temp.topic() + ", Time : "
					+ new Date(temp.timestamp()) + ", @Offset: " + temp.offset());
		}
	}

}
