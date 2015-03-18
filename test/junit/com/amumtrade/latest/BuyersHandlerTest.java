package com.amumtrade.latest;

import org.junit.Test;

import com.amumtrade.handler.BuyersHandler;

public class BuyersHandlerTest {

	@Test
	public void fetchBuyersStock() throws Exception{
		BuyersHandler handler = new BuyersHandler();
		handler.execute();
		
		}
}
