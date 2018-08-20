package com.training;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * https://kafka.apache.org/documentation/
 * 
 * @author birendra.ks
 *
 */
public class KafkaProducerClient {
	public static void main(String[] args) {
		// set all the properties
		// details can be kept in flat file or properties Class
		Properties properties = new Properties();
		// identify the kafka server details
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", StringSerializer.class.getName());

		// ------------for ack-----------------
		// when producer sends we can ask for ack
		properties.setProperty("acks", "1");
		properties.setProperty("retries", "3");

		Producer<String, String> producer = new KafkaProducer<>(properties);

		ProducerRecord<String, String> record = new ProducerRecord<String, String>("test_topic1", "1",
				"Testing message from java");
		producer.send(record);

		producer.flush();
		producer.close();

		// close the connection
		
		System.out.println("Message Sent....");
	}
}
