package com.ing.sql.model;

import java.io.Serializable;

public class FilesToRemove implements Serializable {
	
	int id;
	String folder;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	

}
