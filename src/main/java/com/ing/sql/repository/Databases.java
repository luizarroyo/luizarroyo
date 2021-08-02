package com.ing.sql.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ing.sql.model.Database;
import com.ing.sql.model.DatabasePk;

public class Databases implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public Databases(EntityManager manager) {
		this.manager = manager;
	}

	public Database byDatabaseName(DatabasePk id) {
		return manager.find(Database.class, id);
	}
	
	public List<Database> all(String filter) {
		TypedQuery<Database> query = manager.createQuery(
			"from Database where databaseName like :filter or :filter='' order by databaseName", Database.class);
		query.setParameter("filter" , filter);
		return query.getResultList();
	}

	public List<Database> allStatus(String filter, String status) {
		TypedQuery<Database> query = manager.createQuery(
			"from Database where (databaseName like :filter or :filter='') and status=:status order by databaseName", Database.class);
		query.setParameter("filter" , filter);
		query.setParameter("status" , status);
		return query.getResultList();
	}

	public List<String> allDatabases() {
		TypedQuery<String> query = manager.createQuery(
			"select distinct databaseName from Database order by databaseName", String.class);
		return query.getResultList();
	}

	public List<String> allDatabasesHostnames() {
		TypedQuery<String> query = manager.createQuery(
			"select distinct databaseName+'-'+hostName from Database order by 1", String.class);
		return query.getResultList();
	}

	public void insert(Database database) {
		this.manager.persist(database);
	}
	
	public Database update(Database database) {
		return this.manager.merge(database);
	}
	
	public void delete(Database database) {
		this.manager.remove(database);
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
