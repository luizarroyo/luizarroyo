package com.ing.sql.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ing.sql.model.Message;

public class Messages implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;

	@Inject
	public Messages(EntityManager manager) {
		this.manager = manager;
	}

	public Message byId(int id) {
		return manager.find(Message.class, id);
	}
	
	public List<Message> all() {
		TypedQuery<Message> query = manager.createQuery("from Message order by id", Message.class);
		return query.getResultList();
	}

	public List<Message> allDesc() {
		TypedQuery<Message> query = manager.createQuery("from Message order by id desc", Message.class);
		return query.getResultList();
	}

	public List<Integer> allId() {
		TypedQuery<Integer> query = manager.createQuery("Select id from Message order by id", Integer.class);
		return query.getResultList();
	}

	public List<Message> getMessagesbyDate(Date date) {
		TypedQuery<Message> query = manager.createQuery("from Message where msgDate=:msgDate", Message.class);
		   query.setParameter("msgDate", date);
		return query.getResultList();
	}
	
	public List<Message> getMessagesByInterval(String date1, String date2) {
		TypedQuery<Message> query = manager.createQuery("from Message where MSG_DATE >= :msgDate1 and MSG_DATE <= :msgDate2 order by MSG_DATE desc", Message.class);
		   query.setParameter("msgDate1", date1+" 00:00:00");
		   query.setParameter("msgDate2", date2+" 23:59:59");
		return query.getResultList();
	}

	public List<Message> getMessagesByIntervalAndType(String date1, String date2, String module, String type) {
		TypedQuery<Message> query = manager.createQuery("from Message where MSG_DATE >= :msgDate1 and MSG_DATE <= :msgDate2 and (MSG_TYPE = :msgType or :msgType='') and  (MODULE_NAME = :module or :module='') order by MSG_DATE desc", Message.class);
		   query.setParameter("msgDate1", date1+" 00:00:00");
		   query.setParameter("msgDate2", date2+" 23:59:59");
		   query.setParameter("module", module);
		   query.setParameter("msgType", type);
		return query.getResultList();
	}

	public void insert(Message Message) {
		this.manager.persist(Message);
	}
	
	public Message update(Message Message) {
		return this.manager.merge(Message);
	}
	
	public void delete(Message Message) {
		this.manager.remove(this.manager.contains(Message) ? Message : this.manager.merge(Message));
	}
	
	public void rollback() {
		if ( manager.getTransaction().isActive() ) {
			manager.getTransaction().rollback();
		}
	}
	

}