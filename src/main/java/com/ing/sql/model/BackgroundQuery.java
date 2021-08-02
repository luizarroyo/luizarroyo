package com.ing.sql.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OFFLINE_QUERIES")
public class BackgroundQuery implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "ID", insertable = false)
	int id;
	@Column(name = "USER_ID",length = 6, nullable = false)
	String userId;
	@Column(name = "CREATED_DATE", insertable = false)
	Timestamp createdDate;
	@Column(name = "REQUESTED_DATE", insertable = false)
	Timestamp requestedDate;
	@Column(name = "PROCESSED_DATE")
	Timestamp processedDate;
	@Column(name = "SCHEDULE_ID", insertable = false)
	Integer scheduleId;
	@Column(name = "HOST_NAME")
	String HostName;
	@Column(name = "DATABASE_NAME")
	String databaseName;
	@Column(name = "SQL_STATEMENT",length = 17)
	String SqlStatement;
	@Column(name = "EXECUTION_TIME", insertable = false)
	float executionTime;
	@Column(name = "NUMBER_OF_RETURNED_RECORDS", insertable = false)
	int numberOfReturnedRecords;
	@Column(name = "MESSAGE",length = 100)
	String message;
	@Column(name = "END_DATE",length = 100)
	Timestamp endDate;
	@Column(name = "MIX_NUM")
	int MixNum;
	@Column(name = "FILE_NAME",length = 100)
	String fileName;
	@Column(name = "STATUS", insertable = false)
	String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Timestamp requestedDate) {
		this.requestedDate = requestedDate;
	}
	public Timestamp getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Timestamp processedDate) {
		this.processedDate = processedDate;
	}
	public String getHostName() {
		return HostName;
	}
	public void setHostName(String hostName) {
		HostName = hostName;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getSqlStatement() {
		return SqlStatement;
	}
	public void setSqlStatement(String sqlStatement) {
		SqlStatement = sqlStatement;
	}
	public float getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(float executionTime) {
		this.executionTime = executionTime;
	}
	public int getNumberOfReturnedRecords() {
		return numberOfReturnedRecords;
	}
	public void setNumberOfReturnedRecords(int numberOfReturnedRecords) {
		this.numberOfReturnedRecords = numberOfReturnedRecords;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public int getMixNum() {
		return MixNum;
	}
	public void setMixNum(int mixNum) {
		MixNum = mixNum;
	}
	
	public String status(String value) {
		if (value.equals("C") ) {
			return "Completed";
		} else if (value.equals("P") ) {
			return "Pending";
		} else {
			return "Running";
		}
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
		BackgroundQuery other = (BackgroundQuery) obj;
		if (id != other.id)
			return false;
		return true;
	}
	


}
