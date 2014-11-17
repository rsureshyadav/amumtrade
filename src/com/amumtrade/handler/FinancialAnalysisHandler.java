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
import com.amumtrade.factory.FinancialAnalysisRunner;

public class FinancialAnalysisHandler {
	List<ConcurrentGainersBean> concurrentGainersWithRatingList ;
	BufferedReader br;

	public void execute(String rating) throws IOException{
		concurrentGainersWithRatingList = convertConGainersCsvToBean();
		List<ConcurrentGainersBean> financeRatingList  = convertUrlToFinancialUrl(concurrentGainersWithRatingList,rating);
		runFinanceRatingUrl(financeRatingList);
	}
	private void runFinanceRatingUrl(List<ConcurrentGainersBean> financeUrlList) throws IOException {
		List<String> urlList = null;
		Map<String,ConcurrentGainersBean> financialAnalyzerMap = new HashMap<String,ConcurrentGainersBean>();
		FileWriter fwo = new FileWriter( "config/amumFinancialAnalyzerList.csv", false );
		BufferedWriter bwObj = null;
		try {
			System.out.println(">>"+financeUrlList.size());
			urlList = new ArrayList<String>();
			for(ConcurrentGainersBean financeAnalyzerBean : financeUrlList){
				urlList.add(financeAnalyzerBean.getFinanceApi());
				financialAnalyzerMap.put(financeAnalyzerBean.getApi(), financeAnalyzerBean);
			}
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,CurrentPrice,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,Rating,EPS"+"\n");
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){//for (int i = 0; i < 10; i++) {
				// System.out.println(i);
		            Runnable worker = new FinancialAnalysisRunner(new URL(httpUrl),financialAnalyzerMap,bwObj,"" + i);
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
	private List<ConcurrentGainersBean> convertUrlToFinancialUrl(List<ConcurrentGainersBean> concurrentList,String rating) {
		String apiUrl = null;
		String urlSplitBy = "/";
		ConcurrentGainersBean bean = null; 
		List<ConcurrentGainersBean>financeUrlList= new ArrayList<ConcurrentGainersBean>();

		try {
			for(ConcurrentGainersBean record: concurrentList){
				if(rating.equalsIgnoreCase(record.getRating())){
					bean = new ConcurrentGainersBean();
					apiUrl = record.getApi();
					apiUrl = apiUrl.replace("http://", "");
					String[] apiUrlSplit = apiUrl.split(urlSplitBy);
					apiUrl = "http://"+apiUrlSplit[0]+"/"+"financials"+"/"+apiUrlSplit[4]+"/"+"ratios"+"/"+apiUrlSplit[5]+"#"+apiUrlSplit[5];
					//System.out.println(apiUrl);
					bean.setName(record.getName());
					bean.setApi(record.getApi());
					bean.setFinanceApi(apiUrl);
					financeUrlList.add(bean);
				}
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
					gainerBean.setRating(conGainers[6]);
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
	public List<ConcurrentGainersBean> getConcurrentGainersWithRatingList() {
		return concurrentGainersWithRatingList;
	}
	public void setConcurrentGainersWithRatingList(
			List<ConcurrentGainersBean> concurrentGainersWithRatingList) {
		this.concurrentGainersWithRatingList = concurrentGainersWithRatingList;
	}
	
}
