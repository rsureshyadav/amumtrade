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

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.factory.FinancialEPSAnalysisRunner;
import com.amumtrade.util.StockUtil;

public class EPSOnConGainersHandler {
	String csvFileName = "config/amumEPSConcurrentGainersAnalyzer.csv";
	List<ConcurrentGainersBean> concurrentGainersWithRatingList ;
	BufferedReader br;

	public void execute() throws IOException{
		concurrentGainersWithRatingList = new ArrayList<ConcurrentGainersBean>();
		concurrentGainersWithRatingList = convertConGainersCsvToBean();
		List<ConcurrentGainersBean> financeRatingList  = convertUrlToFinancialUrl(concurrentGainersWithRatingList);
		runFinanceRatingUrl(financeRatingList);
		StockUtil.initiateEmail(csvFileName);
	}
	private void runFinanceRatingUrl(List<ConcurrentGainersBean> financeUrlList) throws IOException {
		List<String> urlList = null;
		Map<String,ConcurrentGainersBean> financialAnalyzerMap = new HashMap<String,ConcurrentGainersBean>();
		FileWriter fwo = new FileWriter( csvFileName, false );
		BufferedWriter bwObj = null;
		try {
			System.out.println(">>"+financeUrlList.size());
			urlList = new ArrayList<String>();
			for(ConcurrentGainersBean financeAnalyzerBean : financeUrlList){
				urlList.add(financeAnalyzerBean.getFinanceApi());
				financialAnalyzerMap.put(financeAnalyzerBean.getFinanceApi(), financeAnalyzerBean);
			}
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,CurrentPrice,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,EPS,EPSRating,API"+"\n");
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){//for (int i = 0; i < 10; i++) {
				// System.out.println(i);
		            Runnable worker = new FinancialEPSAnalysisRunner(new URL(httpUrl),financialAnalyzerMap,bwObj,"" + i);
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
	private List<ConcurrentGainersBean> convertUrlToFinancialUrl(List<ConcurrentGainersBean> concurrentList) {
		String apiUrl = null;
		String urlSplitBy = "/";
		ConcurrentGainersBean bean = null; 
		List<ConcurrentGainersBean>financeUrlList= new ArrayList<ConcurrentGainersBean>();

		try {
			for(ConcurrentGainersBean record: concurrentList){
					bean = new ConcurrentGainersBean();
					apiUrl = record.getApi();
					apiUrl = apiUrl.replace("http://", "");
					String[] apiUrlSplit = apiUrl.split(urlSplitBy);
					apiUrl = "http://"+apiUrlSplit[0]+"/"+"financials"+"/"+apiUrlSplit[4]+"/"+"ratios"+"/"+apiUrlSplit[5]+"#"+apiUrlSplit[5];
					//System.out.println(apiUrl);
					bean.setName(record.getName());
					bean.setApi(record.getApi());
					bean.setFinanceApi(apiUrl);
					bean.setCurrentPrice(record.getCurrentPrice());
					bean.setCurrentDayVolume(record.getCurrentDayVolume());
					bean.setFiveDayAvgVolume(record.getFiveDayAvgVolume());
					bean.setTenDayAvgVolume(record.getTenDayAvgVolume());
					bean.setThirtyDayAvgVolume(record.getThirtyDayAvgVolume());
					bean.setVolumeRating(record.getVolumeRating());
					
					financeUrlList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return financeUrlList;
	}
	private List<ConcurrentGainersBean> convertConGainersCsvToBean() throws IOException {
		List<ConcurrentGainersBean>  gainerBeanList= new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean gainerBean = null;
		String csvFile = "config/amumConcurrentGainersVolumeBasedList.csv";
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(skipFirstLineHeader!=0){ 
					gainerBean = new ConcurrentGainersBean();
					String[] conGainers = line.split(cvsSplitBy);
					gainerBean.setName(conGainers[0]);
					gainerBean.setCurrentPrice(conGainers[1]);
					gainerBean.setCurrentDayVolume(conGainers[2]);
					gainerBean.setFiveDayAvgVolume(conGainers[3]);
					gainerBean.setTenDayAvgVolume(conGainers[4]);
					gainerBean.setThirtyDayAvgVolume(conGainers[5]);
					gainerBean.setVolumeRating(conGainers[6]);
					gainerBean.setApi(conGainers[7]);
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
