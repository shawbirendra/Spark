package com.exil.sparkworks;

public class Department {

	private int id;
	private String deptName;
	public Department() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
	
	public Department(int id, String deptName) {
		super();
		this.id = id;
		this.deptName = deptName;
	}
	

	
	
}
