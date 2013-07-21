package com.amumtrade.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.amumtrade.bean.StockBean;

public class EpsTtmServiceDao implements Runnable {

	StockBean stockBean;
	public EpsTtmServiceDao(StockBean stockBean) {
		this.stockBean = stockBean;
	}
	
	@Override
	public void run() {
		try {
			String charset = "UTF-8";
			URLConnection connection = new URL("http://finance.yahoo.com/q/ks?s="+stockBean.getSymbol().trim()+"+Key+Statistics").openConnection();
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			BufferedReader reader = null;
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			for (String line; (line = reader.readLine()) != null;) {
				if(line.contains("Diluted")){
					line = line.substring(line.indexOf("Diluted EPS (ttm):"), line.lastIndexOf("</td></tr><tr><td class=\"yfnc_tablehead1\" width=\"74%\">Qtrly Earnings Growth (yoy):</td>"));
					line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
					stockBean.setEpsTtm(line);
					System.out.println(stockBean.getSymbol()+"<>"+line);
				}else{
					stockBean.setEpsTtm("No Data");
					System.out.println("No Data");
				}
	        }
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
