package com.amumtrade.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;

import sun.net.www.protocol.http.HttpURLConnection;

import com.amumtrade.bean.AMUMStockBean;

public class AMUMStockMetricsDAO implements Runnable {

	AMUMStockBean bean;
/*	String EPS = null;
	String Revenue = null;
	String PE_Ratio = null;*/
	BufferedWriter bwObj;
	public AMUMStockMetricsDAO(AMUMStockBean bean, BufferedWriter bwObj) {
		this.bean = bean;
		this.bwObj = bwObj;
	}

	@Override
	public void run() {

		URL url;
	    HttpURLConnection connection = null;  
		try {
			String targetURL=bean.getStockURL();
			//String targetURL="http://msn.bankbazaar.com/tata-consultancy-services-ltd/stock?scid=16467";
			 url = new URL(targetURL);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(20000);
		      
		    InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			boolean isCapture = false;
			String name =null;
			while ((line = br.readLine()) != null) {
				if(line.contains("P/E Ratio</td>")){
					isCapture = true;
					name ="P/E Ratio";
				}else if(isCapture && name.equalsIgnoreCase("P/E Ratio")){
					line = line.substring(line.indexOf("\">"),line.lastIndexOf("</td>"));
					line = line.replace("\">", "").trim();
					line = line.replace(",", "");
					bean.setPERatio(line);
				//	System.out.println("P/E Ratio>>"+PE_Ratio);
					isCapture = false;
					name=null;
				}
				if(line.contains("EPS (Rs.)")){
					isCapture = true;
					name = "EPS";
				}else if(isCapture && name.equalsIgnoreCase("EPS") ){
					line = line.replace("<td>", "");
					line = line.replace("</td>", "").trim();
					line = line.replace(",", "");
					bean.setEPS(line);
				//	System.out.println("EPS>>"+EPS);
					isCapture= false;
					name = null;
				}
				
				if(line.contains("Revenue (Rs. Cr)")){
					isCapture = true;
					name = "Revenue";
				}else if(isCapture && name.equalsIgnoreCase("Revenue") ){
					line = line.replace("<td>", "");
					line = line.replace("</td>", "").trim();
					line = line.replace(",", "");
					bean.setRevenue(line);
					//System.out.println("Revenue>>"+Revenue);
					isCapture = false;
					break;
				}
			
			}
			checkPEValidation(bwObj);
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		      if(connection != null) {
		        connection.disconnect(); 
		      }
		    }
		
	
		
	}

	private void checkPEValidation(BufferedWriter bwObj) {
		try {
			if(!bean.getPERatio().contains("-") && !bean.getPERatio().contains("0") 
					&& !bean.getEPS().contains("-") && !bean.getEPS().contains("0") ){
				double peratio = Double.valueOf(bean.getPERatio());
				double sharePrice = Double.valueOf(bean.getLastScalePrice());
				double amumtradeRate = (peratio * sharePrice * 100);
				if(peratio >= 10){
					bean.setAmumtradePercent(String.valueOf(amumtradeRate));
					bwObj.write("\n");
					bwObj.write(bean.getStockName()+","+bean.getLastScalePrice()+","+bean.getPERatio()+","+bean.getAmumtradePercent()+","+bean.getEPS()+","+bean.getRevenue());
					System.out.println(bean.getStockName()+","+bean.getLastScalePrice()+","+bean.getPERatio()+","+bean.getEPS()+","+bean.getRevenue());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}