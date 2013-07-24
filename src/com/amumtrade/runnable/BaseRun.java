package com.amumtrade.runnable;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;

import com.amumtrade.bean.StockBean;

public abstract class BaseRun implements Runnable{
	
	protected String urlString =null;
	protected String symbol = null;
	protected HashMap<String, String> ratingMap = null;
	
	public BaseRun(String urlString, String symbol,HashMap<String, String> ratingMap ){
		this.urlString = urlString;
		this.symbol = symbol;
		this.ratingMap = ratingMap;
	}

	public String getResponseData(URLConnection conn) throws Exception {
		StringBuffer sb = new StringBuffer();
		String data = "";
		InputStream is = conn.getInputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			sb.append((char) ch);
		}

		data = sb.toString();
		is.close();
		is = null;
		sb = null;
		System.gc();
		return data;
	}

/*	public String getFinalStock() {
		return finalStock;
	}

	public void setFinalStock(String finalStock) {
		this.finalStock = finalStock;
	}*/
	
}
