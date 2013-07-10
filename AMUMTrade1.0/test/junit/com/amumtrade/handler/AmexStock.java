package com.amumtrade.handler;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;
import com.amumtrade.handler.DownloadLinkHandler;
import com.amumtrade.handler.StockHandler;

public class AmexStock {
@Test
public void fetchNasdaqStock() throws Exception{
	long startTime= System.currentTimeMillis();
	System.out.println("Downloading  Amex_list.csv file...");
	DownloadLinkHandler.execute(StringConstant.AMEX_URL,StringConstant.AMEX_INPUT_PATH);
	System.out.println("Executing Amex process...");
    StockHandler.execute(StringConstant.AMEX_INPUT_PATH, 0.5, 1.1, StringConstant.AMEX_NAME, StringConstant.AMEX_OUTPUT_PATH);
    System.out.println("Completed Amex quote output file...");
    long endTime= System.currentTimeMillis();
	long elapsedTime = endTime - startTime;
	
	int s = (int) ((elapsedTime / 1000) % 60);
	int m = (int) ((elapsedTime / (1000 * 60)) % 60);
	int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
	
	 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
