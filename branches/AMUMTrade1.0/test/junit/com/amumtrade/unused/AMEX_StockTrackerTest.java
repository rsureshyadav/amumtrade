 package com.amumtrade.unused;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;
import com.amumtrade.engine.StockEngine;

public class AMEX_StockTrackerTest {

		 @Test
		 public void executeTest() throws Exception {
				StockEngine.getStockQuote(StringConstant.AMEX_OUTPUT_PATH, StringConstant.AMEX_NAME);
			}
}
