package com.exil.commons;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class DataSource {

	// simulating the data is pulled from some resources
	public static JavaRDD<Integer> getCollectionData() {
		JavaSparkContext sparkContext = SparkConnection.getContext();

		List<Integer> data = Arrays.asList(1, 5, 3);
		JavaRDD<Integer> collectionData = sparkContext.parallelize(data);

		collectionData.cache();

		return collectionData;
	}
}
