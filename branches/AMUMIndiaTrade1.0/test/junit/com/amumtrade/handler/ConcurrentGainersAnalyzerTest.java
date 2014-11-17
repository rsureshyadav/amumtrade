package com.amumtrade.handler;

import org.junit.Test;

public class ConcurrentGainersAnalyzerTest {
	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		
		ConcurrentGainersVolumeAnalyzerHandler cgva = new ConcurrentGainersVolumeAnalyzerHandler();
		cgva.execute();
		
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
