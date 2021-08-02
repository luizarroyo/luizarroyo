package com.ing.deamon;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.unisys.mcpsql.provider.DMConnection;

public class DMSIIConnections {

	static List<DMSIIConnectionAttributes> connections = new ArrayList<DMSIIConnectionAttributes>() ;

	public DMConnection getDMConnection(int id) {
		for (DMSIIConnectionAttributes dm: connections) {
			if (dm.getId() == id ) {
				return dm.getConn();
			}
		}
		return null;
	}
	
	public synchronized void addConnection(int id, DMConnection conn, Timestamp startTime, int timeout ) {
		DMSIIConnectionAttributes dmsiiConnAtt = new DMSIIConnectionAttributes();
		dmsiiConnAtt.setConn(conn);
		dmsiiConnAtt.setId(id);
		dmsiiConnAtt.setStartTime(startTime);
		dmsiiConnAtt.setTimeout(timeout);
		connections.add(dmsiiConnAtt);
	}

	public synchronized void removeConnection(int id) throws Exception {
		int i=0;
		for (DMSIIConnectionAttributes dm: connections) {
			if (dm.getId() == id ) {
				dm.getConn().closeSocketconnection();
				connections.remove(i);				
				return;
			}
			i++;
		}
		
	}

	public synchronized boolean hasExpired(int id) throws Exception {
		for (DMSIIConnectionAttributes dm: connections) {
			if (dm.getId() == id ) {
				return dm.isHasExpired();
			}
		}
		return false;
	}

}
