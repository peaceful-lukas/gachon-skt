package com.lukaslab.proj.skt.job;

import java.util.concurrent.Future;

import com.lukaslab.proj.skt.database.DataObject;

public interface AsyncTaskRunnable {
	public void onFinished(Future f, DataObject dataObj);

}
