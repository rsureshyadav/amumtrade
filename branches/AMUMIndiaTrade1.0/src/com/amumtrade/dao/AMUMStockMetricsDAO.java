package com.amumtrade.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import sun.net.www.protocol.http.HttpURLConnection;

import com.amumtrade.bean.AMUMStockBean;

public class AMUMStockMetricsDAO implements Runnable {

	AMUMStockBean bean;
	String EPS = null;
	String Revenue = null;
	String PE_Ratio = null;
	public AMUMStockMetricsDAO(AMUMStockBean bean) {
		this.bean = bean;
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
		      connection.setReadTimeout(10000);
		      
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
					PE_Ratio = line.replace("\">", "");
				//	System.out.println("P/E Ratio>>"+PE_Ratio);
					isCapture = false;
					name=null;
				}
				if(line.contains("EPS (Rs.)")){
					isCapture = true;
					name = "EPS";
				}else if(isCapture && name.equalsIgnoreCase("EPS") ){
					line = line.replace("<td>", "");
					EPS = line.replace("</td>", "");
				//	System.out.println("EPS>>"+EPS);
					isCapture= false;
					name = null;
				}
				
				if(line.contains("Revenue (Rs. Cr)")){
					isCapture = true;
					name = "Revenue";
				}else if(isCapture && name.equalsIgnoreCase("Revenue") ){
					line = line.replace("<td>", "");
					Revenue = line.replace("</td>", "");
					//System.out.println("Revenue>>"+Revenue);
					isCapture = false;
					break;
				}
			
			}
			System.out.println(bean.getStockName()+", "+PE_Ratio.trim()+", "+EPS.trim()+", "+Revenue.trim());
		      br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		      if(connection != null) {
		        connection.disconnect(); 
		      }
		    }
		
	
		
	}

}