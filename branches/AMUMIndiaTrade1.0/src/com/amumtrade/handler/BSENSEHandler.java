package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BSENSEHandler {


	public void execute() throws Exception{
		long startTime= System.currentTimeMillis();
		try {
			String stockURL = "http://money.rediff.com/companies/Jaypee-Infratech-Ltd/11620144?src=gain_lose";
			getPrice(stockURL);
		
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
		
	}
	
	
	private static void getPrice(String url) throws Exception{
		
		 URL website = new URL(url); 
		 
		 BufferedReader in = new BufferedReader(
			        new InputStreamReader(website.openStream()));

			        String inputLine;
			        String price = null;
			        String removeCurrPriceStartString="<span id=\"ltpid\" class=\"bold\">";
			        String removeCurrPriceEndString="</span> &nbsp;";
			        while ((inputLine = in.readLine()) != null)
			        	if(inputLine.contains(removeCurrPriceStartString)){
			        		price = inputLine.replace(removeCurrPriceStartString, "");
			        		price = price.replace(removeCurrPriceEndString, ""); 
			        		System.out.println(price.trim());
			        	}
			        in.close();
				    
	}
}
