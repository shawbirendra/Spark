package com.exil.commons;

import org.apache.spark.api.java.JavaRDD;

public class Utilities {

	public static void printStringRDD(JavaRDD<String> stringRDD, int limit) {
		for (String temp : stringRDD.take(limit)) {
			System.out.println(temp);
		}
		System.out.println("-------------------------------------------------");
	}

	// this method to keep java running, so that we can explore spark instance
	public static void hold() {
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
