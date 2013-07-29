package com.amumtrade.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import sun.net.www.protocol.http.HttpURLConnection;

public class AMUMStockFilterDAO implements Runnable {

	String targetURL;
	int  count =0;
	BufferedWriter bwObj;
	
	public AMUMStockFilterDAO(String targetURL, BufferedWriter bwObj) {
		this.targetURL = targetURL;
		this.bwObj = bwObj;
	}
	
	@Override
	public void run() {
		URL url;
	    HttpURLConnection connection = null;  
	    String stockName = null;
	    String lastScalePrice = null;
	    String stockUrl = null;
		try {
			 url = new URL(targetURL);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(30000);
		      
		    InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			boolean isCapture = false;
			boolean isExit = false;
			while ((line = br.readLine()) != null) {
				if (line.contains("<tbody>")) {
					isCapture = true;
				} else if (isCapture) {

					if (line.contains("<td class=\"first\">")) {
						count = 1;
						line = line.replace("<td class=\"first\">", "");
						stockUrl = line.substring(line.indexOf("<a href=\""), line.lastIndexOf("\">"));
						stockUrl = stockUrl.replace("<a href=\"", "");
						line = line.substring(line.indexOf("\">"), line.lastIndexOf("</a>"));
						line = line.replace("\">", "");
						stockName = line;
					}
					if (count == 3) {
						line = line.replace("<td align=\"right\">", "");
						line = line.replace("</td>", "");
						lastScalePrice = line.replace(",", "");
						bwObj.write(stockName+","+lastScalePrice.trim()+","+stockUrl);
						bwObj.write("\n");
					}
					count++;
					if (line.contains("</tbody>")) {
						isExit = true;
					} else if (isExit) {
						break;
					}
					
				}
		      }
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
