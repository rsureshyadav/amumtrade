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
import com.amumtrade.constant.AMUMStockConstant;

public class MasterCombineTopConGainersHandler {
	String conGainersCsvFileName = "config/amumEPSConcurrentGainersAnalyzer.csv";
	String topGainersCsvFileName = "config/amumEPSTopGainersAnalyzer.csv";

	List<ConcurrentGainersBean> concurrentGainersList ;
	List<ConcurrentGainersBean> topGainersList ;
	Set<String> concurrentGainersApiSet;
	Set<String> topGainersApiSet;
	Map<String,ConcurrentGainersBean> apiBeanMap;
	public void execute() throws IOException{
		concurrentGainersList = new ArrayList<ConcurrentGainersBean>();
		topGainersList = new ArrayList<ConcurrentGainersBean>();
		concurrentGainersList = convertCsvToBean(concurrentGainersList,conGainersCsvFileName);
		topGainersList =  convertCsvToBean(concurrentGainersList,conGainersCsvFileName);
		topGainersApiSet = new HashSet<String>();
		concurrentGainersApiSet = new HashSet<String>();
		apiBeanMap = new HashMap<String, ConcurrentGainersBean>();
		topGainersApiSet = getTopGainersApiAPI();
		concurrentGainersApiSet = getConcurrentGainersApi();
		compareBothGainers();
		
	}
	private void compareBothGainers() throws IOException {
		FileWriter fwo = new FileWriter( "config/amumCommonTopConcurrentGainersHandler.csv", false );
		BufferedWriter bwObj = null;
		try {
			if(topGainersApiSet != null && !topGainersApiSet.isEmpty()
					&& concurrentGainersApiSet != null && !concurrentGainersApiSet.isEmpty()){
				bwObj = new BufferedWriter( fwo );  
				bwObj.write("CompanyName,CurrentPrice,DayVolume,FiveDayVolume,TenDayVolume,ThirtyDayVolume,VolumeRating,EPS,EPSRating,API"+"\n");
			
				topGainersApiSet.retainAll(concurrentGainersApiSet);
				for(String api : topGainersApiSet){
					//System.out.println(api);
					createCSVFile(apiBeanMap.get(api),bwObj);
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
					+bean.getVolumeRating()+","+bean.getEps()+","+bean.getEpsRating()+","+bean.getApi()+"\n");
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
					gainerBean.setApi(conGainers[9]);
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