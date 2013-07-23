package com.amumtrade.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.amumtrade.bean.AMUMStockBean;
import com.amumtrade.helper.AMUMStockWriter;

public class AMUMStockDAO implements Runnable {

	private  AMUMStockBean stockBean;
	BufferedWriter bwObj;
	public AMUMStockDAO(AMUMStockBean stockBean, BufferedWriter bwObj) {
		this.stockBean = stockBean;
		this.bwObj = bwObj;
	}
	
	@Override
	public void run() {
		try {
			String charset = "UTF-8";
			URLConnection connection = new URL("http://finance.yahoo.com/q/ks?s="+stockBean.getSymbol().trim()+"+Key+Statistics").openConnection();
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			BufferedReader reader = null;
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			for (String line; (line = reader.readLine()) != null;) {
			//	findEPS(line);
				findDilutedEPS(line);
				findOperatingMargin(line);
				findReturnOnAssets(line);
				findReturnOnEquity(line);
				findRevenuePerShare(line);
	        }
			
			bwObj.write(stockBean.getSymbol()+","+stockBean.getLastSale()
					+","+stockBean.getDilutedEPS()+","+stockBean.getOperatingMargin()
					+","+stockBean.getReturnOnAssets()+","+stockBean.getReturnOnEquity()
					+","+stockBean.getRevenuePerShare());
			bwObj.newLine();  
			/*System.out.println(stockBean.getSymbol()+","+stockBean.getLastSale()
					+","+stockBean.getDilutedEPS()+","+stockBean.getOperatingMargin()
					+","+stockBean.getReturnOnAssets()+","+stockBean.getReturnOnEquity()
					+","+stockBean.getRevenuePerShare());*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void findDilutedEPS(String line) {
		try {
			if (line.contains("Diluted EPS (ttm):")) {
				line = line.substring(line.indexOf("Diluted EPS (ttm):"),
						line.lastIndexOf("Qtrly Earnings Growth (yoy):"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Diluted EPS (ttm):", "");
				line = line.substring(0, line.indexOf("</td>"));
				stockBean.setDilutedEPS(line);
			} else if (line
					.contains("There is no Key Statistics data available")) {
				stockBean.setDilutedEPS("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

/*	private String getKeyStatistics(String inputString, String startString, String endString) {

		if(inputString.contains(startString)){
			inputString = inputString.substring(inputString.indexOf(startString), inputString.lastIndexOf(endString));
			inputString = inputString.replace("</td><td class=\"yfnc_tabledata1\">", "");
			inputString = inputString.replace(startString,"");
			inputString = inputString.substring(0,inputString.indexOf("</td>"));
			return endString ;
		}else if(inputString.contains("There is no Key Statistics data available")){
			inputString = "No Data";
			return ;
		}
	
		return inputString;
	}
*/
	private void findRevenuePerShare(String line) {
		try {
			if(line.contains("Revenue Per Share (ttm):")){
	    		line = line.substring(line.indexOf("Revenue Per Share (ttm):"), line.lastIndexOf("Qtrly Revenue Growth (yoy):"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Revenue Per Share (ttm):","");
				line = line.substring(0,line.indexOf("</td>"));
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
				stockBean.setOperatingMargin(line);
			}else if(line.contains("There is no Key Statistics data available")){
				stockBean.setOperatingMargin("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*private void findEPS(String line) {
		try {
			if(line.contains("Diluted EPS (ttm):")){
				line = line.substring(line.indexOf("Diluted EPS (ttm):"), line.lastIndexOf("</td></tr><tr><td class=\"yfnc_tablehead1\" width=\"74%\">Qtrly Earnings Growth (yoy):</td>"));
				line = line.replace("</td><td class=\"yfnc_tabledata1\">", "");
				line = line.replace("Diluted EPS (ttm):","");
				stockBean.setEps(line);
			}else if(line.contains("There is no Key Statistics data available")){
				stockBean.setEps("No Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/



}
