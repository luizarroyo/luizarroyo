package com.ing.sql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PARAMETERS")
public class Parameter {
	
	@Id
	@GeneratedValue
	@Column(name = "ID", insertable = false)
	int id;
	@Column(name = "HOST_NAME", nullable = false)
	String hostName;
	@Column(name = "LIMIT_CONCURRENT_USERS", nullable = false)
	int limitConcurrentUsers;
	@Column(name = "NUMBER_OF_OFFLINE_QUERIES", nullable = false)
	int numberOfOfflineQueries;
	@Column(name = "MAX_NUMBER_OF_RECORDS_RETURNED", nullable = false)
	int maxNumberOfRecordsReturned;
	@Column(name = "CONNECTION_TIMEOUT", nullable = false)
	int connectionTimeout;
	@Column(name = "RESULT_FOLDER", nullable = false)
	String resultFolder;
	@Column(name = "RETENTION_OFFLINE_QUERY_FILES", nullable = false)
	int retentionOfflineQueryFiles;
	@Column(name = "OFFLINE_RUN_ALL_DAY", nullable = false)
	int offlineRunAllDay;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLimitConcurrentUsers() {
		return limitConcurrentUsers;
	}
	public void setLimitConcurrentUsers(int limitConcurrentUsers) {
		this.limitConcurrentUsers = limitConcurrentUsers;
	}
	public int getNumberOfOfflineQueries() {
		return numberOfOfflineQueries;
	}
	public void setNumberOfOfflineQueries(int numberOfOfflineQueries) {
		this.numberOfOfflineQueries = numberOfOfflineQueries;
	}
	public int getMaxNumberOfRecordsReturned() {
		return maxNumberOfRecordsReturned;
	}
	public void setMaxNumberOfRecordsReturned(int maxNumberOfRecordsReturned) {
		this.maxNumberOfRecordsReturned = maxNumberOfRecordsReturned;
	}
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getResultFolder() {
		return resultFolder;
	}
	public void setResultFolder(String resultFolder) {
		this.resultFolder = resultFolder;
	}
	public int getRetentionOfflineQueryFiles() {
		return retentionOfflineQueryFiles;
	}
	public void setRetentionOfflineQueryFiles(int retentionOfflineQueryFiles) {
		this.retentionOfflineQueryFiles = retentionOfflineQueryFiles;
	}
	public int getOfflineRunAllDay() {
		return offlineRunAllDay;
	}
	public void setOfflineRunAllDay(int offlineRunAllDay) {
		this.offlineRunAllDay = offlineRunAllDay;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
