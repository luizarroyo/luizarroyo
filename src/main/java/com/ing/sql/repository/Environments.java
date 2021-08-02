package com.ing.sql.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ing.sql.model.Environment;

public class Environments implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public Environments(EntityManager manager) {
		this.manager = manager;
	}

	public Environment getRecord(int id) {
		return manager.find(Environment.class,id);
	}
	
	public void insert(Environment environment) {
		this.manager.persist(environment);
	}

	public Environment update(Environment environment) {
		return this.manager.merge(environment);
	}
	
	public void delete(Environment environment) {
		this.manager.remove(this.manager.contains(environment) ? environment : this.manager.merge(environment));
	}

	public List<Environment> all() {
		TypedQuery<Environment> query = manager.createQuery("from Environment order by environment", Environment.class);
		return query.getResultList();
	}

	public List<Environment> allStatus(String status) {
		TypedQuery<Environment> query = manager.createQuery("from Environment where status=:status order by environment", Environment.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	public Environment byEnvironment(String environmentName) {
		Query query = manager.createQuery("from Environment where environment=:environment", Environment.class);
		query.setParameter("environment", environmentName);
		return (Environment)query.getSingleResult();
	}

	public List<String> allEnvironments() {
		TypedQuery<String> query = manager.createQuery("select environment from Environment order by environment ", String.class);
		return query.getResultList();
	}
	
	public void rollback() {
		if ( manager.getTransaction().isActive() ) {
			manager.getTransaction().rollback();
		}
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	

}
