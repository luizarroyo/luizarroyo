package com.ing.connection;

import java.io.Serializable;
import java.sql.Timestamp;

import com.unisys.mcpsql.provider.DMConnection;

public class DMConn extends DMConnection implements Serializable {

	int connectionId;
	String userId;
	String sessionId;
	String hostname;
	String databaseName;
	String sqlText;
	Timestamp startTime;
	int timeout;
	boolean inTransactionState;
	
	public DMConn() throws Exception {
		super();
	}

	public void cleanFields() {
		userId="";
		sessionId="";
		sqlText="";
		startTime=null;		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public boolean isInTransactionState() {
		return inTransactionState;
	}

	public void setInTransactionState(boolean inTransactionState) {
		this.inTransactionState = inTransactionState;
	}

}
