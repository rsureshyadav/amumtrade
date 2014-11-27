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
import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.factory.VolumeSplitRunner;

public class TopGainersVolumeHandler {
	private BufferedReader br;
	List<TopGainerBean> topGainerWithVolume ;
	
	public void execute() throws IOException{
		List<TopGainerBean> gainersBeanList = getTopGainersCsvToBean();
		runVolumeSplitter(gainersBeanList);
	}
	
	private void runVolumeSplitter(List<TopGainerBean> gainersBeanList) throws IOException{
		List<String> urlList = null;
		Map<String,TopGainerBean> topGainerMap = new HashMap<String,TopGainerBean>();
		FileWriter fwo = new FileWriter( FileNameConstant.VOLUME_TOP_GAINERS, false );
		BufferedWriter bwObj = null;
		
		try {
			System.out.println("TOP GAINERS VOLUME EXECUTION SIZE>>"+gainersBeanList.size());
			urlList = new ArrayList<String>();
			for(TopGainerBean gainerBean : gainersBeanList){
				String httpURL = AMUMStockConstant.STOCK_URL+gainerBean.getApi().trim();
				if(!httpURL.contains("///")){
					urlList.add(httpURL);
					topGainerMap.put(httpURL, gainerBean);
				}
			}
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("Company Name,High,Low,Last Price,Prv Close,Change,Day Volume,FiveDay AvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,Rating,Api"+"\n");
			
			
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){
				  Runnable worker = new VolumeSplitRunner(new URL(httpUrl),topGainerMap,bwObj,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("FINISHED TOP GAINERS VOLUME THREADS EXECUTION..");
			
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
		String csvFile =FileNameConstant.TOP_GAINERS;
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
