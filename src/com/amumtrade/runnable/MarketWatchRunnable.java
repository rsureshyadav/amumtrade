package com.amumtrade.runnable;

import static com.amumtrade.constant.StringConstant.BUY;
import static com.amumtrade.constant.StringConstant.HOLD;
import static com.amumtrade.constant.StringConstant.MARKET_WATCH_FIRST_STRING;
import static com.amumtrade.constant.StringConstant.MARKET_WATCH_SECOND_STRING;
import static com.amumtrade.constant.StringConstant.MARKET_WATCH_THIRD_STRING;
import static com.amumtrade.constant.StringConstant.NO_OPINION;
import static com.amumtrade.constant.StringConstant.OVERWEIGTH;
import static com.amumtrade.constant.StringConstant.SELL;
import static com.amumtrade.constant.StringConstant.STRONG_BUY;
import static com.amumtrade.constant.StringConstant.STRONG_SELL;
import static com.amumtrade.constant.StringConstant.UNDERWEIGTH;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.amumtrade.bean.StockBean;

public class MarketWatchRunnable extends BaseRun{



	public MarketWatchRunnable(String urlString, String symbol,
			HashMap<String, String> ratingMap) {
		super(urlString, symbol, ratingMap);
		// TODO Auto-generated constructor stub
	}

	public void run() {

	 String rating = null;
		try {
			 URL url = new URL(urlString);
			 URLConnection ucon = url.openConnection();
			 String htmlContents = getResponseData(ucon);
			try {
				String marketWatchRating1 = htmlContents.substring(htmlContents.indexOf(MARKET_WATCH_FIRST_STRING)+MARKET_WATCH_FIRST_STRING.length());
				String marketWatchRating2 = marketWatchRating1.substring(marketWatchRating1.indexOf(MARKET_WATCH_SECOND_STRING)+MARKET_WATCH_SECOND_STRING.length(),marketWatchRating1.indexOf(MARKET_WATCH_THIRD_STRING));
				rating=getMarketWatchRatingLabel(marketWatchRating2);
		} catch (Exception e) {
				rating=NO_OPINION;
			}
		} catch (Exception e) {
			rating=NO_OPINION;
		}
		ratingMap.put(symbol, rating);
		System.out.println("Completed marketwatch rating>>"+symbol+">>"+rating);		
	}

	private String getMarketWatchRatingLabel(String rat){
		String finalRating=NO_OPINION;
		if(rat.equalsIgnoreCase(BUY)){
			finalRating=STRONG_BUY;
		}else if(rat.equalsIgnoreCase(OVERWEIGTH)){
			finalRating= BUY;
		}else if(rat.equalsIgnoreCase(HOLD)){
			finalRating=HOLD;
		}else if(rat.equalsIgnoreCase(UNDERWEIGTH)){
			finalRating=SELL;
		}else if(rat.equalsIgnoreCase(SELL)){
			finalRating=STRONG_SELL;
		}
		return finalRating;
	}
}
