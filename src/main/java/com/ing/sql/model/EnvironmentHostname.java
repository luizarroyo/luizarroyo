package com.ing.sql.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENVIRONMENTS_HOSTNAMES")
public class EnvironmentHostname implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name = "ID", insertable = false)
	private int id;
	@Column(name = "ENVIRONMENT",length = 15, nullable = false)
	private String environment;
	@Column(name = "HOSTNAME",length = 20, nullable = false)
	private String hostName;
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	@Column(name = "STATUS",length = 1)
	private String status;
	@Column(name = "WHO",length = 6)
	private String who;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int in) {
		this.id = in;
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
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
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
		EnvironmentHostname other = (EnvironmentHostname) obj;
		if (id != other.id)
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
