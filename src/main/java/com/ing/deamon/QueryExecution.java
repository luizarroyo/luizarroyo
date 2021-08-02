package com.ing.deamon;

import java.util.concurrent.Future;

public class QueryExecution {

	int id;
	Future<?> execution;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Future<?> getExecution() {
		return execution;
	}
	
	public void setExecution(Future<?> execution) {
		this.execution = execution;
	}
	
	
}
