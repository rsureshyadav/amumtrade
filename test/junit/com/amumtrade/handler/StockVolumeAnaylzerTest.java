package com.amumtrade.handler;

import org.junit.Test;

public class StockVolumeAnaylzerTest {
	@Test
	public void executeStockVolumeAnaylzer() throws Exception{
		long startTime= System.currentTimeMillis();
		//This will generate CSV file of Top Gainers
		TopGainersHandler topGainersHandler = new TopGainersHandler();
		topGainersHandler.execute();
		//This will add the volume for the Top Gainers
		StockVolumeAnaylzer volumeAnaylzer = new StockVolumeAnaylzer();
		volumeAnaylzer.execute();
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
