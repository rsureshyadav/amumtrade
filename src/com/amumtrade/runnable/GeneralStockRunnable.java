package com.amumtrade.runnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.amumtrade.bean.StockBean;
import com.amumtrade.constant.StringConstant;
import com.amumtrade.util.StockUtil;


public class GeneralStockRunnable extends BaseRun {

	private StockBean stockBean;
	public GeneralStockRunnable(String urlString, String symbol,
			HashMap<String, String> ratingMap, StockBean stockBean) {
		super(urlString, symbol, ratingMap);
		this.stockBean = stockBean;
		// TODO Auto-generated constructor stub
	}

	public void run(){
		String finalRating=null;
		BufferedReader br =null;
		try {
			URL url = new URL(urlString);
			URLConnection yc = url.openConnection();
			br = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			String str = br.readLine();
            System.out.println(">>"+br);

			if(str !=null){
				List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
				stockBean.setAsk(items.get(0));
				stockBean.setLastTradeDate(items.get(1));
				stockBean.setDayLow(items.get(2));
				stockBean.setDayHigh(items.get(3));
				stockBean.setFiftyTwoWeekLow(items.get(4));
	            stockBean.setFiftyTwoWeekHigh(items.get(5));
	            stockBean.setDayRange(items.get(6));
	            stockBean.setChangeFromTwoHundredDayMovingAverage(items.get(7));
	            stockBean.setChangeFromFiftyDayMovingAverage(items.get(8));
	            stockBean.setPrice(items.get(9));
	            stockBean.setSymbol(items.get(10));
	            stockBean.setCommission(items.get(11));
	            finalRating=StockUtil.getFinalStockValue(stockBean);
	            System.out.println(">>"+finalRating);
	            ratingMap.put(symbol, finalRating);
			}
		
		}catch (MalformedURLException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
	}finally{
		try {
			if(br !=null)
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	}
}
