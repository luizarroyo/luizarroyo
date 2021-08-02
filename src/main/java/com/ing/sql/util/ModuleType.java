package com.ing.sql.util;

public enum ModuleType {
	
	ADMIN("ADMIN"),
	BACKGROUNDQUERY("BACKGOUNDQUERY"),
	SECURITY("SECURITY"),
	SQL("SQL"),
	OPERATOR("OPERATOR");

	private String description;
	
	ModuleType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
