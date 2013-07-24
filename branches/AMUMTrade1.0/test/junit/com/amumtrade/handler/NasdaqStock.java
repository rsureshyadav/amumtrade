package com.amumtrade.handler;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;
import com.amumtrade.handler.DownloadLinkHandler;
import com.amumtrade.handler.StockHandler;

public class NasdaqStock {
@Test
public void fetchNasdaqStock() throws Exception{
	long startTime= System.currentTimeMillis();
	System.out.println("Downloading  Nasdaq_list.csv file...");
	DownloadLinkHandler.execute(StringConstant.NASDAQ_URL,StringConstant.NASDAQ_INPUT_PATH);
	System.out.println("Executing Nasdaq process...");
    StockHandler.execute(StringConstant.NASDAQ_INPUT_PATH, 1, 1.1, StringConstant.NASDAQ_NAME, StringConstant.NASDAQ_OUTPUT_PATH);
    System.out.println("Completed Nasdaq quote output file...");
    long endTime= System.currentTimeMillis();
	long elapsedTime = endTime - startTime;
	
	int s = (int) ((elapsedTime / 1000) % 60);
	int m = (int) ((elapsedTime / (1000 * 60)) % 60);
	int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
	
	 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
