package com.ing.sql.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ing.sql.model.Hostname;

public class Hostnames implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public Hostnames(EntityManager manager) {
		this.manager = manager;
	}

	public Hostname getRecord(int id) {
		return manager.find(Hostname.class,id);
	}
	
	public void insert(Hostname hostname) {
		this.manager.persist(hostname);
	}

	public Hostname update(Hostname hostname) {
		return this.manager.merge(hostname);
	}
	
	public void delete(Hostname hostname) {
		this.manager.remove(this.manager.contains(hostname) ? hostname : this.manager.merge(hostname));
	}

	public List<Hostname> all() {
		TypedQuery<Hostname> query = manager.createQuery("from Hostname order by hostName", Hostname.class);
		return query.getResultList();
	}

	public List<Hostname> allStatus(String status) {
		TypedQuery<Hostname> query = manager.createQuery("from Hostname where status=:status order by hostName", Hostname.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	public Hostname byHostname(String hostName) {
		Query query = manager.createQuery("from Hostname where hostName=:hostname", Hostname.class);
		query.setParameter("hostname", hostName);
		return (Hostname)query.getSingleResult();
	}

	public List<String> allHostnames() {
		TypedQuery<String> query = manager.createQuery("select hostName from Hostname order by hostName ", String.class);
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
