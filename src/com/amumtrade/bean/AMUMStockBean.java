package com.amumtrade.bean;

import java.math.BigDecimal;


public class AMUMStockBean {
	private String stockName;
	private double lastScalePrice;;
	private String stockURL;
	private String PERatio;
	private String EPS;
	private String revenue; 
	private String amumtradePercent; 
	
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getLastScalePrice() {
		return lastScalePrice;
	}
	public void setLastScalePrice(double lastScalePrice) {
		this.lastScalePrice = lastScalePrice;
	}
	public String getStockURL() {
		return stockURL;
	}
	public void setStockURL(String stockURL) {
		this.stockURL = stockURL;
	}
	public String getPERatio() {
		return PERatio;
	}
	public void setPERatio(String ratio) {
		PERatio = ratio;
	}
	public String getEPS() {
		return EPS;
	}
	public void setEPS(String eps) {
		EPS = eps;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	public String getAmumtradePercent() {
		return amumtradePercent;
	}
	public void setAmumtradePercent(String amumtradePercent) {
		this.amumtradePercent = amumtradePercent;
	}

	
	
}
