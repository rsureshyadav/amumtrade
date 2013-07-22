package com.amumtrade.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.StockBean;
import com.amumtrade.dao.EPSTTMServiceDao;

public class StockRouteHelper {

    private List<StockBean> beanList = new ArrayList<StockBean>();
    private String inputPath;
    private String outputPath;
    private double startRange;
    private double endRange;
    private String line = null;
    private List<String> lineItem  = null;

	public StockRouteHelper(double startRange, double endRange, String inputPath, String outputPath) {
	this.startRange = startRange;
	this.endRange = endRange;
	this.inputPath = inputPath;
	this.outputPath = outputPath;
	}

	public void digest() {
		try {
			beanList =	filterStock ();
			ExecutorService executor = Executors.newFixedThreadPool(beanList.size());
			int count =1;
			for (StockBean stockBean : beanList) {
				if(stockBean.getLastSale()>= startRange && stockBean.getLastSale()<= endRange){
					Runnable epsWorker = new EPSTTMServiceDao(stockBean);
					executor.execute(epsWorker);
					count++;
				}
			}
			executor.shutdown();
		    while (!executor.isTerminated()) {
		    	 
	        }
	        System.out.println("\nFinished all threads");
			//shutdown(eService, "EPS TTM Service");
			//eService.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	private List<StockBean> filterStock() throws IOException {
		StockBean bean;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputPath));
			int count = 0;
			while ((line = br.readLine()) != null) {
				if (count == 0) {
					// skip first line to ignore the header
					count++;
					continue;
				}
				bean = new StockBean();
				line = validateLine(line);
				lineItem = Arrays.asList(line.split("\\s*,\\s*"));
				bean.setSymbol(lineItem.get(0));
				bean.setName(lineItem.get(1));
				bean.setLastSale(validateLastSale(lineItem.get(2)));
				bean.setMarketCap(lineItem.get(3));
				bean.setSector(lineItem.get(6));
				bean.setIndustry(lineItem.get(7));
				bean.setSummaryQuote(lineItem.get(8));
				beanList.add(bean);

				count++;
			}
			
		} catch (Exception e) {
			System.out.println("StockRouteHelper exception occured: "+e.getMessage());
		}finally{
			if(br != null){
				br.close();
			}
		}
	
		return beanList;
	}
	
	private String validateLine(String line) {
		StringBuffer newLine = new StringBuffer();
		for (String s : line.split("\",")) {
			s = s.replace(",", "");
			s = s.replace("\"", "");
			newLine.append(s + ",");
		}
		return newLine.toString();
	}
	
	private double validateLastSale(String lastSale) {
		double value;
		if (lastSale.equalsIgnoreCase("n/a")) {
			value = 0;
		} else {
			value = Double.valueOf(lastSale);
		}
		return value;
	}
	
	private void shutdown(ExecutorService executor, String label) {
		executor.shutdown();
        while (!executor.isTerminated()) {
 
        }
        System.out.println("\nFinished "+label+" threads execution");
        
		
	}
}
