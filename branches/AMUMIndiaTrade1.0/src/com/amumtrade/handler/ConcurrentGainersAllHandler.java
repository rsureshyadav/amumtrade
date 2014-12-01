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
import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.email.CsvToEmailBody;
import com.amumtrade.factory.ConcurrentGainersVolumeRunner;
import com.amumtrade.marketstat.LastEigthDayConcurrentGainers;
import com.amumtrade.marketstat.LastFiveDayConcurrentGainers;
import com.amumtrade.marketstat.LastThreeDayConcurrentGainers;
import com.amumtrade.util.StockUtil;

public class ConcurrentGainersAllHandler {
//String csvInputFileName ="config/amumConcurrentGainersVolumeBasedList.csv";
//String csvOutputName ="config/output/AMUM_ALL_ConcurrentGainers_Analyzer.csv";
long sTime;
	public void execute(long startTime) throws IOException{
		this.sTime = startTime;
		LastThreeDayConcurrentGainers tcg = new LastThreeDayConcurrentGainers();
		List<ConcurrentGainersBean> threeDayConGainersList = tcg.execute();
		
		LastFiveDayConcurrentGainers fcg = new LastFiveDayConcurrentGainers();
		List<ConcurrentGainersBean> fiveDayConGainersList = fcg.execute();
		
		LastEigthDayConcurrentGainers ecg = new LastEigthDayConcurrentGainers();
		List<ConcurrentGainersBean> eigthDayConGainersList = ecg.execute();
		
		List<ConcurrentGainersBean> finalConGainersList = getCommonConcurrentGainers(threeDayConGainersList,fiveDayConGainersList,eigthDayConGainersList);
		runVolumeSplitter(finalConGainersList);
		
		/*CsvToEmailBody emailBody = new CsvToEmailBody();
		String htmlText= emailBody.execute();*/
		String htmlText="dummy";
		StockUtil.initiateEmail(FileNameConstant.ALL_CONCURRENT_GAINER,startTime,htmlText);
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
				}
				for(ConcurrentGainersBean bean : eigthDayConGainersList){
					eigthDayConGainersAPI.add(bean.getApi());
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
		FileWriter fwo = new FileWriter( FileNameConstant.CURRENT_CONCURRENT_VOLUME_GAINERS, false );
		BufferedWriter bwObjVolume = null;

		try {
			System.out.println("TOTAL CONCURRENT GAINERS VOLUME SIZE>>"+concurrentGainersList.size());
			urlList = new ArrayList<String>();
			for(ConcurrentGainersBean gainerBean : concurrentGainersList){
				urlList.add(gainerBean.getApi());
				concurrentGainerMap.put(gainerBean.getApi(), gainerBean);
			}
			bwObjVolume = new BufferedWriter( fwo );  
			bwObjVolume.write("CompanyName,CurrentPrice,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,PostiveBreakOut,Api"+"\n");
			
			Set<String> keyNameSet = StockUtil.getAPIKeyNameList(urlList);
			boolean postiveBreakOutFlag = false;
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){
				 String httpUrlApi = StockUtil.getUrlToKeyAPI(httpUrl);
				 if(keyNameSet.contains(httpUrlApi)){
					 postiveBreakOutFlag = true; 
				 }else{
					 postiveBreakOutFlag = false;
				 }
		            Runnable worker = new ConcurrentGainersVolumeRunner(new URL(httpUrl),concurrentGainerMap,bwObjVolume,postiveBreakOutFlag,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        
		        if(bwObjVolume != null){
					bwObjVolume.close();
				}
		        System.out.println("FINISHED ALL CONCURRENT GAINERS VOLUME THREAD EXECUTION...");
		        ConcurrentGainersEPSHandler epsHandler = new ConcurrentGainersEPSHandler();
		        epsHandler.executeGainers(sTime,FileNameConstant.ALL_CONCURRENT_GAINER);
		        
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObjVolume != null){
				bwObjVolume.close();
			}
		}
	}
}
