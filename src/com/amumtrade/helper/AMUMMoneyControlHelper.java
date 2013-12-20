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
		//Net Sales as per the latest Profit & Loss Account available.
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
							  //System.out.println(companyName+","+lastPrice+","+change); 
							  resultMap.put(lastPrice, companyName+","+lastPrice+","+change);
						  }
						 
					  }
				  }

			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
		
	}

	public Map<String, String> fiftyTwoWeekAHighDigest() throws IOException{

		//Net Sales as per the latest Profit & Loss Account available.
		String httpUrl="http://www.moneycontrol.com/stocks/marketstats/highlows/index.php?sel_opt=highs&sel_index=B|A|A%20Group";
		
		String companyName = null;
		String wkHigh = null;
		String intraHigh = null;
		String lastClose = null;


		URL url = null;
	    HttpURLConnection connection = null;  
	    StringBuffer strBuffer = new StringBuffer();
	    Map<String, String> fiftyTwoWeekHighMap =   new  HashMap<String, String>();

	    try {
			  url = new URL(httpUrl);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(30000);
		      InputStream is = connection.getInputStream();
			  BufferedReader br = new BufferedReader(new InputStreamReader(is));
			  String wkLine;
			  boolean isCapture = false;
			  int resetCount = 0; 
			  while ((wkLine = br.readLine()) != null) {
				  if(wkLine.contains("Company</th>")){
					  isCapture = true;
				  }
				  if(wkLine.contains("function validation()")){
					  isCapture = false;
				  }
				  if(isCapture){
					  System.out.println(wkLine);
					  if(wkLine.contains("<td align=\"left\" class=\"brdrgtgry\">")){
						  companyName = wkLine.substring(wkLine.indexOf("<b>")+3, wkLine.lastIndexOf("</b>")).trim();
						  System.out.println(companyName);
					  }
					  if(wkLine.contains("<td width=\"19%\" align=\"right\" class=\"brdrgtgry")){ 
						  wkHigh = wkLine.substring(wkLine.indexOf("brdrgtgry")+11, wkLine.lastIndexOf("</td>")).trim();
						  System.out.println(wkHigh);

						  }
					  if(wkLine.contains("<td width=\"16%\" align=\"right\" class=\"brdrgtgry")){ 
						  intraHigh = wkLine.substring(wkLine.indexOf("brdrgtgry")+12, wkLine.lastIndexOf("</td>")).trim();
						  System.out.println(intraHigh);

						  }
					  if(wkLine.contains("<td width=\"14%\" align=\"right\" class=\"brdrgtgry")){ 
						  lastClose = wkLine.substring(wkLine.indexOf("brdrgtgry")+12, wkLine.lastIndexOf("</td>")).trim();
						  System.out.println(lastClose);

						  }
					  }
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	
	}
}
