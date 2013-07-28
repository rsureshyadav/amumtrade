package com.amumtrade.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.amumtrade.constant.AMUMStockConstant;

public class AmexStockTest {
@Test
public void fetchNasdaqStock() throws Exception{
	double startRange = 1;
	double endRange = 2;
	AMUMStockHandler handler = new AMUMStockHandler(AMUMStockConstant.AMEX_NAME);
	handler.execute(startRange, endRange, AMUMStockConstant.AMEX_URL,AMUMStockConstant.AMEX_INPUT_PATH+getTodayDate()+".csv", AMUMStockConstant.AMEX_OUTPUT_PATH);
	}


private String getTodayDate() throws Exception {
	String sDate;
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	Date date = new Date();
	sDate= sdf.format(date);
	return sDate;
}
}
