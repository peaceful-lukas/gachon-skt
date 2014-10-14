package com.lukaslab.proj.skt.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lukaslab.proj.skt.database.DataObject;
import com.lukaslab.proj.skt.logger.Logger;

public class AsyncTaskManager {

	private int poolSize;
	private int taskListSize;
	private List<DataObject> taskList;
	private List<Future<String>> futureList;
	private String taskName;

	private ExecutorService execService;
	private AsyncTaskRunnable runnable;

	public AsyncTaskManager(List<DataObject> taskList, int poolSize, String taskName) {
		this.poolSize = poolSize;
		this.taskListSize = taskList.size();
		this.taskList = taskList;
		this.futureList = new ArrayList<Future<String>>();
		this.taskName = taskName;
	}
	
	
	public void run() {
		run(null);
	}
	
	public void run(AsyncTaskRunnable runnable) {
		this.runnable = runnable;
		
		execService = Executors.newFixedThreadPool(poolSize);
		submitTasks();
		handleTasks();
		execService.shutdown();
		
		futureList.clear();
	}

	private void submitTasks() {
		if("execute external program".equals(taskName)) {
			for(int i=0; i<taskListSize; i++) {			
				DataObject dataObj = taskList.get(i);
				Future<String> f = execService.submit(new ExternalExecutionCallable(dataObj, i));
				futureList.add(f);
			}
		}
		else if("update for cache hit".equals(taskName)) {
			for(int i=0; i<taskListSize; i++) {
				DataObject dataObj = taskList.get(i);
				Future<String> f = execService.submit(new UpdateForCacheHitCallable(dataObj));
				futureList.add(f);
			}
		}
		else if("update for cache miss".equals(taskName)) {
			for(int i=0; i<taskListSize; i++) {
				DataObject dataObj = taskList.get(i);
				Future<String> f = execService.submit(new UpdateForCacheMissCallable(dataObj));
				futureList.add(f);
			}
		}
		else {
			Logger.debugln("no class matched");
		}
	}
	
	private void handleTasks() {
		for(int i=0; i<taskListSize; i++) {
			Future<String> f = futureList.get(i);
			DataObject dataObj = taskList.get(i);
			
			if(runnable != null) {
				runnable.onFinished(f, dataObj);
			}
		}
	}






}
