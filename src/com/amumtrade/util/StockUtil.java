package com.amumtrade.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockUtil {

	public static void initiateEmail(String filePath, long startTime ){
		String startDateTimeLog = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			startDateTimeLog = dateFormat.format(date);
			/*SendAttachmentInEmail email = new SendAttachmentInEmail();
			email.execute(filePath, startDateTimeLog, getTotalExecutionTime(startTime));*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getTotalExecutionTime(long startTime){
		String totalExecutionTime = null;
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			totalExecutionTime =  "Total Execution Time: "+ h +" : "+ m +" : "+ s;
		return totalExecutionTime;
	}
}
