package com.lukaslab.proj.skt.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigRemoteDB {
	private String ipaddr;
	private int port;
	private String dbName;  
	private String username;
	private String password;
	private int maxRowsAtOnce;
	private List<String> deviceList = new ArrayList<String>();
	private String tableFrom;
	private String tableTo;
	
	public ConfigRemoteDB() {}
	public ConfigRemoteDB(String ipaddr, int port, String username, String password, int maxRowsAtOnce, ArrayList<String> deviceList, String from, String to) {
		this.ipaddr = ipaddr;
		this.port = port;
		this.username = username;
		this.password = password;
		this.maxRowsAtOnce = maxRowsAtOnce;
		this.deviceList = deviceList;
		this.tableFrom = from;
		this.tableTo = to;
	}
	
	public void setIpAddress(String ipaddr) { this.ipaddr = ipaddr; }
	public void setPort(int port) { this.port = port; }
	public void setDbName(String dbName) { this.dbName = dbName; }
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	public void setMaxRowsAtOnce(int maxRowsAtOnce) { this.maxRowsAtOnce = maxRowsAtOnce; }
	public void setDeviceList(ArrayList<String> deviceList) { this.deviceList = deviceList; }
	public void setTableFrom(String tableFrom) { this.tableFrom = tableFrom; }
	public void setTableTo(String tableTo) { this.tableTo = tableTo; }
	
	public String getIpAddress() { return this.ipaddr; }
	public int getPort() { return this.port; }
	public String getDbName() { return this.dbName; }
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	public int getMaxRowsAtOnce() { return this.maxRowsAtOnce; }
	public List<String> getDeviceList() { return this.deviceList; }
	public String getTableFrom() { return this.tableFrom; }
	public String getTableTo() { return this.tableTo; }
}
