package com.amumtrade.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.amumtrade.constant.AMUMStockConstant;

public class BSEStockTest {
@Test
public void fetchNasdaqStock() throws Exception{
	double startRange = 2.9;
	double endRange = 3;
	AMUMBSEStockHandler handler = new AMUMBSEStockHandler(AMUMStockConstant.BSE_NAME);
	handler.execute(startRange, endRange);
	}


private String getTodayDate() throws Exception {
	String sDate;
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	Date date = new Date();
	sDate= sdf.format(date);
	return sDate;
}
}
