package com.amumtrade.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RunStockTimer {

	public static void main(String str[]){
	
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			Date before = sdf.parse("19/11/2014 07:00");
			Date after = sdf.parse("19/11/2014 07:30");
			Date toCheck = sdf.parse("19/11/2014 06:09");
			//is toCheck between the two?
			boolean isAvailable = (before.getTime() < toCheck.getTime()) && after.getTime() > toCheck.getTime();
			if(isAvailable){
				System.out.println("Hellow World!!!!");
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
