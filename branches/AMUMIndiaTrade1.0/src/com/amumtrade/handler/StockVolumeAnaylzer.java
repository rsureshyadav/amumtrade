package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.TopGainerBean;
import com.amumtrade.factory.VolumeSplitRunner;

public class StockVolumeAnaylzer {
	public  String stockURL = "http://www.moneycontrol.com";

	private BufferedReader br;
	private BufferedReader in;
	private List<TopGainerBean> csvFileList  = new ArrayList<TopGainerBean>();
	List<TopGainerBean> topGainerWithVolume ;
	public void execute() throws IOException{
		List<TopGainerBean> gainersBeanList = getTopGainersCsvToBean();
		csvFileList.add(runVolumeSplitter(gainersBeanList));
		//List<TopGainerBean> topGainerWithVolume = getStockVolume(gainersBeanList);
	//	writeToCSVFile(topGainerWithVolume);
	}
	
	private TopGainerBean runVolumeSplitter(List<TopGainerBean> gainersBeanList) throws IOException{
		int splitSize = 10;
		int totalListSize= gainersBeanList.size();
		int indivdualSplitSize;
		int reminingSplitSize;
		int execStartCount;
		int execEndCount = 0;
		int beanStartCount=1;
		int gainerBeanStartCount=1;
		TopGainerBean  bean = null;
		List<TopGainerBean> execBeanList = null;
		List<String> urlList = null;
		Map<String,TopGainerBean> topGainerMap = new HashMap<String,TopGainerBean>();
		FileWriter fwo = new FileWriter( "config/amumTopGainerVolumeBasedList.csv", false );
		BufferedWriter bwObj = null;
		
		try {
			bean = new TopGainerBean();
			execBeanList = new ArrayList<TopGainerBean>();
			System.out.println(">>"+gainersBeanList.size());
			//indivdualSplitSize = totalListSize/splitSize + 1;
			//reminingSplitSize =  totalListSize - indivdualSplitSize * splitSize;
			//System.out.println("indivdualSplitSize>>"+indivdualSplitSize);
			//System.out.println("reminingSplitSize>>"+reminingSplitSize);
			//List<TopGainerBean> gainerBeanList = new ArrayList<TopGainerBean>();
			/*for(int i=1; i<=indivdualSplitSize ; i++){ // Master Split List
				int beanStartCount=0;
				execStartCount = i*splitSize;//25//50
				for(TopGainerBean gainerBean : gainersBeanList){
					beanStartCount++;//1
					if(execEndCount>beanStartCount){
					//	if(execStartCount <= beanStartCount){
							System.out.println(gainerBean.getCompanyName());
						//}
					}
				}
				execEndCount = execStartCount;
				System.out.println("**************************");
			}*/
			
			/*for(TopGainerBean gainerBean : gainersBeanList){
				//System.out.println(gainerBean.getCompanyName());
				execBeanList.add(gainerBean);
				if(beanStartCount == splitSize){
				//	System.out.println("Thread ["+(gainerBeanStartCount - beanStartCount)+"-"+(gainerBeanStartCount)+"]");
					VolumeSplitRunner volumeRunner = new VolumeSplitRunner("Thread ["+(gainerBeanStartCount - beanStartCount)+"-"+(gainerBeanStartCount)+"]",execBeanList);
					volumeRunner.start();
					topGainerWithVolume = volumeRunner.getTopGainerWithVolume();
					execBeanList = new ArrayList<TopGainerBean>();
					beanStartCount=0;
				}
				beanStartCount++;
				gainerBeanStartCount++;
			}*/
			/*	VolumeSplitRunner volumeRunner = new VolumeSplitRunner("Thread ["+beanStartCount+"]",urlList);
				volumeRunner.start();
				beanStartCount++;*/

			urlList = new ArrayList<String>();
			for(TopGainerBean gainerBean : gainersBeanList){
				String httpURL = stockURL+gainerBean.getApi().trim();
				urlList.add(httpURL);
				topGainerMap.put(httpURL, gainerBean);
			}
			
		/*	BlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(1024);
			ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS, runnables);
			for(String httpUrl : urlList){
				System.out.println(httpUrl);
				executor.submit(new VolumeSplitRunner(new URL(httpUrl)));
			}
			executor.shutdown();*/
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("Company Name,High,Low,Last Price,Prv Close,Change,DayVolume,FiveDayAvgVolume,TenDayAvgVolume,ThirtyDayAvgVolume,Rating"+"\n");
			
			
			int i=0;
			 ExecutorService executor = Executors.newFixedThreadPool(5);
			 for(String httpUrl : urlList){//for (int i = 0; i < 10; i++) {
				// System.out.println(i);
		            Runnable worker = new VolumeSplitRunner(new URL(httpUrl),topGainerMap,bwObj,"" + i);
		            executor.execute(worker);
		            i++;
		          }
		        executor.shutdown();
		        while (!executor.isTerminated()) {
		        }
		        System.out.println("Finished all threads");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj != null){
				bwObj.close();
			}
		}
		return bean;
	}
	
	
	private void writeToCSVFile(List<TopGainerBean> topGainerWithVolume) throws IOException {
		Set<String> urlSet = new HashSet<String>();
		FileWriter fwo = new FileWriter( "config/amumTopGainerVolumeBasedList.csv", false );
		BufferedWriter bwObj = null;
		try {
			bwObj = new BufferedWriter( fwo );  
			bwObj.write("Company Name,High,Low,Last Price,Prv Close,Change,Day Volume,Five Day Avg Volume,Ten Day Avg Volume,Thirty Day Avg Volume,Rating"+"\n");
			for(TopGainerBean bean : topGainerWithVolume){
				if(!urlSet.contains(bean.getApi())){
					int dayVolume = Integer.parseInt(bean.getCurrentDayVolume());
					int fiveDayAvgVolume = Integer.parseInt(bean.getFiveDayAvgVolume());
					int tenDayAvgVolume = Integer.parseInt(bean.getTenDayAvgVolume());
					int thirtyDayAvgVolume = Integer.parseInt(bean.getThirtyDayAvgVolume());
					if(dayVolume >= fiveDayAvgVolume && dayVolume >= tenDayAvgVolume && dayVolume >= thirtyDayAvgVolume 
							&& fiveDayAvgVolume > tenDayAvgVolume && fiveDayAvgVolume >thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"5 Star"+"\n");
						
						System.out.println(bean.getCompanyName()+","+"^"+bean.getChange()+","+bean.getCurrentDayVolume()+"5 Star");
					}else if(dayVolume >= fiveDayAvgVolume && dayVolume >= tenDayAvgVolume && dayVolume >= thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"4 Star"+"\n");
					}else if(fiveDayAvgVolume > tenDayAvgVolume && fiveDayAvgVolume >thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"3 Star"+"\n");
					}else if(fiveDayAvgVolume <= tenDayAvgVolume && tenDayAvgVolume>=thirtyDayAvgVolume){
						bwObj.write(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"2 Star"+"\n");
					}
						
						/*System.out.println(bean.getCompanyName()+","+bean.getHigh()+","+ bean.getLow()+","
								+ bean.getLastPrice()+","+ bean.getPrvClose()+","+ bean.getChange()+","
								+bean.getCurrentDayVolume()+","+bean.getFiveDayAvgVolume()+","+bean.getTenDayAvgVolume()+","
								+bean.getThirtyDayAvgVolume()+","+"1 Star");*/
					urlSet.add(bean.getApi());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null){
				bwObj.close();
			}
			
		}
		
	}
	private List<TopGainerBean> getTopGainersCsvToBean() throws IOException {
		List<TopGainerBean>  gainerBeanList= new ArrayList<TopGainerBean>();
		TopGainerBean gainerBean = null;
		String csvFile = "config/amumTopGainerList.csv";
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(skipFirstLineHeader!=0){ 
					//	if (skipFirstLineHeader==3){ //For Testing with single entry 
					//if (skipFirstLineHeader!=0 && skipFirstLineHeader<=2){ //For Testing with first 10 entrys 
					gainerBean = new TopGainerBean();
					String[] topGainers = line.split(cvsSplitBy);
					gainerBean.setCompanyName(topGainers[0]);
					gainerBean.setHigh(topGainers[1]);
					gainerBean.setLow(topGainers[2]);
					gainerBean.setLastPrice(topGainers[3]);
					gainerBean.setPrvClose(topGainers[4]);
					gainerBean.setChange(topGainers[5]);
					gainerBean.setPercentGain(topGainers[6]);
					gainerBean.setApi(topGainers[7]);
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
	private List<TopGainerBean> getStockVolume(List<TopGainerBean> gainersBeanList) throws IOException{
		String currentDayVolume = null;
		String fiveDayAvgVolume = null;
		String tenDayAvgVolume = null;
		String thirtyDayAvgVolume = null;
		URL website = null;
		 String inputLine = null;
		 List<String> stockURLList = null; 
		 boolean toolTip3 = false;
		 int toolTip3Count =0;
		 int count = 1;
		 Map<String,TopGainerBean> topGainerMap = new HashMap<String,TopGainerBean>();
		 List<TopGainerBean> stockGainOnVolume = new ArrayList<TopGainerBean>();
		 TopGainerBean gainerBean ;
		try {
			stockURLList = new ArrayList<String>();
			for(TopGainerBean bean : gainersBeanList){
			String httpURL = stockURL+bean.getApi().trim();
			stockURLList.add(httpURL);
			topGainerMap.put(httpURL, bean);
			}
			for(String url : stockURLList){
				 gainerBean = new TopGainerBean();

				website  = new URL(url);
				in= new BufferedReader(new InputStreamReader(website.openStream()));
				 while ((inputLine = in.readLine()) != null)
			        {
					 if(inputLine.contains("<span id=\"tt03\">VOLUME")){
						 currentDayVolume = inputLine.trim();
						 currentDayVolume = currentDayVolume.substring(currentDayVolume.indexOf("<strong>"),currentDayVolume.lastIndexOf("</strong>"));
						 currentDayVolume = currentDayVolume.replace("<strong>", "");
						 currentDayVolume = currentDayVolume.replace(",", "");
			/*			 System.out.println("volume>>"+volume);
						 System.out.println("toolTip3>>"+toolTip3);
						 System.out.println("toolTip3Count>>"+toolTip3Count);*/
					 }else if(inputLine.contains("<div class=\"tooltip3\">") || toolTip3){
						 toolTip3 = true;
						 if(toolTip3Count >22){
							 toolTip3=false; 
						 }else if(toolTip3Count ==6){
							 fiveDayAvgVolume = inputLine.trim();
							 fiveDayAvgVolume = fiveDayAvgVolume.substring(fiveDayAvgVolume.indexOf("<strong>"),fiveDayAvgVolume.lastIndexOf("</strong>"));
							 fiveDayAvgVolume = fiveDayAvgVolume.replace("<strong>", "");
							 fiveDayAvgVolume = fiveDayAvgVolume.replace(",", "");
					//		 System.out.println("5 Day avg Volume>>"+fiveDayAvgVolume);
						 }else if(toolTip3Count ==14){
							 tenDayAvgVolume = inputLine.trim();
							 tenDayAvgVolume = tenDayAvgVolume.substring(tenDayAvgVolume.indexOf("<strong>"),tenDayAvgVolume.lastIndexOf("</strong>"));
							 tenDayAvgVolume = tenDayAvgVolume.replace("<strong>", "");
							 tenDayAvgVolume = tenDayAvgVolume.replace(",", "");
						//	 System.out.println("10 Day avg Volume>>"+tenDayAvgVolume); 
						 }else if(toolTip3Count ==22){
							 thirtyDayAvgVolume = inputLine.trim();
							 thirtyDayAvgVolume = thirtyDayAvgVolume.substring(thirtyDayAvgVolume.indexOf("<strong>"),thirtyDayAvgVolume.lastIndexOf("</strong>"));
							 thirtyDayAvgVolume = thirtyDayAvgVolume.replace("<strong>", "");
							 thirtyDayAvgVolume = thirtyDayAvgVolume.replace(",", "");
							// System.out.println("30 Day avg Volume>>"+thirtyDayAvgVolume); 
						 }
						 toolTip3Count++;

						 if(fiveDayAvgVolume != null && tenDayAvgVolume!=null && thirtyDayAvgVolume!=null  ){
							 gainerBean.setCurrentDayVolume(currentDayVolume);
							 gainerBean.setFiveDayAvgVolume(fiveDayAvgVolume);
							 gainerBean.setTenDayAvgVolume(tenDayAvgVolume);
							 gainerBean.setThirtyDayAvgVolume(thirtyDayAvgVolume);
							 TopGainerBean bean = topGainerMap.get(url);
							 
							 gainerBean.setCompanyName(bean.getCompanyName());
							 gainerBean.setHigh(bean.getHigh());
							 gainerBean.setLow(bean.getLow());
							 gainerBean.setLastPrice(bean.getLastPrice());
							 gainerBean.setPrvClose(bean.getPrvClose());
							 gainerBean.setChange(bean.getChange());
							 gainerBean.setPercentGain(bean.getPercentGain());
							 gainerBean.setApi(url);
							// System.out.println(">5Day>>"+gainerBean.getFiveDayAvgVolume()+">10Day>>"+gainerBean.getTenDayAvgVolume()+">30Day>>"+gainerBean.getThirtyDayAvgVolume());
							 stockGainOnVolume.add(gainerBean);
							 fiveDayAvgVolume = null;
							 tenDayAvgVolume = null;
							 thirtyDayAvgVolume = null; 
						 }
					 }
			        }
				 toolTip3 = false;
				 toolTip3Count=0;
				 System.out.println(stockURLList.size()-count);
				 count++;
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				in.close();
			}
		}
		return stockGainOnVolume;
	}
}
