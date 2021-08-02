package com.ing.sql.model;

import java.io.Serializable;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "DATABASES")
@IdClass(DatabasePk.class)
public class Database implements Serializable {
	
	public Database() {
		status="A";
	}

	private static final long serialVersionUusercode = 1L;
	
	@Id
	@Column(name = "DATABASE_NAME",length = 17, nullable = false)
	private String databaseName;
	@Id
	@Column(name = "HOST_NAME",length = 17, nullable = false)
	private String hostName;
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	@Column(name = "STATUS",length = 1)
	@ColumnDefault("A")
	private String status;
	@Column(name = "RELID",length = 1)
	private String releaseId;
	@Column(name = "WHO",length = 6, nullable = false)
	private String who;
	
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}	
    public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	
	public String status(String value) {
		if (value.equals("A") ) {
			return "Active";
		} else {
			return "Suspended";
		}
	}
	
	


}
