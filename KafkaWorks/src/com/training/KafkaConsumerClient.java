package com.training;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class KafkaConsumerClient {
	public static void main(String[] args) {
		// set all the properties
		// details can be kept in flat file or properties Class
		Properties properties = new Properties();
		// identify the kafka server details
		properties.setProperty("bootstrap.servers", "localhost:9093");
		properties.setProperty("key.deserializer", StringDeserializer.class.getName());
		properties.setProperty("value.deserializer", StringDeserializer.class.getName());

		properties.setProperty("group.id", "First Group");
		properties.setProperty("session.timeout.ms", "30000");
		properties.setProperty("auto.offset.reset", "earliest");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

		consumer.subscribe(Arrays.asList("test_topic2"));

		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(1000);

			System.out.println("Count Number Of Messages Got " + records.count());

			for (ConsumerRecord<String, String> temp : records) {
				System.out.println("Key : " + temp.key() + ", Value : " + temp.value() + ", Partition : "
						+ temp.partition() + ", Topic : " + temp.topic() + ", Time : " + new Date(temp.timestamp()));
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			
		}
		consumer.close();

	}

}
