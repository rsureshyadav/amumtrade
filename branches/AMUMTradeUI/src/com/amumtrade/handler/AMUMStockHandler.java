package com.amumtrade.handler;

import java.io.File;

import com.amumtrade.helper.AMUMStockRouter;

public class AMUMStockHandler {
	
	private String exchName;

	 public AMUMStockHandler(String exchName) {
		this.exchName = exchName;
	}

	public void execute(double startRange, double endRange, String nsdqURL, String inputPath, String outputPath) throws Exception{
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
				
			 outputPath = outputPath.replace("@", exchName);
			// System.out.println(">>"+outputPath);
			 AMUMStockRouter helper = new AMUMStockRouter(startRange, endRange ,inputPath, outputPath);
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
	
	private void removeFile(String path) throws Exception{
		File f = new File(path);

		if (f.isDirectory()) {
			for (File c : f.listFiles())
				c.delete();
		}

	}


}
