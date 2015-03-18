package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
import com.amumtrade.bean.TopGainerBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.factory.MoneyControlRatingRunner;
import com.amumtrade.marketstat.LastEightDayConcurrentGainers;
import com.amumtrade.marketstat.LastFiveDayConcurrentGainers;
import com.amumtrade.marketstat.LastThreeDayConcurrentGainers;

public class FirstLevelHandler {
	private BufferedReader br;

public void execute() throws IOException{
	FileWriter fwo = new FileWriter( FileNameConstant.GAINERS_BUYERS_RATING, false );
	BufferedWriter bwObj = null;
	
	FileWriter fwo1 = new FileWriter( FileNameConstant.SENTIMETER_RATING, false );
	BufferedWriter bwObj1 = null;
	
	List<String> buyerApi  = new ArrayList<String>();
	List<String> gainerApi  = new ArrayList<String>();
	Set<String> buyerGainerApiSet = new HashSet<String>();
	Set<String> finalBuyerGainerApiSet = new HashSet<String>();

	Set<String> lastThreeDaySet = new HashSet<String>();
	Set<String> lastFiveDaySet = new HashSet<String>();
	Set<String> lastEightDaySet = new HashSet<String>();
	Map<String,ConcurrentGainersBean> buyerMap = new HashMap<String, ConcurrentGainersBean>();
	Map<String,TopGainerBean> gainerMap = new HashMap<String, TopGainerBean>();
	Map<String, String> gainerBuyerMap = new HashMap<String, String>();
	Map<String, String> ratingMap = new HashMap<String, String>();
	Set<String> ratingApi = new HashSet<String>();


	 
	try {
		//OnlyBuyersList
		List<ConcurrentGainersBean> buyersList = BuyersHandler.getOnlyBuyer(FileNameConstant.ONLY_BUYERS_URL);
		//CurrentTopGainersList
		List<TopGainerBean> gainersList = TopGainersHandler.getTopGainersList(FileNameConstant.TOP_GAINERS_URL);
		//LastThreeDayConcurrentGainers
		List<ConcurrentGainersBean> lastThreeDayList = LastThreeDayConcurrentGainers.getLastThreeDayGainers(FileNameConstant.LAST_THREE_DAY_GAINER_URL);
		for(ConcurrentGainersBean bean : lastThreeDayList){
			lastThreeDaySet.add(bean.getApi().trim());
		}
		//LastThreeFiveConcurrentGainers
		List<ConcurrentGainersBean> lastFiveDayList = LastFiveDayConcurrentGainers.getLastFiveDayGainers(FileNameConstant.LAST_FIVE_DAY_GAINER_URL);
		for(ConcurrentGainersBean bean : lastFiveDayList){
			lastFiveDaySet.add(bean.getApi().trim());
		}
		//LastEightDayConcurrentGainers
		List<ConcurrentGainersBean> lastEightDayList = LastEightDayConcurrentGainers.getLastEightDayGainers(FileNameConstant.LAST_EIGHT_DAY_GAINER_URL);
		for(ConcurrentGainersBean bean : lastEightDayList){
			lastEightDaySet.add(bean.getApi().trim());
		}
		
		for(ConcurrentGainersBean bb : buyersList ){
			buyerMap.put(bb.getApi().trim(), bb);
			buyerApi.add(bb.getApi().trim());
			buyerGainerApiSet.add(bb.getApi().trim());
		}
		
		for(TopGainerBean gb : gainersList ){
			gainerMap.put("http://www.moneycontrol.com"+gb.getApi().trim(), gb);
			gainerApi.add("http://www.moneycontrol.com"+gb.getApi().trim());
			buyerGainerApiSet.add("http://www.moneycontrol.com"+gb.getApi().trim());
		}
		buyerApi.retainAll(gainerApi);//Filter the Common stock based on Buyer & Gainers
		bwObj = new BufferedWriter( fwo );  
		bwObj.write("CompanyName,Rating,Price,Difference,%Change,Sector,ThreeDayGainer,FiveDayGainer,EightDayGainer,BidQuantity,Url"+"\n");
		for(String api : buyerApi){
			finalBuyerGainerApiSet.add(api);
			String threeDayGainer = FileNameConstant.NO;
			String fiveDayGainer = FileNameConstant.NO;
			String eightDayGainer = FileNameConstant.NO;
			
			if(lastThreeDaySet.contains(api)){
				threeDayGainer = FileNameConstant.YES;
			}
			if(lastFiveDaySet.contains(api)){
				fiveDayGainer = FileNameConstant.YES;
			}
			if(lastEightDaySet.contains(api)){
				eightDayGainer = FileNameConstant.YES;
			}
			
			ConcurrentGainersBean bb = buyerMap.get(api);
			TopGainerBean gb = gainerMap.get(api);
			
			/*bwObj.write(bb.getCompanyName()+",@rating,"+gb.getLastPrice()
					+","+gb.getChange()+","+bb.getPercentChange()
					+","+bb.getSector()+","+threeDayGainer+","+fiveDayGainer+","+eightDayGainer+","
					+bb.getBidQuantity()+","+bb.getApi()+"\n");*/
			gainerBuyerMap.put(api, bb.getCompanyName()+",@rating,"+gb.getLastPrice()
					+","+gb.getChange()+","+bb.getPercentChange()
					+","+bb.getSector()+","+threeDayGainer+","+fiveDayGainer+","+eightDayGainer+","
					+bb.getBidQuantity()+","+bb.getApi());	
		}
		
		//ConcurrentGainet(LastThreeDayConcurrentGainers+LastThreeFiveConcurrentGainers+LastEightDayConcurrentGainers)
		List<ConcurrentGainersBean> concurrentGainersList = ConcurrentGainersAllHandler.getCommonConcurrentGainers(lastThreeDayList,lastFiveDayList,lastEightDayList);
		for(ConcurrentGainersBean bean : concurrentGainersList){
			if(!buyerGainerApiSet.contains(bean.getApi().trim())){
			//	bwObj.write("**"+bean.getCompanyName()+"**,@rating,,,,,"+FileNameConstant.YES+","+FileNameConstant.YES+","+FileNameConstant.YES+",,"+bean.getApi()+"\n");
				gainerBuyerMap.put(bean.getApi().trim(),"**"+bean.getCompanyName()+"**,@rating,,,,,"+FileNameConstant.YES+","+FileNameConstant.YES+","+FileNameConstant.YES+",,"+bean.getApi());
				finalBuyerGainerApiSet.add(bean.getApi());
			}
		}
		System.out.println("SENTIMETER EXECUTION STARTED FOR SIZE>>"+finalBuyerGainerApiSet.size());
		
		bwObj1 = new BufferedWriter( fwo1 );  
		bwObj1.write("SentimeterRating,Api"+"\n");
		int i=0;
		 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
		 for(String httpUrl : finalBuyerGainerApiSet){
			  Runnable worker = new MoneyControlRatingRunner(new URL(httpUrl),bwObj1,"" + i);
	            executor.execute(worker);
	            i++;
	          }
	        executor.shutdown();
	        while (!executor.isTerminated()) {
	        }
		System.out.println("SENTIMETER EXECUTION COMPLETED!!!");
		if(bwObj1 != null){
			bwObj1.close();
		}
		
		//Merging the multiple output files
		
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		br = new BufferedReader(new FileReader(FileNameConstant.SENTIMETER_RATING));
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			if(skipFirstLineHeader!=0){ 
				String[] topGainers = line.split(cvsSplitBy);
					ratingMap.put(topGainers[1].trim(), topGainers[0]);
					ratingApi.add(topGainers[1].trim());
				}
			skipFirstLineHeader++;
			}
		
		for(String api : finalBuyerGainerApiSet){
			if(ratingApi.contains(api)){
				String rating = ratingMap.get(api);
				String record = gainerBuyerMap.get(api);
				record = record.replace("@rating", rating);
				bwObj.write(record+"\n");
			}else{
				bwObj.write(gainerBuyerMap.get(api)+"\n");
			}
			
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		if(bwObj != null){
			bwObj.close();
		}
		if(bwObj1 != null){
			bwObj1.close();
		}
		if(br !=null){
			br.close();
		}
	}
	
	
}
}
