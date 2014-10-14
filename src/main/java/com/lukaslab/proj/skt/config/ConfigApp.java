package com.lukaslab.proj.skt.config;

public class ConfigApp {
	private int threadPoolSize;
	
	public ConfigApp() {}
	
	public void setThreadPoolSize(int threadPoolSize) { this.threadPoolSize = threadPoolSize; }
	public int getThreadPoolSize() { return this.threadPoolSize; }
}
