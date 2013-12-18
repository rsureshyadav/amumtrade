package com.amumtrade.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.helper.AMUMMoneyControlHelper;

public class AMUMMoneyControlHandler {

	public void execute() throws Exception{
		double startRange = 100.0;
		double endRange = 250.0;
		
		long startTime= System.currentTimeMillis();
		String fileName = "./config/amumMoneyControl_"+AMUMStockConstant.timerDateFormat.format(AMUMStockConstant.cal.getTime())+".csv";;
		File newFile = null;
		BufferedWriter writer = null;
		
		 try {
			 AMUMMoneyControlHelper helper = new AMUMMoneyControlHelper();
			 Map<String, String> resMap  = helper.digest();
			 
			 newFile = new File(fileName);
			 writer = new BufferedWriter(new FileWriter(newFile));
			 writer.write("Company, Last Price, Change (Rs)");
			 writer.write("\n");
			 for (Map.Entry<String, String> entry : resMap.entrySet()) {
				 double currPrice = Double.valueOf(entry.getKey());
				 if(currPrice >= startRange && currPrice <=endRange){
					 writer.write(entry.getValue());
					 writer.write("\n");
				 }
			 }


		} catch (Exception e) {
			e.getLocalizedMessage();
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		
		    long endTime= System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Total execute time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
