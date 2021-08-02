 package com.ing.connection;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.ing.deamon.DMSIIConnectionAttributes;

public class TimeoutControl extends Thread {
	
	List<DMSIIConnectionAttributes> dmConnections;
	
	public TimeoutControl(List<DMSIIConnectionAttributes> conn) {
		this.dmConnections=conn;
	}

    public void run(){
    	System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+":  Timeout Pool Started ");
    	while ( true ) {
    		
    			try {
    				
    			// Active Connections running SQL statements
    			System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+":  Timeout Pool: ******  CHECKING CONNECTIONS  ******");
	    		for (DMSIIConnectionAttributes conn:dmConnections) {
	    			
	    			if ( conn.getTimeout() > 0 ) {
		        		try {
			    			System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+":  Timeout DM Pool: Connection selected-"+conn.getConn().getDatabasename());
			    			
			    			long difference = new Timestamp(new java.util.Date().getTime()).getTime() - conn.getStartTime().getTime() ;
		
			    			System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+":  Timeout Dm Pool:   ("+conn.getId() +") "+conn.getConn().getDatabasename()+" (Mix="+conn.getConn().getMixNum()+")-"+conn.getTimeout()+"-"+conn.getStartTime()+"-"+difference/60000.0+" Timeout:"+conn.getTimeout());
			    				    			
			    			if ( difference/60000.0 > conn.getTimeout() ) {
			    				System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+":       "+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+" DM Timeout exceeded for : "+conn.getConn().getDatabasename()+" ID="+conn.getId());
			    				conn.setHasExpired(true);
			    				conn.getConn().closeSocketconnection();
			    				break;
			    			}
		        		} catch (Exception e) {
	
		        			System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+":  Timeout DM Pool Error: "+e.getMessage());
		        			e.printStackTrace();
		    				    			
		        		}
	    			}

	    		}
	    		
	    	try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
			}

			} catch (Exception e) {
				
				System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+":  Timeout Error: "+e.getMessage());
				e.printStackTrace();
				
			}
    	
    	}
    	
    }	
}
