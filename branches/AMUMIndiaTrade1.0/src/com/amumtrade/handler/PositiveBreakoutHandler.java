package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PositiveBreakoutHandler {
private String thirthyDaysPostiveURl ="http://www.moneycontrol.com/technicals/breakout/positive/avg30/index.html";
private String fiftyDaysPostiveURl ="http://www.moneycontrol.com/technicals/breakout/positive/avg50/index.html";
private String oneFiftyDaysPostiveURl ="http://www.moneycontrol.com/technicals/breakout/positive/avg150/index.html";
private String twoHundredPostiveURl ="http://www.moneycontrol.com/technicals/breakout/positive/avg200/index.html";

private Set<String> positiveBreakoutSet = new HashSet<String>();

	public Set<String> execute(){
		try {
			List<String> postiveUrlList = new ArrayList<String>();
			postiveUrlList.add(thirthyDaysPostiveURl);
			postiveUrlList.add(fiftyDaysPostiveURl);
			postiveUrlList.add(oneFiftyDaysPostiveURl);
			postiveUrlList.add(twoHundredPostiveURl);
			for(String url : postiveUrlList){
				getCurrentPositiveBreakoutRecords(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return positiveBreakoutSet;
	}
	/*
	 * This method will convert  <a href="/stock-charts/vardhmanholdings/charts/VH05#VH05" class="bl_12">
	 *  to VH05
	 */
	private void getCurrentPositiveBreakoutRecords(String url) {
		String inputLine = null;
		BufferedReader in = null;
		
		String apiKeyName = null;
		try {
			 URL website = new URL(url);
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
			 while ((inputLine = in.readLine()) != null)
		        {
				 if(inputLine.contains("<td width=\"147\">")){
					 apiKeyName = inputLine.trim();
					 apiKeyName = apiKeyName.substring(apiKeyName.indexOf("#"),apiKeyName.lastIndexOf("class")).trim();
					 apiKeyName = apiKeyName.replace("#", "");
					 apiKeyName = apiKeyName.replace("\"", "");
					 positiveBreakoutSet.add(apiKeyName);
				 }
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
