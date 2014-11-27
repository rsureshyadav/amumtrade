package com.amumtrade.constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AMUMStockConstant {

	public static final String BSE_A_INPUT_PATH="config/input/BSE_A.csv";
	public static final String BSE_B_INPUT_PATH="config/input/BSE_B.csv";
	public static final String BSE_E_INPUT_PATH="config/input/BSE_E.csv";
	public static final String BSE_F_INPUT_PATH="config/input/BSE_F.csv";
	public static final String BSE_I_INPUT_PATH="config/input/BSE_I.csv";
	public static final String BSE_M_INPUT_PATH="config/input/BSE_M.csv";
	public static final String BSE_MT_INPUT_PATH="config/input/BSE_MT.csv";
	public static final String BSE_T_INPUT_PATH="config/input/BSE_T.csv";
	public static final String BSE_Z_INPUT_PATH="config/input/BSE_Z.csv";

	

	public static final String BSE_NAME="BSE";
	public static final String NASDAQ_NAME="NASDAQ";
	public static final String NYSE_NAME="NYSE";

	public static final String BSE_OUTPUT_PATH="config/output/";
	public static final String NASDAQ_OUTPUT_PATH="config/output/@";
	public static final String NYSE_OUTPUT_PATH="config/output/@";
	public static final DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
	public static final DateFormat timerDateFormat = new SimpleDateFormat("dd_MM_yyyy");

	public static final Calendar cal = Calendar.getInstance();
	
	// http://in.finance.yahoo.com/gainers?e=bo
	//http://in.finance.yahoo.com/losers?e=bo
	//http://download.finance.yahoo.com/d/quotes.csv?s=BOBSL.BO,JAIPAN.BO,SANGHIIN.BO&f=snl1d1t1ohgdrx
	
	public static final String BSE_URL="http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=amex&render=download";
	public static final String MSN_URL="http://msn.bankbazaar.com/welcome/stocksStartingWith?q=@";
	public static final String NYSE_URL="http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=nyse&render=download";

	public static final String YAHOO_URL="http://finance.yahoo.com/d/quotes.csv?s=";
	//public static final String STD_SELECTION="&f=sl1d1t1c1ohgv&e=.csv";
	//aa2a5b b2b3b4b6cc1c3c6c8dd1d2e1e7e8e9f6ghjkg1g3g4g5g6ii5j1j3j4j5j6k1k2k3k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7t1t6t7t8vv1v7ww1w4xy  
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
	 * c1 - Commision
	 */
	public static final String DEFULT_SELECTION="&f=b2d1ghjkmm5m7l1sc1=.csv";
	public static final String COMMA=",";
	public static final String EMPTY="";
	public static final String NOT_APP="n/a";
	public static final String ZERO="0";
	public static final int ONE=1;
	public static final int TWO=2;
	public static final int THREE=3;
	public static final int FOUR=4;
	public static final int FIVE=5;
	
	public static final String NEW_LINE="\n";
	public static final String SYMBOL="Symbol";
	public static final String PRICE="Price";
	public static final String ASK = "Ask"; 
	public static final String DAY_RANGE="Day Range";
	public static final String DAY_LOW = "Day Low";
	public static final String DAY_HIGH = "Day High";
	public static final String FIFTY_TWO_WEEK_LOW ="52 wk Low";
	public static final String FIFTY_TWO_WEEK_HIGH="52 wk High";
	public static final String CHANGE_FROM_FIFTY_DAY ="Change from 50 days";
	public static final String CHANGE_FROM_TWO_HUNDRED_DAY="Change from 200 days";
	public static final String LAST_TRADE_DATE="Last Trade Date";
	public static final String COMMISSION="Commission";
	public static final String AMUM_PROJECTION="AMUM Proj";
	public static final String YAHOO_RATING= "Yahoo Rating";
	public static final String RATING_START_TOKEN="Mean Recommendation (this week):";
	public static final String RATING_ADDI_START_TOKEN="\">";
	public static final String RATING_END_TOKEN="<tr>";
	public static final String RATING_ADDI_END_TOKEN="</td>";
	public static final String YAHOO_RATING_URL="http://finance.yahoo.com/q/ao?s=Symbol+Analyst+Opinion";
	public static final String STRONG_BUY = "Strong Buy";
	public static final String BUY="Buy";
	public static final String OVERWEIGTH="Overweight";
	public static final String UNDERWEIGTH="Underweight";
	public static final String STRONG_HOLD="Strong Hold";
	public static final String HOLD="Hold";
	public static final String SELL="Sell";
	public static final String STRONG_SELL ="Strong Sell";
	public static final String NO_RATING ="n/a";
	public static final String SPACE=" ";
	public static final String NO_OPINION="No Opinion";
	public static final String CNN_RATING= "CNN Rating";
	public static final String CNN_RATING_URL="http://money.cnn.com/quote/forecast/forecast.html?symb=Symbol";
	public static final String CNN_FIRST_STRING="<strong class=\"wsod_rating\">";
	public static final String CNN_SECOND_STRING="</strong>";
	public static final String CNN_THIRD_STRING="There are no recommendations available";
	public static  String CNN_FOURTH_STRING="Your search for &ldquo;<strong>Symbol</strong>&rdquo; was not found.";
	public static final String CNN_FIFTH_STRING="Symbol not found";
	public static final String THE_STREET_RATING= "The Street Rating";
	public static final String THE_STREET_RATING_URL="http://www.thestreet.com/quote/Symbol/details/analyst-ratings.html";
	public static final String THE_STREET_FIRST_STRING="CurrentRating\":\"";
	public static final String THE_STREET_SECOND_STRING="\",\"LetterGradeRating\":\"";
	public static final String THE_STREET_THIRD_STRING="\"}]}});";
	public static final String THE_STREET_FOURTH_STRING="Sorry that you couldn't find the page you wanted.";
	public static final String A_PLUS ="A+";
	public static final String A="A";
	public static final String A_MINUS="A-";
	public static final String B_Plus="B+";
	public static final String B="B";
	public static final String B_MINUS="B-";
	public static final String C_PLUS="C+"; 
	public static final String C="C"; 
	public static final String C_MINUS="C-"; 
	public static final String D_PLUS="D+"; 
	public static final String D="D"; 
	public static final String D_MINUS="D-"; 
	public static final String E_PLUS="E+"; 
	public static final String E="E"; 
	public static final String E_MINUS="E-"; 
	public static final String F="F"; 
	 
	public static final String MARKET_WATCH= "Market Watch";
	public static final String MARKET_WATCH_RATING_URL="http://www.marketwatch.com/investing/stock/Symbol/analystestimates";
    public static final String MARKET_WATCH_FIRST_STRING="<td class=\"first\">MEAN</td>";
	public static final String MARKET_WATCH_SECOND_STRING="<td class=\"current\">";
	public static final String MARKET_WATCH_THIRD_STRING="</td>";
	
	public static final int THREAD_COUNT = 20;
	public static final String FIVE_STAR = "5 star";
	public static final String FOUR_STAR = "4 star";
	public static final String THREE_STAR = "3 star";
	public static final String TWO_STAR = "2 star";
	public static final String ONE_STAR = "1 star";

	public static String STOCK_URL = "http://www.moneycontrol.com";

}
