package com.amumtrade.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.net.www.protocol.http.HttpURLConnection;

public class AMUMMoneyControlHelper {

	public Map<String, String> digestNetProfit() throws IOException {
		//Net Sales as per the latest Profit & Loss Account available.
		String httpUrl="http://www.moneycontrol.com/stocks/top-companies-in-india/net-profit-nse.html";
		String companyName = null;
		String lastPrice = null;
		String change = null;

		URL url = null;
	    HttpURLConnection connection = null;  
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

	public Map<String, String> digestFiftyTwoWeekHigh() throws IOException{

		//Net Sales as per the latest Profit & Loss Account available.
	    Map<String, String> fiftyTwoWeekHighMap =   new  HashMap<String, String>();

		List<String>  httpUrlList = new ArrayList<String>();
		//A Group
		httpUrlList.add("http://www.moneycontrol.com/stocks/marketstats/highlows/index.php?sel_opt=highs&sel_index=B|A|A%20Group");
		//B Group
		httpUrlList.add("http://www.moneycontrol.com/stocks/marketstats/highlows/homebody.php?sel_opt=highs&sel_index=B|B|B%20Group");
		int groupNameCount = httpUrlList.size();
		for(String  httpUrl :httpUrlList ){
		String companyName = null;
		String wkHigh = null;
		String intraHigh = null;
		String lastClose = null;
		String groupName = null;
		double priceUp = 0.0;
		if(groupNameCount == 2){
			groupName = "A";
		}else {
			groupName = "B";
		}
		URL url = null;
	    HttpURLConnection connection = null;  

	    try {
			  url = new URL(httpUrl);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(30000);
		      InputStream is = connection.getInputStream();
			  BufferedReader br = new BufferedReader(new InputStreamReader(is));
			  String wkLine;
			  boolean isCapture = false;
			  while ((wkLine = br.readLine()) != null) {
				  if(wkLine.contains("Company</th>")){
					  isCapture = true;
				  }
				  if(wkLine.contains("function validation()")){
					  isCapture = false;
				  }
				  if(isCapture){
					  if(wkLine.contains("<td align=\"left\" class=\"brdrgtgry\">")){
						  companyName = wkLine.substring(wkLine.indexOf("<b>")+3, wkLine.lastIndexOf("</b>")).trim();
					  }else if(wkLine.contains("<td width=\"19%\" align=\"right\" class=\"brdrgtgry")){ 
						  wkHigh = wkLine.substring(wkLine.indexOf("brdrgtgry")+11, wkLine.lastIndexOf("</td>")).trim();
					  }else if(wkLine.contains("<td width=\"16%\" align=\"right\" class=\"brdrgtgry")){ 
						  intraHigh = wkLine.substring(wkLine.indexOf("brdrgtgry")+12, wkLine.lastIndexOf("</td>")).trim();
					  }else if(wkLine.contains("<td width=\"14%\" align=\"right\" class=\"brdrgtgry")){ 
						  lastClose = wkLine.substring(wkLine.indexOf("brdrgtgry")+11, wkLine.lastIndexOf("</td>")).trim();
						  wkHigh = wkHigh.replace(",", "");
						  lastClose = lastClose.replace(",", "");
						  priceUp =  Double.valueOf(lastClose) - Double.valueOf(wkHigh);
						  priceUp = Math.round( priceUp * 100.0 ) / 100.0;
						  
						  fiftyTwoWeekHighMap.put(intraHigh, companyName+","+groupName+","+wkHigh+","+intraHigh+","+lastClose+","+priceUp);
						//  System.out.println(companyName+","+groupName+","+wkHigh+","+intraHigh+","+lastClose+","+priceUp);

						  }

					  }
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		groupNameCount--;
		}
		return fiftyTwoWeekHighMap;
		
	
	}
	

	public Map<String, String> digestFiftyTwoWeekLow() throws IOException{


		//Net Sales as per the latest Profit & Loss Account available.
	    Map<String, String> fiftyTwoWeekLowMap =   new  HashMap<String, String>();
		List<String>  httpUrlList = new ArrayList<String>();
		//A Group
		httpUrlList.add("http://www.moneycontrol.com/stocks/marketstats/highlows/index.php?sel_opt=lows&sel_index=B|A|A%20Group");
		//B Group
		httpUrlList.add("http://www.moneycontrol.com/stocks/marketstats/highlows/homebody.php?sel_opt=lows&sel_index=B|B|B%20Group");
		int groupNameCount = httpUrlList.size();
		for(String  httpUrl :httpUrlList ){
		String companyName = null;
		String wkLow = null;
		String intraLow = null;
		String lastClose = null;
		String groupName = null;
		double priceUp = 0.0;
		if(groupNameCount == 2){
			groupName = "A";
		}else {
			groupName = "B";
		}
		URL url = null;
	    HttpURLConnection connection = null;  

	    try {
			  url = new URL(httpUrl);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(30000);
		      InputStream is = connection.getInputStream();
			  BufferedReader br = new BufferedReader(new InputStreamReader(is));
			  String wkLine;
			  boolean isCapture = false;
			  while ((wkLine = br.readLine()) != null) {
				  if(wkLine.contains("Company</th>")){
					  isCapture = true;
				  }
				  if(wkLine.contains("function validation()")){
					  isCapture = false;
				  }
				  if(isCapture){
					  if(wkLine.contains("<td align=\"left\" class=\"brdrgtgry\">")){
						  companyName = wkLine.substring(wkLine.indexOf("<b>")+3, wkLine.lastIndexOf("</b>")).trim();
					  }else if(wkLine.contains("<td width=\"19%\" align=\"right\" class=\"brdrgtgry")){ 
						  wkLow = wkLine.substring(wkLine.indexOf("brdrgtgry")+11, wkLine.lastIndexOf("</td>")).trim();
					  }else if(wkLine.contains("<td width=\"16%\" align=\"right\" class=\"brdrgtgry")){ 
						  intraLow = wkLine.substring(wkLine.indexOf("brdrgtgry")+12, wkLine.lastIndexOf("</td>")).trim();
					  }else if(wkLine.contains("<td width=\"14%\" align=\"right\" class=\"brdrgtgry")){ 
						  lastClose = wkLine.substring(wkLine.indexOf("brdrgtgry")+11, wkLine.lastIndexOf("</td>")).trim();
						  wkLow = wkLow.replace(",", "");
						  lastClose = lastClose.replace(",", "");
						  priceUp =  Double.valueOf(lastClose) - Double.valueOf(wkLow);
						  priceUp = Math.round( priceUp * 100.0 ) / 100.0;
						  
						  fiftyTwoWeekLowMap.put(intraLow, companyName+","+groupName+","+wkLow+","+intraLow+","+lastClose+","+priceUp);
						  System.out.println(companyName+","+groupName+","+wkLow+","+intraLow+","+lastClose+","+priceUp);

						  }

					  }
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		groupNameCount--;
		}
		return fiftyTwoWeekLowMap;
		
	
	
	}

}
