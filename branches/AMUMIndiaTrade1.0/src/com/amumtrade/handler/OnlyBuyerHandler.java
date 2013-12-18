package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class OnlyBuyerHandler {
	public void execute() throws Exception{
		long startTime= System.currentTimeMillis();
		try {
			String stockURL = "http://www.moneycontrol.com/stocks/marketstats/onlybuyers.php";
			//http://www.moneycontrol.com/stocks/marketstats/onlysellers.php
			//http://www.moneycontrol.com/stocks/marketstats/bsegainer/index.html
			//http://www.moneycontrol.com/stocks/marketstats/bsemact1/index.html
			//http://www.moneycontrol.com/stocks/marketstats/nse_vshockers/index.html
			
			getBuyers(stockURL);
		
		
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

	private void getBuyers(String stockURL) throws IOException {
		BufferedWriter bwObj = null;
		BufferedReader in = null;
		FileWriter fwo = new FileWriter( "config/file.txt", false );
		try {
			 URL website = new URL(stockURL);
			 bwObj = new BufferedWriter( fwo );  
			 in= new BufferedReader(
				        new InputStreamReader(website.openStream()));
				        String inputLine;
				        boolean isRun = false;
				        while ((inputLine = in.readLine()) != null)
				        {
				        	if(inputLine.contains("<div class=\"PT10\">")){
				        		isRun = true;
				        	}else if(inputLine.contains("sprite_gi.gif")){
				        		isRun = false;
				        	}
				        	if(isRun){
				        		bwObj.write(inputLine.trim()+"\n");
				        	}
				        		System.out.println(inputLine.trim());
				        	
				        }	
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
