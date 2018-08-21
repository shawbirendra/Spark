package com.exil.sparkworks;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import com.exil.commons.SparkConnection;
import com.exil.commons.Utilities;

public class SparkSQLDemo {
	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.ERROR);
		Logger.getLogger("akka").setLevel(Level.ERROR);

		JavaSparkContext sparkContext = SparkConnection.getContext();

		SparkSession session = SparkConnection.getSession();

		Dataset<org.apache.spark.sql.Row> empDataFields = session.read().json("./data/customer.json");

		empDataFields.show();
		empDataFields.printSchema();

		// data queries
		System.out.println("-------  SELECT   DEMO  ---------");
		empDataFields.select(col("name"), col("salary")).show();
		// data queries on selection ( condition )

		System.out.println("--------   Select Demo With Condition  -----------");
		empDataFields.filter(col("gender").equalTo("male")).show();

		System.out.println("-----  AGGREGATE - GROUP BY GENDER ----------");
		empDataFields.groupBy(col("gender")).count().show();

		// group by deptno, average sal, max age
		Dataset<org.apache.spark.sql.Row> summaryData = empDataFields.groupBy(col("deptid"))
				.agg(avg(empDataFields.col("salary")), max(empDataFields.col("age")));
		summaryData.show();

		// bean class
		Department department1 = new Department(100, "Developer");
		Department department2 = new Department(200, "Testing");

		List<Department> deptList = new ArrayList<>();
		deptList.add(department1);
		deptList.add(department2);
		// creating dataset object of row
		Dataset<org.apache.spark.sql.Row> departmentFields = session.createDataFrame(deptList, Department.class);

		System.out.println("------   DEPARTMENT CONTENTS ARE  ---------");
		departmentFields.show();

		// join employee with department
		System.out.println("------   EMPLOYEE DEPARTMENT JOIN ARE  ---------");
		Dataset<org.apache.spark.sql.Row> empDepartmentJoin = empDataFields.join(departmentFields,
				col("id").equalTo(col("deptId")));
		empDepartmentJoin.show();

		//
		empDataFields.filter(col("age").gt(30)).join(departmentFields, col("deptid").equalTo("id"))
				.groupBy(col("deptid")).agg(avg(empDataFields.col("salary")), max(empDataFields.col("age"))).show();

		System.out.println("--------AUTO DATA--------");
		Dataset<org.apache.spark.sql.Row> autodata = session.read().option("header", true).csv("./data/auto-data.csv");
		autodata.show(5);

		// creating RDD with row object
		Row row1 = RowFactory.create(1, "India", "Bengaluru");
		Row row2 = RowFactory.create(2, "USA", "Reston");
		Row row3 = RowFactory.create(3, "UK", "steevenscreek");

		List<Row> rowsList = new ArrayList<>();

		rowsList.add(row1);
		rowsList.add(row2);
		rowsList.add(row3);

		JavaRDD<Row> rowRDD = sparkContext.parallelize(rowsList);

		StructType schema = DataTypes
				.createStructType(new StructField[] { DataTypes.createStructField("id", DataTypes.IntegerType, false),
						DataTypes.createStructField("country", DataTypes.StringType, false),
						DataTypes.createStructField("city", DataTypes.StringType, false) });

		Dataset<Row> tempDataFields = session.createDataFrame(rowRDD, schema);
		tempDataFields.show();

		// working with csv data, with sql statenments provided the data i skept in
		// table like format. the persistence will only till the end of program meaning
		// temporary
		autodata.createOrReplaceTempView("autos");
		System.out.println("-----------   Temp Table Contents  ---------------");
		session.sql("select * from autos where HP > 250").show();
		;

		// to find make, maximum RPM from autos group by make
		session.sql("select make,max(rpm) from autos group by make order by 2").show();

		// convert dataframe to JavaRDD
		Map<String, String> jdbcConnectionParams = new HashMap<String, String>();
		jdbcConnectionParams.put("url", "jdbc:mysql://localhost:3306/exdb");
		jdbcConnectionParams.put("driver", "com.mysql.jdbc.Driver");
		jdbcConnectionParams.put("dbtable", "employee");
		jdbcConnectionParams.put("user", "root");
		jdbcConnectionParams.put("password", "root@123");

		Dataset<Row> sqlDataFields = session.read().format("jdbc").options(jdbcConnectionParams).load();
		sqlDataFields.show();

		Utilities.hold();
	}
}
