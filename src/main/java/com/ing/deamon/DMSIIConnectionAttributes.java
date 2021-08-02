package com.ing.deamon;

import java.sql.Timestamp;

import com.unisys.mcpsql.provider.DMConnection;

public class DMSIIConnectionAttributes {

	private int id;
	private DMConnection conn ;
	Timestamp startTime;
	int timeout;
	boolean hasExpired;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DMConnection getConn() {
		return conn;
	}
	public void setConn(DMConnection conn) {
		this.conn = conn;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public boolean isHasExpired() {
		return hasExpired;
	}
	public void setHasExpired(boolean hasExpired) {
		this.hasExpired = hasExpired;
	}
	
		
}
