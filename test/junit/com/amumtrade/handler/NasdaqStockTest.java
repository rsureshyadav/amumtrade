package com.amumtrade.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.amumtrade.constant.AMUMStockConstant;

public class NasdaqStockTest{
	

	@Test
	public void fetchNasdaqStock() throws Exception{
		double startRange = 3;
		double endRange = 3;
		AMUMStockHandler handler = new AMUMStockHandler( AMUMStockConstant.NASDAQ_NAME);
		handler.execute(startRange, endRange, AMUMStockConstant.NASDAQ_URL,AMUMStockConstant.NASDAQ_INPUT_PATH+getTodayDate()+".csv", AMUMStockConstant.NASDAQ_OUTPUT_PATH);
		}


	private String getTodayDate() throws Exception {
		String sDate;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		sDate= sdf.format(date);
		return sDate;
	}

}