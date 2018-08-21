package com.exil.test;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import com.exil.commons.DataSource;
import com.exil.commons.SparkConnection;
import com.exil.commons.Utilities;
import com.exil.sparkworks.CleanseRDDCars;

public class SparkOperationClient {
	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);

		// Only one SparkContext may be active per JVM. You must stop() the active SparkContext before creating a new one
		// A Java-friendly version of SparkContext that returns JavaRDDs and works with Java collections instead of Scala ones.
		JavaSparkContext sparkContext = SparkConnection.getContext();

		// start loading the data
		// 1. load the collection and cache it.
		JavaRDD<Integer> collectionData = DataSource.getCollectionData();
		System.out.println("Total No. of Records : " + collectionData.distinct().count());

		// 2. load the file and cache it.

		// Read a text file from HDFS, a local file system (available on all nodes), or any Hadoop-supported file system URI, and return it as an RDD of Strings.
		JavaRDD<String> autoData = sparkContext.textFile("./data/auto-data");
		System.out.println("Total Data : " + autoData.count());
		System.out.println("------------------------------------------");
		Utilities.printStringRDD(autoData, 10);

		// autoData.saveAsTextFile("data/auto-data-modified.csv");

		// spark transformation conversion from tsv to csv
		JavaRDD<String> data = autoData.map(str -> str.replace("\t", ","));
		System.out.println("CSV Format");
		System.out.println("-------------------------------------");
		Utilities.printStringRDD(data, 5);

		//////// FILTER EXAMPLE ////////////
		String header = autoData.first();
		JavaRDD<String> filterData = autoData.filter(s -> s.equals(header));

		Utilities.printStringRDD(filterData, 5);

		///// Filter those records which has only toyota
		JavaRDD<String> toyotaData = autoData.filter(t -> t.contains("toyota"));

		Utilities.printStringRDD(toyotaData, 5);

		///// Avoid duplicate
		JavaRDD<String> dis = autoData.distinct();

		Utilities.printStringRDD(dis, 5);

		/// to count number of words in the given RDD
		System.out.println("-------using flat map----------");
		JavaRDD<String> words = toyotaData.flatMap(new FlatMapFunction<String, String>() {

			@Override
			public Iterator<String> call(String t) throws Exception {
				return Arrays.asList(t.split("\t")).iterator();

			}

		});

		System.out.println("Toyoto RDD word count : " + words.count());
		System.out.println("---------after cleansing data-----------");
		
		// after cleansing data
		JavaRDD<String> cleanseRDD = autoData.map(new CleanseRDDCars());

		Utilities.printStringRDD(cleanseRDD, 5);

		////// SET OPERATION //////
		JavaRDD<String> words1 = sparkContext.parallelize(Arrays.asList("hello", "how", "are", "you", "today?"));
		JavaRDD<String> words2 = sparkContext.parallelize(Arrays.asList("hello", "how", "were", "yesterday?"));

		System.out.println("      Union Operation - set          ");
		Utilities.printStringRDD(words1.union(words2), 9);
		System.out.println("      Intersection Operation - set          ");
		Utilities.printStringRDD(words1.intersection(words2), 9);

		// find sum of number in the given RDD
		Integer colData=collectionData.reduce((x,y)->x+y);
		System.out.println("sum of given integer is : "+colData);
		
		/////// find average milege in city and hwy
		//JavaRDD<Double> d=autoData.map(new MPGWorks());
		//List<Double> a=d.collect();
	}
}