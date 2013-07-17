package com.amumtrade.dao;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;

import com.amumtrade.bean.StockBean;
import com.amumtrade.constant.StringConstant;

public class StockEngineDAO {
    private static final StockEngineDAO stockDAO = new StockEngineDAO();
    private static HashMap<String, StockBean> stocks = new HashMap<String, StockBean>();
    private static final long TWENTY_MIN = 120;
    public StockEngineDAO() {}
    public static StockEngineDAO getInstance() {
        return stockDAO;
    }
    /**
     *
     * @param symbol
     * @return StockBean
     * will return null if unable to retrieve information
     */
    public StockBean getStockPrice(String symbol) {
        StockBean stock;
        long currentTime = (new Date()).getTime();
        // Check last updated and only pull stock on average every 20 minutes
        if (stocks.containsKey(symbol)) {
            stock = stocks.get(symbol);
            if(currentTime - stock.getLastUpdated() > TWENTY_MIN) {
                stock = getStockInfo(symbol, currentTime);
            }
        } else {
            stock = getStockInfo(symbol, currentTime);
        }
        return stock;
    }
    //This is synched so we only do one request at a time
    //If yahoo doesn't return stock info we will try to return it from the map in memory
    private synchronized StockBean getStockInfo(String symbol, long time) {
        try {
            URL yahoofin = new URL(StringConstant.YAHOO_URL + symbol + StringConstant.DEFULT_SELECTION);
            URLConnection yc = yahoofin.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] yahooStockInfo = inputLine.split(",");
                StockBean stockInfo = new StockBean();
/*
                System.out.println(" * b2 - Ask (Real-time) "+yahooStockInfo[0]);
                System.out.println(" * d1- Last Trade Date  "+yahooStockInfo[1]);
                System.out.println("* g - Day's Low  "+yahooStockInfo[2]);
                System.out.println(" * h - Day's High  "+yahooStockInfo[3]);
                System.out.println("* j - 52-week Low  "+yahooStockInfo[4]);
                System.out.println("* k - 52-week High "+yahooStockInfo[5]);
                System.out.println("* m - Day's Range "+yahooStockInfo[6]);
                System.out.println("* m5 - Change From 200-day Moving Average "+yahooStockInfo[7]);
                System.out.println(" * m7 - Change From 50-day Moving Average "+yahooStockInfo[8]);
                System.out.println(" * l1 - Last Trade (Price Only) "+yahooStockInfo[9]);
                System.out.println("* s -Symbol "+yahooStockInfo[10]);
                System.out.println("* c -Commision "+yahooStockInfo[11]);
                System.out.println("(((((((((((((((((((((((((((((((((((((((((((((((((())))))))))))))))))))))))))");
                
*/              stockInfo.setAsk(yahooStockInfo[0]);
                stockInfo.setLastTradeDate(yahooStockInfo[1]);
                stockInfo.setDayLow(yahooStockInfo[2]);
                stockInfo.setDayHigh(yahooStockInfo[3]);
                stockInfo.setFiftyTwoWeekLow(yahooStockInfo[4]);
                stockInfo.setFiftyTwoWeekHigh(yahooStockInfo[5]);
                stockInfo.setDayRange(yahooStockInfo[6]);
                stockInfo.setChangeFromTwoHundredDayMovingAverage(yahooStockInfo[7]);
                stockInfo.setChangeFromFiftyDayMovingAverage(yahooStockInfo[8]);
                stockInfo.setPrice(yahooStockInfo[9]);
                stockInfo.setSymbol(yahooStockInfo[10]);
                stockInfo.setCommission(yahooStockInfo[11]);
            	/**
            	 * b2 - Ask (Real-time) 
            	 * d1- Last Trade Date 
            	 * g - Day's Low 
            	 * h - Day's High 
            	 * j - 52-week Low 
            	 * k - 52-week High 
            	 * m - Day's Range 
            	 * m5 - Change From 200-day Moving Average 
            	 * m7 - Change From 50-day Moving Average 
            	 * l1 - Last Trade (Price Only) 
            	 * s -Symbol
            	 */
                stocks.put(symbol, stockInfo);
                break;
            }
            in.close();
        } catch (Exception ex) {
        	System.out.println("Unable to get stockinfo for: " + symbol + ex);
            //log.error("Unable to get stockinfo for: " + symbol + ex);
        }
        return stocks.get(symbol);
     }
}