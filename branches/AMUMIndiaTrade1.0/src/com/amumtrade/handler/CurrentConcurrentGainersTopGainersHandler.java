package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.email.CsvToEmailBody;
import com.amumtrade.util.StockUtil;

public class CurrentConcurrentGainersTopGainersHandler {
	String conGainersCsvFileName = FileNameConstant.ALL_CURRENT_CONCURRENT_GAINER;
	String topGainersCsvFileName = FileNameConstant.ALL_TOP_GAINER;
	String csvFileName = FileNameConstant.ALL_TOP_GAINERS_CONCURRENT_GAINERS;
	List<ConcurrentGainersBean> concurrentGainersList ;
	List<ConcurrentGainersBean> topGainersList ;
	Set<String> concurrentGainersApiSet;
	Set<String> topGainersApiSet;
	Map<String,ConcurrentGainersBean> apiBeanMap;
	public void execute(long startTime) throws IOException{
		concurrentGainersList = new ArrayList<ConcurrentGainersBean>();
		topGainersList = new ArrayList<ConcurrentGainersBean>();
		concurrentGainersList = convertCsvToBean(concurrentGainersList,conGainersCsvFileName);
		topGainersList =  convertCsvToBean(concurrentGainersList,topGainersCsvFileName);
		topGainersApiSet = new HashSet<String>();
		concurrentGainersApiSet = new HashSet<String>();
		apiBeanMap = new HashMap<String, ConcurrentGainersBean>();
		topGainersApiSet = getTopGainersApiAPI();
		concurrentGainersApiSet = getConcurrentGainersApi();
		compareBothGainers();

		CsvToEmailBody emailBody = new CsvToEmailBody();
		String htmlText= emailBody.execute(csvFileName);
		StockUtil.initiateEmail(csvFileName,startTime,htmlText);
		
	}
	private void compareBothGainers() throws IOException {
		FileWriter fwo = new FileWriter( csvFileName, false );
		BufferedWriter bwObj = null;
		Set<String> gainersVsPostiveBreakOut;
		try {
			if(topGainersApiSet != null && !topGainersApiSet.isEmpty()
					&& concurrentGainersApiSet != null && !concurrentGainersApiSet.isEmpty()){
				bwObj = new BufferedWriter( fwo );  
				bwObj.write("CompanyName,CurrentPrice,DayVolume,FiveDayVolume,TenDayVolume,ThirtyDayVolume,VolumeRating,EPS,EPSRating,StanaloneProfit,Recommendation,News,PositiveBreakOut,API"+"\n");
			
				topGainersApiSet.retainAll(concurrentGainersApiSet);
				
				//Check with postive breakouts
				//PositiveBreakoutHandler breakoutHandler = new PositiveBreakoutHandler();
				//Set<String> positiveBreakoutSet = breakoutHandler.execute();
			////	gainersVsPostiveBreakOut = StockUtil.compareWithPostiveBreakOutSet(positiveBreakoutSet,topGainersApiSet);
				for(String api : topGainersApiSet){
					//String apiKey = StockUtil.getUrlToKeyAPI(api); 
				//	if(gainersVsPostiveBreakOut.contains(apiKey)){
				//		ConcurrentGainersBean bean = apiBeanMap.get(api);
				//		bean.setPositiveBreakout(AMUMStockConstant.YES);
				//		createCSVFile(bean,bwObj);
				//		System.out.println(apiKey+" POSITIVE BREAKOUT STOCK");
					//}else{
					//	ConcurrentGainersBean bean = apiBeanMap.get(api);
					//	bean.setPositiveBreakout("");
						createCSVFile( apiBeanMap.get(api),bwObj);
					//}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
				bwObj.close();
			}
		}
		
	}


	private void createCSVFile(ConcurrentGainersBean bean, BufferedWriter bwObj) {
		try {
			bwObj.write(bean.getName()+","+bean.getCurrentPrice()+","
					+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","
					+bean.getTenDayAvgVolume()+","+bean.getThirtyDayAvgVolume()+","
					+bean.getVolumeRating()+","+bean.getEps()+","+bean.getEpsRating()+","
					+bean.getStandaloneProfit()+","+bean.getRecommendation()+","+bean.getNews()+","
					+bean.getPositiveBreakout()+","+bean.getApi()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private Set<String> getConcurrentGainersApi() {
		try {
			for(ConcurrentGainersBean bean : concurrentGainersList){
				concurrentGainersApiSet.add(bean.getApi());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return concurrentGainersApiSet;
	}
	private Set<String> getTopGainersApiAPI() {
		try {
			for(ConcurrentGainersBean bean : topGainersList){
				topGainersApiSet.add(bean.getApi());
				apiBeanMap.put(bean.getApi(), bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return topGainersApiSet;
	}
	private List<ConcurrentGainersBean> convertCsvToBean(List<ConcurrentGainersBean> gainersList,String fileName) throws IOException {
		List<ConcurrentGainersBean>  gainerBeanList= new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean gainerBean = null;
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
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
					gainerBean.setEps(conGainers[7]);
					gainerBean.setEpsRating(conGainers[8]);
					gainerBean.setStandaloneProfit(conGainers[9]);
					gainerBean.setRecommendation(conGainers[10]);
					gainerBean.setNews(conGainers[11]);
					gainerBean.setPositiveBreakout(conGainers[12]);
					gainerBean.setApi(conGainers[13]);
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
