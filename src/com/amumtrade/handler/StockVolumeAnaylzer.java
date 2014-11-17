package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.TopGainerBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.factory.VolumeSplitRunner;

public class StockVolumeAnaylzer {
	public  String stockURL = "http://www.moneycontrol.com";
	private BufferedReader br;
	List<TopGainerBean> topGainerWithVolume ;
	
	public void execute() throws IOException{
		List<TopGainerBean> gainersBeanList = getTopGainersCsvToBean();
		runVolumeSplitter(gainersBeanList);
	}
	
	private void runVolumeSplitter(List<TopGainerBean> gainersBeanList) throws IOException{
		List<String> urlList = null;
		Map<String,TopGainerBean> topGainerMap = new HashMap<String,TopGainerBean>();
		FileWriter fwo = new FileWriter( "config/amumTopGainerVolumeBasedList.csv", false );
		BufferedWriter bwObj = null;
		
		try {
			System.out.println(">>"+gainersBeanList.size());
			urlList = new ArrayList<String>();
			for(TopGainerBean gainerBean : gainersBeanList){
				String httpURL = stockURL+gainerBean.getApi().trim();
				urlList.add(httpURL);
				topGainerMap.put(httpURL, gainerBean);
			}
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("Company Name,High,Low,Last Price,Prv Close,Change,Day Volume,FiveDay AvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,Rating,Api"+"\n");
			
			
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){//for (int i = 0; i < 10; i++) {
				// System.out.println(i);
		            Runnable worker = new VolumeSplitRunner(new URL(httpUrl),topGainerMap,bwObj,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("Finished all threads Execution");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
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
					//if (skipFirstLineHeader==3){ //For Testing with single entry 
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
}
