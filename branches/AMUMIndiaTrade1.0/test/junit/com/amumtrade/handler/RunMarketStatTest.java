package com.amumtrade.handler;

import java.util.List;

import org.junit.Test;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.marketstat.LastEigthDayConcurrentGainers;

public class RunMarketStatTest {
	@Test
	public void executeStockMarketStat() throws Exception{
		/*CashPriceEarningRatios ep = new CashPriceEarningRatios();
		ep.execute();
		
		List<CashPriceEarningRatiosBean> cashPriceEarningRatiosList = ep.getCashPriceEarningRatiosList();
		for(CashPriceEarningRatiosBean bean : cashPriceEarningRatiosList){
		//	System.out.println(bean.getName()+">>"+bean.getApi());
		}*/
		
		LastEigthDayConcurrentGainers cg = new LastEigthDayConcurrentGainers();
		cg.execute();
		List<ConcurrentGainersBean> concurrentGainersList = cg.getConcurrentGainersList();
		for(ConcurrentGainersBean bean : concurrentGainersList){
			System.out.println(bean.getName()/*+">>"+bean.getApi()*/
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
	}
}
