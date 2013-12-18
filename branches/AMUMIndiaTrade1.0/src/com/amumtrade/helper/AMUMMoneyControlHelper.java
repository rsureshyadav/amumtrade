package com.amumtrade.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import sun.net.www.protocol.http.HttpURLConnection;

public class AMUMMoneyControlHelper {

	public Map<String, String> digest() throws IOException {
		String httpUrl="http://www.moneycontrol.com/stocks/top-companies-in-india/net-profit-nse.html";
		String companyName = null;
		String lastPrice = null;
		String change = null;

		URL url = null;
	    HttpURLConnection connection = null;  
	    StringBuffer strBuffer = new StringBuffer();
	    Map<String, String> resultMap =   new  HashMap<String, String>();
	    try {
			  url = new URL(httpUrl);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(30000);
		      InputStream is = connection.getInputStream();
			  BufferedReader br = new BufferedReader(new InputStreamReader(is));
			  String line;
			  boolean isCapture = false;
			  int resetCount = 0; 
			  while ((line = br.readLine()) != null) {
				  if(line.contains("Company Name")){
					  isCapture = true;
				  }
				  if(line.contains("function swap_tab(id)")){
					  isCapture = false;
				  }
				  if(isCapture){
					  // To find the Company name
					  if(line.contains("<td width=\"25%\" class=\"brdrgtgry\">")){
						  companyName = line.substring(line.indexOf("<b>")+3, line.lastIndexOf("</b>")).trim();
						  resetCount = 0;
					  }
					  // To find the Last Price
					  if(line.contains("<td align=\"right\" class=\"brdrgtgry\"")){
						  if(resetCount == 0){
							  lastPrice = line.substring(line.indexOf(">")+1, line.lastIndexOf("</td>")).trim();
							  lastPrice = lastPrice.replace(",", "");
							  resetCount ++;
						  }else if(resetCount == 1){
							// To find the Change price
							  change = line.substring(line.indexOf(">")+1, line.lastIndexOf("</td>")).trim();
							  resetCount ++;
							  System.out.println(companyName+","+lastPrice+","+change); 
							  resultMap.put(lastPrice, companyName+","+lastPrice+","+change);
						  }
						 
					  }
					  if(line.contains("<td align=\"right\" class=\"brdrgtgry\"") && resetCount != 0){
						  
					  }

				  }
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
		
	}
}
