package com.skillstorm.models;

public class Department {
	int id;
	String name;
	String description;
	
	public Department(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Department(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
