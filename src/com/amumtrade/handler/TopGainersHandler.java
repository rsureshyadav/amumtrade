package com.amumtrade.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amumtrade.bean.TopGainerBean;
import com.amumtrade.constant.FileNameConstant;

public class TopGainersHandler {
	public static Set<String> nameSet = new HashSet<String>();
	public static String stockURL = FileNameConstant.TOP_GAINERS_URL;
	 static Map<String, String> topGainerList= new HashMap<String, String>();
	public void execute() throws Exception{
		long startTime= System.currentTimeMillis();
		try {
			prevTopGainerFile(FileNameConstant.TOP_GAINERS,FileNameConstant.DEST_TOP_GAINER_PATH);
			//http://www.moneycontrol.com/stocks/marketstats/gainerloser.php?optex=NSE&opttopic=topgainers&index=-2&more=true
			//http://www.moneycontrol.com/stocks/marketstats/onlysellers.php
			//http://www.moneycontrol.com/stocks/marketstats/bsegainer/index.html
			//http://www.moneycontrol.com/stocks/marketstats/bsemact1/index.html
			//http://www.moneycontrol.com/stocks/marketstats/nse_vshockers/index.html
			getTopGainersList(stockURL);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
		
	}
	public static List<TopGainerBean>  getTopGainersList(String URL) throws IOException{
		//getTopGainer(URL);
		List<TopGainerBean> gainersBeanList = getTopGainersCsvToBean();
		return gainersBeanList;
		
	}
	
	private static List<TopGainerBean> getTopGainersCsvToBean() throws IOException {
		BufferedReader br = null;
		List<TopGainerBean>  gainerBeanList= new ArrayList<TopGainerBean>();
		TopGainerBean gainerBean = null;
		String csvFile =FileNameConstant.TOP_GAINERS;
		String line = "";
		String cvsSplitBy = ",";
		int skipFirstLineHeader=0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(skipFirstLineHeader!=0){ 
					gainerBean = new TopGainerBean();
					String[] topGainers = line.split(cvsSplitBy);
						gainerBean.setCompanyName(topGainers[0]);
						gainerBean.setHigh(topGainers[1]);
						gainerBean.setLow(topGainers[2]);
						gainerBean.setLastPrice(topGainers[3]);
						gainerBean.setPrvClose(topGainers[4]);
						gainerBean.setChange(topGainers[5]);
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
	private void prevTopGainerFile(String srcPath, String destPath)throws IOException {
		File source = new File(srcPath);
		File dest = new File(destPath);

		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	public static  Map<String, String> getTopGainerList() throws Exception{
		getTopGainer(stockURL);
		
		return topGainerList;
	}

	private static void getTopGainer(String stockURL) throws IOException {
		String companyName=null;
		String api=null;
		String high=null;
		String low=null;
		String lastPrice=null;
		String prvClose=null;
		String change=null;
		String percentGain=null;
		String postiveBreakOut=null;
		BufferedWriter bwObj = null;
		BufferedReader in = null;
		FileWriter fwo = new FileWriter( FileNameConstant.TOP_GAINERS, false );
		try {
			PositiveBreakoutHandler breakoutHandler = new PositiveBreakoutHandler();
			//Set<String> positiveBreakoutSet = breakoutHandler.execute();
			
			 URL website = new URL(stockURL);
			 bwObj = new BufferedWriter( fwo );  
			 bwObj.write(FileNameConstant.TOP_GAINERS_HEADER+"\n");
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
				        String inputLine;
		        		boolean highLastPriceflag=false;
		        		boolean changePercentGain=false;
				        while ((inputLine = in.readLine()) != null)
				        {
				        	if(inputLine.contains("<td align=\"left\" class=\"brdrgtgry\">")){
				        		companyName = inputLine.trim();
				        		companyName=companyName.substring(companyName.indexOf("<b>"));
				        		companyName=companyName.replace("<b>", "");
				        		companyName=companyName.replace("</b></a></td>", "");
				        		
				        		api = inputLine.trim();
				        		api = api.substring(api.indexOf("<a href="), api.lastIndexOf("class='bl_12'"));
				        		api = api.replace("<a href=", "");
				        		api = api.replace("'", "");
				        		//String apiKey = StockUtil.getUrlToKeyAPI(api);
				        		/*if(positiveBreakoutSet.contains(apiKey)){
				        			postiveBreakOut = AMUMStockConstant.YES;
				        		}else{
				        			postiveBreakOut = "";
				        		}*/
				        	}else if(inputLine.contains("<td width=\"13%\" align=\"right\" class=\"brdrgtgry\">")){
				        		if(!highLastPriceflag){
				        			high=inputLine.trim();
				        			high=high.substring(high.indexOf("brdrgtgry\">"));
				        			high=high.replace("brdrgtgry\">", "");
				        			high=high.replace("</td>", "");
				        			high=high.replace(",", "");
				        			high=high.replace(".00", "");
				        			highLastPriceflag=true;
				        		}else if(highLastPriceflag){
				        			prvClose=inputLine.trim();
				        			prvClose=prvClose.substring(prvClose.indexOf("brdrgtgry\">"));
				        			prvClose=prvClose.replace("brdrgtgry\">", "");
				        			prvClose=prvClose.replace("</td>", "");
				        			prvClose=prvClose.replace(",", "");
				        			prvClose=prvClose.replace(".00", "");
				        			highLastPriceflag=false;
				        		}
				        	}else if(inputLine.contains("<td width=\"13%\" align=\"right\" class=\"brdrgtgry\" >")){
				        		low=inputLine.trim();
				        		low=low.substring(low.indexOf("brdrgtgry\" >"));
				        		low=low.replace("brdrgtgry\" >", "");
				        		low=low.replace("</td>", "");
				        		low=low.replace(",", "");
				        		low=low.replace(".00", "");
				        	}else if(inputLine.contains("<td width=\"14%\" align=\"right\" class=\"brdrgtgry\">")){
				        		lastPrice=inputLine.trim();
				        		lastPrice=lastPrice.substring(lastPrice.indexOf("brdrgtgry\">"));
				        		lastPrice=lastPrice.replace("brdrgtgry\">", "");
				        		lastPrice=lastPrice.replace("</td>", "");
				        		lastPrice=lastPrice.replace(",", "");
				        		lastPrice=lastPrice.replace(".00", "");
				        	}else if(inputLine.contains("<td width=\"14%\" align=\"right\" class=\"brdrgtgry\" style=\"color:#008E00\">")){ 
				        		if(!changePercentGain){
				        			change=inputLine.trim();
				        			change=change.substring(change.indexOf("color:#008E00\">"));
				        			change=change.replace("color:#008E00\">", "");
				        			change=change.replace("</td>", "");
				        			change=change.replace(",", "");
				        			change=change.replace(".00", "");
				        			changePercentGain=true;
				        		}else if(changePercentGain){
				        			percentGain=inputLine.trim();
				        			percentGain=percentGain.substring(percentGain.indexOf("color:#008E00\">"));
				        			percentGain=percentGain.replace("color:#008E00\">", "");
				        			percentGain=percentGain.replace("</td>", "");
				        			percentGain=percentGain.replace(",", "");
				        			percentGain=percentGain.replace(".00", "");
				        			changePercentGain=false;
				        		}
				        		String finalTopGainer= companyName+","+high+","+ low+","+ lastPrice+","+ prvClose+","+ change+","+percentGain+","+api;
				        		
				        		
				        		if(nameSet.add(companyName)){
				        			bwObj.write(finalTopGainer+"\n");
				        			topGainerList.put(lastPrice, finalTopGainer);
				        		}
				        		
			        		}
				        }	
						 System.out.println("Total Top Gainer Count ==>"+ nameSet.size());

				        in.close();
					    
		
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(bwObj!=null){
				bwObj.close();
			}
			if(in !=null){
				in.close();
			}
		}
		
	}
	
}
