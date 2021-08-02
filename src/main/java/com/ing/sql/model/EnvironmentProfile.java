package com.ing.sql.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "environments_profiles")
@IdClass(EnvironmentProfilePk.class)
public class EnvironmentProfile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ENVIRONMENT_NAME",length = 15, nullable = false)
    protected String environmentName;
	@Id
    @Column(name = "PROFILE_NAME",length = 30, nullable = false)
    protected String profileName;
	@Column(name = "UPDATED_DATE", length = 6, nullable = false)
	private Date updatedDate;
	@Column(name = "STATUS", length = 1, nullable = false)
	@ColumnDefault("A")
	private String status;
	@Column(name = "WHO", length = 6, nullable = false)
	private String who;
	
//	@Column(name = "TABLE_NAME",length = 30, nullable = false)
//	public String getTableName() {
//		return tableName;
//	}
//	public void setTableName(String tableName) {
//		this.tableName = tableName;
//	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((environmentName == null) ? 0 : environmentName.hashCode());
		result = prime * result + ((profileName == null) ? 0 : profileName.hashCode());
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
		EnvironmentProfile other = (EnvironmentProfile) obj;
		if (environmentName == null) {
			if (other.environmentName != null)
				return false;
		} else if (!environmentName.equals(other.environmentName))
			return false;
		if (profileName == null) {
			if (other.profileName != null)
				return false;
		} else if (!profileName.equals(other.profileName))
			return false;
		return true;
	}

	
}
