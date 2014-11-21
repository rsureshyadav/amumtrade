package com.amumtrade.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.factory.ConcurrentGainersVolumeSplitRunner;
import com.amumtrade.marketstat.LastEigthDayConcurrentGainers;
import com.amumtrade.marketstat.LastFiveDayConcurrentGainers;
import com.amumtrade.marketstat.LastThreeDayConcurrentGainers;
import com.amumtrade.util.StockUtil;

public class MasterCombineConGainersVolumeAnalyzerHandler {
	
String csvFileName ="config/amumMasterConcurrentGainersList.csv";

	public void execute(long startTime) throws IOException{
		LastThreeDayConcurrentGainers tcg = new LastThreeDayConcurrentGainers();
		List<ConcurrentGainersBean> threeDayConGainersList = tcg.execute();
		
		LastFiveDayConcurrentGainers fcg = new LastFiveDayConcurrentGainers();
		List<ConcurrentGainersBean> fiveDayConGainersList = fcg.execute();
		
		LastEigthDayConcurrentGainers ecg = new LastEigthDayConcurrentGainers();
		List<ConcurrentGainersBean> eigthDayConGainersList = ecg.execute();
		
		List<ConcurrentGainersBean> finalConGainersList = getCommonConcurrentGainers(threeDayConGainersList,fiveDayConGainersList,eigthDayConGainersList);
		runVolumeSplitter(finalConGainersList);
		StockUtil.initiateEmail(csvFileName,startTime);
	}

	private List<ConcurrentGainersBean> getCommonConcurrentGainers(
			List<ConcurrentGainersBean> threeDayConGainersList,
			List<ConcurrentGainersBean> fiveDayConGainersList,
			List<ConcurrentGainersBean> eigthDayConGainersList) {
		Set<String> threeDayConGainersAPI = null;
		Set<String> fiveDayConGainersAPI  =  null;
		Set<String> eigthDayConGainersAPI = null;
		Map<String, ConcurrentGainersBean> gainersBeanMap;
		List<ConcurrentGainersBean> conGainersList = null;
		try {
			if(threeDayConGainersList != null && fiveDayConGainersList != null && eigthDayConGainersList !=null){
				threeDayConGainersAPI = new HashSet<String>();
				fiveDayConGainersAPI  =  new HashSet<String>();
				eigthDayConGainersAPI = new HashSet<String>();
				gainersBeanMap = new HashMap<String, ConcurrentGainersBean>();
				for(ConcurrentGainersBean bean : threeDayConGainersList){
					threeDayConGainersAPI.add(bean.getApi());
					gainersBeanMap.put(bean.getApi(), bean);
				}
				for(ConcurrentGainersBean bean : fiveDayConGainersList){
					fiveDayConGainersAPI.add(bean.getApi());
					//gainersBeanMap.put(bean.getApi(), bean);
				}
				for(ConcurrentGainersBean bean : eigthDayConGainersList){
					eigthDayConGainersAPI.add(bean.getApi());
					//gainersBeanMap.put(bean.getApi(), bean);
				}
				threeDayConGainersAPI.retainAll(fiveDayConGainersAPI);
				threeDayConGainersAPI.retainAll(eigthDayConGainersAPI);
				
				if(threeDayConGainersAPI != null && !threeDayConGainersAPI.isEmpty()){
					conGainersList = new ArrayList<ConcurrentGainersBean>();
					for(String api : threeDayConGainersAPI){
						conGainersList.add(gainersBeanMap.get(api));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conGainersList;
	}

	private void runVolumeSplitter(List<ConcurrentGainersBean> concurrentGainersList)throws IOException {
		List<String> urlList = null;
		Map<String,ConcurrentGainersBean> concurrentGainerMap = new HashMap<String,ConcurrentGainersBean>();
		FileWriter fwo = new FileWriter( csvFileName, false );
		BufferedWriter bwObj = null;
		try {
			System.out.println(">>"+concurrentGainersList.size());
			urlList = new ArrayList<String>();
			for(ConcurrentGainersBean gainerBean : concurrentGainersList){
				urlList.add(gainerBean.getApi());
				//System.out.println(gainerBean.getName()+">>"+gainerBean.getCurrentPrice());
				concurrentGainerMap.put(gainerBean.getApi(), gainerBean);
			}
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,CurrentPrice,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,Rating,Api"+"\n");
		
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){//for (int i = 0; i < 10; i++) {
				// System.out.println(i);
		            Runnable worker = new ConcurrentGainersVolumeSplitRunner(new URL(httpUrl),concurrentGainerMap,bwObj,"" + i);
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
}
