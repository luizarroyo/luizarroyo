package com.ing.sql.util;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.poi.poifs.macros.Module;

import com.ing.sql.model.Message;
import com.ing.sql.repository.Messages;

public class WriteLog implements Serializable {
	
	
	private EntityManager manager;
	
	@Inject
	private Messages messages;

	@Inject
	public WriteLog(EntityManager manager) throws Exception {
		this.manager = manager;
		messages=new Messages(manager);
	}
	
	public void writeWindowsEvent(String text, ModuleType moduleType, MessageType  messageType) {
		
		String command = "eventcreate "
	               + " /l APPLICATION"
	               + " /so \""+moduleType.getDescription()+"\""
	               + " /t " + messageType.getDescription()
	               + " /id 590 " 
	               + " /d \"" + text + "\"";

	    try {
			Runtime.getRuntime().exec(command);
			writeLogTable(text, messageType, moduleType);
		} catch (IOException e) {
			writeLogTable("Windows Event "+e.getMessage(), messageType, ModuleType.SECURITY);
			e.printStackTrace();
		}
		
	}

	public void writeOnlyWindowsEvent(String text, ModuleType moduleType, MessageType  type) {
		
		String command = "eventcreate "
	               + " /l APPLICATION"
	               + " /so \""+moduleType.getDescription()+"\""
	               + " /t " + type.getDescription()
	               + " /id 590 " 
	               + " /d \"" + text + "\"";

	    try {
			Runtime.getRuntime().exec(command);			
		} catch (IOException e) {
			writeLogTable("Windows Event "+e.getMessage(),MessageType.ERROR, moduleType);
			e.printStackTrace();
		}
		
	}

	public void writeLogTable(String text, MessageType type, ModuleType module) {
		
		Message msg = new Message();
		msg.setModuleName(module.getDescription());
		msg.setMsgType(type.getDescription());
		msg.setMsgText(text);
		messages.insert(msg);
		
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	
}
