package com.ing.sql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DatabasePk implements Serializable {

    @Column(name = "DATABASE_NAME",length = 17, nullable = false)
    protected String databaseName;
	@Column(name = "HOST_NAME",length = 17, nullable = false)
	protected String hostName;

    public DatabasePk() {}

    public DatabasePk(String databaseName, String hostName ) {
        this.databaseName = databaseName;
        this.hostName = hostName;
    }

    
	public String getHostNameName() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((databaseName == null) ? 0 : databaseName.hashCode());
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
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
		DatabasePk other = (DatabasePk) obj;
		if (databaseName == null) {
			if (other.databaseName != null)
				return false;
		} else if (!databaseName.equals(other.databaseName))
			return false;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		return true;
	}

   
}
