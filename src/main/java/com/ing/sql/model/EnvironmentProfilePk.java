package com.ing.sql.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EnvironmentProfilePk implements Serializable {

    @Column(name = "ENVIRONMENT_NAME",length = 17, nullable = false)
    protected String environmentName;
	@Column(name = "PROFILE_NAME",length = 30, nullable = false)
	protected String profileName;

    public EnvironmentProfilePk() {}

    public EnvironmentProfilePk(String environment, String profileName) {
        this.profileName = profileName;
        this.environmentName = environment;
    }

    
	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
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
		EnvironmentProfilePk other = (EnvironmentProfilePk) obj;
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
