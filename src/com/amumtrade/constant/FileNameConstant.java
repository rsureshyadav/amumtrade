package com.amumtrade.constant;



public class FileNameConstant {
	public static final String CURRENT_CONCURRENT_VOLUME_GAINERS="config/CurrentConcurrentGainersVolume.csv";
	public static final String TOP_GAINERS="config/CurrentTopGainers.csv";
	public static final String FINAL_TOP_GAINERS="config/FinalTopGainers.csv";

	public static final String VOLUME_TOP_GAINERS="config/CurrentTopGainersVolume.csv";
	public static final String VOLUME_ONLY_BUYERS="config/OnlyBuyersVolume.csv";
	public static final String VOLUME_POSITIVE_TRUNAROUND_STOCK="config/PositiveStockVolume.csv";

	
	public static final String ALL_TOP_GAINERS_CONCURRENT_GAINERS="config/output/AMUM_Gainers.csv";
	public static final String ALL_TOP_GAINER="config/output/AMUM_Current_TopGainers.csv";
	public static final String ALL_CONCURRENT_GAINER="config/output/AMUM_ConcurrentGainers.csv";
	public static final String ALL_CURRENT_CONCURRENT_GAINER="config/output/AMUM_Current_ConcurrentGainers.csv";
	public static final String ONLY_BUYERS="config/output/AMUM_Only_Buyers.csv";
	public static final String POSITIVE_TRUNAROUND_STOCK="config/output/AMUM_Positive_Turnarounds.csv";
	public static final String TOP_GAINERS_SENTIMETER_RATING="config/output/TopGainersSentimeterRating.csv";
	public static final String GAINERS_BUYERS_RATING="config/TopGainersBuyersRating.csv";
	public static final String SENTIMETER_RATING="config/output/SentimeterRating.csv";

	public static final String DEST_TOP_GAINER_PATH="config/data/OldCurrentTopGainers.csv";
	
	public static final String HTML_STYLE="<style>table {border-collapse: collapse;}table, td, th {border: 1px solid black;}</style>";
	public static final String HTML_TEMPLATE =  "<html>\n" +
											    "<head>\n" +
											    HTML_STYLE+"\n" +
											    "<body bgcolor=\"#f0f0f0\">\n" +
											    "<table>\n"+
											    " @htmlBody " +
											    "</table>\n"+
											    "</body>\n"+
											    "</html>";
	public static final String TOP_GAINERS_HEADER="Company Name,High,Low,Last Price,Prv Close,Change,% Gain,API";
	public static final String FINAL_TOP_GAINERS_HEADER="Company Name,High,Low,Last Price,Prv Close,Change,% Gain,API,Status";
	public static final String BUYERS_HEADER = "CompanyName,LastPrice,% Chg,Api";
	public static final String NEW = "NEW";
	public static final String REMOVE = "REMOVE";
	public static final String OLD ="OLD";
	public static final String YES ="Yes";
	public static final String NO ="No";
	public static final String MONEYCONTROL_URL =  "http://www.moneycontrol.com";
	public static final String ONLY_BUYERS_URL = "http://www.moneycontrol.com/stocks/marketstats/onlybuyers.php?optex=NSE&opttopic=buyers&index=9&sort=perchg";
	public static final String TOP_GAINERS_URL = "http://www.moneycontrol.com/stocks/marketstats/gainerloser.php?optex=NSE&opttopic=topgainers&index=-2&more=true";
	public static final String LAST_THREE_DAY_GAINER_URL =  "http://www.moneycontrol.com/india/stockmarket/concurrent-gainers/marketstatistics/nse/3days.html";
	public static final String LAST_FIVE_DAY_GAINER_URL  =  "http://www.moneycontrol.com/india/stockmarket/concurrent-gainers/marketstatistics/nse/5days.html";
	public static final String LAST_EIGHT_DAY_GAINER_URL = "http://www.moneycontrol.com/india/stockmarket/concurrent-gainers/marketstatistics/nse/8days.html";



}
