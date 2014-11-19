package com.amumtrade.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockUtil {

	public static void initiateEmail(String filePath){
		String startDateTimeLog = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date));  
			startDateTimeLog = dateFormat.format(date);
			SendAttachmentInEmail email = new SendAttachmentInEmail();
			email.execute(filePath, startDateTimeLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
