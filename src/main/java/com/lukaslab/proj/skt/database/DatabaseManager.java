package com.lukaslab.proj.skt.database;

import java.util.List;

public interface DatabaseManager {
	public void init();
	public List<DataObject> fetchData();
	public void updateData(DataObject dataObj);
	
}
