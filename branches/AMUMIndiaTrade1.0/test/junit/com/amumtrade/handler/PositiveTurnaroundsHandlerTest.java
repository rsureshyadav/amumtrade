package com.amumtrade.handler;

import org.junit.Test;

public class PositiveTurnaroundsHandlerTest {

	@Test
	public void fetchOnlyBuyersStock() throws Exception{
		long startTime= System.currentTimeMillis();
		PositiveTurnaroundsHandler handler = new PositiveTurnaroundsHandler();
		handler.execute(startTime);
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Total Execution total time  ==> "+ h +" : "+ m +" : "+ s);
		}
}
