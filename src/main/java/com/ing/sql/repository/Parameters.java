package com.ing.sql.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ing.sql.model.Parameter;


public class Parameters implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public Parameters(EntityManager manager) {
		this.manager = manager;
	}

	public Parameter getRecord(int id) {
		return manager.find(Parameter.class,id);
	}
	
	public void insert(Parameter parameter) {
		this.manager.persist(parameter);
		this.manager.flush();
	}

	public void update(Parameter parameter) {
		this.manager.merge(parameter);
		this.manager.flush();
	}
	
	public List<Parameter> all() {
		TypedQuery<Parameter> query = manager.createQuery("from Parameter order by hostName", Parameter.class);
		return query.getResultList();
	}

	public Parameter byHostname(String hostName) {
		TypedQuery<Parameter> query = manager.createQuery("from Parameter where hostName=:hostname", Parameter.class);
		query.setParameter("hostname", hostName);
		List<Parameter> lp=query.getResultList();
		if ( lp.size() == 0 ) {
			return null;
		} else {
			return lp.get(0);
		}
	}

	public List<String> allHostnames() {
		TypedQuery<String> query = manager.createQuery("hostName from Parameter order by hostName", String.class);
		return query.getResultList();
	}
	
	public void rollback() {
		if ( manager.getTransaction().isActive() ) {
			manager.getTransaction().rollback();
		}
	}
	

}
