package com.employee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SCIMResponseObject {
	@JsonProperty("totalResults") int totalResults;
	@JsonIgnore int startIndex;
	@JsonIgnore int itemsPerPage;
	@JsonIgnore String[] schemas;
	@JsonProperty("Resources") EmployeeResponse[] Resources;
	
	public SCIMResponseObject() {}
	
	public SCIMResponseObject(int totalResults, int startIndex, int itemsPerPage, String[] schemas, EmployeeResponse[] resources) {
		super();
		this.totalResults = totalResults;
		this.startIndex = startIndex;
		this.itemsPerPage = itemsPerPage;
		this.schemas = schemas;
		Resources = resources;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public String[] getSchemas() {
		return schemas;
	}
	public void setSchemas(String[] schemas) {
		this.schemas = schemas;
	}
	public EmployeeResponse[] getResources() {
		return Resources;
	}
	public void setResources(EmployeeResponse[] resources) {
		Resources = resources;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	
}
