package com.amumtrade.handler;

import java.io.File;

import com.amumtrade.helper.StockRouteHelper;

public class StockHandler {

	 double startRange;
	 double endRange;
	 String exchName;
    

	public StockHandler(double startRange, double endRange, 
			String exchName) {
		this.startRange = startRange;
		this.endRange = endRange;
		this.exchName = exchName;
	}

	public void execute(String nsdqURL, String inputPath, String outputPath) throws Exception{
		long startTime= System.currentTimeMillis();

		 try {
				File f = new File(inputPath);
				if(f.exists()) { 
					System.out.println(exchName+" exchange file avilable");
				}else{
					removeFile("config/input/");
					System.out.println(exchName+" exchange file not avilable, downloading .csv file...");
					DownloadLinkHandler.execute(nsdqURL,inputPath);
				}
				
				
			 StockRouteHelper helper = new StockRouteHelper(inputPath, outputPath);
			 helper.digest();

		} catch (Exception e) {
			e.getLocalizedMessage();
			//e.getMessage();
			//e.printStackTrace();
		}
		
		 System.out.println("Completed "+exchName+" quote output file...");
		    long endTime= System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution of "+exchName+" total time  ==> "+ h +" : "+ m +" : "+ s);
	}
	
	private void removeFile(String path) throws Exception{
		File f = new File(path);

		if (f.isDirectory()) {
			for (File c : f.listFiles())
				c.delete();
		}

	}


}
