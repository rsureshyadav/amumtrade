package com.amumtrade.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import sun.net.www.protocol.http.HttpURLConnection;

import com.amumtrade.bean.AMUMStockBean;

public class AMUMStockDAO implements Runnable {

	private  AMUMStockBean stockBean;
	BufferedWriter bwObj;
	public AMUMStockDAO(AMUMStockBean stockBean, BufferedWriter bwObj) {
		this.stockBean = stockBean;
		this.bwObj = bwObj;
	}
	
	@Override
	public void run() {
		URL url;
	    HttpURLConnection connection = null;  
		try {
			String targetURL = "http://finance.yahoo.com/q/ks?s="+stockBean.getSymbol().trim()+"+Key+Statistics";
			 url = new URL(targetURL);
		      connection = (HttpURLConnection)url.openConnection();
		      connection.setReadTimeout(5000);
		      
		      InputStream is = connection.getInputStream();
		      BufferedReader br = new BufferedReader(new InputStreamReader(is));
		      String line;
		      
		      while((line = br.readLine()) != null) {
		        findDilutedEPS(line);
		        findPERatio(line);
				findOperatingMargin(line);
				findReturnOnAssets(line);
				findReturnOnEquity(line);
				findRevenuePerShare(line);
				
		      }
		      br.close();
		      if(isValidLine(stockBean)){
		    	  bwObj.write(stockBean.getSymbol()+","+stockBean.getLastSale()
		    			  +","+stockBean.getDilutedEPS()+","+stockBean.getPeRatio()+","+stockBean.getOperatingMargin()
		    			  +","+stockBean.getReturnOnAssets()+","+stockBean.getReturnOnEquity()
		    			  +","+stockBean.getRevenuePerShare()+","+stockBean.getSector()+","+stockBean.getIndustry());
		    	  bwObj.newLine();  
		      }
			
			System.out.println("[ "+( stockBean.getTotalCount())+" ]"+stockBean.getSymbol()+","+stockBean.getLastSale()
					+","+stockBean.getDilutedEPS()+","+stockBean.getPeRatio()+","+stockBean.getOperatingMargin()
					+","+stockBean.getReturnOnAssets()+","+stockBean.getReturnOnEquity()
					+","+stockBean.getRevenuePerShare());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		      if(connection != null) {
		        connection.disconnect(); 
		      }
		    }
		
	}

	private boolean isValidLine(AMUMStockBean stockBean2) {

		if(!stockBean.getDilutedEPS().contains("-") && !stockBean.getDilutedEPS().contains("No Data") 
				&& !stockBean.getDilutedEPS().contains("N/A") 
				&&!stockBean.getOperatingMargin().contains("-")&&
	    		!stockBean.getReturnOnAssets().contains("-")&&!stockBean.getReturnOnEquity().contains("-")
	    		&& !stockBean.getReturnOnEquity().contains("No Data") 
				&& !stockBean.getReturnOnEquity().contains("N/A") &&
	    		! stockBean.getRevenuePerShare().contains("-")){
			return true;
		}
	    		
		return false;
	}

	private void findDilutedEPS(String line) {
		try {
			if (line.contains("Diluted EPS (ttm):")) {
				line = line.substring(line.indexOf("Diluted EPS (ttm):"),
						line.lastIndexOf("Qtrly Earnings Growth (yoy):"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Diluted EPS (ttm):", "");
				line = line.substring(0, line.indexOf("</td>"));
				line = line.replace(",", "");
				stockBean.setDilutedEPS(line);
			} else if (line
					.contains("There is no Key Statistics data available")) {
				stockBean.setDilutedEPS("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void findPERatio(String line) {
		try {

			if (line.contains("Trailing P/E (ttm, intraday):")) {
				line = line.substring(line.indexOf("Trailing P/E (ttm, intraday):"),
						line.lastIndexOf("Forward P/E"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Trailing P/E (ttm, intraday):", "");
				line = line.substring(0, line.indexOf("</td>"));
				line = line.replace(",", "");
				//System.out.println(">>>>>>>>>>>P/ERatio>>>>>>>>>"+line);
				stockBean.setPeRatio(line);
			} else if (line
					.contains("There is no Key Statistics data available")) {
				stockBean.setPeRatio("No Data");
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
private void findRevenuePerShare(String line) {
		try {
			if(line.contains("Revenue Per Share (ttm):")){
	    		line = line.substring(line.indexOf("Revenue Per Share (ttm):"), line.lastIndexOf("Qtrly Revenue Growth (yoy):"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Revenue Per Share (ttm):","");
				line = line.substring(0,line.indexOf("</td>"));
				line = line.replace(",", "");
				stockBean.setRevenuePerShare(line);
			}else if(line.contains("There is no Key Statistics data available")){
				stockBean.setRevenuePerShare("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void findReturnOnEquity(String line) {
		try {
			if(line.contains("Return on Equity (ttm):")){
	    		line = line.substring(line.indexOf("Return on Equity (ttm):"), line.lastIndexOf("Income Statement"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Return on Equity (ttm):","");
				line = line.substring(0,line.indexOf("</td>"));
				line = line.replace(",", "");
				stockBean.setReturnOnEquity(line);
			}else if(line.contains("There is no Key Statistics data available")){
				stockBean.setReturnOnEquity("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void findReturnOnAssets(String line) {
		try {
			if(line.contains("Return on Assets (ttm):")){
	    		line = line.substring(line.indexOf("Return on Assets (ttm):"), line.lastIndexOf("Return on Equity (ttm):"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Return on Assets (ttm):","");
				line = line.substring(0,line.indexOf("</td>"));
				line = line.replace(",", "");
				stockBean.setReturnOnAssets(line);
			}else if(line.contains("There is no Key Statistics data available")){
				stockBean.setReturnOnAssets("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void findOperatingMargin(String line) {
		try {
			if(line.contains("Operating Margin (ttm):")){
	    		line = line.substring(line.indexOf("Operating Margin (ttm):"), line.lastIndexOf("Return on Assets (ttm):"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Operating Margin (ttm):","");
				line = line.substring(0,line.indexOf("</td>"));
				line = line.replace(",", "");
				stockBean.setOperatingMargin(line);
			}else if(line.contains("There is no Key Statistics data available")){
				stockBean.setOperatingMargin("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
