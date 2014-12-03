package com.amumtrade.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amumtrade.bean.ConcurrentGainersBean;

public class StockUtil {

	public static void initiateEmail(String filePath, long startTime, String htmlText ){
		String startDateTimeLog = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			startDateTimeLog = dateFormat.format(date);
		/*	SendAttachmentInEmail email = new SendAttachmentInEmail();
			email.execute(filePath, startDateTimeLog, getTotalExecutionTime(startTime),htmlText);
	*/	} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getTotalExecutionTime(long startTime){
		String totalExecutionTime = null;
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			totalExecutionTime =  "Total Execution Time: "+ h +" : "+ m +" : "+ s;
		return totalExecutionTime;
	}
	
	public static List<ConcurrentGainersBean> convertCsvToBean(String csvFile) throws IOException {
		BufferedReader br =null;
		List<ConcurrentGainersBean>  gainerBeanList= new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean gainerBean = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
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
					gainerBean.setApi(conGainers[12]);
					gainerBeanList.add(gainerBean);
				//}
				//skipFirstLineHeader++;

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
	
	public static String getUrlToKeyAPI(String url) {
		url = url.substring(url.lastIndexOf("/"));
		url = url.replace("/", "");
		return url;
	}
	public static Set<String> compareWithPostiveBreakOutSet(Set<String> positiveBreakoutSet, Set<String> gainersApiSet) {
		try {
			Set<String> gainersApiKeySet = getAPIKeyNameSet(gainersApiSet);
			positiveBreakoutSet.retainAll(gainersApiKeySet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return positiveBreakoutSet;
	}
	
	public static Set<String> getAPIKeyNameSet(Set<String> gainersApiSet) {
		Set<String> apiKeySet = new HashSet<String>();
		for(String url : gainersApiSet){
			url = url.substring(url.lastIndexOf("/"));
			url = url.replace("/", "");
			apiKeySet.add(url);
		}
		return apiKeySet;
	}
	
	public static Set<String> getAPIKeyNameList(List<String> gainersApiList) {
		Set<String> apiKeyList = new HashSet<String>();
		for(String url : gainersApiList){
			url = url.substring(url.lastIndexOf("/"));
			url = url.replace("/", "");
			apiKeyList.add(url);
		}
		return apiKeyList;
	}
	
	public static List<ConcurrentGainersBean> convertUrlToEPSUrl(Map<String, ConcurrentGainersBean> onlyBuyersMap,
			Set<String> urlList) {
		List<ConcurrentGainersBean> beanList = new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean bean = null; 
		String urlSplitBy = "/";
		String apiUrl = null;

		try {
			for(String url: urlList){
				bean = new ConcurrentGainersBean();
				bean = onlyBuyersMap.get(url);
				apiUrl = bean.getApi();
				apiUrl = apiUrl.replace("http://", "");
				String[] apiUrlSplit = apiUrl.split(urlSplitBy);
				apiUrl = "http://"+apiUrlSplit[0]+"/"+"financials"+"/"+apiUrlSplit[4]+"/"+"ratios"+"/"+apiUrlSplit[5]+"#"+apiUrlSplit[5];
				bean.setFinanceApi(apiUrl);
				beanList.add(bean);
		}
	} catch (Exception e) {
			e.printStackTrace();
		}
		return beanList;
	}
}
