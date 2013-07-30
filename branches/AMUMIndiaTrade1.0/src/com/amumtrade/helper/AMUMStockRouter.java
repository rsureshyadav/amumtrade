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
import com.amumtrade.dao.AMUMStockFilterDAO;
import com.amumtrade.dao.AMUMStockMetricsDAO;

public class AMUMStockRouter {

    private List<AMUMStockBean> beanList = new ArrayList<AMUMStockBean>();

    private double startRange;
    private double endRange;
    private String line = null;
    private List<String> lineItem  = null;
    private String outputPath;
    private FileWriter fwo = null;
    private BufferedWriter bwObj = null;
	
	public AMUMStockRouter(double startRange, double endRange) {
	this.startRange = startRange;
	this.endRange = endRange;
	}

	public void digest() throws IOException {
		
	    char alphabets;
		try {
		    outputPath = AMUMStockConstant.BSE_OUTPUT_PATH+"BSE_"+AMUMStockConstant.dateFormat.format(AMUMStockConstant.cal.getTime())+".csv";
			writeFile();
			ExecutorService executor = Executors.newFixedThreadPool(10);
			for( alphabets = 'A' ; alphabets <= 'Z' ; alphabets++ ){
					String httpUrl = AMUMStockConstant.MSN_URL.replace("@", String.valueOf(alphabets));
					System.out.println(httpUrl);
					Runnable worker = new AMUMStockFilterDAO(httpUrl, bwObj);
					executor.execute(worker);	
		   }
			executor.shutdown();
			while (!executor.isTerminated()) {
		    	 
		    }
			System.out.println("\nFinished all threads");
			if(bwObj!=null)
			bwObj.close();
			//filterFile();
			List<AMUMStockBean> filterList = filterFile();
			runKeyMetric(filterList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null)
			bwObj.close();
		}
		
	}

	private void runKeyMetric(List<AMUMStockBean> filterList) {
		int tmpCount =0;
		try {
			writeFile();
			bwObj.write(getMetricHeader());
			ExecutorService executor = Executors.newFixedThreadPool(10);
			for(AMUMStockBean bean : filterList){
				Runnable worker = new AMUMStockMetricsDAO(bean, bwObj);
				executor.execute(worker);
				tmpCount++;
			}
				
			executor.shutdown();
			while (!executor.isTerminated()) {
		    	 
		    }
			System.out.println("\nFinished runKeyMetric threads");
			bwObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private List<AMUMStockBean> filterFile() {
		List<AMUMStockBean> filterList = new ArrayList<AMUMStockBean>();
		try {
			List<AMUMStockBean> beanInputList = readFile();
			AMUMStockBean filterBean = null;
			writeFile();
			int count =0;
			for(AMUMStockBean bean : beanInputList){
				if(bean.getLastScalePrice() >= startRange && bean.getLastScalePrice() <= endRange){
					filterBean = new AMUMStockBean();
					count++;
					bwObj.write("\n");
					bwObj.write(bean.getStockName()+","+ bean.getLastScalePrice()+","+ bean.getStockURL());
					
					filterBean.setStockName(bean.getStockName());
					filterBean.setLastScalePrice(bean.getLastScalePrice());
					filterBean.setStockURL(bean.getStockURL());
					filterList.add(filterBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filterList;
	}

	private void writeFile() throws IOException{
		try {
			fwo = new FileWriter( outputPath, false );
			bwObj = new BufferedWriter( fwo );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private List<AMUMStockBean> readFile() throws IOException {
		AMUMStockBean bean;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(outputPath));
			int count = 0;
			while ((line = br.readLine()) != null && line.length()>0) {
				/*if (count == 0) {
					// skip first line to ignore the header
					count++;
					continue;
				}
				count++;*/
				bean = new AMUMStockBean();
				lineItem = Arrays.asList(line.split("\\s*,\\s*"));
				System.out.println(lineItem);
				bean.setStockName(lineItem.get(0));
				try {
					bean.setLastScalePrice(Double.valueOf(lineItem.get(1)));
				} catch (Exception e) {
				//	System.out.println("Error in readFile(),"+bean.getStockName());
					throw new Exception("Error in List<AMUMStockBean> readFile(),"+bean.getStockName());
					//e.printStackTrace();
				}
				bean.setStockURL(lineItem.get(2));
				beanList.add(bean);
			}
			
		} catch (Exception e) {
			
			System.out.println("StockRouteHelper exception occured: "+e.getMessage());
			e.printStackTrace();
		}finally{
			if(br != null){
				br.close();
			}
		}
	
		return beanList;
	}
	
	private String getMetricHeader(){
		return "Name,Last Scale Price, P/E Ratio, EPS, Revenue";
	}

}
