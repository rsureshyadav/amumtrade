package com.amumtrade.util;

import static com.amumtrade.constant.StringConstant.ASK;
import static com.amumtrade.constant.StringConstant.CHANGE_FROM_FIFTY_DAY;
import static com.amumtrade.constant.StringConstant.CHANGE_FROM_TWO_HUNDRED_DAY;
import static com.amumtrade.constant.StringConstant.CNN_RATING;
import static com.amumtrade.constant.StringConstant.COMMA;
import static com.amumtrade.constant.StringConstant.COMMISSION;
import static com.amumtrade.constant.StringConstant.DAY_HIGH;
import static com.amumtrade.constant.StringConstant.DAY_LOW;
import static com.amumtrade.constant.StringConstant.DAY_RANGE;
import static com.amumtrade.constant.StringConstant.FIFTY_TWO_WEEK_HIGH;
import static com.amumtrade.constant.StringConstant.FIFTY_TWO_WEEK_LOW;
import static com.amumtrade.constant.StringConstant.LAST_TRADE_DATE;
import static com.amumtrade.constant.StringConstant.MARKET_WATCH;
import static com.amumtrade.constant.StringConstant.PRICE;
import static com.amumtrade.constant.StringConstant.SYMBOL;
import static com.amumtrade.constant.StringConstant.THE_STREET_RATING;
import static com.amumtrade.constant.StringConstant.YAHOO_RATING;
import static com.amumtrade.constant.StringConstant.AMUM_PROJECTION;
import com.amumtrade.bean.StockBean;

public class StockUtil {

	
	public static String getHeader() {
		return
		SYMBOL + COMMA+
		PRICE + COMMA+
		ASK + COMMA+
		DAY_RANGE + COMMA+
		DAY_LOW + COMMA+
		DAY_HIGH + COMMA+
		FIFTY_TWO_WEEK_LOW + COMMA+
		FIFTY_TWO_WEEK_HIGH + COMMA+
		CHANGE_FROM_FIFTY_DAY + COMMA+
		CHANGE_FROM_TWO_HUNDRED_DAY + COMMA+
		LAST_TRADE_DATE + COMMA+
		COMMISSION+ COMMA+
		AMUM_PROJECTION+ COMMA+
		YAHOO_RATING+COMMA+
		CNN_RATING+COMMA+
		THE_STREET_RATING+
		COMMA+
		MARKET_WATCH;
	}
	
	public static String getFinalStockValue(StockBean stock) {
		return 
		stock.getSymbol()+
		COMMA+
		stock.getPrice()+
		COMMA+
		stock.getAsk()+
		COMMA+
		stock.getDayRange()+
		COMMA+
		stock.getDayLow()+
		COMMA+
		stock.getDayHigh()+
		COMMA+
		stock.getFiftyTwoWeekLow()+
		COMMA+
		stock.getFiftyTwoWeekHigh()+
		COMMA+
		stock.getChangeFromFiftyDayMovingAverage()+
		COMMA+
		stock.getChangeFromTwoHundredDayMovingAverage()+
		COMMA+
		stock.getLastTradeDate()+
		COMMA+
		stock.getCommission()+
		COMMA+
		stock.getAMUMTradeWeight()+
		COMMA+
		stock.getYahooRating()+
		COMMA+
		stock.getCnnRating()+
		COMMA+
		stock.getTheStreetRating()+
		COMMA+
		stock.getMarketWatchRating();
	}

	
	
}
