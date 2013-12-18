package com.amumtrade.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.amumtrade.helper.AMUMStockHelper;

public class AMUMTimerStock {


		 
	    public static void main(String[] args) {
	        Timer timer = new Timer("AMUMStockTimer");
	        MyTask t = new MyTask();
	        timer.schedule(t, 0, 5000);
	 
	 
	    }
	}
	 
	class MyTask extends TimerTask {
	    //times member represent calling times.
	    private int times = 0;
	 
	 
	    public void run() {
	        times++;
	        if (times <= 1) {
	            System.out.println("I'm alive..."+times);
	            try {
					execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } else {
	            System.out.println("Timer stops now...");
	 
	            //Stop Timer.
	            this.cancel();
	        }
	    }


		private void execute()throws Exception {
			
			double startRange = 50.0;
			double endRange = 150.0;
			
			long startTime= System.currentTimeMillis();
			String fileName = "./config/amumMoney.csv";
			File newFile = null;
			BufferedWriter writer = null;
			
			 try {
				 AMUMStockHelper helper = new AMUMStockHelper();
				 Map<String, String> resMap  = helper.digest();
				 
				 newFile = new File(fileName);
				 writer = new BufferedWriter(new FileWriter(newFile));
				 writer.write("Company, Group, Prev Close (Rs), Current Price (Rs), Diff Price (Rs)");
				 writer.write("\n");
				 for (Map.Entry<String, String> entry : resMap.entrySet()) {
					 double currPrice = Double.valueOf(entry.getKey());
					 if(currPrice >= startRange && currPrice <=endRange){
						 writer.write(entry.getValue());
						 writer.write("\n");
					 }
				  //   System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
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
