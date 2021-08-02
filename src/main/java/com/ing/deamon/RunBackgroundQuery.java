package com.ing.deamon;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ing.connection.JpaUtil;
import com.ing.connection.McpSqlParameter;
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

public class RunBackgroundQuery implements Runnable {
    
private DMConnection conn ;

String errorMsg="";

int numRows = 0;
    	
double time;
String fileName;

private List<ColumnModel> columns=new ArrayList<ColumnModel>();
private List<String> cols1=new ArrayList<String>();

int id; 
String hostname,
       database,
       sql;

private BackgroundQueries backgroundQueries;

private Parameters params;
private Parameter param;

private WriteLog log;

EntityManager manager;

DMSIIConnections dmsiiConn = new DMSIIConnections();

RunBackgroundQuery(int id, String hostname, String databaseName, String sqlStatement) {
	this.id=id;
	this.hostname=hostname;
	this.database=databaseName;
	this.sql=sqlStatement;
}

public void run() {
	    	
	errorMsg=null;
	String suspendPendingQueries="N";
	
	fileName=String.valueOf(id)+".csv";
	
	manager = JpaUtil.getEntityManager();
	Session session = manager.unwrap(Session.class);

    Hostnames hostnames=new Hostnames(manager);
    Hostname hn=hostnames.byHostname(hostname);
    
    String hostconn=hostname;
    
    if ( hn.getServerName() != null ) {
    	hostconn= hn.getServerName();
    }	

	DMConnectParams p = McpSqlParameter.getParameter(database, hostconn);
    
    //connect(p, null);
    

    long startTime = System.nanoTime();
    
	BackgroundQuery offQry=null;
	
	try {
    	
    	backgroundQueries=new BackgroundQueries(manager);
    	log=new WriteLog(manager);
    	params=new Parameters(manager);
    	
    	param=params.byHostname(hostname);
    	
    	Environments environments = new Environments(manager);
    	Environment env = environments.byEnvironment(SddDaemon.environment);

    	suspendPendingQueries=env.getSuspendPendingQueries();
    	offQry=backgroundQueries.byId(id);

    	SddDaemon.log("Starting background query "+offQry.getId());


    	conn = new DMConnection();
    	
    	dmsiiConn.addConnection(id, conn, new Timestamp(System.currentTimeMillis()), env.getBackgroundQueryTimeout());

        this.conn.connect(p);
        this.conn.open(p);
        this.conn.setIsolationLevel(1);
        
        SddDaemon.log("Database "+offQry.getDatabaseName()+" opened for id "+offQry.getId()+". MixNum="+conn.getMixNum());

    	Transaction t=session.beginTransaction();
		offQry.setProcessedDate(new Timestamp(System.currentTimeMillis()));
		offQry.setStatus("R");
		offQry.setMixNum(conn.getMixNum());
		backgroundQueries.update(offQry);
  	    log.writeLogTable("Offline Query Selected. Id:"+offQry.getId(), MessageType.SUCCESS, ModuleType.BACKGROUNDQUERY);
		t.commit();

        DMStatement stmt = conn.createStatement();
        
    	startTime = System.nanoTime();
    	DMResultSet rs = stmt.executeQuery(sql);
    	
    	SddDaemon.log("Query for id "+offQry.getId()+ " executed, go to fetch:"+sql);

    	numRows = 0;
    	fetch(rs, startTime, offQry.getId());

    	SddDaemon.log("Query for id "+offQry.getId()+ " , 2");

    	time = (float)((System.nanoTime()-startTime)/1000000000.0);
    	stmt.close();
    	
    } catch (Exception e) {
    	
    	SddDaemon.log("Query for id "+offQry.getId()+ " , 3");

    	SddDaemon.log("Query for id "+offQry.getId()+", error Statement "+e);
    	SddDaemon.log( "Error while processing Id:"+offQry.getId()+":"+e.getMessage() );
        
        try {
			if ( dmsiiConn.hasExpired(offQry.getId()) ) {
	        	errorMsg="Timeout exceeded, query aborted";  
			} else {
	        	errorMsg=e.getMessage();  
	        	if ( errorMsg == null ) {
	        		errorMsg="Query aborted by request(1)";
	        	}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	e.printStackTrace();
    	SddDaemon.logErr("3:"+e);
    	if ( e.getMessage().toUpperCase().indexOf("LIMIT") > -1 ) {
    		
    	}

    }
    finally {
       	
    	SddDaemon.log("Query for id "+offQry.getId()+ " , 4");

    	boolean hasExpired=false;
		try {
			hasExpired=dmsiiConn.hasExpired(offQry.getId());
			dmsiiConn.removeConnection(offQry.getId());
			SddDaemon.log("Database "+offQry.getDatabaseName()+" closed for id "+offQry.getId());
		} catch (Exception e1) {
			SddDaemon.log("Error when trying to close the MCP connection:"+e1);
		}

		Transaction t=session.beginTransaction();
    	
		if (! session.getTransaction().isActive() ) {
    		t=session.beginTransaction();
		} else {
			t=session.getTransaction();
		}

		time = (float)((System.nanoTime()-startTime)/1000000000.0);

		offQry=backgroundQueries.byId(id);
		
		offQry.setNumberOfReturnedRecords(numRows);
		offQry.setExecutionTime((float)time);
		offQry.setEndDate(new Timestamp(System.currentTimeMillis()));
		if ( errorMsg != null ) {
			if ( offQry.getMessage() == null ) {
    			offQry.setMessage(errorMsg);    				
			} else {
    			offQry.setMessage(offQry.getMessage()+"("+errorMsg+")");
			}
    		offQry.setStatus("A");
		} else {
			offQry.setStatus("C");
		}
		backgroundQueries.update(offQry);
		
		if ( hasExpired && suspendPendingQueries.equals("Y")) {
    		suspendBackgroundQueries(manager, offQry);
		}
		
		if ( errorMsg != null ) {    			
      	    log.writeLogTable("Offline Query id " + id +" for database "+offQry.getDatabaseName()+": "+errorMsg, MessageType.ERROR, ModuleType.BACKGROUNDQUERY);
		} else {
      	    log.writeLogTable("Offline Query id " + id + " processed for database "+offQry.getDatabaseName(), MessageType.SUCCESS, ModuleType.BACKGROUNDQUERY);
		}
		t.commit();
		
		SddDaemon.log("End for background query "+offQry.getId());

    	manager.close();
		
    	for (int i=0;i<SddDaemon.exec.size();i++) {
    		if ( SddDaemon.exec.get(i).getId()==id ) {
    			SddDaemon.exec.remove(i);
    			return;
    		}
    	}
    	

   } 
    	

}

private void suspendBackgroundQueries(EntityManager manager, BackgroundQuery oq ) {
	
	int recs=manager.createNativeQuery("update offline_queries set status = 'S' where user_id='"+oq.getUserId()+"' and database_name='"+oq.getDatabaseName()+"' and status='P'").executeUpdate();
	
	SddDaemon.log("   ABORT id "+oq.getId()+": "+recs+" queued Offline Queries were changed to Suspended for "+oq.getUserId()+" Database "+oq.getDatabaseName());
	
}
    
private boolean fetch(DMResultSet rs, long startTime, int id) throws Exception {
    boolean hasLobs = false;
    
    SddDaemon.log("Query id "+id+ ", file to be created:"+param.getResultFolder()+"/"+fileName);
    
    Path path = Paths.get(param.getResultFolder()+"/"+fileName); 
    if ( Files.exists(path) ) {
    	Files.delete(path);
    }
    BufferedWriter writer = Files.newBufferedWriter(path,  Charset.forName("cp1252"),   StandardOpenOption.CREATE_NEW);

    SddDaemon.log("Query id "+id+ ", reading records");

    try {
    	
    	
      DMItemDescription[] desc = rs.getDescription();

      columns=new ArrayList<ColumnModel>(); 
      cols1=new ArrayList<String>();
      List rows = new ArrayList();
      StringBuilder sb; 
      numRows=0;
      
      try {
        while (true) {
          Object[] values = rs.fetch();
          if (values == null) {
        	  SddDaemon.log("End of resultset = "+numRows+" records");  
            break;
          }
          
//          System.out.println("Record n."+ ++j);
          int cols = values.length;
          sb=new StringBuilder();

          if ( numRows==0) {
              for (int i = 0; i < cols; i++) {
        		  columns.add(new ColumnModel(desc[i].getName().toUpperCase(), values));
        		  cols1.add(desc[i].getName().toUpperCase());
        		  if ( i > 0 ) {
        			  sb.append(";");
        		  }
        		  sb.append(desc[i].getName().toUpperCase());
        	  }
        	  writer.write(sb.toString()+"\n");
          }
      	  sb=new StringBuilder();  
          for (int i = 0; i < cols; i++) {
        	  
            //System.out.println(" Column:"+desc[i].getName()+" "+desc[i].getJavaTypeString()+" "+ values[i]);
                 	  
   		    if ( i > 0 ) {
   		    	sb.append(";");
    		}
        	
            if (desc[i].getSqlType() == 1) {
            	if (values[i] != null ) {
            		values[i] = values[i].toString().trim() ;
            	} else {
            		values[i]="<null>";
            	}
            }
            if (values[i] == null) {                 
              values[i] = "<null>";
              sb.append(";");
            }
            else if (values[i] instanceof DMLocator) {
              
              if (desc[i].getSqlType() == 2004) {
                
                hasLobs = true;
                values[i] = new MCPSQLColLob(1, (DMLocator)values[i]);
              } 
              
              if (desc[i].getSqlType() == 2005) {
                
                hasLobs = true;
                values[i] = new MCPSQLColLob(2, (DMLocator)values[i]);
              } 
            } else {
              sb.append(String.valueOf(values[i]));
            }
          } 
          rows.add(values);
          writer.write(sb.toString()+"\n");
          numRows++;
        } 
      } catch (DMProviderException e) {
    	  SddDaemon.log("Error 1:"+e);
    	  SddDaemon.logErr("Error 1:"+e);
    	  errorMsg=e.getMessage();
    	  return hasLobs;
      } catch (Exception e) {
    	  SddDaemon.log("Error 2:"+e);
    	  SddDaemon.logErr("Error 2:"+e);
    	  errorMsg=e.getMessage();
    	  return hasLobs;
      } 
      
    }
    finally {
      
    	SddDaemon.log("Query id "+id+ ", closing file "+param.getResultFolder()+"/"+fileName);

        writer.flush();
        writer.close();
    	
    } 
    
    
    return hasLobs;
  }
    
}
