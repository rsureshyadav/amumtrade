package com.amumtrade.unused;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;
import com.amumtrade.engine.StockEngine;

public class NASDAQ_StockTrackerTest {

	@Test
	public void executeTest() throws Exception {
		StockEngine.getStockQuote(StringConstant.NASDAQ_OUTPUT_PATH, StringConstant.NASDAQ_NAME);
	}

}
