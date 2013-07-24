package com.amumtrade.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.amumtrade.bean.AMUMStockBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.dao.AMUMStockDAO;

public class AMUMStockRouter {

    private List<AMUMStockBean> beanList = new ArrayList<AMUMStockBean>();
    private String inputPath;
    private String outputPath;
    private double startRange;
    private double endRange;
    private String line = null;
    private List<String> lineItem  = null;
    private String path = null;
    
	public AMUMStockRouter(double startRange, double endRange, String inputPath, String outputPath) {
	this.startRange = startRange;
	this.endRange = endRange;
	this.inputPath = inputPath;
	this.outputPath = outputPath;
	}

	public void digest() throws IOException {
		BufferedWriter bwObj = null;
		try {
			path = outputPath+"_"+AMUMStockConstant.dateFormat.format(AMUMStockConstant.cal.getTime())+".csv";
			FileWriter fwo = new FileWriter( path, false );
			bwObj = new BufferedWriter( fwo );
			bwObj.write(getHeader());
			bwObj.newLine();
			System.out.println(getHeader());
			beanList =	readNASDAQFile();
			System.out.println(beanList.size());
			ExecutorService executor = Executors.newFixedThreadPool(10);
			int totalCount=0;
					
			for (AMUMStockBean stockBean : beanList) {
		
				if(stockBean.getLastSale()>= startRange && stockBean.getLastSale()<= endRange
						&& !stockBean.getSymbol().contains("/")
						&& !stockBean.getSymbol().contains("^")){
					stockBean.setTotalCount(totalCount++);		
					Runnable epsWorker = new AMUMStockDAO(stockBean,bwObj);
					executor.execute(epsWorker);	
				}
			}
			executor.shutdown();
		    while (!executor.isTerminated()) {
		    	 
	        }
	        System.out.println("\nFinished all threads");
	 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null)
			bwObj.close();
		}
		
	}

	
	private String getHeader() {
		return
		AMUMStockConstant.SYMBOL + AMUMStockConstant.COMMA+
		AMUMStockConstant.PRICE + AMUMStockConstant.COMMA+
		"Diluted EPS" + AMUMStockConstant.COMMA+
		"Operating Margin" + AMUMStockConstant.COMMA+
		"Return On Assets" + AMUMStockConstant.COMMA+
		"Return On Equity" + AMUMStockConstant.COMMA+
		"Revenue Per Share";
	}

	private List<AMUMStockBean> readNASDAQFile() throws IOException {
		AMUMStockBean bean;
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
				bean = new AMUMStockBean();
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
	
	
}
