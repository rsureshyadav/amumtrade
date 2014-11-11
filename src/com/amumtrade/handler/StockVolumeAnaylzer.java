package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amumtrade.bean.TopGainerBean;

public class StockVolumeAnaylzer {
	public  String stockURL = "http://www.moneycontrol.com";

	private BufferedReader br;
	private BufferedReader in;

	public void execute() throws IOException{
		List<TopGainerBean> gainersBeanList = getTopGainersCsvToBean();
		List<TopGainerBean> topGainerWithVolume = getStockVolume(gainersBeanList);
		writeToCSVFile(topGainerWithVolume);
	}
	private void writeToCSVFile(List<TopGainerBean> topGainerWithVolume) throws IOException {
		Set<String> urlSet = new HashSet<String>();
		FileWriter fwo = new FileWriter( "config/amumTopGainerVolumeBasedList.csv", false );
		BufferedWriter bwObj = null;
		try {
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("Company Name,High,Low,Last Price,Prv Close,Change,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,Rating"+"\n");
			for(TopGainerBean bean : topGainerWithVolume){
				if(!urlSet.contains(bean.getApi())){
					int dayVolume = Integer.parseInt(bean.getCurrentDayVolume());
					int fiveDayAvgVolume = Integer.parseInt(bean.getFiveDayAvgVolume());
					int tenDayAvgVolume = Integer.parseInt(bean.getTenDayAvgVolume());
					int thirtyDayAvgVolume = Integer.parseInt(bean.getThirtyDayAvgVolume());
					if(dayVolume >= fiveDayAvgVolume && dayVolume >= tenDayAvgVolume && dayVolume >= thirtyDayAvgVolume 
							&& fiveDayAvgVolume > tenDayAvgVolume && fiveDayAvgVolume >thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"5 Star"+"\n");
						
						System.out.println(bean.getCompanyName()+","+"^"+bean.getChange()+","+bean.getCurrentDayVolume()+"5 Star");
					}else if(dayVolume >= fiveDayAvgVolume && dayVolume >= tenDayAvgVolume && dayVolume >= thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"4 Star"+"\n");
					}else if(fiveDayAvgVolume > tenDayAvgVolume && fiveDayAvgVolume >thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"3 Star"+"\n");
					}else if(fiveDayAvgVolume <= tenDayAvgVolume && tenDayAvgVolume>=thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"2 Star"+"\n");
					}
						
						/*System.out.println(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"1 Star");*/
					urlSet.add(bean.getApi());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null){
				bwObj.close();
			}
			
		}
		
	}
	private List<TopGainerBean> getTopGainersCsvToBean() throws IOException {
		List<TopGainerBean>  gainerBeanList= new ArrayList<TopGainerBean>();
		TopGainerBean gainerBean = null;
		String csvFile = "config/amumTopGainerList.csv";
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(skipFirstLineHeader!=0){ 
					//	if (skipFirstLineHeader==3){ //For Testing with single entry 
					//if (skipFirstLineHeader!=0 && skipFirstLineHeader<=2){ //For Testing with first 10 entrys 
					gainerBean = new TopGainerBean();
					String[] topGainers = line.split(cvsSplitBy);
					gainerBean.setCompanyName(topGainers[0]);
					gainerBean.setHigh(topGainers[1]);
					gainerBean.setLow(topGainers[2]);
					gainerBean.setLastPrice(topGainers[3]);
					gainerBean.setPrvClose(topGainers[4]);
					gainerBean.setChange(topGainers[5]);
					gainerBean.setPercentGain(topGainers[6]);
					gainerBean.setApi(topGainers[7]);
					gainerBeanList.add(gainerBean);
				}
				skipFirstLineHeader++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br !=null){
				br.close();
			}
		}
		return gainerBeanList;
	}
	private List<TopGainerBean> getStockVolume(List<TopGainerBean> gainersBeanList) throws IOException{
		String currentDayVolume = null;
		String fiveDayAvgVolume = null;
		String tenDayAvgVolume = null;
		String thirtyDayAvgVolume = null;
		
		 String inputLine = null;
		 List<String> stockURLList = null; 
		 boolean toolTip3 = false;
		 int toolTip3Count =0;
		 int count = 1;
		 Map<String,TopGainerBean> topGainerMap = new HashMap<String,TopGainerBean>();
		 List<TopGainerBean> stockGainOnVolume = new ArrayList<TopGainerBean>();
		 TopGainerBean gainerBean ;
		try {
			stockURLList = new ArrayList<String>();
			for(TopGainerBean bean : gainersBeanList){
			String httpURL = stockURL+bean.getApi().trim();
			stockURLList.add(httpURL);
			topGainerMap.put(httpURL, bean);
			}
			for(String url : stockURLList){
				 gainerBean = new TopGainerBean();

				URL website  = new URL(url);
				in= new BufferedReader(new InputStreamReader(website.openStream()));
				 while ((inputLine = in.readLine()) != null)
			        {
					 if(inputLine.contains("<span id=\"tt03\">VOLUME")){
						 currentDayVolume = inputLine.trim();
						 currentDayVolume = currentDayVolume.substring(currentDayVolume.indexOf("<strong>"),currentDayVolume.lastIndexOf("</strong>"));
						 currentDayVolume = currentDayVolume.replace("<strong>", "");
						 currentDayVolume = currentDayVolume.replace(",", "");
			/*			 System.out.println("volume>>"+volume);
						 System.out.println("toolTip3>>"+toolTip3);
						 System.out.println("toolTip3Count>>"+toolTip3Count);*/
					 }else if(inputLine.contains("<div class=\"tooltip3\">") || toolTip3){
						 toolTip3 = true;
						 if(toolTip3Count >22){
							 toolTip3=false; 
						 }else if(toolTip3Count ==6){
							 fiveDayAvgVolume = inputLine.trim();
							 fiveDayAvgVolume = fiveDayAvgVolume.substring(fiveDayAvgVolume.indexOf("<strong>"),fiveDayAvgVolume.lastIndexOf("</strong>"));
							 fiveDayAvgVolume = fiveDayAvgVolume.replace("<strong>", "");
							 fiveDayAvgVolume = fiveDayAvgVolume.replace(",", "");
					//		 System.out.println("5 Day avg Volume>>"+fiveDayAvgVolume);
						 }else if(toolTip3Count ==14){
							 tenDayAvgVolume = inputLine.trim();
							 tenDayAvgVolume = tenDayAvgVolume.substring(tenDayAvgVolume.indexOf("<strong>"),tenDayAvgVolume.lastIndexOf("</strong>"));
							 tenDayAvgVolume = tenDayAvgVolume.replace("<strong>", "");
							 tenDayAvgVolume = tenDayAvgVolume.replace(",", "");
						//	 System.out.println("10 Day avg Volume>>"+tenDayAvgVolume); 
						 }else if(toolTip3Count ==22){
							 thirtyDayAvgVolume = inputLine.trim();
							 thirtyDayAvgVolume = thirtyDayAvgVolume.substring(thirtyDayAvgVolume.indexOf("<strong>"),thirtyDayAvgVolume.lastIndexOf("</strong>"));
							 thirtyDayAvgVolume = thirtyDayAvgVolume.replace("<strong>", "");
							 thirtyDayAvgVolume = thirtyDayAvgVolume.replace(",", "");
							// System.out.println("30 Day avg Volume>>"+thirtyDayAvgVolume); 
						 }
						 toolTip3Count++;

						 if(fiveDayAvgVolume != null && tenDayAvgVolume!=null && thirtyDayAvgVolume!=null  ){
							 gainerBean.setCurrentDayVolume(currentDayVolume);
							 gainerBean.setFiveDayAvgVolume(fiveDayAvgVolume);
							 gainerBean.setTenDayAvgVolume(tenDayAvgVolume);
							 gainerBean.setThirtyDayAvgVolume(thirtyDayAvgVolume);
							 TopGainerBean bean = topGainerMap.get(url);
							 
							 gainerBean.setCompanyName(bean.getCompanyName());
							 gainerBean.setHigh(bean.getHigh());
							 gainerBean.setLow(bean.getLow());
							 gainerBean.setLastPrice(bean.getLastPrice());
							 gainerBean.setPrvClose(bean.getPrvClose());
							 gainerBean.setChange(bean.getChange());
							 gainerBean.setPercentGain(bean.getPercentGain());
							 gainerBean.setApi(url);
							// System.out.println(">5Day>>"+gainerBean.getFiveDayAvgVolume()+">10Day>>"+gainerBean.getTenDayAvgVolume()+">30Day>>"+gainerBean.getThirtyDayAvgVolume());
							 stockGainOnVolume.add(gainerBean);
							 fiveDayAvgVolume = null;
							 tenDayAvgVolume = null;
							 thirtyDayAvgVolume = null; 
						 }
					 }
			        }
				 toolTip3 = false;
				 toolTip3Count=0;
				 System.out.println(stockURLList.size()-count);
				 count++;
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				in.close();
			}
		}
		return stockGainOnVolume;
	}
}
