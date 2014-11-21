package com.amumtrade.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RunStockTimer extends TimerTask{


	@Override
	public void run() {
		System.out.println("Start time:" + new Date());
		doSomeWork();
		System.out.println("End time:" + new Date());
	}

	// simulate a time consuming task
	private void doSomeWork() {
		try {

			Thread.sleep(10000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		TimerTask timerTask = new RunStockTimer();
		// running timer task as daemon thread
		long interval = 1000 * 60 * 120;//run for every two hours
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, interval, 10 * 1000);
		System.out.println("TimerTask begins! :" + new Date());
		// cancel after sometime
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		System.out.println("TimerTask cancelled! :" + new Date());
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
