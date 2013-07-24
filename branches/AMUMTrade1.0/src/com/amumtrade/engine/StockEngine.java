package com.amumtrade.engine;

import static com.amumtrade.constant.StringConstant.NEW_LINE;
import static com.amumtrade.constant.StringConstant.NOT_APP;
import static com.amumtrade.constant.StringConstant.ZERO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amumtrade.bean.StockWebBean;
import com.amumtrade.helper.StockRouteHelper;
import com.csvreader.CsvReader;
public class StockEngine {

	public static void getStockQuote(String stockOutputPath, String txtFileName)throws Exception{

		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		Calendar cal = Calendar.getInstance();
		System.out.println("Writing Stock to csv file........");
		FileWriter fwo = new FileWriter( stockOutputPath+"_"+dateFormat.format(cal.getTime())+".csv", false );	 
		BufferedWriter bwObj = new BufferedWriter( fwo );
		try {
			new StockRouteHelper(txtFileName, bwObj);
			
			//need to write for monitoring thread and upcomming new futures here...
		} catch (Exception e) {
			e.printStackTrace();	
		}finally{
			bwObj.close();
		}

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
	
}
