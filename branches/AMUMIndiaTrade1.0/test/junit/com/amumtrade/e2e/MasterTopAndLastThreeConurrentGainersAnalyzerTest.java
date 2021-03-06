package com.amumtrade.e2e;

import org.junit.Test;

import com.amumtrade.handler.CurrentConcurrentGainersTopGainersHandler;

public class MasterTopAndLastThreeConurrentGainersAnalyzerTest {

	
	
	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		
		CurrentConcurrentGainersTopGainersHandler ctcgh = new CurrentConcurrentGainersTopGainersHandler();
		ctcgh.execute(startTime);
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
