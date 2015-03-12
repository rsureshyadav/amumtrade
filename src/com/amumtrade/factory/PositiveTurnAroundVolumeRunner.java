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

public class PositiveTurnAroundVolumeRunner implements Runnable {
	private String command;
	private URL urlConn;
	private Map<String,ConcurrentGainersBean> postiveTurnAroundMap;
	private BufferedWriter bwObj;
	public PositiveTurnAroundVolumeRunner(URL httpUrl,Map<String,ConcurrentGainersBean> positiveMap,BufferedWriter bufferWriter,String s){
		this.urlConn=httpUrl;
		this.postiveTurnAroundMap = positiveMap;
		this.bwObj = bufferWriter;
		this.command=s;
	}
	public void run() {
	        try {
				processCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.out.println(Thread.currentThread().getName()+" End.");
		
	}
	private void processCommand() throws IOException {
        try {
            Thread.sleep(5000);
            Reader reader = null;
            BufferedReader bufferReader = null;
            try {
		          bufferReader = new BufferedReader(new InputStreamReader(urlConn.openStream()));
		          findVolumeInReader(bufferReader,urlConn.toString());
		        }
		        finally {
		          if (reader != null) 
		              reader.close();
		        }   
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	private void findVolumeInReader(BufferedReader bufferReader, String url) throws IOException {
		String inputLine = null;
		String currentDayVolume = null;
		String fiveDayAvgVolume = null;
		String tenDayAvgVolume = null;
		String thirtyDayAvgVolume = null;
		
		boolean toolTip3 = false;
		int toolTip3Count =0;
		
		Set<String> urlSet = null;
		try {
			urlSet = new HashSet<String>();
			 while ((inputLine = bufferReader.readLine()) != null)
		        {
				 if(inputLine.contains("<span id=\"tt03\">VOLUME")){
					 currentDayVolume = inputLine.trim();
					 currentDayVolume = currentDayVolume.substring(currentDayVolume.indexOf("<strong>"),currentDayVolume.lastIndexOf("</strong>"));
					 currentDayVolume = currentDayVolume.replace("<strong>", "");
					 currentDayVolume = currentDayVolume.replace(",", "");
				 }else if(inputLine.contains("<div class=\"tooltip3\">") || toolTip3){
					 toolTip3 = true;
					 if(toolTip3Count >22){
						 toolTip3=false; 
					 }else if(toolTip3Count ==6){
						 fiveDayAvgVolume = inputLine.trim();
						 fiveDayAvgVolume = fiveDayAvgVolume.substring(fiveDayAvgVolume.indexOf("<strong>"),fiveDayAvgVolume.lastIndexOf("</strong>"));
						 fiveDayAvgVolume = fiveDayAvgVolume.replace("<strong>", "");
						 fiveDayAvgVolume = fiveDayAvgVolume.replace(",", "");
					 }else if(toolTip3Count ==14){
						 tenDayAvgVolume = inputLine.trim();
						 tenDayAvgVolume = tenDayAvgVolume.substring(tenDayAvgVolume.indexOf("<strong>"),tenDayAvgVolume.lastIndexOf("</strong>"));
						 tenDayAvgVolume = tenDayAvgVolume.replace("<strong>", "");
						 tenDayAvgVolume = tenDayAvgVolume.replace(",", "");
					 }else if(toolTip3Count ==22){
						 thirtyDayAvgVolume = inputLine.trim();
						 thirtyDayAvgVolume = thirtyDayAvgVolume.substring(thirtyDayAvgVolume.indexOf("<strong>"),thirtyDayAvgVolume.lastIndexOf("</strong>"));
						 thirtyDayAvgVolume = thirtyDayAvgVolume.replace("<strong>", "");
						 thirtyDayAvgVolume = thirtyDayAvgVolume.replace(",", "");
					 }
					 toolTip3Count++;
					 if(fiveDayAvgVolume != null && tenDayAvgVolume!=null 
							 && thirtyDayAvgVolume!=null
							 && !urlSet.contains(url)
							 && currentDayVolume !=null
							 && fiveDayAvgVolume != null
							 && tenDayAvgVolume != null
							 && thirtyDayAvgVolume != null
							 && !currentDayVolume.isEmpty()
							 && !fiveDayAvgVolume.isEmpty()
							 && !tenDayAvgVolume.isEmpty()
							 && !thirtyDayAvgVolume.isEmpty()){
					 
						 ConcurrentGainersBean bean = postiveTurnAroundMap.get(url);
						 bean.setCurrentDayVolume(currentDayVolume);
						 bean.setFiveDayAvgVolume(fiveDayAvgVolume);
						 bean.setTenDayAvgVolume(tenDayAvgVolume);
						 bean.setThirtyDayAvgVolume(thirtyDayAvgVolume);
						 
						 fiveDayAvgVolume = null;
						 tenDayAvgVolume = null;
						 thirtyDayAvgVolume = null; 
						 urlSet.add(url);
						 writeVolumeToCSVFile(bean);
					 }
				 }
		        
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bufferReader!=null){
				bufferReader.close();	
			}
		}
		
	}

private void writeVolumeToCSVFile(ConcurrentGainersBean bean) {
		try {
			int dayVolume = Integer.parseInt(bean.getCurrentDayVolume());
			int fiveDayAvgVolume = Integer.parseInt(bean.getFiveDayAvgVolume());
			int tenDayAvgVolume = Integer.parseInt(bean.getTenDayAvgVolume());
			int thirtyDayAvgVolume = Integer.parseInt(bean.getThirtyDayAvgVolume());
			if(dayVolume >= fiveDayAvgVolume && dayVolume >= tenDayAvgVolume && dayVolume >= thirtyDayAvgVolume 
					&& fiveDayAvgVolume > tenDayAvgVolume && fiveDayAvgVolume >thirtyDayAvgVolume){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
						+bean.getThirtyDayAvgVolume()+","+AMUMStockConstant.FIVE_STAR+","+bean.getApi()+"\n");
				System.out.println(bean.getCompanyName()+","+AMUMStockConstant.FIVE_STAR);
			}else if(dayVolume >= fiveDayAvgVolume && dayVolume >= tenDayAvgVolume && dayVolume >= thirtyDayAvgVolume){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
						+bean.getThirtyDayAvgVolume()+","+AMUMStockConstant.FOUR_STAR+","+bean.getApi()+"\n");
			}else if(fiveDayAvgVolume > tenDayAvgVolume && fiveDayAvgVolume >thirtyDayAvgVolume){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
						+bean.getThirtyDayAvgVolume()+","+AMUMStockConstant.THREE_STAR+","+bean.getApi()+"\n");
			}else if(fiveDayAvgVolume <= tenDayAvgVolume && tenDayAvgVolume>=thirtyDayAvgVolume){
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
						+bean.getThirtyDayAvgVolume()+","+AMUMStockConstant.TWO_STAR+","+bean.getApi()+"\n");
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
