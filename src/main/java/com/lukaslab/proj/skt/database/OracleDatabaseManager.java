package com.lukaslab.proj.skt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lukaslab.proj.skt.config.Config;
import com.lukaslab.proj.skt.config.ConfigRemoteDB;

public class OracleDatabaseManager implements DatabaseManager {

	// Singleton Pattern
	public static OracleDatabaseManager getInstance() { return OracleDatabaseManager.instance; }
	private final static OracleDatabaseManager instance = new OracleDatabaseManager();
	
	private OracleDatabaseManager() {
		ConfigRemoteDB remotedb = Config.getRemoteDB();
		dbIpAddr = remotedb.getIpAddress();
		dbPort = remotedb.getPort();
		dbName = remotedb.getDbName();
		dbUser = remotedb.getUsername();
		dbPassword = remotedb.getPassword();
		connectionUrl = "jdbc:oracle:thin:@//" + dbIpAddr + ":" + dbPort + "/" + dbName;
		
		tableFrom = remotedb.getTableFrom();
		tableTo = remotedb.getTableTo();
	}
	
	private final String dbIpAddr;
	private final int dbPort;
	private final String dbName;
	private final String dbUser;
	private final String dbPassword;
	private final String connectionUrl;
	private final String tableFrom;
	private final String tableTo;

	
	public void init() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	private Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(connectionUrl, dbUser, dbPassword);
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	public List<DataObject> fetchData() {
		List<DataObject> dataList = new ArrayList<DataObject>();
		
		try {
			// get connection from the connection pool.
			Connection conn = getConnection();

			int maxRowsAtOnce = Config.getRemoteDB().getMaxRowsAtOnce();
			List<String> deviceList = Config.getRemoteDB().getDeviceList();
			int deviceListSize = deviceList.size();
			
//			--------------------- 완성된 쿼리 형태 예시 ------------------
//				SELECT * FROM
//		    		(SELECT * FROM 
//		            	( SELECT * FROM TWAMP WHERE device_ipaddr IN ('38.24.0.223', '38.56.0.23', '38.56.0.24'))
//		        	ORDER BY getdate DESC )
//		    	WHERE rownum <= 70;
			
			
			String query = "SELECT * FROM "
								+ "( SELECT * FROM "
										+ "( SELECT * FROM " + tableFrom + " WHERE device_ipaddr IN (";			
			
			for(int i=0; i<deviceListSize; i++) {
				if( i < deviceListSize-1 )	query += "?, ";
				else						query += "?";
			}
			query += ")) ORDER BY getdate DESC ) WHERE rownum <= ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			for(int i=1; i<=deviceListSize; i++) {
				pstmt.setString(i, deviceList.get(i-1));
			}
			pstmt.setInt(deviceListSize+1, maxRowsAtOnce);
			
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String targetDevice = rs.getString("target_device");
				String groupName = rs.getString("group_name");
				String deviceIpAddr = rs.getString("device_ipaddr");
				String getdate = rs.getString("getdate");
				double tx_loss = rs.getDouble("tx_loss");
				int tx_dmean = rs.getInt("tx_delay");
				int tx_jmean = rs.getInt("tx_jitter");
				double rx_loss = rs.getDouble("rx_loss");
				int rx_dmean = rs.getInt("rx_delay");
				int rx_jmean = rs.getInt("rx_jitter");

				DataObject dataObj = new DataObject(targetDevice, groupName, deviceIpAddr, getdate,
						tx_loss, tx_dmean, tx_jmean, rx_loss, rx_dmean, rx_jmean);
				
				dataList.add(dataObj);
			}
			
			rs.close();
			conn.close();

		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	public void updateData(DataObject dataObj) {
		String targetDevice = dataObj.getTargetDevice();
		String groupName = dataObj.getGroupName();
		String deviceIpAddr = dataObj.getDeviceIpAddr();
		String getdate = dataObj.getGetdate();
		double userQos = dataObj.getResult();
		
		try {
			Connection conn = getConnection();
			
			String query = "INSERT INTO " + tableTo + " "
					+ "(target_device, group_name, device_ipaddr, getdate, user_qos) "
					+ "VALUES(?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, targetDevice);
			pstmt.setString(2, groupName);
			pstmt.setString(3, deviceIpAddr);
			pstmt.setString(4, getdate);
			pstmt.setDouble(5, userQos);
			
			pstmt.executeUpdate();
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}