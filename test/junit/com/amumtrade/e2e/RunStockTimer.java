package com.amumtrade.e2e;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RunStockTimer extends TimerTask{
	public static void main(String[] args) {
		long interval = 1000 * 60 * 1;//run for every three hours
	      // creating timer task, timer
	      TimerTask tasknew = new RunStockTimer();
	      Timer timer = new Timer();
	      
	      // scheduling the task at fixed rate delay
	      timer.scheduleAtFixedRate(tasknew,interval,interval);      
	   }
	@Override
	public void run() {
		 System.out.println("AMUM Trade Execution Started @"+ new Date());   
		MasterAMUMTradeTest amumtrade= new MasterAMUMTradeTest();
		try {
			amumtrade.executeStockMarketStat();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
