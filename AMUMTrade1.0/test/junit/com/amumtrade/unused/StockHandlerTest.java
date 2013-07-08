 package com.amumtrade.unused;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;
import com.amumtrade.handler.StockHandler;

public class StockHandlerTest {

		 @Test
		 /**
		  * inputPath = CommonConstant.NYSE_INPUT_PATH
		  * firstValue = CommonConstant.ONE		
		  * lastValue = CommonConstant.TWO
		  * marketName = CommonConstant.NYSE_NAME
		  * outputPath =CommonConstant.NYSE_OUTPUT_PATH
		  **/
		 public void executeTest() throws Exception {
			 System.out.println("Executing NYSE......");
			 StockHandler.execute(StringConstant.NYSE_INPUT_PATH, StringConstant.ONE, StringConstant.TWO, StringConstant.NYSE_NAME, StringConstant.NYSE_OUTPUT_PATH);
			 System.out.println("Executing NASDAQ......");
			 StockHandler.execute(StringConstant.NASDAQ_INPUT_PATH, StringConstant.ONE, StringConstant.TWO, StringConstant.NASDAQ_NAME, StringConstant.NASDAQ_OUTPUT_PATH);
			 System.out.println("Executing AMEX......");
			 StockHandler.execute(StringConstant.AMEX_INPUT_PATH, StringConstant.ONE, StringConstant.TWO, StringConstant.AMEX_NAME, StringConstant.AMEX_OUTPUT_PATH);
		 }
		
	}
