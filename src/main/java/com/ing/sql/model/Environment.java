package com.ing.sql.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENVIRONMENTS")
public class Environment implements Serializable {
	
	public Environment() {
		status="A";
	}
	
	@Id
	@Column(name = "ENVIRONMENT",length = 15, nullable = false)
	private String environment;
	@Column(name = "DESCRIPTION",length = 50, nullable = false)
	private String description;
	@Column(name = "THEME",length = 25, nullable = false)
	private String theme;
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	@Column(name = "STATUS",length = 1)
	private String status;
	@Column(name = "DAEMON_STATUS",length = 1)
	private String daemonStatus;	
	@Column(name = "WHO",length = 6)
	private String who;
	@Column(name = "OFFLINE_QUERY_TIMEOUT")
	int backgroundQueryTimeout;
	@Column(name = "BLOCK_OFFLINE_QUERY_SQL",length = 1)
	String blockOfflineQuerySql;
	@Column(name = "SUSPEND_PENDING_QUERIES",length = 1)
	String suspendPendingQueries;
	
	
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updateDate) {
		this.updatedDate = updateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}	
	public String getDaemonStatus() {
		return daemonStatus;
	}
	public void setDaemonStatus(String daemonStatus) {
		this.daemonStatus = daemonStatus;
	}
	public int getBackgroundQueryTimeout() {
		return backgroundQueryTimeout;
	}
	public void setBackgroundQueryTimeout(int backgroundQueryTimeout) {
		this.backgroundQueryTimeout = backgroundQueryTimeout;
	}
	public String getBlockOfflineQuerySql() {
		return blockOfflineQuerySql;
	}
	public void setBlockOfflineQuerySql(String blockOfflineQuerySql) {
		this.blockOfflineQuerySql = blockOfflineQuerySql;
	}
	public String getSuspendPendingQueries() {
		return suspendPendingQueries;
	}
	public void setSuspendPendingQueries(String suspendPendingQueries) {
		this.suspendPendingQueries = suspendPendingQueries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((environment == null) ? 0 : environment.hashCode());
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
		Environment other = (Environment) obj;
		if (environment == null) {
			if (other.environment != null)
				return false;
		} else if (!environment.equals(other.environment))
			return false;
		return true;
	}
	
	
	public String status(String value) {
		if (value.equals("A") ) {
			return "Active";
		} else {
			return "Suspended";
		}
	}

}
