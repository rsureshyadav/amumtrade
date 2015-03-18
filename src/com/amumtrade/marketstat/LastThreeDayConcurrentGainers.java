package com.amumtrade.marketstat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.FileNameConstant;

public class LastThreeDayConcurrentGainers {
	
	private String concurrentGainersUrl = FileNameConstant.LAST_THREE_DAY_GAINER_URL;
	private List<ConcurrentGainersBean> concurrentGainersList;
	public List<ConcurrentGainersBean>  execute() throws IOException{
		concurrentGainersList = new ArrayList<ConcurrentGainersBean>();
		concurrentGainersList = getLastThreeDayGainers(concurrentGainersUrl);
		return concurrentGainersList;
	}
	public static List<ConcurrentGainersBean> getLastThreeDayGainers(String url) throws IOException{
		List<ConcurrentGainersBean> recordList = null;
		BufferedReader in = null;
		URL website = null;
		ConcurrentGainersBean bean = null;
		String companyName = null;
		String urlAPI = null;
		String currentPrice = null;
		String currentPercentChange = null;
		String currentVolume = null;
		String threeDayAgoPrice = null;
		String threeDayAgoPercentChange = null;
		String fiveDayAgoPrice = null;
		String fiveDayAgoPercentChange  = null;
		String eigthDayAgoPrice = null;
		String eigthDayAgoPercentChange  = null;
	    Set<String> recordSet = null;

		try {
			recordList = new ArrayList<ConcurrentGainersBean>();
			recordSet = new HashSet<String>();
			 website = new URL(url);
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
		     String inputLine;
		     int count =0;
		     boolean isCount = false;
			     while ((inputLine = in.readLine()) != null)
			     {
			    	 //<td width='300px'><a href='/india/stockpricequote/foodprocessing/vadilalindustries/VI01'>Vadilal Ind</a><div class="addPrWhs">

			    	 if(inputLine.contains("<td width='300px'>")){
			    		 bean = new ConcurrentGainersBean();
			    		 if(inputLine.contains("<a href=")){
			    			 urlAPI = inputLine.trim();
			    			 urlAPI = urlAPI.substring(urlAPI.indexOf("<a href='"),urlAPI.lastIndexOf("'>"));
			    			 urlAPI = urlAPI.replace("<a href='", "");
			    			 urlAPI = FileNameConstant.MONEYCONTROL_URL+urlAPI.trim();
			    			 //<a href='/india/stockpricequote/pharmaceuticals/ahlconparenteral(india)/API01'>Ahlcon Parent

			    			 companyName = inputLine.trim();
			    			 companyName = companyName.substring(companyName.indexOf("<a href='"),companyName.lastIndexOf("</a>"));
			    			 companyName = companyName.substring(companyName.indexOf("'>"));
			    			 companyName = companyName.replace("'>", "");
			    			 companyName = companyName.replace(",", "");

			    			// System.out.println(companyName);
			    		 }
			    	 }
			    	 if(inputLine.contains("</div></td>")){
			    		 isCount = true;
			    	 }
		    		 
			    	 if(isCount){
			    		 if(count <=9){
			    			 //System.out.println(inputLine);
			    			 if(count ==1){
			    				 currentPrice = inputLine.trim();
			    				 currentPrice = currentPrice.substring(currentPrice.indexOf("'>"),currentPrice.lastIndexOf("</td>"));
			    				 currentPrice = currentPrice.replace("'>", "");
			    				 currentPrice = currentPrice.replace(",", "");
				    			// System.out.println(">>"+currentPrice);
			    			 }else if(count ==2){
			    				 currentPercentChange = inputLine.trim();
			    				 currentPercentChange = currentPercentChange.substring(currentPercentChange.indexOf("'>"),currentPercentChange.lastIndexOf("</td>"));
			    				 currentPercentChange = currentPercentChange.replace("'>", "");
				    			// System.out.println(">>"+currentPercentChange);
			    			 }else if(count ==3){
			    				 currentVolume = inputLine.trim();
			    				 currentVolume = currentVolume.substring(currentVolume.indexOf("'>"),currentVolume.lastIndexOf("</td>"));
			    				 currentVolume = currentVolume.replace("'>", "");
			    				 currentVolume = currentVolume.replace(",", "");

				    			 //System.out.println(">>"+currentVolume);
			    			 }else if(count ==4){
			    				 threeDayAgoPrice = inputLine.trim();
			    				 threeDayAgoPrice = threeDayAgoPrice.substring(threeDayAgoPrice.indexOf("'>"),threeDayAgoPrice.lastIndexOf("</td>"));
			    				 threeDayAgoPrice = threeDayAgoPrice.replace("'>", "");
				    			 //System.out.println(">>"+threeDayAgoPrice);
			    			 }else if(count ==5){
			    				 threeDayAgoPercentChange = inputLine.trim();
			    				 threeDayAgoPercentChange = threeDayAgoPercentChange.substring(threeDayAgoPercentChange.indexOf("'>"),threeDayAgoPercentChange.lastIndexOf("</td>"));
			    				 threeDayAgoPercentChange = threeDayAgoPercentChange.replace("'>", "");
				    			 //System.out.println(">>"+threeDayAgoPercentChange);
			    			 }else if(count ==6){
			    				 fiveDayAgoPrice = inputLine.trim();
			    				 fiveDayAgoPrice = fiveDayAgoPrice.substring(fiveDayAgoPrice.indexOf(">"),fiveDayAgoPrice.lastIndexOf("</td>"));
			    				 fiveDayAgoPrice = fiveDayAgoPrice.replace(">", "");
				    			 //System.out.println(">>"+fiveDayAgoPrice);
			    			 }else if(count ==7){
			    				 fiveDayAgoPercentChange = inputLine.trim();
			    				 fiveDayAgoPercentChange = fiveDayAgoPercentChange.substring(fiveDayAgoPercentChange.indexOf(">"),fiveDayAgoPercentChange.lastIndexOf("</td>"));
			    				 fiveDayAgoPercentChange = fiveDayAgoPercentChange.replace(">", "");
				    			 //System.out.println(">>"+fiveDayAgoPercentChange);
			    			 }else if(count ==8){
			    				 eigthDayAgoPrice = inputLine.trim();
			    				 eigthDayAgoPrice = eigthDayAgoPrice.substring(eigthDayAgoPrice.indexOf(">"),eigthDayAgoPrice.lastIndexOf("</td>"));
			    				 eigthDayAgoPrice = eigthDayAgoPrice.replace(">", "");
				    			// System.out.println(">>"+eigthDayAgoPrice);
			    			 }else if(count ==9){
			    				 eigthDayAgoPercentChange = inputLine.trim();
			    				 eigthDayAgoPercentChange = eigthDayAgoPercentChange.substring(eigthDayAgoPercentChange.indexOf(">"),eigthDayAgoPercentChange.lastIndexOf("</td>"));
			    				 eigthDayAgoPercentChange = eigthDayAgoPercentChange.replace(">", "");
				    			//System.out.println(">>"+eigthDayAgoPercentChange);
			    			 }
			    		 }
			    		 count++;
			    		 if(count >9){
			    			 count = 0;
			    			 isCount = false;
			    		 }
			    	 }
		    		 if(urlAPI != null 
		    			&& companyName != null 
		    			&& !urlAPI.contains("///")
		    			&& !recordSet.contains(urlAPI)
		    			&& currentPrice != null
		    			&& currentPercentChange != null
		    			&& currentVolume != null
		    			&& threeDayAgoPrice != null
		    			&& threeDayAgoPercentChange != null
		    			&& fiveDayAgoPrice != null
		    			&& fiveDayAgoPercentChange != null
		    			&& eigthDayAgoPrice != null
		    			&& eigthDayAgoPercentChange  != null ){
		    			 bean.setApi(urlAPI);
		    			 bean.setCompanyName(companyName);
		    			 bean.setCurrentPrice(currentPrice);
		    			 bean.setCurrentPercentChange(currentPercentChange);
		    			 bean.setCurrentVolume(currentVolume);
		    			 bean.setThreeDayAgoPrice(threeDayAgoPrice);
		    			 bean.setThreeDayAgoPercentChange(threeDayAgoPercentChange);
		    			 bean.setFiveDayAgoPrice(fiveDayAgoPrice);
		    			 bean.setFiveDayAgoPercentChange(fiveDayAgoPercentChange);
		    			 bean.setEigthDayAgoPrice(eigthDayAgoPrice);
		    			 bean.setEigthDayAgoPercentChange(eigthDayAgoPercentChange);
		    			 recordList.add(bean);
		    			 recordSet.add(urlAPI);
		    		 }
					
						 currentPrice = null;
						 
			     }
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recordList;
	}
	
}