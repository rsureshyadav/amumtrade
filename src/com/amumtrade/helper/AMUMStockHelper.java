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
import java.util.StringTokenizer;

import sun.net.www.protocol.http.HttpURLConnection;

public class AMUMStockHelper {

	public static String currentPrice = null;
	
	public Map<String, String> digest() throws IOException {
		String httpUrl="http://money.rediff.com/gainers/bse/daily/groupall";
		URL url = null;
	    HttpURLConnection connection = null;  
	    StringBuffer strBuffer = new StringBuffer();
	    Map<String, String> resultMap = null;
	    try {
			  url = new URL(httpUrl);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(30000);
		      InputStream is = connection.getInputStream();
			  BufferedReader br = new BufferedReader(new InputStreamReader(is));
			  String line;
			  List<String> nameList = null;
			  boolean isCapture=false;
			  while ((line = br.readLine()) != null) {
				  if(line.contains("<tbody>")){
					  isCapture=true;
				  }
				  if(line.contains("</tbody>")){
					  strBuffer.append(line+"\n");  
					  isCapture=false; 
				  }
				  if(isCapture){
					  strBuffer.append(line+"\n");  
				  }
			  }
			 String str = strBuffer.toString();
			 str = str.replaceAll("\\s+","");
			 str = str.replaceAll("</tr><tr>", "</tr>^<tr>");
			 str = str.replaceAll("\"", "");
			 str = str.replaceAll(",", "");
			 
			 StringTokenizer strTokenizer = new StringTokenizer(str,"^");
			 nameList = new ArrayList<String>();
			while (strTokenizer.hasMoreElements()){
				nameList.add(strTokenizer.nextToken());
			 }
			 resultMap = new  HashMap<String, String>();
			 for(String s : nameList){
				 System.out.println(s);
				 String finalStr = validateStrLine(s);
				 resultMap.put(currentPrice, finalStr);
			 }
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	private String validateStrLine(String str1) {
		String companyUrl=null;
		String companyName = null;
		String companyGroup = null;
		String companyPrevClose = null;
		String companyCurrentPrice = null;
		double diff = 0;
		double  priceRoundOff =0;
		try {
			companyUrl = str1.substring(str1.indexOf("ahref")+6, str1.lastIndexOf("</a>"));
			companyName = companyUrl.substring(companyUrl.indexOf(">")+1);
			companyUrl = companyUrl.substring(0, companyUrl.indexOf(">"));
			companyGroup=str1.substring(str1.indexOf("</td><td>"), str1.lastIndexOf("<FONT"));
			companyGroup=companyGroup.replace("</td><td>", ",");
			companyGroup = companyGroup.substring(companyGroup.indexOf(",")+1, companyGroup.lastIndexOf(","));
			
			String[] strSplit = companyGroup.split(",");
			companyGroup = strSplit[0];
			companyPrevClose = strSplit[1].trim(); 
			companyCurrentPrice = strSplit[2].trim(); 
			currentPrice = companyCurrentPrice;
			
			
			double prevPrice =  Double.valueOf(companyPrevClose);
			double currPrice = Double.valueOf(companyCurrentPrice);
			diff = prevPrice - currPrice;
			priceRoundOff = Math.round(diff * 100.0) / 100.0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//+","+companyUrl - will be used later
		return companyName+","+companyGroup+","+companyPrevClose+","+companyCurrentPrice+","+Double.toString(priceRoundOff);
	}
}
