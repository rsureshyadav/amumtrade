package com.amumtrade.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.amumtrade.bean.StockWebBean;
import com.amumtrade.engine.StockEngine;

public class StockHandler {

	public static void execute(String inputPath, double firstValue, double lastValue, String marketName, String outputPath) throws Exception{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		 Calendar cal = Calendar.getInstance();
		 try {
			 StockWebBean webBean =null;
			 List<String> symbolList = new ArrayList<String>();
			 Map<String, StockWebBean> webStockMap=StockEngine.getQuoteFromInputFile(inputPath, webBean);
			 for (String  symbol : webStockMap.keySet()) {
				 webBean=webStockMap.get(symbol);
				 Double price = Double.valueOf(webBean.getLastSale());
				 if(price>= firstValue&& price<=lastValue && !webBean.getSymbol().contains("/") && !webBean.getSymbol().contains("^")){
					 symbolList.add(webBean.getSymbol());
				 }
			 }
			 String fileName= marketName+"_"+dateFormat1.format(cal.getTime())+".txt";
			 StockEngine.writeQuoteToTempFile(symbolList, fileName);
			 StockEngine.getStockQuote(outputPath, fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
