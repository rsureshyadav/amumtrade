package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.amumtrade.bean.TopGainerBean;

public class StockVolumeAnaylzer {
	public  String stockURL = "http://www.moneycontrol.com";

	private BufferedReader br;
	private BufferedReader in;

	public void execute() throws IOException{
		List<TopGainerBean> gainersBeanList = getTopGainersCsvToBean();
		getStockVolume(gainersBeanList);
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
				//if(skipFirstLineHeader!=0){ 
				if (skipFirstLineHeader==1){
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
			// TODO: handle exception
		}finally{
			if(br !=null){
				br.close();
			}
		}
		return gainerBeanList;
	}
	private void getStockVolume(List<TopGainerBean> gainersBeanList) throws IOException{
		String volume = null;
		String fiveDayAvgVolume = null;
		String tenDayAvgVolume = null;
		String thirtyDayAvgVolume = null;

		 String inputLine = null;
		 List<String> stockURLList = null; 
		 boolean toolTip3 = false;
		 int toolTip3Count =0;
		try {
			stockURLList = new ArrayList<String>();
			for(TopGainerBean bean : gainersBeanList){
			stockURL = stockURL+bean.getApi().trim();
			stockURLList.add(stockURL);
			}
			for(String url : stockURLList){
				URL website  = new URL(url);
				in= new BufferedReader(new InputStreamReader(website.openStream()));
				 while ((inputLine = in.readLine()) != null)
			        {
					 if(inputLine.contains("<span id=\"tt03\">VOLUME")){
						 volume = inputLine.trim();
						 volume = volume.substring(volume.indexOf("<strong>"),volume.lastIndexOf("</strong>"));
						 volume = volume.replace("<strong>", "");
						 volume = volume.replace(",", "");
						 System.out.println("Volume>>"+volume);
					 }else if(inputLine.contains("<div class=\"tooltip3\">") || toolTip3){
						 toolTip3 = true;
						 if(toolTip3Count >22){
							 //System.out.println(inputLine.trim());
							 toolTip3=false; 
						 }else if(toolTip3Count ==6){
							 fiveDayAvgVolume = inputLine.trim();
							 fiveDayAvgVolume = fiveDayAvgVolume.substring(fiveDayAvgVolume.indexOf("<strong>"),fiveDayAvgVolume.lastIndexOf("</strong>"));
							 fiveDayAvgVolume = fiveDayAvgVolume.replace("<strong>", "");
							 fiveDayAvgVolume = fiveDayAvgVolume.replace(",", "");
							 System.out.println("5 Day avg Volume>>"+fiveDayAvgVolume);
						 }else if(toolTip3Count ==14){
							 tenDayAvgVolume = inputLine.trim();
							 tenDayAvgVolume = tenDayAvgVolume.substring(tenDayAvgVolume.indexOf("<strong>"),tenDayAvgVolume.lastIndexOf("</strong>"));
							 tenDayAvgVolume = tenDayAvgVolume.replace("<strong>", "");
							 tenDayAvgVolume = tenDayAvgVolume.replace(",", "");
							 System.out.println("10 Day avg Volume>>"+tenDayAvgVolume); 
						 }else if(toolTip3Count ==22){
							 thirtyDayAvgVolume = inputLine.trim();
							 thirtyDayAvgVolume = thirtyDayAvgVolume.substring(thirtyDayAvgVolume.indexOf("<strong>"),thirtyDayAvgVolume.lastIndexOf("</strong>"));
							 thirtyDayAvgVolume = thirtyDayAvgVolume.replace("<strong>", "");
							 thirtyDayAvgVolume = thirtyDayAvgVolume.replace(",", "");
							 System.out.println("30 Day avg Volume>>"+thirtyDayAvgVolume); 
						 }
						 toolTip3Count++;
					 }
			        }
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(in != null){
				in.close();
			}
		}
	}
}
