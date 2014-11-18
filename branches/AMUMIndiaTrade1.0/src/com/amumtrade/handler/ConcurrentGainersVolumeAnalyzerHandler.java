package com.amumtrade.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.factory.ConcurrentGainersVolumeSplitRunner;
import com.amumtrade.marketstat.LastThreeDayConcurrentGainers;

public class ConcurrentGainersVolumeAnalyzerHandler {
	

	public void execute() throws IOException{
		LastThreeDayConcurrentGainers cg = new LastThreeDayConcurrentGainers();
		List<ConcurrentGainersBean> concurrentGainersList = cg.execute();
		runVolumeSplitter(concurrentGainersList);
	}

	private void runVolumeSplitter(List<ConcurrentGainersBean> concurrentGainersList)throws IOException {
		List<String> urlList = null;
		Map<String,ConcurrentGainersBean> concurrentGainerMap = new HashMap<String,ConcurrentGainersBean>();
		FileWriter fwo = new FileWriter( "config/amumConcurrentGainersVolumeBasedList.csv", false );
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
