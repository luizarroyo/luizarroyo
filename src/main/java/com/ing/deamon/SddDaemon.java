	package com.ing.deamon;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ing.connection.JpaUtil;
import com.ing.connection.McpSqlParameter;
import com.ing.connection.TimeoutControl;
import com.ing.sql.model.Environment;
import com.ing.sql.model.Hostname;
import com.ing.sql.model.BackgroundQuery;
import com.ing.sql.model.Parameter;
import com.ing.sql.repository.Environments;
import com.ing.sql.repository.Hostnames;
import com.ing.sql.repository.BackgroundQueries;
import com.ing.sql.repository.Parameters;
import com.ing.sql.util.ColumnModel;
import com.ing.sql.util.MessageType;
import com.ing.sql.util.ModuleType;
import com.ing.sql.util.WriteLog;
import com.unisys.mcpsql.provider.DMConnectParams;
import com.unisys.mcpsql.provider.DMConnection;
import com.unisys.mcpsql.provider.DMItemDescription;
import com.unisys.mcpsql.provider.DMLocator;
import com.unisys.mcpsql.provider.DMProviderException;
import com.unisys.mcpsql.provider.DMResultSet;
import com.unisys.mcpsql.provider.DMStatement;
import com.unisys.mcpsql.server.serverimpl.MCPSQLColLob;

import sun.security.util.PendingException;

public class SddDaemon {
	
	@Inject
	private BackgroundQueries backgroundQueries;
	
	@Inject
	private WriteLog log;

	private static List<BackgroundQuery> listPendingQueries;
	
	boolean debug=false;
	
	String database,
	       userId;
	
	static String environment;

	ExecutorService executor;
	static List<QueryExecution> exec = new ArrayList<QueryExecution>();
	private static int maxRun = 1;
	
	EntityManager manager;
	
	DMSIIConnections dmsiiConn = new DMSIIConnections();
	
	String version="21.8.1";

    public static void main( String[] args )     {
    	if ( args.length == 0 ) {
    		System.out.println("Parameter error: the environment parameter is mandatory");
    		System.out.println("  java SddDaemon <environment> [<parallel runs>]");
    	} else if ( args.length > 1 ) {
        	environment=args[0];
    		int newMaxRun=maxRun;
    		try {
    			newMaxRun=Integer.valueOf(args[1]);
    		} catch (Exception e) {
    			
    		}
        	maxRun=newMaxRun;    		
    	}
        new SddDaemon().process();
    }
    
    void process()  {

        log( "************************************" );
        log( "*       SddDaemon started          *" );
        log( "*          Version "+version+"           *" );
        log( "************************************" );
        log( "" );

        log( "Environment Selected="+environment );
        log( "Maximum number of simultaneos runs="+maxRun );

        manager = JpaUtil.getEntityManager();
    	backgroundQueries = new BackgroundQueries(manager);
    	
    	executor = Executors.newFixedThreadPool(maxRun);
    	
        TimeoutControl timeoutControl=new TimeoutControl(DMSIIConnections.connections);
        timeoutControl.start();
    	    	
    	try {

	       	log=new WriteLog(manager);
	       	
	    	clearRunningQueries();
	    	
	    	CheckQueries cq = new CheckQueries();
	    	cq.start();
	       	
	    	while ( true ) {
	    		
	    		selectPendingQueries();    		
	    		Thread.sleep(20000);
	    		
	    	}
	    	
    	} catch (Exception e) {
    		
    		log("Error in SddDaemon:"+e);
      	    log.writeLogTable("Error in SddDaemon: "+e,	MessageType.ERROR, ModuleType.BACKGROUNDQUERY);
    		
    	}
    		
    	manager.close();
    	
    }
    
    private void CleanFiles() {
		
    }
    
    void clearRunningQueries() {
    	
		Session session = manager.unwrap(Session.class);
		Transaction t=session.beginTransaction();
    	List<BackgroundQuery> listRunningQueries=backgroundQueries.allRunning(null);
    	for (BackgroundQuery offQry: listRunningQueries) {
    	    offQry.setMessage("Query aborted by deamon: clearRunningQueries() method");	
    	    offQry.setStatus("C");
    	    backgroundQueries.update(offQry);
      	    log.writeLogTable("Offline Query id "
    				+ offQry.getId() 
    				+": Query aborted by deamon: clearRunningQueries() method", MessageType.ERROR, ModuleType.BACKGROUNDQUERY);

    	}
    	t.commit();
    }
    
    void selectPendingQueries() {
    	
		manager = JpaUtil.getEntityManager();
		Session session = manager.unwrap(Session.class);
		backgroundQueries=new BackgroundQueries(manager);

    	try {
    			
    			if ( exec.size() >= maxRun ) {
    				return;
    			}
    			
		    	listPendingQueries=backgroundQueries.allPendingDeamon(environment);
		    	
		    	for ( BackgroundQuery bq : listPendingQueries) {		    		
		    		if ( exec.size() < maxRun ) {
			            log( "Offline Query Selected. Id:"+bq.getId() );
						Runnable worker = new RunBackgroundQuery(bq.getId(), bq.getHostName(), bq.getDatabaseName(), bq.getSqlStatement());
						Future<?> f=executor.submit(worker);
						QueryExecution rq = new QueryExecution();
						rq.setExecution(f);
						rq.setId(bq.getId());
						exec.add(rq);    							
		    		} else {
		    			break;
		    		}
		    	}
		    	
    	} catch (Exception e) {
    		logErr("Error when checking pending requests: "+e);
    		e.printStackTrace();
    	} finally {
    		manager.close();
    	}
    	
    }
    
    static void log (String msg) {
    	System.out.println( (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(Calendar.getInstance().getTime())+": "+ msg );
    }
    static void logErr (String msg) {
    	System.err.println( (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(Calendar.getInstance().getTime())+": "+ msg );
    }
     
}
