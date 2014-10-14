package com.lukaslab.proj.skt.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.lukaslab.proj.skt.cache.CacheManager;
import com.lukaslab.proj.skt.cache.LocalCacheManager;
import com.lukaslab.proj.skt.config.Config;
import com.lukaslab.proj.skt.database.DataObject;
import com.lukaslab.proj.skt.database.DatabaseManager;
import com.lukaslab.proj.skt.database.OracleDatabaseManager;
import com.lukaslab.proj.skt.logger.Logger;

public class MainJob implements Job {

	private DatabaseManager dbManager;
	private CacheManager cacheManager;

	private List<DataObject> cachedList;
	private List<DataObject> taskList;



	public MainJob() {
		dbManager = OracleDatabaseManager.getInstance();
		cacheManager = LocalCacheManager.getInstance();
	}



	public void start() {

		List<DataObject> dataList = dbManager.fetchData();
		
		cachedList = new ArrayList<DataObject>();
		taskList = new ArrayList<DataObject>();
		
		// look up cache for checking hit or miss 
		for(int i=0; i<dataList.size(); i++){
			DataObject dataObj = dataList.get(i);

			double result = cacheManager.isHit(dataObj);

			// cache hit
			if(result > -1) {
				Logger.debugln("Cache Hit!");
				dataObj.setResult(result);
				cachedList.add(dataObj);
			}

			// cache miss
			else {
				Logger.debugln("Cache Miss!");
				taskList.add(dataObj);
			}
		}


		AsyncTaskManager atm;
		int threadPoolSize = Config.getApp().getThreadPoolSize();

		// Cache Hit
		atm = new AsyncTaskManager(cachedList, threadPoolSize, "update for cache hit");
		atm.run(new AsyncTaskRunnable() {
			public void onFinished(Future f, DataObject dataObj) {
				try {
					f.get();

				} catch(ExecutionException e) {
					// TODO : 다시 실행시켜야함.

					Logger.warnln("Execution exception occurred when updating record...");
					e.printStackTrace();
				} catch(InterruptedException e) {
					// TODO : 다시 실행시켜야함.

					Logger.warnln("Interrupted exception occurred when updating record...");
					e.printStackTrace();
				}
			}
		});
		cachedList.clear();


		// Cache Miss
		//------------- taskList 의 각 dataObject 의 result 갱신 (외부 프로그램 실행)
		atm = new AsyncTaskManager(taskList, threadPoolSize, "execute external program");
		atm.run(new AsyncTaskRunnable() {
			public void onFinished(Future f, DataObject dataObj) {
				try {
					String outFileName = (String)f.get();
					Scanner scanner = new Scanner(new File(outFileName));

					double result = scanner.nextDouble();
					dataObj.setResult(result);

					scanner.close();

				} catch(FileNotFoundException e) {
					e.printStackTrace();
				} catch(ExecutionException e) {
					e.printStackTrace();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		});


		// Final task : update into Oracle DB & Java DB (Local Cache)
		//--------- taskList 에 포함된 dataObject 의 result 를 LocalCache 와 Oracle Database 갱신.
		atm = new AsyncTaskManager(taskList, threadPoolSize, "update for cache miss");
		atm.run(new AsyncTaskRunnable() {
			public void onFinished(Future f, DataObject dataObj) {
				try {
					f.get();

				} catch(ExecutionException e) {
					// TODO : 다시 실행시켜야함.

					Logger.warnln("Execution exception occurred when updating record...");
					e.printStackTrace();
				} catch(InterruptedException e) {
					// TODO : 다시 실행시켜야함.

					Logger.warnln("Interrupted exception occurred when updating record...");
					e.printStackTrace();
				}
			}
		});

		taskList.clear();
	}

}
