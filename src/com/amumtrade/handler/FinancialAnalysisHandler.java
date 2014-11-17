package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amumtrade.bean.ConcurrentGainersBean;

public class FinancialAnalysisHandler {
	List<ConcurrentGainersBean> concurrentGainersWithRatingList ;
	BufferedReader br;

	public void execute() throws IOException{
		concurrentGainersWithRatingList = convertConGainersCsvToBean();
		concurrentGainersWithRatingList = convertUrlToFinancialUrl(concurrentGainersWithRatingList);
	
	}
	private List<ConcurrentGainersBean> convertUrlToFinancialUrl(List<ConcurrentGainersBean> concurrentList) {
		String apiUrl = null;
		String urlSplitBy = "/";
		try {
			for(ConcurrentGainersBean bean: concurrentList){
				apiUrl = bean.getApi();
				apiUrl = apiUrl.replace("http://", "");
				String[] apiUrlSplit = apiUrl.split(urlSplitBy);
				apiUrl = "http://"+apiUrlSplit[0]+"/"+"financials"+"/"+apiUrlSplit[4]+"/"+"ratios"+"/"+apiUrlSplit[5]+"#"+apiUrlSplit[5];
				System.out.println(apiUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
