package com.ing.sql.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public class FieldsDialogColumns implements Serializable {

	private String dataname;
	private String type;
	private int lenght;
	private int decimals;
	private String description;

	public FieldsDialogColumns(String dataname, String type,  int length, int decimals, String description) {
        this.dataname = dataname;
        this.description=description;
        this.type=type;
        this.lenght = length;
        this.decimals = decimals;
    }


	public String getDataname() {
		return dataname;
	}
	public void setDataname(String dataname) {
		this.dataname = dataname;
	}
	public int getLenght() {
		return lenght;
	}
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	public int getDecimals() {
		return decimals;
	}
	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
}
