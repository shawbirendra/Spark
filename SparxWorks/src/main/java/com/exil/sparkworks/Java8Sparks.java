package com.exil.sparkworks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class Java8Sparks {
	public static void main(String[] args) {
		// Stream.of(1, 2, 3)
		// .peek(n -> System.out.println("Peeked at: " + n))
		// .map(n -> n * n)
		// .forEach(System.out::println);

		SparkConf conf = new SparkConf().setAppName("test").setMaster("local[3]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> squares = sc.parallelize(Arrays.asList(1, 2, 3)).map(n -> n * n).collect();	
		System.out.println(squares.toString());

		// Rough equivalent using Java Streams
		List<Integer> squares2 = Stream.of(1, 2, 3).map(n -> n * n).collect(Collectors.toList());

		System.out.println(squares2.toString());
		
		while(true) {
			
		}
	}
}
