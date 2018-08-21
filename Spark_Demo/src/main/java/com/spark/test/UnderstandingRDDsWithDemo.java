package com.spark.test;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import com.spark.configs.SparkContextConfig;

/**
 * 
 * @author birendra.ks
 * 
 *         RDD-->Resilient Distributed Datasets (RDD) is a fundamental data
 *         structure of Spark. It is an immutable distributed collection of
 *         objects. Each dataset in RDD is divided into logical partitions,
 *         which may be computed on different nodes of the cluster. RDDs can
 *         contain any type of Python, Java, or Scala objects, including
 *         user-defined classes.
 * 
 *         RDD ----> There are two ways to create RDDs − parallelizing an
 *         existing collection in your driver program, or referencing a dataset
 *         in an external storage system, such as a shared file system, HDFS,
 *         HBase, or any data source offering a Hadoop Input Format.
 */
public class UnderstandingRDDsWithDemo {
	public static void main(String[] args) {
		JavaSparkContext context = SparkContextConfig.getContext();

		List<Integer> ints = Arrays.asList(1, 5, 10, 2, 3, 15, 4);

		/** Distribute a local Scala collection to form an RDD. */
		/**
		 * 1. parallelizing an existing collection
		 */
		JavaRDD<Integer> rddINT = context.parallelize(ints);

		System.out.println("Sum Of Available ints : " + rddINT.reduce((x, y) -> x + y));

		// System.out.println("Total Count Of element in RDD : " + rddINT.count());
		// System.out.println("---------------------------------------------------");
		// System.out.println("first Element in RDD : " + rddINT.first());

		/**
		 * 2. referencing a dataset in an external storage system, such as a shared file
		 * system, HDFS, HBase
		 */
		JavaRDD<String> datasetFromFile = context.textFile("./data/namelist.txt");
		/**
		 * Only Action on RDDs
		 */
		System.out.println("Total Names : " + datasetFromFile.count());
		/**
		 * Transformations as well as Action on RDDs
		 */
		System.out.println("Total Names Starts With 'B' : " + datasetFromFile.filter(s -> s.startsWith("B")).count());

	}
}
