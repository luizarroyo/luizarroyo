package com.ing.sql.repository;

import java.io.Serializable;

public class UserAccess implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String profileName;
	String description;
	String hostName;
	String databaseName;
	String updateAllowed;
	int numberOfRecordsPerQuery;
	String inquiryUpdate;
	
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getUpdateAllowed() {
		return updateAllowed;
	}
	public void setUpdateAllowed(String updateAllowed) {
		this.updateAllowed = updateAllowed;
	}
	public int getNumberOfRecordsPerQuery() {
		return numberOfRecordsPerQuery;
	}
	public void setNumberOfRecordsPerQuery(int numberOfRecordsPerQuery) {
		this.numberOfRecordsPerQuery = numberOfRecordsPerQuery;
	}
	public String getInquiryUpdate() {
		return inquiryUpdate;
	}
	public void setInquiryUpdate(String inquiryUpdate) {
		this.inquiryUpdate = inquiryUpdate;
	}

}
