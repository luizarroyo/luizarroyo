package com.ing.sql.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ing.sql.model.EnvironmentProfile;
import com.ing.sql.model.EnvironmentProfilePk;


public class EnvironmentsProfiles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public EnvironmentsProfiles(EntityManager manager) {
		this.manager = manager;
	}

	public EnvironmentProfile byId(EnvironmentProfilePk id) {
		return manager.find(EnvironmentProfile.class, id);
	}
	
	public List<String> test(String desc) {
		TypedQuery<String> query = manager.createQuery(
				"select   "
				+ "where upper(desc) like upper(:desc)", 
				String.class);
		query.setParameter("desc", "%" + desc + "%");
		return query.getResultList();
	}
	
	public List<EnvironmentProfile> all() {
		TypedQuery<EnvironmentProfile> query = manager.createQuery(
				"from EnvironmentProfile order by environmentName, profileName", EnvironmentProfile.class);
		return query.getResultList();
	}

	public List<String> notProfiles(String environmentName) {
		TypedQuery<String> query = manager.createQuery(
		               "select profileName from Profile a  " + 
		               "where " + 
		               "not exists (select 1 from EnvironmentProfile b where " + 
		               "b.environmentName=:environmentName and " + 
		               "b.profileName=a.profileName  " + 
		               ") " + 
		               "order by a.profileName", String.class);
		query.setParameter("environmentName", environmentName );
		return query.getResultList();
	}

	public List<String> profiles(String environmentName) {
		TypedQuery<String> query = manager.createQuery(
				"select up.profileName from EnvironmentProfile up where "+ 
		         " up.environmentName = :environmentName order by up.profileName", String.class);
		query.setParameter("environmentName", environmentName );
		return query.getResultList();
	}

	public List<EnvironmentProfile> profilesPerEnvironment(String environmentName) {
		TypedQuery<EnvironmentProfile> query = manager.createQuery(
				"from EnvironmentProfile up where up.environmentName = :environmentName order by up.profileName", EnvironmentProfile.class);
		query.setParameter("environmentName", environmentName );
		return query.getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public void insert(EnvironmentProfile EnvironmentProfile) {
		this.manager.persist(EnvironmentProfile);
	}
	
	public EnvironmentProfile update(EnvironmentProfile EnvironmentProfile) {
		return this.manager.merge(EnvironmentProfile);
	}
	
	public void delete(EnvironmentProfile EnvironmentProfile) {
		this.manager.remove(EnvironmentProfile);
	}

	
	public void rollback() {
		if ( manager.getTransaction().isActive() ) {
			manager.getTransaction().rollback();
		}
	}
	

}