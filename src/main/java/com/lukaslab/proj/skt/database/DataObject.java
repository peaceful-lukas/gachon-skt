package com.lukaslab.proj.skt.database;

public class DataObject {
	public String targetDevice;
	public String groupName;
	public String deviceIpAddr;
	public String getdate;
	public double tx_loss;
	public int tx_dmean;
	public int tx_jmean;
	public double rx_loss;
	public int rx_dmean;
	public int rx_jmean;
	
	public double result;
	
	
	public DataObject(String targetDevice, String groupName, String deviceIpAddr, String getdate, double tx_loss, int tx_dmean, int tx_jmean, double rx_loss, int rx_dmean, int rx_jmean) {
		this.targetDevice = targetDevice;
		this.groupName = groupName;
		this.deviceIpAddr = deviceIpAddr;
		this.getdate = getdate;
		this.tx_loss = tx_loss;
		this.tx_dmean = tx_dmean;
		this.tx_jmean = tx_jmean;
		this.rx_loss = rx_loss;
		this.rx_dmean = rx_dmean;
		this.rx_jmean = rx_jmean;
	}
	
	public void setTargetDevice(String targetDevice) { this.targetDevice = targetDevice; }
	public void setGroupName(String groupName) { this.groupName = groupName; }
	public void setDeviceIpAddr(String deviceIpAddr) { this.deviceIpAddr = deviceIpAddr; }
	public void setGetdate(String getdate) { this.getdate = getdate; }
	public void setTxLoss(double tx_loss) { this.tx_loss = tx_loss; }
	public void setTxDmean(int tx_dmean) { this.tx_dmean = tx_dmean; }
	public void setTxJmean(int tx_jmean) { this.tx_jmean = tx_jmean; }
	public void setRxLoss(double rx_loss) { this.rx_loss = rx_loss; }
	public void setRxDmean(int rx_dmean) { this.rx_dmean = rx_dmean; }
	public void setRxJmean(int rx_jmean) { this.rx_jmean = rx_jmean; }
	public void setResult(double result) { this.result = result; }
	
	public String getTargetDevice() { return this.targetDevice; }
	public String getGroupName() { return this.groupName; }
	public String getDeviceIpAddr() { return this.deviceIpAddr; }
	public String getGetdate() { return this.getdate; }
	public double getTxLoss() { return this.tx_loss; }
	public int getTxDmean() { return this.tx_dmean; }
	public int getTxJmean() { return this.tx_jmean; }
	public double getRxLoss() { return this.rx_loss; }
	public int getRxDmean() { return this.rx_dmean; }
	public int getRxJmean() { return this.rx_jmean; }
	public double getResult() { return this.result; }
	
}
