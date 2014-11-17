package com.amumtrade.handler;

import org.junit.Test;

import com.amumtrade.constant.AMUMStockConstant;

public class FinancialAnalysisHandlerTest {

	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		FinancialAnalysisHandler fah = new FinancialAnalysisHandler();
		fah.execute(AMUMStockConstant.FIVE_STAR);
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		
		int s = (int) ((elapsedTime / 1000) % 60);
		int m = (int) ((elapsedTime / (1000 * 60)) % 60);
		int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
		
		 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
