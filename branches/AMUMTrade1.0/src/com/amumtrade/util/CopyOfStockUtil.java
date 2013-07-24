package com.amumtrade.util;

import static com.amumtrade.constant.StringConstant.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amumtrade.bean.StockBean;
import com.amumtrade.bean.StockWebBean;
import com.amumtrade.dao.StockEngineDAO;
import com.csvreader.CsvReader;
public class CopyOfStockUtil {

	public static void getStockQuote(String stockOutputPath, String stockFileName)throws Exception{

		int currentCount=0;
		String rating=null;
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		DateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		System.out.println("Writing Stock to output file........");
		FileWriter fwo = new FileWriter( stockOutputPath+"_"+dateFormat.format(cal.getTime())+".csv", false );	 
		List<String> marketNameList = getQuoteFromFile(stockFileName);
		BufferedWriter bwObj = new BufferedWriter( fwo );
		try {
			String header = getHeader();
			//bwObj.newLine();
			//bwObj.write(dayFormat.format(cal.getTime()));
			bwObj.newLine();
			bwObj.write(header);
			bwObj.newLine();
			for(String symbol : marketNameList){
				currentCount++;
				StockBean  stock = StockEngineDAO.getInstance().getStockPrice(symbol.trim());
				rating = getYahooRating(symbol.trim());
				stock.setYahooRating(rating);
				rating=getCNNRating(symbol.trim());
				stock.setCnnRating(rating);
				rating = getTheStreetRating(symbol.trim());
				stock.setTheStreetRating(rating);
				rating=getMarketWatchRating(symbol.trim());
				stock.setMarketWatchRating(rating);
				String finalStock=getFinalStockValue(stock);
				System.out.println(">>>>>>>>"+finalStock);
				int cCount=marketNameList.size()-currentCount;
				System.out.println(stockFileName+">> "+marketNameList.size() +" / "+ cCount);
				bwObj.write(finalStock);
				bwObj.newLine();
			}

		} catch (Exception e) {
			e.printStackTrace();	
		}finally{
			bwObj.close();
		}

	}

	private static String getYahooRating(String symbol) throws Exception{
		String rating=null;
		try {
			String urlString=YAHOO_RATING_URL.replaceAll(SYMBOL, symbol);
			URL url = new URL(urlString);
			URLConnection ucon = url.openConnection();
			String htmlContents = getResponseData(ucon);
			try {
				String tmp= htmlContents.substring(htmlContents.indexOf(RATING_START_TOKEN));
				String tmp1= tmp.substring(tmp.indexOf(RATING_START_TOKEN), tmp.indexOf(RATING_END_TOKEN));
				rating= tmp1.substring(tmp.indexOf(RATING_ADDI_START_TOKEN), tmp1.lastIndexOf(RATING_ADDI_END_TOKEN));
				rating=rating.replace(RATING_ADDI_START_TOKEN, "").toLowerCase().trim();
				if(!NO_OPINION.equalsIgnoreCase(rating)|| !NO_RATING.equalsIgnoreCase(rating)){
					double d= Double.valueOf(rating);
					rating =getRatingLabel(d,rating);
				}
			} catch (Exception e) {
				rating=NO_OPINION;
			}
		} catch (Exception e) {
			rating=NO_OPINION;
		}
		return rating;
	}

	private static String getCNNRating(String symbol) throws Exception{
		String rating=null;
		try {
			String urlString=CNN_RATING_URL.replaceAll(SYMBOL, symbol);
			URL url = new URL(urlString);
			URLConnection ucon = url.openConnection();
			String htmlContents = getResponseData(ucon);
			try {
				if(htmlContents.contains(CNN_THIRD_STRING) || htmlContents.contains(CNN_FIFTH_STRING) ){
					rating=NO_OPINION;
				}else{
					CNN_FOURTH_STRING= CNN_FOURTH_STRING.replaceAll(SYMBOL, symbol);
					if(htmlContents.contains(CNN_FOURTH_STRING)){
						rating=NO_OPINION;
					}else{
						String cnnRating = htmlContents.substring(htmlContents.indexOf(CNN_FIRST_STRING)+CNN_FIRST_STRING.length(),htmlContents.indexOf(CNN_SECOND_STRING));
						rating=cnnRating;
					}
				}
			} catch (Exception e) {
				rating=NO_OPINION;
			}
		} catch (Exception e) {
			rating=NO_OPINION;
		}
		return rating;
	}
	
	private static String getTheStreetRating(String symbol) throws Exception{
		String rating=null;
		try {
			String urlString=THE_STREET_RATING_URL.replaceAll(SYMBOL, symbol);
			URL url = new URL(urlString);
			URLConnection ucon = url.openConnection();
			String htmlContents = getResponseData(ucon);
			try {
				if(htmlContents.contains(THE_STREET_FOURTH_STRING)){
					rating=NO_OPINION;
				}else{
					String theStreetRating = htmlContents.substring(htmlContents.indexOf(THE_STREET_FIRST_STRING)+THE_STREET_FIRST_STRING.length(),htmlContents.indexOf(THE_STREET_SECOND_STRING));
					String grade=htmlContents.substring(htmlContents.indexOf(THE_STREET_SECOND_STRING)+THE_STREET_SECOND_STRING.length(),htmlContents.indexOf(THE_STREET_THIRD_STRING));
					rating= getTheStreetRatingLabel(theStreetRating ,grade);
				}
			} catch (Exception e) {
				rating=NO_OPINION;
			}
		} catch (Exception e) {
			rating=NO_OPINION;
		}
		return rating;
	}

	private static String getMarketWatchRating(String symbol) throws Exception{
		String rating=null;
		try {
			String urlString=MARKET_WATCH_RATING_URL.replaceAll(SYMBOL, symbol);
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
		return rating;
	}
	private static String getMarketWatchRatingLabel(String rat){
		String finalRating=null;
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
	private static String getTheStreetRatingLabel(String rat, String key){
		String finalRating=null;
		if(rat.equalsIgnoreCase(BUY)){
			if(key.equalsIgnoreCase(A_PLUS)){
				finalRating=STRONG_BUY;
			}else if(key.equalsIgnoreCase(A)){
				finalRating=STRONG_BUY;
			}else if(key.equalsIgnoreCase(A_MINUS)){
				finalRating=BUY;
			}else if(key.equalsIgnoreCase(B_Plus)){
				finalRating=BUY;
			}else if(key.equalsIgnoreCase(B)){
				finalRating=BUY;
			}else if(key.equalsIgnoreCase(B_MINUS)){
				finalRating=HOLD;
			}else{
				finalRating=rat;
			}
		}else if(rat.equalsIgnoreCase(HOLD)){
			if(key.equalsIgnoreCase(C_PLUS)){
				finalRating=STRONG_HOLD;
			}else if(key.equalsIgnoreCase(C)){
				finalRating=HOLD;
			}else if(key.equalsIgnoreCase(C_MINUS)){
				finalRating=SELL;
			}else{
				finalRating=rat;
			}
			
		}else if(rat.equalsIgnoreCase(SELL)){
			if(key.equalsIgnoreCase(D_PLUS)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(D)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(D_MINUS)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(E_PLUS)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(E)){
				finalRating=STRONG_SELL;
			}else if(key.equalsIgnoreCase(E_MINUS)){
				finalRating=STRONG_SELL;
			}else if(key.equalsIgnoreCase(F)){
				finalRating=STRONG_SELL;
			}else{
				finalRating=rat;
			}
		}
		return finalRating;
	}
	public static String getRatingLabel(double key, String rating) {
		rating =null;
		if(key <= 1.0){
			rating=STRONG_BUY;
		}else if(key <= 2.0){
			rating=BUY;
		}else if(key <= 3.0){
			rating=HOLD;
		}else if(key <= 4.0){
			rating=SELL;
		}else if(key <= 5.0){
			rating=STRONG_SELL;
		}
		return rating;
	}

	private static String getResponseData(URLConnection conn) throws Exception {
		StringBuffer sb = new StringBuffer();
		String data = "";
		InputStream is = conn.getInputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			sb.append((char) ch);
		}

		data = sb.toString();
		is.close();
		is = null;
		sb = null;
		System.gc();
		return data;
	}
	private static String getHeader() {
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
		YAHOO_RATING+COMMA+
		CNN_RATING+COMMA+
		THE_STREET_RATING+
		COMMA+
		MARKET_WATCH;
	}

	
	public static Map<String, StockWebBean> getQuoteFromInputFile(String fileName, StockWebBean webStockBean)throws Exception{
		Map<String, StockWebBean> webQuoteMap=new HashMap<String, StockWebBean>();
		CsvReader products = new CsvReader(fileName);
		try{
			products.readHeaders();
			while (products.readRecord())
			{
				webStockBean = new StockWebBean();
				String symbol = products.get("Symbol");
				webStockBean.setSymbol(symbol);
				String name = products.get("Name");
				webStockBean.setName(name);
				String lastSale = products.get("LastSale");
					if(NOT_APP.equalsIgnoreCase(lastSale)){
						webStockBean.setLastSale(ZERO);
					}else{
						webStockBean.setLastSale(lastSale);
					}
				String marketCap = products.get("MarketCap");
				webStockBean.setMarketCap(marketCap);
				String ADR_TSO = products.get("ADR TSO");
				webStockBean.setAdrTso(ADR_TSO);
				String IPOyear	 = products.get("IPOyear");
				webStockBean.setIPOyear(IPOyear);
				String sector = products.get("Sector");
				webStockBean.setSector(sector);
				String industry = products.get("industry");
				webStockBean.setIndustry(industry);
				String summary_Quote = products.get("Summary Quote");
				webStockBean.setSummaryQuote(summary_Quote);
				
				if(webStockBean.getSymbol()!=null){
					webQuoteMap.put(webStockBean.getSymbol(), webStockBean);
				}
			}
	}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}finally{
				products.close();

		}
		return webQuoteMap;
	}
	public static void writeQuoteToTempFile(List<String> symbolList, String marketName)throws Exception{
		try {

			FileWriter  fstream = new FileWriter (marketName);
			BufferedWriter out = new BufferedWriter(fstream);
			for(String symbol :symbolList){
				out.write(symbol);
				out.write(NEW_LINE);
			}
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> getQuoteFromFile(String marketName)throws Exception{
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
	private static String getFinalStockValue(StockBean stock) {
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
		stock.getYahooRating()+
		COMMA+
		stock.getCnnRating()+
		COMMA+
		stock.getTheStreetRating()+
		COMMA+
		stock.getMarketWatchRating();
	}
}
