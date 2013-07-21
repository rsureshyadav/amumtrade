package com.amumtrade.bean;


public class StockBean {
	private String symbol;
	private String name;
	private double lastSale;
	private String marketCap;
	private String sector;
	private String industry;
	private String summaryQuote;
	private String epsTtm;
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
	public String getEpsTtm() {
		return epsTtm;
	}
	public void setEpsTtm(String epsTtm) {
		this.epsTtm = epsTtm;
	}

}
