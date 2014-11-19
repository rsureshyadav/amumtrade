package com.amumtrade.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RunStockTimer {

	public static void main(String str[]){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));

		try {
		    String string1 = "07:05:00";
		    Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
		    Calendar calendar1 = Calendar.getInstance();
		    calendar1.setTime(time1);

		    String string2 = "07:07:00";
		    Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.setTime(time2);
		    calendar2.add(Calendar.DATE, 1);

		    /*String someRandomTime = "01:00:00";
		    Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
		    Calendar calendar3 = Calendar.getInstance();
		    calendar3.setTime(d);
		    calendar3.add(Calendar.DATE, 1);*/

		   /* Date x = calendar3.getTime();
		    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
		        //checkes whether the current time is between 14:49:00 and 20:11:13.
		        System.out.println(true);
		    }*/
		    
		    Date x = calendar2.getTime();
		    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
		        //checkes whether the current time is between 14:49:00 and 20:11:13.
		        System.out.println("loop execute");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
