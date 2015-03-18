package com.amumtrade.latest;

import org.junit.Test;

import com.amumtrade.handler.FirstLevelHandler;

public class FirstLevelHandlerTest {

	@Test
	public void fetchBuyersStock() throws Exception{
		long startTime= System.currentTimeMillis();

		FirstLevelHandler handler = new FirstLevelHandler();
		handler.execute();
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);

		}
}
