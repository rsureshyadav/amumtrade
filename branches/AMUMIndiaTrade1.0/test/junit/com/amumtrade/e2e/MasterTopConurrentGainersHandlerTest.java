package com.amumtrade.e2e;

import org.junit.Test;

import com.amumtrade.handler.CombineTopConcurrentGainersHandler;

public class MasterTopConurrentGainersHandlerTest {

	
	
	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		
		CombineTopConcurrentGainersHandler ctcgh = new CombineTopConcurrentGainersHandler();
		ctcgh.execute();
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
