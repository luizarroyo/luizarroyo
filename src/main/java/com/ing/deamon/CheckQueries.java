package com.ing.deamon;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ing.connection.JpaUtil;
import com.ing.sql.model.BackgroundQuery;
import com.ing.sql.repository.BackgroundQueries;
import com.ing.sql.util.MessageType;
import com.ing.sql.util.ModuleType;
import com.ing.sql.util.WriteLog;

public class CheckQueries extends Thread {
	
	EntityManager manager;
	private BackgroundQueries backgroundQueries;
	private WriteLog log;
	
	DMSIIConnections dmsiiConn = new DMSIIConnections();

	public void run() {
			    	
    	while ( true ) {

    		manager = JpaUtil.getEntityManager();
    		Session session = manager.unwrap(Session.class);
    		backgroundQueries=new BackgroundQueries(manager);
			
    		try {
    			log=new WriteLog(manager);
			} catch (Exception e1) {				
				e1.printStackTrace();
				SddDaemon.logErr(""+e1);
			}

    		try { 	    		
    			
	        	List<BackgroundQuery> listRunningQueries=backgroundQueries.markedToKill(null);
	        	
	        	for (BackgroundQuery bq: listRunningQueries) {
	        		for ( QueryExecution qe : SddDaemon.exec ) {
	        			
		          	    if ( qe.getId() == bq.getId() ) {
			          	    if ( ! qe.getExecution().isDone()  ) {
			          	    	
			          	    	dmsiiConn.removeConnection(bq.getId());			          	    	
			          	    	
			          	    	qe.getExecution().cancel(true);
			          	    	
					    		Transaction t=session.beginTransaction();
					    		bq.setMessage(bq.getMessage()+"CheckQueries - query has been aborted by request(1) ");	
					    		bq.setStatus("A");
			            	    backgroundQueries.update(bq);
			              	    log.writeLogTable(
			              	    		"CheckQueries-Background Query id "
			            				+ bq.getId() 
			            				+"has been aborted by deamon", 
			            				MessageType.ERROR, ModuleType.BACKGROUNDQUERY);
					        	t.commit();			
			          	    }
			          	    
			          	    qe.getExecution().cancel(true);
			          	    
		          	    }
		          	    
	        		}
	        	}
    		} catch (Exception e) {
    			SddDaemon.log("CheckQueries error: "+e);
    			SddDaemon.logErr("CheckQueries error: "+e);
	    		e.printStackTrace();
    		}

    		manager.close();
    		
    		try {
    			Thread.sleep(20000);
    		} catch (Exception e) {
    			SddDaemon.log("CheckQueries Sleep error: "+e);
    			SddDaemon.logErr("CheckQueries Sleep error:  "+e);
	    		e.printStackTrace();
    			
    		}

    	}
		
	}

	
}
