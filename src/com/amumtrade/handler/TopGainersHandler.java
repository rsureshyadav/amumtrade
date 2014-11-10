package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.amumtrade.bean.TopGainerBean;

import sun.reflect.generics.tree.ReturnType;

public class TopGainersHandler {
	public static Set<String> nameSet = new HashSet<String>();
	public static String stockURL = "http://www.moneycontrol.com/stocks/marketstats/gainerloser.php?optex=NSE&opttopic=topgainers&index=-2&more=true";
	 static Map<String, String> topGainerList= new HashMap<String, String>();
	public void execute() throws Exception{
		long startTime= System.currentTimeMillis();

		try {
			//http://www.moneycontrol.com/stocks/marketstats/gainerloser.php?optex=NSE&opttopic=topgainers&index=-2&more=true
			//http://www.moneycontrol.com/stocks/marketstats/onlysellers.php
			//http://www.moneycontrol.com/stocks/marketstats/bsegainer/index.html
			//http://www.moneycontrol.com/stocks/marketstats/bsemact1/index.html
			//http://www.moneycontrol.com/stocks/marketstats/nse_vshockers/index.html
			getTopGainer(stockURL);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
		
	}

	public static  Map<String, String> getTopGainerList() throws Exception{
		getTopGainer(stockURL);
		return topGainerList;
	}

	public static void getTopGainer(String stockURL) throws IOException {
		String companyName=null;
		String api=null;
		String high=null;
		String low=null;
		String lastPrice=null;
		String prvClose=null;
		String change=null;
		String percentGain=null;
		BufferedWriter bwObj = null;
		BufferedReader in = null;
		FileWriter fwo = new FileWriter( "config/amumTopGainerList.csv", false );
		try {
			 URL website = new URL(stockURL);
			 bwObj = new BufferedWriter( fwo );  
			 bwObj.write("Company Name,High,Low,Last Price,Prv Close,Change,% Gain,API"+"\n");
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
				        String inputLine;
				        //boolean isRun = false;
		        		boolean highLastPriceflag=false;
		        		boolean changePercentGain=false;
				        while ((inputLine = in.readLine()) != null)
				        {
				        	//<td align="left" class="brdrgtgry"><a href='/india/stockpricequote/finance-general/microsecfinancialservices/MFS' class='bl_12'><b>Microsec Fin</b></a></td>
				        	if(inputLine.contains("<td align=\"left\" class=\"brdrgtgry\">")){
				        		companyName = inputLine.trim();
				        		companyName=companyName.substring(companyName.indexOf("<b>"));
				        		companyName=companyName.replace("<b>", "");
				        		companyName=companyName.replace("</b></a></td>", "");
				        		
				        		api = inputLine.trim();
				        		api = api.substring(api.indexOf("<a href="), api.lastIndexOf("class='bl_12'"));
				        		api = api.replace("<a href=", "");
				        		api = api.replace("'", "");
					        		
				        		//isRun = true;
				        		//if(companyName.equalsIgnoreCase("Radha Madhav")){
				        			
				        		//	System.out.println("Company: "+companyName);
				        		//}

				        	}else if(inputLine.contains("<td width=\"13%\" align=\"right\" class=\"brdrgtgry\">")){
				        		if(!highLastPriceflag){
				        			high=inputLine.trim();
				        			high=high.substring(high.indexOf("brdrgtgry\">"));
				        			high=high.replace("brdrgtgry\">", "");
				        			high=high.replace("</td>", "");
				        			high=high.replace(",", "");
				        			high=high.replace(".00", "");
				        		//	if(companyName.equalsIgnoreCase("Radha Madhav")){
					        		//System.out.println("High: "+high);
				        		//	}
				        			highLastPriceflag=true;
				        		}else if(highLastPriceflag){
				        			prvClose=inputLine.trim();
				        			prvClose=prvClose.substring(prvClose.indexOf("brdrgtgry\">"));
				        			prvClose=prvClose.replace("brdrgtgry\">", "");
				        			prvClose=prvClose.replace("</td>", "");
				        			prvClose=prvClose.replace(",", "");
				        			prvClose=prvClose.replace(".00", "");
				        		//	if(companyName.equalsIgnoreCase("Radha Madhav")){
					        		//System.out.println("prvClose: "+prvClose);
				        			//}
				        			highLastPriceflag=false;
				        		}
				        		//System.out.println(inputLine.trim());
				        	}else if(inputLine.contains("<td width=\"13%\" align=\"right\" class=\"brdrgtgry\" >")){
				        		low=inputLine.trim();
				        		low=low.substring(low.indexOf("brdrgtgry\" >"));
				        		low=low.replace("brdrgtgry\" >", "");
				        		low=low.replace("</td>", "");
				        		low=low.replace(",", "");
				        		low=low.replace(".00", "");
				        		//System.out.println("Low :"+low);
			        		}else if(inputLine.contains("<td width=\"14%\" align=\"right\" class=\"brdrgtgry\">")){
				        		lastPrice=inputLine.trim();
				        		lastPrice=lastPrice.substring(lastPrice.indexOf("brdrgtgry\">"));
				        		lastPrice=lastPrice.replace("brdrgtgry\">", "");
				        		lastPrice=lastPrice.replace("</td>", "");
				        		lastPrice=lastPrice.replace(",", "");
				        		lastPrice=lastPrice.replace(".00", "");
				        		//System.out.println("Last Price :"+lastPrice);
			        		}else if(inputLine.contains("<td width=\"14%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#008E00\">")){ 
				        		if(!changePercentGain){
				        			change=inputLine.trim();
				        			change=change.substring(change.indexOf("color:#008E00\">"));
				        			change=change.replace("color:#008E00\">", "");
				        			change=change.replace("</td>", "");
				        			change=change.replace(",", "");
				        			change=change.replace(".00", "");
				        		//	System.out.println("Change :"+change);
				        			changePercentGain=true;
				        		}else if(changePercentGain){
				        			percentGain=inputLine.trim();
				        			percentGain=percentGain.substring(percentGain.indexOf("color:#008E00\">"));
				        			percentGain=percentGain.replace("color:#008E00\">", "");
				        			percentGain=percentGain.replace("</td>", "");
				        			percentGain=percentGain.replace(",", "");
				        			percentGain=percentGain.replace(".00", "");
				        		//	if(companyName.equalsIgnoreCase("Radha Madhav")){
					        //		System.out.println("Percent Gain: "+percentGain);
				        		//	}
				        			changePercentGain=false;
				        		}
				        		//System.out.println(inputLine.trim());
				        		String finalTopGainer= companyName+","+high+","+ low+","+ lastPrice+","+ prvClose+","+ change+","+percentGain+","+api;
				        		
				        		
				        		if(nameSet.add(companyName)){
				        			bwObj.write(finalTopGainer+"\n");
				        			topGainerList.put(lastPrice, finalTopGainer);
				        		}
				        		
			        		}
				        }	
						 System.out.println("Total Top Gainer Count ==>"+ nameSet.size());

				        in.close();
					    
		
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(bwObj!=null){
				bwObj.close();
			}
			if(in !=null){
				in.close();
			}
		}
		
	}
	
}
