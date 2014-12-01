package com.amumtrade.constant;



public class FileNameConstant {
	public static final String CURRENT_CONCURRENT_VOLUME_GAINERS="config/CurrentConcurrentGainersVolume.csv";
	public static final String TOP_GAINERS="config/CurrentTopGainers.csv";
	public static final String VOLUME_TOP_GAINERS="config/CurrentTopGainersVolume.csv";
	
	public static final String ALL_TOP_GAINERS_CONCURRENT_GAINERS="config/output/AMUM_Gainers.csv";
	public static final String ALL_TOP_GAINER="config/output/AMUM_Current_TopGainers.csv";
	public static final String ALL_CONCURRENT_GAINER="config/output/AMUM_ConcurrentGainers.csv";
	public static final String ALL_CURRENT_CONCURRENT_GAINER="config/output/AMUM_Current_ConcurrentGainers.csv";
	
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
}
