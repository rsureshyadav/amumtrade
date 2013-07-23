package com.amumtrade.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;

public class AmexStockTest {
@Test
public void fetchNasdaqStock() throws Exception{
	double startRange = 0.8;
	double endRange = 3;
	StockHandler handler = new StockHandler(StringConstant.AMEX_NAME);
	handler.execute(startRange, endRange, StringConstant.AMEX_URL,StringConstant.AMEX_INPUT_PATH+getTodayDate()+".csv", StringConstant.AMEX_OUTPUT_PATH);
	}


private String getTodayDate() throws Exception {
	String sDate;
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	Date date = new Date();
	sDate= sdf.format(date);
	return sDate;
}
}
