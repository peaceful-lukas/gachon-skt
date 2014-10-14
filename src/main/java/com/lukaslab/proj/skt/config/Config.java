package com.lukaslab.proj.skt.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.lukaslab.proj.skt.logger.Logger;

public class Config {
	
	// Singleton Pattern
	private Config() {}
	private static Config instance = new Config();
		
	public static void init() {
		instance.parseXmlFile();
	}
	
	
	private ConfigApp app;
	private ConfigRemoteDB remotedb;
	private ConfigCache cache;
	
	public static ConfigApp getApp() { return instance.app; }
	public static ConfigRemoteDB getRemoteDB() { return instance.remotedb; }
	public static ConfigCache getCache() { return instance.cache; }
	
	
	/* Read config.xml file and parse each value. */
	private void parseXmlFile() {
		Logger.debugln("XML Config: init() started");
		
		try {
			File xmlFile = new File("resources/config.xml");
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document xmlDoc = docBuilder.parse(xmlFile);
			
			xmlDoc.getDocumentElement().normalize();

//@ ISSUE: I really don't know why to access the items with odd numbers, rather than with natural numbers.

			//-------- App -----------
			int threadPoolSize = Integer.valueOf(xmlDoc.getElementsByTagName("thread_pool_size").item(0).getTextContent());
			
			app = new ConfigApp();
			app.setThreadPoolSize(threadPoolSize);

			
			//-------- RemoteDB -----------
			NodeList connection = xmlDoc.getElementsByTagName("connection").item(0).getChildNodes();
			NodeList table = xmlDoc.getElementsByTagName("table").item(0).getChildNodes();
			NodeList fetch = xmlDoc.getElementsByTagName("fetch").item(0).getChildNodes();
 
			String ipaddr = connection.item(1).getTextContent();
			int port = Integer.valueOf(connection.item(3).getTextContent());
			String dbName = connection.item(5).getTextContent();
			String username = connection.item(7).getTextContent();
			String password = connection.item(9).getTextContent();
			
			String tableFrom = table.item(1).getTextContent();
			String tableTo = table.item(3).getTextContent();
			
			int maxRowsAtOnce = Integer.valueOf(fetch.item(1).getTextContent());
			ArrayList<String> deviceList = new ArrayList<String>();
			
			NodeList deviceListForNodeList = fetch.item(3).getChildNodes();
			for(int i=1; i<deviceListForNodeList.getLength(); i+=2) {
				String device = deviceListForNodeList.item(i).getTextContent();
				deviceList.add(device);
			}
			
			remotedb = new ConfigRemoteDB();
			remotedb.setIpAddress(ipaddr);
			remotedb.setPort(port);
			remotedb.setDbName(dbName);
			remotedb.setUsername(username);
			remotedb.setPassword(password);
			remotedb.setMaxRowsAtOnce(maxRowsAtOnce);
			remotedb.setDeviceList(deviceList);
			remotedb.setTableFrom(tableFrom);
			remotedb.setTableTo(tableTo);
			
			
			//-------- Cache -----------
			boolean enable = Boolean.valueOf(xmlDoc.getElementsByTagName("enable").item(0).getTextContent());
			NodeList margins = xmlDoc.getElementsByTagName("margins").item(0).getChildNodes();
			double mrg_tx_loss = Double.valueOf(margins.item(1).getTextContent());
			double mrg_tx_dmean = Double.valueOf(margins.item(3).getTextContent());
			double mrg_tx_jmean = Double.valueOf(margins.item(5).getTextContent());
			double mrg_rx_loss = Double.valueOf(margins.item(7).getTextContent());
			double mrg_rx_dmean = Double.valueOf(margins.item(9).getTextContent());
			double mrg_rx_jmean = Double.valueOf(margins.item(11).getTextContent());

			cache = new ConfigCache();
			cache.setCacheEnable(enable);
			cache.setMarginTxLoss(mrg_tx_loss);
			cache.setMarginTxDmean(mrg_tx_dmean);
			cache.setMarginTxJmean(mrg_tx_jmean);
			cache.setMarginRxLoss(mrg_rx_loss);
			cache.setMarginRxDmean(mrg_rx_dmean);
			cache.setMarginRxJmean(mrg_rx_jmean);
			
			
			
			//-------- Print values out -----------
			Logger.debugln(app.getThreadPoolSize());
			
			Logger.debugln(remotedb.getIpAddress());
			Logger.debugln(remotedb.getPort());
			Logger.debugln(remotedb.getDbName());
			Logger.debugln(remotedb.getUsername());
			Logger.debugln(remotedb.getPassword());
			Logger.debugln(remotedb.getTableFrom());
			Logger.debugln(remotedb.getTableTo());
			Logger.debugln(remotedb.getMaxRowsAtOnce());
			Logger.debugln(remotedb.getDeviceList().get(0));
			Logger.debugln(remotedb.getDeviceList().get(1));
			
			Logger.debugln(cache.isCacheEnable());
			Logger.debugln(cache.getMarginTxLoss());
			Logger.debugln(cache.getMarginTxDmean());
			Logger.debugln(cache.getMarginTxJmean());
			Logger.debugln(cache.getMarginRxLoss());
			Logger.debugln(cache.getMarginRxDmean());
			Logger.debugln(cache.getMarginRxJmean());
			
		} catch(ParserConfigurationException e) {
			Logger.error("Exception occurred when configuring xml parsor.");
			e.printStackTrace();
		} catch(SAXException e) {
			Logger.error("Exception occurred when parsing xml document.");
			e.printStackTrace();
		} catch(IOException e) {
			Logger.error("Exception occurred when handling xml document.");
			e.printStackTrace();
		}
	}
}

