package com.amumtrade.bean;


public class AMUMStockBean {
	private String symbol;
	private String name;
	private double lastSale;
	private String marketCap;
	private String sector;
	private String industry;
	private String summaryQuote;
	private String eps;
	private String operatingMargin;
	private String returnOnAssets;
	private String returnOnEquity; 
	private String revenuePerShare; 
	private String dilutedEPS;
	
	private int totalCount;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLastSale() {
		return lastSale;
	}
	public void setLastSale(double lastSale) {
		this.lastSale = lastSale;
	}
	public String getMarketCap() {
		return marketCap;
	}
	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getSummaryQuote() {
		return summaryQuote;
	}
	public void setSummaryQuote(String summaryQuote) {
		this.summaryQuote = summaryQuote;
	}
	public String getEps() {
		return eps;
	}
	public void setEps(String eps) {
		this.eps = eps;
	}
	public String getOperatingMargin() {
		return operatingMargin;
	}
	public void setOperatingMargin(String operatingMargin) {
		this.operatingMargin = operatingMargin;
	}
	public String getReturnOnAssets() {
		return returnOnAssets;
	}
	public void setReturnOnAssets(String returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}
	public String getReturnOnEquity() {
		return returnOnEquity;
	}
	public void setReturnOnEquity(String returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}
	public String getRevenuePerShare() {
		return revenuePerShare;
	}
	public void setRevenuePerShare(String revenuePerShare) {
		this.revenuePerShare = revenuePerShare;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getDilutedEPS() {
		return dilutedEPS;
	}
	public void setDilutedEPS(String dilutedEPS) {
		this.dilutedEPS = dilutedEPS;
	}

	
}
