package com.amumtrade.handler;

import java.io.File;

import com.amumtrade.helper.AMUMStockRouter;

public class AMUMBSEStockHandler {
	
	private String exchName;
	double startRange;
	double endRange; 
	 public AMUMBSEStockHandler(String exchName) {
		this.exchName = exchName;
	}

	public void execute(double startRange, double endRange) throws Exception{
		long startTime= System.currentTimeMillis();
		this.startRange = startRange;
		this.endRange = endRange;
		 try {
			 AMUMStockRouter helper = new AMUMStockRouter(startRange, endRange );
			 helper.digest();

		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		
		 System.out.println("Completed "+exchName+" quote output file...");
		    long endTime= System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution of "+exchName+" total time  ==> "+ h +" : "+ m +" : "+ s);
	}
	
	

}
