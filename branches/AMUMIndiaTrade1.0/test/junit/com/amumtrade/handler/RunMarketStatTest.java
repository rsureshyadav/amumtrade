package com.amumtrade.handler;

import java.util.List;

import org.junit.Test;

import com.amumtrade.bean.CashPriceEarningRatiosBean;
import com.amumtrade.marketstat.CashPriceEarningRatios;

public class RunMarketStatTest {
	@Test
	public void executeStockMarketStat() throws Exception{
		CashPriceEarningRatios ep = new CashPriceEarningRatios();
		ep.execute();
		
		List<CashPriceEarningRatiosBean> cashPriceEarningRatiosList = ep.getCashPriceEarningRatiosList();
		for(CashPriceEarningRatiosBean bean : cashPriceEarningRatiosList){
			System.out.println(bean.getName());
		}
	}
}
