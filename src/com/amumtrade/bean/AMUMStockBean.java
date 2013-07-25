package com.amumtrade.bean;


public class AMUMStockBean {
	private String scripCode;
	private String scripId;
	private String scripName;
	private String status;
	private String group;
	private String faceValue;
	private String ISINNo;
	private String lastScale;
	
	
	public String getLastScale() {
		return lastScale;
	}
	public void setLastScale(String lastScale) {
		this.lastScale = lastScale;
	}
	public String getISINNo() {
		return ISINNo;
	}
	public void setISINNo(String iSINNo) {
		ISINNo = iSINNo;
	}
	private String industry;
	private String instrument;
	
	public String getScripCode() {
		return scripCode;
	}
	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}
	public String getScripId() {
		return scripId;
	}
	public void setScripId(String scripId) {
		this.scripId = scripId;
	}
	public String getScripName() {
		return scripName;
	}
	public void setScripName(String scripName) {
		this.scripName = scripName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	
	

}
