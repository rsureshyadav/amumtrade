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
import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.email.CsvToEmailBody;
import com.amumtrade.factory.EPSRunner;
import com.amumtrade.util.StockUtil;

public class TopGainersEPSHandler {
	String csvFileName=FileNameConstant.ALL_TOP_GAINER;
	List<ConcurrentGainersBean> topGainersWithRatingList ;
	BufferedReader br;

	public void execute(long startTime) throws IOException{
		topGainersWithRatingList = new ArrayList<ConcurrentGainersBean>();
		topGainersWithRatingList = convertTopGainersVolumeCsvToBean();
		List<ConcurrentGainersBean> gainerRatingList  = convertUrlToFinancialUrl(topGainersWithRatingList);
		runFinanceRatingUrl(gainerRatingList);

		CsvToEmailBody emailBody = new CsvToEmailBody();
		String htmlText= emailBody.execute(csvFileName);
		StockUtil.initiateEmail(csvFileName,startTime,htmlText);
	}
	private void runFinanceRatingUrl(List<ConcurrentGainersBean> gainerUrlList) throws IOException {
		List<String> urlList = null;
		Map<String,ConcurrentGainersBean> financialAnalyzerMap = new HashMap<String,ConcurrentGainersBean>();
		FileWriter fwo = new FileWriter( csvFileName, false );
		BufferedWriter bwObj = null;
		try {
			System.out.println("TOTAL CURRENT TOP GAINERS EPS SIZE>>"+gainerUrlList.size());
			urlList = new ArrayList<String>();
			for(ConcurrentGainersBean topGainerBean : gainerUrlList){
				urlList.add(topGainerBean.getFinanceApi());
				financialAnalyzerMap.put(topGainerBean.getFinanceApi(), topGainerBean);
			}
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,CurrentPrice,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,EPS,EPSRating,StandaloneProfit,Recommendation,News,PositiveBreakOut,API"+"\n");
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){//for (int i = 0; i < 10; i++) {
				// System.out.println(i);
		            Runnable worker = new EPSRunner(new URL(httpUrl),financialAnalyzerMap,bwObj,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("FINISHED CURRENT TOP GAINERS EPS SIZE THREADS EXECUTIONS...");
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
					bean.setPositiveBreakout(record.getPositiveBreakout());

					financeUrlList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return financeUrlList;
	}
	private List<ConcurrentGainersBean> convertTopGainersVolumeCsvToBean() throws IOException {
		List<ConcurrentGainersBean>  gainerBeanList= new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean gainerBean = null;
		String csvFile = FileNameConstant.VOLUME_TOP_GAINERS;
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(skipFirstLineHeader!=0){ 
					gainerBean = new ConcurrentGainersBean();
					String[] topGainers = line.split(cvsSplitBy);
					gainerBean.setName(topGainers[0]);
					gainerBean.setCurrentPrice(topGainers[3]);
					gainerBean.setCurrentDayVolume(topGainers[6]);
					gainerBean.setFiveDayAvgVolume(topGainers[7]);
					gainerBean.setTenDayAvgVolume(topGainers[8]);
					gainerBean.setThirtyDayAvgVolume(topGainers[9]);
					gainerBean.setVolumeRating(topGainers[10]);
					gainerBean.setPositiveBreakout(topGainers[11]);
					gainerBean.setApi(topGainers[12]);
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
