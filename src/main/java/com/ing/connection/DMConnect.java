package com.ing.connection;

import java.io.Serializable;
import java.sql.Timestamp;

import com.unisys.mcpsql.provider.DMConnectParams;
import com.unisys.mcpsql.provider.DMConnection;
import com.unisys.mcpsql.server.Connection;
import com.unisys.mcpsql.server.ServerFactory;

public class DMConnect implements Serializable {

	Connection c;
	int connectionId;
	String userId;
	String sessionId;
	String hostname;
	String databaseName;
	String sqlText;
	Timestamp startTime;
	int timeout;
	boolean inTransactionState;
	
//	public DMConn() throws Exception {
//		super();
//	}
	public DMConnect(DMConnectParams p) throws Exception {
		c = setConnection(p);
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

	public Connection getConnection() {
		return c;
	}
	
	public void setIsolationLevel(int level) throws Exception {
		c.setIsolationLevel(level);
	}

	public Connection setConnection(DMConnectParams p) throws Exception {
		return (new ServerFactory()).createConnection("com.unisys.mcpsql.server.serverimpl.MCPSQLConnection", p);
	}

	public boolean isInTransactionState() {
		return inTransactionState;
	}

	public void setInTransactionState(boolean inTransactionState) {
		this.inTransactionState = inTransactionState;
	}
	
}
