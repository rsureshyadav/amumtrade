package com.amumtrade.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.factory.ConcurrentGainersVolumeRunner;
import com.amumtrade.marketstat.LastThreeDayConcurrentGainers;
import com.amumtrade.util.StockUtil;

public class CurrentConcurrentGainersVolumeHandler {
	

	public void execute() throws IOException{
		LastThreeDayConcurrentGainers cg = new LastThreeDayConcurrentGainers();
		List<ConcurrentGainersBean> concurrentGainersList = cg.execute();
		writeVolumeToFile(concurrentGainersList, FileNameConstant.CURRENT_CONCURRENT_VOLUME_GAINERS);
	}

	private void writeVolumeToFile(List<ConcurrentGainersBean> concurrentGainersList, String csvFileName)throws IOException {
		List<String> urlList = null;
		Map<String,ConcurrentGainersBean> concurrentGainerMap = new HashMap<String,ConcurrentGainersBean>();
		FileWriter fwo = new FileWriter( csvFileName, false );
		BufferedWriter bwObj = null;
		try {
			System.out.println("TOTAL CURRENT_CONCURRENT_VOLUME_GAINERS >>"+concurrentGainersList.size());
			urlList = new ArrayList<String>();
			for(ConcurrentGainersBean gainerBean : concurrentGainersList){
				urlList.add(gainerBean.getApi());
				//System.out.println(gainerBean.getName()+">>"+gainerBean.getCurrentPrice());
				concurrentGainerMap.put(gainerBean.getApi(), gainerBean);
			}
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,CurrentPrice,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,Rating,PostiveBreakOut,Api"+"\n");
			Set<String> keyNameSet = StockUtil.getAPIKeyNameList(urlList);
			boolean postiveBreakOutFlag = false;
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrlApi : urlList){//for (int i = 0; i < 10; i++) {
					 if(keyNameSet.contains(httpUrlApi)){
						 postiveBreakOutFlag = true; 
					 }else{
						 postiveBreakOutFlag = false;
					 }
		            Runnable worker = new ConcurrentGainersVolumeRunner(new URL(httpUrlApi),concurrentGainerMap,bwObj,postiveBreakOutFlag,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("Finished all threads Execution of CURRENT_CONCURRENT_VOLUME_GAINERS");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
				bwObj.close();
			}
		}
	}
}
