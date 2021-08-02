package com.ing.sql.util;

public enum MessageType {
	
	WARNING("WARNING"),
	ERROR("ERROR"),
	SUCCESS("SUCCESS"),
	INFORMATION("INFORMATION");

	private String description;
	
	MessageType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
