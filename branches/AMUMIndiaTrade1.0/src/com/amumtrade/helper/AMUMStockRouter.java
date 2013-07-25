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

    private double startRange;
    private double endRange;
    private String line = null;
    private List<String> lineItem  = null;
    private String outputPath;
    
	public AMUMStockRouter(double startRange, double endRange) {
	this.startRange = startRange;
	this.endRange = endRange;
	}

	public void digest() throws IOException {
		FileWriter fwo = null;
		BufferedWriter bwObj = null;
	      char alphabets;
		try {
		    outputPath = AMUMStockConstant.BSE_OUTPUT_PATH+"_"+AMUMStockConstant.dateFormat.format(AMUMStockConstant.cal.getTime())+".csv";
			fwo = new FileWriter( outputPath, false );
			bwObj = new BufferedWriter( fwo );
			bwObj.write("Stock Name,Last Scale Price, Stock URL");

			ExecutorService executor = Executors.newFixedThreadPool(10);
			int tmpCount =0;
			for( alphabets = 'A' ; alphabets <= 'Z' ; alphabets++ ){
				tmpCount++;
				if(tmpCount==1){
					
					String httpUrl = AMUMStockConstant.MSN_URL.replace("@", String.valueOf(alphabets));
					Runnable worker = new AMUMStockDAO(httpUrl, bwObj);
					executor.execute(worker);	
				}
		   }
			executor.shutdown();
			while (!executor.isTerminated()) {
		    	 
		    }
			System.out.println("\nFinished all threads");
			fileCompare();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null)
			bwObj.close();
		}
		
	}

	private List<AMUMStockBean> fileCompare() throws IOException {
		AMUMStockBean bean;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(AMUMStockConstant.BSE_A_INPUT_PATH));
			int count = 0;
			while ((line = br.readLine()) != null) {
				if (count == 0) {
					// skip first line to ignore the header
					count++;
					continue;
				}
				count++;
				bean = new AMUMStockBean();
				lineItem = Arrays.asList(line.split("\\s*,\\s*"));
				System.out.println("[ "+count+" ] ==> "+lineItem);
				bean.setScripCode(lineItem.get(0));
				bean.setScripId(lineItem.get(1));
				bean.setScripName(lineItem.get(2));
				bean.setStatus(lineItem.get(3));
				bean.setGroup(lineItem.get(4));
				bean.setFaceValue(lineItem.get(5));
				bean.setISINNo(lineItem.get(6));
				bean.setIndustry(lineItem.get(7));
				bean.setInstrument(lineItem.get(8));
				beanList.add(bean);
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
	
}
