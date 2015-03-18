package com.amumtrade.handler;

import java.util.Set;

import org.junit.Test;

public class PositiveBreakoutHandlerTest {
	@Test
	public void executeStockMarketStat() throws Exception{
	long startTime= System.currentTimeMillis();
	
	PositiveBreakoutHandler cgva = new PositiveBreakoutHandler();
	Set<String> positiveBreakoutSet  = cgva.execute();
	for(String s : positiveBreakoutSet){
		System.out.println(s);
	}
	
	long endTime= System.currentTimeMillis();
	long elapsedTime = endTime - startTime;
		
		int s = (int) ((elapsedTime / 1000) % 60);
		int m = (int) ((elapsedTime / (1000 * 60)) % 60);
		int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
		
		 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
}
}
