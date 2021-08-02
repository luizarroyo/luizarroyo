package com.ing.sql.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ing.sql.model.FilesToRemove;
import com.ing.sql.model.BackgroundQuery;

public class BackgroundQueries implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public BackgroundQueries(EntityManager manager) {
		this.manager = manager;
	}

	public BackgroundQuery byId(int id) {
		return manager.find(BackgroundQuery.class, id);
	}
	
	public List<BackgroundQuery> allPending(String userId) {
		TypedQuery<BackgroundQuery> query = manager.createQuery("from OfflineQuery where status='P' and (userId=:user or :user is null) order by requestedDate", BackgroundQuery.class);
			query.setParameter("user", userId);
		return query.getResultList();
	}

	public List<BackgroundQuery> allCompleted(String userId) {
		TypedQuery<BackgroundQuery> query = manager.createQuery("from OfflineQuery where status='C' and (userId=:user or :user is null) order by processedDate desc", BackgroundQuery.class);
			query.setParameter("user", userId);
		return query.getResultList();
	}

	public List<BackgroundQuery> allRunning(String userId) {
		TypedQuery<BackgroundQuery> query = manager.createQuery("from OfflineQuery where (status='R' or status='K') and (userId=:user or :user is null) order by processedDate", BackgroundQuery.class);
			query.setParameter("user", userId);
		return query.getResultList();
	}

	public List<BackgroundQuery> markedToKill(String userId) {
		TypedQuery<BackgroundQuery> query = manager.createQuery("from OfflineQuery where ( status='K') and (userId=:user or :user is null) order by processedDate", BackgroundQuery.class);
			query.setParameter("user", userId);
		return query.getResultList();
	}

	public List<BackgroundQuery> allPendingDeamon(String environment) {
		TypedQuery<BackgroundQuery> query = manager.createQuery(" " + 
				"select a from  OfflineQuery as a " + 
				" ,Parameter as b " + 
				" ,EnvironmentHostname as c " + 
				" ,Environment as d " + 
				" ,Hostname as e " + 
				" ,Database as f " + 
				"where " + 
				"a.status='P' " + 
				"and a.HostName=b.hostName " + 
				"and c.hostName=b.hostName " + 
				"and c.environment=d.environment " + 
				"and d.status='A' " +
				"and a.HostName=e.hostName " + 
				"and a.HostName=f.hostName " + 
				"and a.databaseName=f.databaseName " + 
				"and f.status='A' " + 
				"and e.status='A' " + 
				"and d.environment=:env " + 
				"and d.daemonStatus='A' " + 
				"and (b.offlineRunAllDay=1 or " + 
				"(" + 
				"b.offlineRunAllDay=0 and (DATEPART(HOUR, GETDATE()) >= 18 " + 
				"or DATEPART(HOUR, GETDATE()) <= 6 ) " + 
				")" + 
				")" + 
				"order by a.requestedDate ", BackgroundQuery.class);
		query.setParameter("env", environment);
		return query.getResultList();
	}


	public List<String> resultFolder(int id) {
		TypedQuery<String> query = manager.createQuery(" " + 
				"select b.result_Folder from " +
				"offline_queries a  " +
				"join parameters b  " +
				"on a.HOST_NAME=b.host_name  " +
				"where   " +
				"PROCESSED_DATE < getdate() - b.retention_Offline_Query_Files   " +
				" a.id=:id  " , String.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	public List<FilesToRemove> filesToRemove() {
		TypedQuery<FilesToRemove> query = manager.createQuery(" " + 
				"select a.id, b.result_Folder from " +
				"offline_queries a  " +
				"join parameters b  " +
				"on a.HOST_NAME=b.host_name  " +
				"where   " +
				"PROCESSED_DATE < getdate() - b.retention_Offline_Query_Files   " +
				"and a.STATUS='C'  " +
				" order by a.id ", FilesToRemove.class);
		return query.getResultList();
	}

	
	
	public List<String> allUserId(String userId) {
		TypedQuery<String> query = manager.createQuery("select * from OfflineQuery where userID=:user order by requestedDate", String.class);
		return query.getResultList();
	}

	public void insert(BackgroundQuery offlineQuery) {
		this.manager.persist(offlineQuery);
	}
	
	public BackgroundQuery update(BackgroundQuery offlineQuery) {
		return this.manager.merge(offlineQuery);
	}
	
	public void delete(BackgroundQuery offlineQuery) {
		this.manager.remove(this.manager.contains(offlineQuery) ? offlineQuery : this.manager.merge(offlineQuery));
//		this.manager.remove(profile);
	}

	public EntityManager getManager() {
		return manager;
	}
	
	public void setManager(EntityManager manager) {
		this.manager=manager;
	}
	
	public void rollback() {
		if ( manager.getTransaction().isActive() ) {
			manager.getTransaction().rollback();
		}
	}
	
}
