package com.spark.configs;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class SparkContextConfig {
	// ---------------------------------------------------------------------------------------------------------
	private static String appName = "SparkDemoApp";
	/**
	 * Name of Spark AppName that will appear @ Spark UI
	 */
	// ---------------------------------------------------------------------------------------------------------

	private static String sparkMaster = "local[2]";
	/**
	 * The master URL to connect to, such as "local" to run locally with one thread,
	 * "local[4]" to run locally with 4 cores, or "spark://master:7077" to run on a
	 * Spark standalone cluster.
	 */
	// ---------------------------------------------------------------------------------------------------------

	static JavaSparkContext context = null;
	/**
	 * A Java-friendly version of [[org.apache.spark.SparkContext]] that returns
	 * [[org.apache.spark.api.java.JavaRDD]]s and works with Java collections
	 * instead of Scala ones.
	 *
	 * Only one SparkContext may be active per JVM. You must `stop()` the active
	 * SparkContext before creating a new one. This limitation may eventually be
	 * removed; see SPARK-2243 for more details.
	 */
	// ---------------------------------------------------------------------------------------------------------

	static SparkSession session = null;

	/** The entry point to programming Spark with the Dataset and DataFrame API. */
	// ---------------------------------------------------------------------------------------------------------

	public static void getConnection() {
		SparkConf conf = new SparkConf().setAppName(appName).setMaster(sparkMaster);
		if (context == null) {
			context = new JavaSparkContext(conf);
			session = SparkSession.builder().appName(appName).master(sparkMaster).getOrCreate();

		}
	}// closed getConnection()

	public static JavaSparkContext getContext() {
		if (context == null) {
			getConnection();
		}
		return context;
	}// closed getContext()

	public SparkSession getSession() {
		if (session == null) {
			getConnection();
		}
		return session;
	}// closed getSession()

}// end of Class
