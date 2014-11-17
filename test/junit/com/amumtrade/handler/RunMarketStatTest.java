package com.amumtrade.handler;

import org.junit.Test;

public class RunMarketStatTest {
	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		/*CashPriceEarningRatios ep = new CashPriceEarningRatios();
		ep.execute();
		
		List<CashPriceEarningRatiosBean> cashPriceEarningRatiosList = ep.getCashPriceEarningRatiosList();
		for(CashPriceEarningRatiosBean bean : cashPriceEarningRatiosList){
		//	System.out.println(bean.getName()+">>"+bean.getApi());
		}*/
		
	/*	LastThreeDayConcurrentGainers cg = new LastThreeDayConcurrentGainers();
		cg.execute();
		List<ConcurrentGainersBean> concurrentGainersList = cg.getConcurrentGainersList();
		for(ConcurrentGainersBean bean : concurrentGainersList){
			System.out.println(bean.getName()+">>"+bean.getApi()
			+">>"+bean.getCurrentPrice()
			+">>"+ bean.getCurrentPercentChange()
			+">>"+ bean.getCurrentVolume()
			+">>"+ bean.getThreeDayAgoPrice()
			+">>"+ bean.getThreeDayAgoPercentChange()
			+">>"+ bean.getFiveDayAgoPrice()
			+">>"+ bean.getFiveDayAgoPercentChange()
			+">>"+ bean.getEigthDayAgoPrice()
			+">>"+ bean.getEigthDayAgoPercentChange());
			}
		System.out.println(concurrentGainersList.size());*/
		
		ConcurrentGainersVolumeAnalyzer cgva = new ConcurrentGainersVolumeAnalyzer();
		cgva.execute();
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
