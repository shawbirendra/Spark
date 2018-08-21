package com.exil.commons;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class SparkConnection {
	/** Set a name for your application. Shown in the Spark web UI. */
	private static String appName = "sampleApp";
	/**
	 * The master URL to connect to, such as "local" to run locally with one thread,
	 * "local[4]" to run locally with 4 cores, or "spark://master:7077" to run on a
	 * Spark standalone cluster.
	 */
	private static String sparkMaster = "local[2]";

	private static JavaSparkContext sparkContext = null;

	private static SparkSession sparkSession = null;

	private static String tempDir = "file://Users/birendra.ks/Desktop/spark-warehouse";

	private static void getConnection() {
		if (sparkContext == null) {
			SparkConf conf = new SparkConf().setAppName(appName).setMaster(sparkMaster);
			sparkContext = new JavaSparkContext(conf);
			sparkSession = SparkSession.builder().appName(appName).master(sparkMaster)
					.config("sparkl.sql.warehouse.dir", tempDir).getOrCreate();
		}
	}

	public static JavaSparkContext getContext() {
		if (sparkContext == null) {
			getConnection();
		}
		return sparkContext;
	}

	public static SparkSession getSession() {
		if (sparkSession == null) {
			getConnection();
		}
		return sparkSession;
	}

}
