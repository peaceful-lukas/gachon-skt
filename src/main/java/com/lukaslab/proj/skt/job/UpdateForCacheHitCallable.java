package com.lukaslab.proj.skt.job;

import java.util.concurrent.Callable;

import com.lukaslab.proj.skt.database.DataObject;
import com.lukaslab.proj.skt.database.DatabaseManager;
import com.lukaslab.proj.skt.database.OracleDatabaseManager;

public class UpdateForCacheHitCallable implements Callable<String> {
	
	private DatabaseManager dbManager = OracleDatabaseManager.getInstance();
	private DataObject dataObj;
	
	public UpdateForCacheHitCallable(DataObject dataObj) {
		this.dataObj = dataObj;
	}
	
	public String call() throws Exception {		
		dbManager.updateData(dataObj);
		return "";
	}

}
