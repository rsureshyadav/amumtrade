package com.amumtrade.handler;

import org.junit.Test;

import com.amumtrade.constant.AMUMStockConstant;

public class BSEStockTest {
@Test
public void fetchNasdaqStock() throws Exception{
	double startRange = 50;
	double endRange = 60;
	AMUMBSEStockHandler handler = new AMUMBSEStockHandler(AMUMStockConstant.BSE_NAME);
	handler.execute(startRange, endRange);
	}
}
