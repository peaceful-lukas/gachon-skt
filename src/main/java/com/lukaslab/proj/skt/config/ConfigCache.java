package com.lukaslab.proj.skt.config;

public class ConfigCache {
	private boolean enable;
	private double mrg_tx_loss;
	private double mrg_tx_dmean;
	private double mrg_tx_jmean;
	private double mrg_rx_loss;
	private double mrg_rx_dmean;
	private double mrg_rx_jmean;
	
	public ConfigCache() {}
	public ConfigCache(boolean enable, double txl, double txd, double txj, double rxl, double rxd, double rxj) {
		this.enable = enable;
		this.mrg_tx_loss = txl;
		this.mrg_tx_dmean = txd;
		this.mrg_tx_jmean = txj;
		this.mrg_rx_loss = rxl;
		this.mrg_rx_dmean = rxd;
		this.mrg_rx_jmean = rxj;
	}
	
	public void setCacheEnable(boolean enable) { this.enable = enable; }
	public void setMarginTxLoss(double mrg_tx_loss) { this.mrg_tx_loss = mrg_tx_loss; }
	public void setMarginTxDmean(double mrg_tx_dmean) { this.mrg_tx_dmean = mrg_tx_dmean; }
	public void setMarginTxJmean(double mrg_tx_jmean) { this.mrg_tx_jmean = mrg_tx_jmean; }
	public void setMarginRxLoss(double mrg_rx_loss) { this.mrg_rx_loss = mrg_rx_loss; }
	public void setMarginRxDmean(double mrg_rx_dmean) { this.mrg_rx_dmean = mrg_rx_dmean; }
	public void setMarginRxJmean(double mrg_rx_jmean) { this.mrg_rx_jmean = mrg_rx_jmean; }
	
	public boolean isCacheEnable() { return this.enable; }
	public double getMarginTxLoss() { return this.mrg_tx_loss; }
	public double getMarginTxDmean() { return this.mrg_tx_dmean; }
	public double getMarginTxJmean() { return this.mrg_tx_jmean; }
	public double getMarginRxLoss() { return this.mrg_rx_loss; }
	public double getMarginRxDmean() { return this.mrg_rx_dmean; }
	public double getMarginRxJmean() { return this.mrg_rx_jmean; }
}
