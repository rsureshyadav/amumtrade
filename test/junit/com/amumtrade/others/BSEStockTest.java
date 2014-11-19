package com.amumtrade.others;

import org.junit.Test;

import com.amumtrade.constant.AMUMStockConstant;

public class BSEStockTest {
@Test
public void fetchNasdaqStock() throws Exception{
	double startRange = 14;
	double endRange = 15;
	AMUMBSEStockHandler handler = new AMUMBSEStockHandler(AMUMStockConstant.BSE_NAME);
	handler.execute(startRange, endRange);
	}
}
