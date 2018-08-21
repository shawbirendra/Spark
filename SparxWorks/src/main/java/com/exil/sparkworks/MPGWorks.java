//package com.exil.sparkworks;
//
//import org.apache.spark.api.java.function.Function;
//
//public class MPGWorks implements Function<String,String,String> {
//
//	Double totalMPGCity;
//	Double totalMPGHwy;
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public Double call(String args0,String args1) throws Exception {
//		String[] attributeList = v1.split("\t");
//		totalMPGCity = Double.parseDouble(attributeList[9]);
//		totalMPGHwy = Double.parseDouble(attributeList[10]);
//		return totalMPGHwy;
//	}
//	public double getAvgMPGCity(int count) {
//		return totalMPGCity/count;	
//	}
//
//}
