package com.lukaslab.proj.skt.job;

import java.util.concurrent.Callable;

import com.lukaslab.proj.skt.cache.CacheManager;
import com.lukaslab.proj.skt.cache.LocalCacheManager;
import com.lukaslab.proj.skt.database.DataObject;
import com.lukaslab.proj.skt.database.DatabaseManager;
import com.lukaslab.proj.skt.database.OracleDatabaseManager;

public class UpdateForCacheMissCallable implements Callable<String> {
	
	private DatabaseManager dbManager = OracleDatabaseManager.getInstance();
	private CacheManager cacheManager = LocalCacheManager.getInstance();
	private DataObject dataObj;
	
	public UpdateForCacheMissCallable(DataObject dataObj) {
		this.dataObj = dataObj;
	}
	
	public String call() throws Exception {
		cacheManager.update(dataObj);
		dbManager.updateData(dataObj);
		return "";
	}

}
