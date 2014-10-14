package com.lukaslab.proj.skt.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	public static final int LEVEL_NONE = 0;
	public static final int LEVEL_DEBUG = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_WARN = 3;
	public static final int LEVEL_ERROR = 4;
	
	private static int level = LEVEL_NONE;
	public static void setLevel(int level) { Logger.level = level; }
	
	public static void debug(Object obj) {
		if(Logger.level <= LEVEL_DEBUG) {
			obj = createTimestamp() + obj;
			System.out.print(obj);
		}
	}
	
	public static void info(Object obj) {
		if(Logger.level <= LEVEL_INFO) {
			obj = createTimestamp() + obj;
			System.out.print(obj);
		}
	}
	
	public static void warn(Object obj) {
		if(Logger.level <= LEVEL_WARN) {
			obj = createTimestamp() + obj;
			System.out.print(obj);
		}
	}
	
	public static void error(Object obj) {
		if(Logger.level <= LEVEL_ERROR) {
			obj = createTimestamp() + obj;
			System.out.print(obj);
		}
	}
	
	public static void debugln(Object obj) {
		if(Logger.level <= LEVEL_DEBUG) {
			obj = createTimestamp() + obj;
			System.out.println(obj);
		}
	}
	
	public static void infoln(Object obj) {
		if(Logger.level <= LEVEL_INFO) {
			obj = createTimestamp() + obj;
			System.out.println(obj);
		}
	}
	
	public static void warnln(Object obj) {
		if(Logger.level <= LEVEL_WARN) {
			obj = createTimestamp() + obj;
			System.out.println(obj);
		}
	}
	
	public static void errorln(Object obj) {
		if(Logger.level <= LEVEL_ERROR) {
			obj = createTimestamp() + obj;
			System.out.println(obj);
		}
	}
	
	private static String createTimestamp() {
		String timestamp = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss] ").format(new Date());
		return timestamp;
	}

}
