package com.amumtrade.others;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.handler.TopGainersHandler;
import com.amumtrade.helper.AMUMMoneyControlHelper;

public class AMUMMoneyControlHandler {

	public void execute() throws Exception{
		double startRange = 5.0;
		double endRange = 50.0;

		double startFiveTwoLowRange = 5.0;
		double endFiveTwoLowRange = 150.0;
		
		long startTime= System.currentTimeMillis();
		String fileName = "./config/amumMoneyControl_"+AMUMStockConstant.timerDateFormat.format(AMUMStockConstant.cal.getTime())+".csv";;
		File newFile = null;
		BufferedWriter writer = null;
		
		 try {
			 AMUMMoneyControlHelper helper = new AMUMMoneyControlHelper();
			 Map<String, String> netProfitMap  = helper.digestNetProfit();
			 Map<String, String> fiftyTwoWeekAHighMap  = helper.digestFiftyTwoWeekHigh();
			 Map<String, String> fiftyTwoWeekALowMap  = helper.digestFiftyTwoWeekLow();
			 newFile = new File(fileName);
			 writer = new BufferedWriter(new FileWriter(newFile));
			 writer.write("Top 100 Companies Net Profit...");
			 writer.write("\n");
			 writer.write("Company, Last Price, Change (Rs)");
			 writer.write("\n");
			 for (Map.Entry<String, String> entry : netProfitMap.entrySet()) {
				 double currPrice = Double.valueOf(entry.getKey());
				 if(currPrice >= startRange && currPrice <=endRange){
					 writer.write(entry.getValue());
					 writer.write("\n");
				 }
			 }
			 writer.write("\n");
			 writer.write("\n");
			 writer.write("\n");

			 writer.write("Top Gainers");
			 writer.write("\n");
			 writer.write("Company Name,High,Low,Last Price,Prv Close,Change,% Gain");
			 writer.write("\n");
			 Map<String, String> topGainerList= TopGainersHandler.getTopGainerList();
		/*	 for(String lastPrice : topGainerList.keySet()){
				 System.out.println(">>>>>>>>>>>>>>>>"+lastPrice);
			 }*/
			 
			 for (Map.Entry<String, String> lastPriceEntry : topGainerList.entrySet()) {
				 double price = Double.valueOf(lastPriceEntry.getKey());
				 if(price >= startRange && price <=endRange){
					 writer.write(lastPriceEntry.getValue());
					 writer.write("\n");
				 }
			 }
			 
			 writer.write("\n");
			 writer.write("\n");
			 writer.write("\n");

			 writer.write("52 Week High...");
			 writer.write("\n");
			 writer.write("Company, Group, 52 Wk High, Intra High, Last Close, Price Up");
			 writer.write("\n");
			 for (Map.Entry<String, String> entry : fiftyTwoWeekAHighMap.entrySet()) {
				 double currPrice = Double.valueOf(entry.getKey());
				 if(currPrice >= startRange && currPrice <=endRange){
					 writer.write(entry.getValue());
					 writer.write("\n");
				 }
			 }
			 
			 writer.write("\n");
			 writer.write("\n");
			 writer.write("\n");

			 writer.write("52 Week Low...");
			 writer.write("\n");
			 writer.write("Company, Group, 52 Wk Low, Intra High, Last Close, Price Up");
			 writer.write("\n");
			 for (Map.Entry<String, String> entry : fiftyTwoWeekALowMap.entrySet()) {
				 double currPrice = Double.valueOf(entry.getKey());
				 if(currPrice >= startFiveTwoLowRange && currPrice <=endFiveTwoLowRange){
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
