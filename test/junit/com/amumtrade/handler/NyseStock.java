package com.amumtrade.handler;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;
import com.amumtrade.handler.DownloadLinkHandler;
import com.amumtrade.handler.StockHandler;

public class NyseStock {
@Test
public void fetchNasdaqStock() throws Exception{
	long startTime= System.currentTimeMillis();
	System.out.println("Downloading  Nyse_list.csv file...");
	DownloadLinkHandler.execute(StringConstant.NYSE_URL,StringConstant.NYSE_INPUT_PATH);
	System.out.println("Executing Nyse process...");
    StockHandler.execute(StringConstant.NYSE_INPUT_PATH, StringConstant.ONE, 1.1, StringConstant.NYSE_NAME, StringConstant.NYSE_OUTPUT_PATH);
    System.out.println("Completed Nyse quote output file...");
    long endTime= System.currentTimeMillis();
	long elapsedTime = endTime - startTime;
	
	int s = (int) ((elapsedTime / 1000) % 60);
	int m = (int) ((elapsedTime / (1000 * 60)) % 60);
	int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
	
	 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
