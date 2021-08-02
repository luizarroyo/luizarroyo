package com.ing.sql.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ing.sql.model.EnvironmentHostname;

public class EnvironmentsHostnames implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public EnvironmentsHostnames(EntityManager manager) {
		this.manager = manager;
	}

	public List<EnvironmentHostname> all() {
		TypedQuery<EnvironmentHostname> query = manager.createQuery(
				"from EnvironmentHostname order by environment, hostnames", EnvironmentHostname.class);
		return query.getResultList();
	}

	public EnvironmentHostname byEnvironmentHostname(String environment, String hostname) {
		TypedQuery<EnvironmentHostname> query = manager.createQuery(
				"from EnvironmentHostname where environment=:environment and hostName=:hostname order by environment, hostName", EnvironmentHostname.class);
		query.setParameter("environment", environment );
		query.setParameter("hostname", hostname );
		return query.getSingleResult();
	}

	public List<String> hostnames(String environment) {
		TypedQuery<String> query = manager.createQuery(
				"select hostName from EnvironmentHostname up where environment = :environment order by hostName", String.class);
		query.setParameter("environment", environment );
		return query.getResultList();
	}

	public List<EnvironmentHostname> hostnamesPerEnvironmebt(String environment) {
		TypedQuery<EnvironmentHostname> query = manager.createQuery(
				"from EnvironmentHostname up where up.environment = :environment order by up.hostName", EnvironmentHostname.class);
		query.setParameter("environment", environment );
		return query.getResultList();
	}
	
	public List<String> notHostnames(String environmentName) {
		TypedQuery<String> query = manager.createQuery(
		               "select hostName from Hostname a " + 
		               "where " + 
		               "not exists (select 1 from EnvironmentHostname b where " + 
		               "b.environment=:environmentName and " + 
		               "b.hostName=a.hostName  " + 
		               ") " + 
		               "order by a.hostName", String.class);
		query.setParameter("environmentName", environmentName );
		return query.getResultList();
	}


	public void insert(EnvironmentHostname EnvironmentHostname) {
		this.manager.persist(EnvironmentHostname);
	}
	
	public EnvironmentHostname update(EnvironmentHostname EnvironmentHostname) {
		return this.manager.merge(EnvironmentHostname);
	}
	
	public void delete(EnvironmentHostname EnvironmentHostname) {
		this.manager.remove(EnvironmentHostname);
	}

	
	public void rollback() {
		if ( manager.getTransaction().isActive() ) {
			manager.getTransaction().rollback();
		}
	}
	

}