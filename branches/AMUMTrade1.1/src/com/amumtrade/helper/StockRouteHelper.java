package com.amumtrade.helper;

import static com.amumtrade.constant.StringConstant.CNN_RATING_URL;
import static com.amumtrade.constant.StringConstant.MARKET_WATCH_RATING_URL;
import static com.amumtrade.constant.StringConstant.SYMBOL;
import static com.amumtrade.constant.StringConstant.THE_STREET_RATING_URL;
import static com.amumtrade.constant.StringConstant.YAHOO_RATING_URL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.StockBean;
import com.amumtrade.constant.StringConstant;
import com.amumtrade.runnable.CNNRunnable;
import com.amumtrade.runnable.GeneralStockRunnable;
import com.amumtrade.runnable.MarketWatchRunnable;
import com.amumtrade.runnable.TheStreetRunnable;
import com.amumtrade.runnable.YahooRunnable;
import com.amumtrade.util.StockUtil;

public class StockRouteHelper  {

	String txtFileName = null;
	BufferedWriter bwObj = null;
	StockBean stockBean =null;
	public StockRouteHelper(String txtFileName, BufferedWriter bwObj) throws IOException{
		this.txtFileName = txtFileName;
		this.bwObj = bwObj;
		performExecute();
	}

	public void performExecute() throws IOException {
		HashMap<String, String> yahooMap = new HashMap<String, String>();
		HashMap<String, String> cnnMap = new HashMap<String, String>();
		HashMap<String, String> mktWatchMap = new HashMap<String, String>();
		HashMap<String, String> theStreetMap = new HashMap<String, String>();
		HashMap<String, String> genStockMap = new HashMap<String, String>();

		int count=0;

	        try {
				String header = StockUtil.getHeader();
				//bwObj.newLine();
				bwObj.write(header);
				bwObj.newLine();
				
	        	List<String> marketNameList = getQuoteFromFile(txtFileName);
				ExecutorService marketExec = Executors.newFixedThreadPool(marketNameList.size());
				for(String symbol : marketNameList){
					
					String yahooURLString = YAHOO_RATING_URL.replaceAll(SYMBOL, symbol);
					Runnable runYahoo = new YahooRunnable(yahooURLString, symbol, yahooMap);
					marketExec.execute(runYahoo);
					
					String cnnURLString = CNN_RATING_URL.replaceAll(SYMBOL, symbol);
					Runnable runCnn = new CNNRunnable(cnnURLString, symbol, cnnMap);
					marketExec.execute(runCnn);
					
					String mktWatchURLString = MARKET_WATCH_RATING_URL.replaceAll(SYMBOL, symbol);
					Runnable runmktWatch = new MarketWatchRunnable(mktWatchURLString, symbol, mktWatchMap);
					marketExec.execute(runmktWatch);
					
					String theStreetURLString = THE_STREET_RATING_URL.replaceAll(SYMBOL, symbol);
					Runnable runTheStreet = new TheStreetRunnable(theStreetURLString, symbol, theStreetMap);
					marketExec.execute(runTheStreet);
				}
				shutdown(marketExec, "Channel Rating");
				
				//This is used execute to fetch general information like current price, last trade price, 52wk
				ExecutorService genStockExec = Executors.newFixedThreadPool(marketNameList.size());
				for(String symbol : marketNameList){
					//count++;
					stockBean = new StockBean();
					stockBean.setAMUMTradeWeight("amum proj");
					stockBean.setYahooRating(yahooMap.get(symbol));
		        	stockBean.setCnnRating(cnnMap.get(symbol));
		        	stockBean.setMarketWatchRating(mktWatchMap.get(symbol));
		        	stockBean.setTheStreetRating(theStreetMap.get(symbol));
					String genStockURL = StringConstant.YAHOO_URL + symbol + StringConstant.DEFULT_SELECTION;
		        	Runnable genStock = new GeneralStockRunnable(genStockURL,symbol,genStockMap, stockBean);
		        	genStockExec.execute(genStock);
		        }
				shutdown(genStockExec, "General Projection");
				
				for(String symbol : marketNameList){
					String finalStock = genStockMap.get(symbol);
					System.out.println(">>"+finalStock);
					bwObj.write(finalStock);
					bwObj.newLine();  
				}
     
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	

	private void shutdown(ExecutorService executor, String label) {
		executor.shutdown();
        while (!executor.isTerminated()) {
 
        }
        System.out.println("\nFinished "+label+" threads execution");
        
		
	}

	public  List<String> getQuoteFromFile(String marketName)throws InterruptedException {
		List<String> quote=new ArrayList<String>();
		try{
			FileInputStream fstream = new FileInputStream(marketName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				quote.add(strLine);
			}
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return quote;
	}
}

