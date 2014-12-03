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
import com.amumtrade.factory.OnlyBuyersEPSRunner;
import com.amumtrade.factory.OnlyBuyersVolumeRunner;
import com.amumtrade.util.StockUtil;

public class OnlyBuyersHandler {
	String URL = "http://www.moneycontrol.com/stocks/marketstats/onlybuyers.php?optex=NSE&opttopic=buyers&index=9&sort=perchg";
	Set<String> urlList = null;
	Set<String> epsApiurlList = null;

	Map<String,ConcurrentGainersBean> onlyBuyersMap = null;
	
	public void execute() throws IOException{
		try {
			//executeOnlyBuyersVolume();
			executeOnlyBuyersEPS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void executeOnlyBuyersEPS()throws IOException {
		BufferedWriter bwObj = null;
		onlyBuyersMap = new HashMap<String,ConcurrentGainersBean>();
		try {
			List<ConcurrentGainersBean> buyersVolumeList = onlyBuyerVolumeFileToBean(FileNameConstant.VOLUME_ONLY_BUYERS);
			urlList = new HashSet<String>();
			for(ConcurrentGainersBean bean : buyersVolumeList){
				urlList.add(bean.getApi());
				onlyBuyersMap.put(bean.getApi(),bean);
			}
			List<ConcurrentGainersBean> buyersEpsApiList  = StockUtil.convertUrlToEPSUrl(onlyBuyersMap,urlList);
			
			FileWriter fwo = new FileWriter(FileNameConstant.ONLY_BUYERS, false);
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,Sector,BidQty,LastPrice,Diff,% Chg,VolumeTraded,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,EPS,EPSRating,StandaloneProfit,Recommendation,News,Api"+"\n");
			
			
			epsApiurlList = new HashSet<String>();
			for(ConcurrentGainersBean epsApiBean : buyersEpsApiList){
				epsApiurlList.add(epsApiBean.getFinanceApi());
				onlyBuyersMap.put(epsApiBean.getFinanceApi(),epsApiBean);
			}
			System.out.println(epsApiurlList);
	    	
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			 for(String httpUrl : epsApiurlList){
				 	Runnable worker = new OnlyBuyersEPSRunner(new URL(httpUrl),onlyBuyersMap,bwObj,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("FINISHED ONLY BUYERS EPS THREADS EXECUTION...");
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
				bwObj.close();
			}
		}
		
	}

	private List<ConcurrentGainersBean> onlyBuyerVolumeFileToBean(String fileName) throws IOException {
		BufferedReader br = null;
		List<ConcurrentGainersBean>  buyersList= new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean buyersBean = null;
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				if(skipFirstLineHeader!=0){ 
					buyersBean = new ConcurrentGainersBean();
					String[] topGainers = line.split(cvsSplitBy);
					buyersBean.setName(topGainers[0]);
					buyersBean.setSector(topGainers[1]);
					buyersBean.setBidQuantity(topGainers[2]);
					buyersBean.setCurrentPrice(topGainers[3]);
					buyersBean.setDifference(topGainers[4]);
					buyersBean.setPercentChange(topGainers[5]);
					buyersBean.setCurrentVolume(topGainers[6]);
					buyersBean.setCurrentDayVolume(topGainers[7]);
					buyersBean.setFiveDayAvgVolume(topGainers[8]);
					buyersBean.setTenDayAvgVolume(topGainers[9]);
					buyersBean.setThirtyDayAvgVolume(topGainers[10]);
					buyersBean.setVolumeRating(topGainers[11]);
					buyersBean.setApi(topGainers[12]);
					buyersList.add(buyersBean);
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
		return buyersList;
	}
	
	private void executeOnlyBuyersVolume() throws IOException{
		BufferedWriter bwObj = null;
		onlyBuyersMap = new HashMap<String,ConcurrentGainersBean>();

		try {
			FileWriter fwo = new FileWriter(FileNameConstant.VOLUME_ONLY_BUYERS, false);
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("CompanyName,Sector,BidQty,LastPrice,Diff,% Chg,VolumeTraded,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,VolumeRating,Api"+"\n");
			urlList = new HashSet<String>();
			List<ConcurrentGainersBean> onlyBuyersList = runOnlyBuyerURL(URL);
			System.out.println("ONLY BUYERS FOR VOLUME EXECUTION SIZE>>"+onlyBuyersList.size());
			
			int count = 0;
			for(ConcurrentGainersBean bean : onlyBuyersList){
				if(count< 5){
					onlyBuyersMap.put(bean.getApi(), bean);
					urlList.add(bean.getApi());
				}
				count++;
			}
			
			int i=0;
			ExecutorService executor = Executors.newFixedThreadPool(AMUMStockConstant.THREAD_COUNT);
			for(String url : urlList){
				Runnable worker = new OnlyBuyersVolumeRunner(new URL(url),onlyBuyersMap,bwObj,"" + i);
	            executor.execute(worker);
	            i++;
			}
			executor.shutdown();
			while (!executor.isTerminated()) {
	        }
	        System.out.println("FINISHED ONLY BUYERS VOLUME THREADS EXECUTION..");

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
				bwObj.close();
			}
		}
		
	}


	private List<ConcurrentGainersBean> runOnlyBuyerURL(String stockURL) {
		BufferedReader in = null;
		String inputLine;
		ConcurrentGainersBean bean;
		List<ConcurrentGainersBean> buyersList = new  ArrayList<ConcurrentGainersBean>();
		Set<String> setToskipDuplicate;

		String api = null;
		String companyName = null;
		String sector = null;
		String bidQuantity = null;
		String currentPrice = null;
		String difference = null;
		String percentChange = null;
		String volumeTraded = null;
		try {
			 URL website = new URL(stockURL);
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
			 setToskipDuplicate = new HashSet<String>();
			  while ((inputLine = in.readLine()) != null)
		        {
				  if(inputLine.contains("<td class=\"brdrgtgry\">")){
					  api = inputLine.trim();
					  companyName = inputLine.trim();
					  api  = api.substring(api.indexOf("href"),api.lastIndexOf("class"));
					  api  = api.replace("href=\"", ""); 
					  api  = api.replace("\"", "").trim();
					  api = AMUMStockConstant.STOCK_URL+api;
					  
					  companyName = companyName.substring(companyName.indexOf("<b>"),companyName.lastIndexOf("</b>"));
					  companyName = companyName.replace("<b>", "").trim();
				  }else 
				  if(inputLine.contains("<td width=\"25%\" align=\"left\" class=\"brdrgtgry\">")){
					  sector = inputLine.trim();
					  sector = sector.substring(sector.indexOf("<b>"),sector.lastIndexOf("</b>"));
					  sector = sector.replace("<b>", "").trim();
				  }else
				  if(inputLine.contains("<td width=\"12%\" align=\"right\" class=\"brdrgtgry\">")){
					  bidQuantity = inputLine.trim();
					  bidQuantity = bidQuantity.replace("<td width=\"12%\" align=\"right\" class=\"brdrgtgry\">", "");
					  bidQuantity = bidQuantity.replace("</td>","");
					  bidQuantity = bidQuantity.replaceAll(",","").trim();
				  }else
				  if(inputLine.contains("<td width=\"10%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#0F6C02;\">")){
					  currentPrice = inputLine.trim();
					  currentPrice = currentPrice.replace("<td width=\"10%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#0F6C02;\">", "");
					  currentPrice = currentPrice.replace("</td>","");
					  currentPrice = currentPrice.replaceAll(",","").trim();
				  }else
				  if(inputLine.contains("<td width=\"10%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#d60614;\">")){
					  currentPrice = inputLine.trim();
					  currentPrice = currentPrice.replace("<td width=\"10%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#d60614;\">", "");
					  currentPrice = currentPrice.replace("</td>","");
					  currentPrice = currentPrice.replaceAll(",","").trim();
				  }else
				  if(inputLine.contains("<td width=\"9%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#0F6C02;\">")){
					  difference = inputLine.trim();
					  difference = difference.replace("<td width=\"9%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#0F6C02;\">", "");
					  difference = difference.replace("</td>","");
					  difference = difference.replaceAll(",","").trim();
				  }else
				  if(inputLine.contains("<td width=\"9%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#d60614;\">")){
					  difference = inputLine.trim();
					  difference = difference.replace("<td width=\"9%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#d60614;\">", "");
					  difference = difference.replace("</td>","");
					  difference = difference.replaceAll(",","").trim();
				  }else
				  if(inputLine.contains("<td width=\"11%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#0F6C02;\">")){
					  percentChange = inputLine.trim();
					  percentChange = percentChange.replace("<td width=\"11%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#0F6C02;\">", "");
					  percentChange = percentChange.replace("</td>","");
					  percentChange = percentChange.replaceAll(",","").trim();
				  }else
				  if(inputLine.contains("<td width=\"11%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#d60614;\">")){
					  percentChange = inputLine.trim();
					  percentChange = percentChange.replace("<td width=\"11%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#d60614;\">", "");
					  percentChange = percentChange.replace("</td>","");
					  percentChange = percentChange.replaceAll(",","").trim();
				  }else
				  if(inputLine.contains("<td width=\"10%\" align=\"right\" class=\"brdrgtgry\">")){
					  volumeTraded = inputLine.trim();
					  volumeTraded = volumeTraded.replace("<td width=\"10%\" align=\"right\" class=\"brdrgtgry\">", "");
					  volumeTraded = volumeTraded.replace("</td>","");
					  volumeTraded = volumeTraded.replaceAll(",","").trim();
					  if( api != null && companyName != null 
							  && sector != null && bidQuantity != null 
							  && currentPrice != null  && difference != null
							  && percentChange != null && volumeTraded != null){
						  
						  if(!setToskipDuplicate.contains(api)){
							  bean = new ConcurrentGainersBean();
							  bean.setApi(api);
							  bean.setName(companyName);
							  bean.setSector(sector);
							  bean.setBidQuantity(bidQuantity);
							  bean.setCurrentPrice(currentPrice);
							  bean.setPercentChange(percentChange);
							  bean.setDifference(difference);
							  bean.setCurrentVolume(volumeTraded);
							  buyersList.add(bean);
						  }
					  }
				  }
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyersList;
	}
}
