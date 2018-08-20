package com.kafka.day2;

public interface KafkaProperties {

	String TOPIC_1 = "test_topic";
	String TOPIC_2 = "test_topic1";
	String TOPIC_3 = "test_topic2";

	String KAFKA_SERVER_URL = "localhost";
	int KAFKA_SERVER_PORT = 9092;
	int KAFKA_PRODUCER_BUFFER_SIZE = 100 * 1024;
	int CONNECTION_TIME_OUT = 1000 * 10 * 10;

	String CLIENT_ID = "SimpleConsumerDemoClient";
	String PRODUCER_CLIENT_ID = "DemoProducer";

}
