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

public class FinancialEPSAnalysisRunner implements Runnable{

	private String command;
	private URL urlConn;
	private Map<String,ConcurrentGainersBean> financialAnalysisMap;
	private BufferedWriter bwObj;
	
	public FinancialEPSAnalysisRunner(URL httpUrl,Map<String,ConcurrentGainersBean> financialAnalysis,BufferedWriter bufferWriter,String s){
		this.urlConn=httpUrl;
		this.financialAnalysisMap = financialAnalysis;
		this.bwObj = bufferWriter;
		this.command=s;
	}
	@Override
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
		boolean isEPS = false;
		Set<String> urlSet = null;
		ConcurrentGainersBean financialInfoBean;
		try {
			urlSet = new HashSet<String>();
			 while ((inputLine = bufferReader.readLine()) != null)
		        {
				 if(inputLine.contains("Earnings Per Share")){
					 isEPS = true;
				 }else if(isEPS && inputLine.contains("<td align=\"right\" class=\"det\">") && !urlSet.contains(url)){
					 financialInfoBean = new ConcurrentGainersBean();
					 eps = inputLine.trim();
					 eps = eps.replace("<td align=\"right\" class=\"det\">", "");
					 eps = eps.replace("</td>", "");
					 eps = eps.replace(",", "");
					 financialInfoBean.setEps(eps);
					 ConcurrentGainersBean bean = financialAnalysisMap.get(url);
					 financialInfoBean.setName(bean.getName());
					 financialInfoBean.setCurrentPrice(bean.getCurrentPrice());
					 financialInfoBean.setFinanceApi(url);
					 financialInfoBean.setApi(bean.getApi());
					 financialInfoBean.setCurrentDayVolume(bean.getCurrentDayVolume());
					 financialInfoBean.setFiveDayAvgVolume(bean.getFiveDayAvgVolume());
					 financialInfoBean.setTenDayAvgVolume(bean.getTenDayAvgVolume());
					 financialInfoBean.setThirtyDayAvgVolume(bean.getThirtyDayAvgVolume());
					 financialInfoBean.setVolumeRating(bean.getVolumeRating());
					//System.out.println(eps);
					 isEPS = false;
					 urlSet.add(url);
					 if(!eps.contains("-")){
						 writeVolumeToCSVFile(financialInfoBean);
					 }
				 }
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
				bwObj.write(bean.getName()+","+bean.getCurrentPrice()+","
						+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","+AMUMStockConstant.FIVE_STAR+","+bean.getApi()+"\n");
				System.out.println(bean.getName()+","+"^"+bean.getCurrentPrice()+","+bean.getCurrentDayVolume()+","+bean.getVolumeRating()+","+bean.getEps()+","+AMUMStockConstant.FIVE_STAR);
				
			}else if(epsValue > 75 && epsValue < 100){
				bwObj.write(bean.getName()+","+bean.getCurrentPrice()+","
						+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","+AMUMStockConstant.FOUR_STAR+","+bean.getApi()+"\n");
			}else if(epsValue > 50 && epsValue < 75){
				bwObj.write(bean.getName()+","+bean.getCurrentPrice()+","
						+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","+AMUMStockConstant.THREE_STAR+","+bean.getApi()+"\n");
			}else if(epsValue > 25 && epsValue < 50){
				bwObj.write(bean.getName()+","+bean.getCurrentPrice()+","
						+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","+AMUMStockConstant.TWO_STAR+","+bean.getApi()+"\n");
			}else if(epsValue > 0 && epsValue < 25){
				bwObj.write(bean.getName()+","+bean.getCurrentPrice()+","
						+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
						+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
						+bean.getVolumeRating()+","+bean.getEps()+","+AMUMStockConstant.ONE_STAR+","+bean.getApi()+"\n");
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
