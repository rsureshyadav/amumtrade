package com.amumtrade.factory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.AMUMStockConstant;

public class PositiveTurnAroundVolumeEPSRunner implements Runnable{

	private String command;
	private URL urlConn;
	private Map<String,ConcurrentGainersBean> financialAnalysisMap;
	private BufferedWriter bwObj;
	
	public PositiveTurnAroundVolumeEPSRunner(URL httpUrl,Map<String,ConcurrentGainersBean> financialAnalysis,BufferedWriter bufferWriter,String s){
		this.urlConn=httpUrl;
		this.financialAnalysisMap = financialAnalysis;
		this.bwObj = bufferWriter;
		this.command=s;
	}
	public void run() {
		try {
			processCommand();
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println(Thread.currentThread().getName()+" End.");

	}
	private void processCommand() throws IOException{
        try {
            Thread.sleep(5000);
            Reader reader = null;
            BufferedReader bufferReader = null;
            try {
		
		          bufferReader = new BufferedReader(new InputStreamReader(urlConn.openStream()));
		          findFinancialInfoReader(bufferReader,urlConn.toString());
		          
		        }
		        finally {
		          if (reader != null) 
		              reader.close();
		        }   
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	private void findFinancialInfoReader(BufferedReader bufferReader,String url) throws IOException{
		String inputLine = null;
		String eps = null;
		String newsLine = null;
		String newsLineDate = null;
		String standaloneProfit = null;
		String recommendation = null;
		boolean isEPS = false;
		boolean isnewsLine = false;
		Set<String> epsUrlSet = null;

		try {
			epsUrlSet = new HashSet<String>();

			 while ((inputLine = bufferReader.readLine()) != null)
		        {
				 if(inputLine.contains("Earnings Per Share")){
					 isEPS = true;
				 }else if(isEPS && inputLine.contains("<td align=\"right\" class=\"det\">") ){
					 eps = inputLine.trim();
					 eps = eps.replace("<td align=\"right\" class=\"det\">", "");
					 eps = eps.replace("</td>", "");
					 eps = eps.replace(",", "");
					 isEPS = false;
				 }else if(inputLine.contains("<div class=\"eleboxBg1\" >")){
					 isnewsLine = true;
				 }else if(isnewsLine){
					 newsLine = inputLine.trim();
					 if(newsLine != null && newsLine.contains("<a href")){
						 newsLineDate = newsLine.substring(newsLine.indexOf("<a href"));
						 newsLineDate = newsLine.replace(newsLineDate, "");
						 newsLineDate = newsLineDate.substring(newsLineDate.indexOf("<span class=\"gray1_11\">"),newsLineDate.lastIndexOf("</span>"));
						 newsLineDate = newsLineDate.replace("<span class=\"gray1_11\">", "");
						 newsLineDate = newsLineDate.replace(",", "");
					 }
					 if(newsLine != null && newsLine.contains("<b>")){
						 newsLine = newsLine.substring(newsLine.indexOf("<b>"));
						 String temp = newsLine.substring(newsLine.indexOf("<div"));
						 newsLine = newsLine.replace(temp, "");
						 newsLine = newsLine.replace("</b></a></div>", "");
						 newsLine = newsLine.replace("<b>", "");
						 newsLine =newsLine.replace(",", "");
						 newsLine = newsLineDate+"@ "+newsLine;
						 if(newsLine.contains("crore")){
							 standaloneProfit = "Yes";
						 }else if(newsLine.contains("Buy") || newsLine.contains("buy")){
							 if(newsLine.contains("Sudarshan Sukhani")){
								 recommendation = "BUY - SS**";  
							 }else if(newsLine.contains("KotakInvestment")){
								 recommendation = "BUY**";  
							 }else if(newsLine.contains("Santosh Nair")){
								 recommendation = "BUY**";  
							 }else if(newsLine.contains("Sonia Shenoy")){
								 recommendation = "BUY**";  
							 }else if(newsLine.contains("Menaka Doshi")){
								 recommendation = "BUY**";  
							 }else{
								 recommendation = "BUY"; 
							 }
						 }else if(newsLine.contains("Hold")){
							 recommendation = "HOLD"; 
						 }else if(newsLine.contains("Sell")){
							 recommendation = "SELL"; 
						 }
					 }else{
						 newsLine = "No news";
					 }
					 isnewsLine = false;
				 }
			    }
			 if(eps != null && !eps.contains("-") && !epsUrlSet.contains(url)){
				 ConcurrentGainersBean bean = financialAnalysisMap.get(url);
				 bean.setEps(eps);
				 bean.setFinanceApi(url);
				 if(standaloneProfit == null){
					 standaloneProfit="";
				 }
				 if(recommendation == null){
					 recommendation="";
				 }
				 if(newsLine == null){
					 newsLine="";
				 }
				 bean.setStandaloneProfit(standaloneProfit);
				 bean.setRecommendation(recommendation);
				 bean.setNews(newsLine);
				 writeVolumeToCSVFile(bean);
				 epsUrlSet.add(url);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void writeVolumeToCSVFile(ConcurrentGainersBean bean) {
		double epsValue ;
		try {
			epsValue = Double.valueOf(bean.getEps());
			if(epsValue > 100){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","	+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","
						+AMUMStockConstant.FIVE_STAR+","+bean.getStandaloneProfit()+","
						+bean.getRecommendation()+","+bean.getNews()
						+","+bean.getApi()+"\n");
				System.out.println(bean.getCompanyName()+","+"^"+bean.getCurrentPrice()+","+bean.getCurrentDayVolume()+","+bean.getVolumeRating()+","+bean.getEps()+","+AMUMStockConstant.FIVE_STAR);
				
			}else if(epsValue > 75 && epsValue < 100){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","	+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","
						+AMUMStockConstant.FOUR_STAR+","+bean.getStandaloneProfit()+","
						+bean.getRecommendation()+","+bean.getNews()
						+","+bean.getApi()+"\n");
			}else if(epsValue > 50 && epsValue < 75){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","	+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","
						+AMUMStockConstant.THREE_STAR+","+bean.getStandaloneProfit()+","
						+bean.getRecommendation()+","+bean.getNews()
						+","+bean.getApi()+"\n");
			}else if(epsValue > 25 && epsValue < 50){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","	+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","
						+AMUMStockConstant.TWO_STAR+","+bean.getStandaloneProfit()+","
						+bean.getRecommendation()+","+bean.getNews()
						+","+bean.getApi()+"\n");
			}else if(epsValue > 0 && epsValue < 25){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","	+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","
						+AMUMStockConstant.ONE_STAR+","+bean.getStandaloneProfit()+","
						+bean.getRecommendation()+","+bean.getNews()
						+","+bean.getApi()+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
    public String toString(){
        return this.command;
    }	
}
