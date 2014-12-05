package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.amumtrade.factory.ApiRunner;
import com.amumtrade.factory.PositiveTurnAroundVolumeEPSRunner;
import com.amumtrade.factory.PositiveTurnAroundVolumeRunner;
import com.amumtrade.util.StockUtil;

//This class to be run for every quarter results
public class PositiveTurnaroundsHandler {
	String URL = "http://www.moneycontrol.com/stocks/cptmarket/results/losstoprofit/qtrlist.php";
	Set<String> urlList = null;
	Map<String,ConcurrentGainersBean> postiveStockMap = null;
	String quarterMonthName = null;
	public void execute(long startTime){
		try {
			executePositiveTurnAroundsAPI();
			executeVolume();
			executeVolumeEPS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeVolumeEPS() throws IOException{
		BufferedWriter bwObj = null;
		postiveStockMap = new HashMap<String,ConcurrentGainersBean>();
		try {
			List<ConcurrentGainersBean> volumeList = setVolumeDataToBean(FileNameConstant.VOLUME_POSITIVE_TRUNAROUND_STOCK);
			urlList = new HashSet<String>();
			for(ConcurrentGainersBean bean : volumeList){
				urlList.add(bean.getApi());
				postiveStockMap.put(bean.getApi(),bean);
			}
			List<ConcurrentGainersBean> epsApiList  = StockUtil.convertUrlToEPSUrl(postiveStockMap,urlList);
			
			FileWriter fwo = new FileWriter(FileNameConstant.POSITIVE_TRUNAROUND_STOCK, false);
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,QuarterSale,PrevYearSale,% ChgSale,QuarterNetProfit,PrevYearNetProfit,NetProfitChg(Rs.Cr.),DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,EPS,EPSRating,StandaloneProfit,Recommendation,News,Api"+"\n");
			System.out.println("POSITIVE TURNAROUNDS EPS SIZE IS "  +epsApiList.size());

			urlList = new HashSet<String>();
			postiveStockMap = new HashMap<String,ConcurrentGainersBean>();
			for(ConcurrentGainersBean epsApiBean : epsApiList){
				urlList.add(epsApiBean.getFinanceApi());
				postiveStockMap.put(epsApiBean.getFinanceApi(),epsApiBean);
			}
	    	
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : urlList){
				 	Runnable worker = new PositiveTurnAroundVolumeEPSRunner(new URL(httpUrl),postiveStockMap,bwObj,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("FINISHED POSITIVE TURNAROUNDS EPS THREADS EXECUTION..");
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
				bwObj.close();
			}
		}
		
	}

	private List<ConcurrentGainersBean> setVolumeDataToBean(String fileName) throws IOException {
		BufferedReader br = null;
		List<ConcurrentGainersBean>  beanList= new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean bean = null;
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				if(skipFirstLineHeader!=0){ 
					bean = new ConcurrentGainersBean();
					String[] csvColumn = line.split(cvsSplitBy);
					bean.setCompanyName(csvColumn[0]);
					bean.setQuarterSale(csvColumn[1]);
					bean.setPrevYearSale(csvColumn[2]);
					bean.setPercentChgSale(csvColumn[3]);
					bean.setQuarterNetProfit(csvColumn[4]);
					bean.setPrevYearNetProfit(csvColumn[5]);
					bean.setNetProfitChg(csvColumn[6]);
					bean.setCurrentDayVolume(csvColumn[7]);
					bean.setFiveDayAvgVolume(csvColumn[8]);
					bean.setTenDayAvgVolume(csvColumn[9]);
					bean.setThirtyDayAvgVolume(csvColumn[10]);
					bean.setVolumeRating(csvColumn[11]);
					bean.setApi(csvColumn[12]);
					beanList.add(bean);
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
		return beanList;
	}
	private void executeVolume() throws IOException{
		BufferedWriter bwObj = null;
		Set<String> apiUrlList;
		Map<String,ConcurrentGainersBean> apiStockMap = new HashMap<String, ConcurrentGainersBean>();
		try {
			Map<String,String> apiMap  = getVolumeAPI();
			apiUrlList = new HashSet<String>();
			for(String positiveApiUrl : urlList){
				ConcurrentGainersBean bean = postiveStockMap.get(positiveApiUrl);
				bean.setApi(apiMap.get(positiveApiUrl));
				apiStockMap.put(bean.getApi(), bean);
				apiUrlList.add(bean.getApi());
			}
			
			FileWriter fwo = new FileWriter(FileNameConstant.VOLUME_POSITIVE_TRUNAROUND_STOCK, false);
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,QuarterSale,PrevYearSale,% ChgSale,QuarterNetProfit,PrevYearNetProfit,NetProfitChg(Rs.Cr.),DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,Api"+"\n");
			System.out.println("POSITIVE TURNAROUNDS VOLUME SIZE IS "  +apiUrlList.size());
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : apiUrlList){
				 	Runnable worker = new PositiveTurnAroundVolumeRunner(new URL(httpUrl),apiStockMap,bwObj,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("FINISHED POSITIVE TURNAROUNDS VOLUME THREADS EXECUTION..");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj !=null){
				bwObj.close();
			}
		}
	}

	private Map<String, String> getVolumeAPI() throws IOException{
		BufferedReader br = null;
		Map<String,String> poitiveApiMap= new HashMap<String, String>();
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(FileNameConstant.VOLUME_POSITIVE_TRUNAROUND_STOCK));
			while ((line = br.readLine()) != null) {
					String[] apis = line.split(cvsSplitBy);
					poitiveApiMap.put(apis[7], apis[8]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br !=null){
				br.close();
			}
		}
		return poitiveApiMap;
	}

	private void executePositiveTurnAroundsAPI() throws IOException{
		BufferedWriter bwObj = null;
		postiveStockMap = new HashMap<String,ConcurrentGainersBean>();

		try {
			FileWriter fwo = new FileWriter(FileNameConstant.VOLUME_POSITIVE_TRUNAROUND_STOCK, false);
			bwObj = new BufferedWriter( fwo );  
		//	bwObj.write("CompanyName,QuarterSale,PrevYearSale,% ChgSale,QuarterNetProfit,PrevYearNetProfit,NetProfitChg(Rs.Cr.),DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,Api"+"\n");
			bwObj.write("CompanyName,QuarterSale,PrevYearSale,% ChgSale,QuarterNetProfit,PrevYearNetProfit,NetProfitChg(Rs.Cr.),PositiveApi,Api"+"\n");
			urlList = new HashSet<String>();
			List<ConcurrentGainersBean> positiveStockList = runPositiveStockURL(URL);
			System.out.println("POSITIVE TURNAROUNDS ["+quarterMonthName+"] SIZE IS "  +positiveStockList.size());
			//int count =0;
			for(ConcurrentGainersBean bean : positiveStockList){
					//if(count < 10){
						postiveStockMap.put(bean.getPositiveAPI(), bean);	
						urlList.add(bean.getPositiveAPI());
					//}
					//count++;
			}
			
			int i=0;
			ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			for(String positiveUrl : urlList){
				Runnable worker = new ApiRunner(new URL(positiveUrl),postiveStockMap,bwObj,"" + i);
	            executor.execute(worker);
	            i++;
			}
			executor.shutdown();
			while (!executor.isTerminated()) {
	        }
	        System.out.println("FINISHED POSITIVE TURNAROUNDS THREADS EXECUTION..");

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
				bwObj.close();
			}
		}
		
	}

	private List<ConcurrentGainersBean> runPositiveStockURL(String stockURL) {
		BufferedReader in = null;
		String inputLine;
		ConcurrentGainersBean bean = null;
		List<ConcurrentGainersBean> positiveStockList = new  ArrayList<ConcurrentGainersBean>();
		Set<String> setToskipDuplicate;

		String positiveAPI = null;
		String companyName = null;
		String quarterSale = null;
		String prevYearSale = null;
		String percentChgSale = null;
		String quarterNetProfit = null;
		String prevYearNetProfit = null;
		String netProfitChg = null;
		
		boolean isSaleNetProfit = false;
		int saleNetProfitCount = 0;
		try {
			 URL website = new URL(stockURL);
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
			 setToskipDuplicate = new HashSet<String>();
			  while ((inputLine = in.readLine()) != null)
		        {
				  if(inputLine.contains("<b>Positive Turnarounds</b>")){
					  quarterMonthName = inputLine.trim();
					  quarterMonthName  = quarterMonthName.substring(quarterMonthName.indexOf("("),quarterMonthName.lastIndexOf(")"));
					  quarterMonthName  = quarterMonthName.replace("(", "");
				  }
				  if(inputLine.contains("<td width='150px' class=\"brdrgtgry\" colspan=\"4\">")){
					  bean = new ConcurrentGainersBean(); 
					 positiveAPI = inputLine.trim();
					 companyName = inputLine.trim();
					 positiveAPI  = positiveAPI.substring(positiveAPI.indexOf("href"),positiveAPI.lastIndexOf("class"));
					 positiveAPI  = positiveAPI.replace("href='", ""); 
					 positiveAPI  = positiveAPI.replace("'", "");
					 positiveAPI = AMUMStockConstant.STOCK_URL+positiveAPI.trim();
					 bean.setPositiveAPI(positiveAPI);
					 companyName = companyName.substring(companyName.indexOf("<b>"),companyName.lastIndexOf("</b>"));
					 companyName = companyName.replace("<b>", "").trim();
					 bean.setCompanyName(companyName);
				  }
				  if(inputLine.contains("<td align=\"right\">")){
					  isSaleNetProfit = true;
				  }
				  if(!isSaleNetProfit){
					  saleNetProfitCount = 0;
				  }
				  if(isSaleNetProfit){
					  if(saleNetProfitCount == 0){
						  	quarterSale = inputLine.trim(); 
						  	quarterSale = quarterSale.substring(quarterSale.indexOf("<td align=\"right\">"),quarterSale.lastIndexOf("</td>"));
						  	quarterSale = quarterSale.replace("<td align=\"right\">", "");
						  	quarterSale = quarterSale.replace(",", "").trim();
						  	bean.setQuarterSale(quarterSale);
					  }else if(saleNetProfitCount == 1){
						  prevYearSale = inputLine.trim(); 
						  prevYearSale = prevYearSale.replace("<td align=\"right\">", "").trim();
						  prevYearSale = prevYearSale.replace("</td>", "");
						  prevYearSale = prevYearSale.replace(",", "").trim(); 
						  bean.setPrevYearSale(prevYearSale);
					  }else if(saleNetProfitCount == 2){
						  percentChgSale = inputLine.trim(); 
						  percentChgSale = percentChgSale.replace("<td align=\"right\" class=\"brdrgtgry\">", "");
						  percentChgSale = percentChgSale.replace("</td>", "");
						  percentChgSale = percentChgSale.replace(",", "").trim(); 
						  bean.setPercentChgSale(percentChgSale);
					  }else if(saleNetProfitCount == 3){
						  quarterNetProfit = inputLine.trim(); 
						  quarterNetProfit = quarterNetProfit.replace("<td align=\"right\">", "");
						  quarterNetProfit = quarterNetProfit.replace("</td>", "");
						  quarterNetProfit = quarterNetProfit.replace(",", "").trim(); 
						  bean.setQuarterNetProfit(quarterNetProfit);
					  }else if(saleNetProfitCount == 4){
						  prevYearNetProfit = inputLine.trim(); 
						  prevYearNetProfit = prevYearNetProfit.replace("<td align=\"right\">", "");
						  prevYearNetProfit = prevYearNetProfit.replace("</td>", "");
						  prevYearNetProfit = prevYearNetProfit.replace(",", "").trim(); 
						  bean.setPrevYearNetProfit(prevYearNetProfit);
					  }else if(saleNetProfitCount == 5){
						  netProfitChg = inputLine.trim(); 
						  netProfitChg = netProfitChg.replace("<td align=\"right\" class=\"brdrgtgry\">", "");
						  netProfitChg = netProfitChg.replace("</td>", "");
						  netProfitChg = netProfitChg.replace(",", "").trim();
						  bean.setNetProfitChg(netProfitChg);
						  isSaleNetProfit = false;
					  }
					  saleNetProfitCount++;
					  if(!setToskipDuplicate.contains(positiveAPI)){
						  positiveStockList.add(bean);
						  setToskipDuplicate.add(positiveAPI);
					  }
				  }
				  
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return positiveStockList;
	}
}
