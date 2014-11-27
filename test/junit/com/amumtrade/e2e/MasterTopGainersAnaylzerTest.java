package com.amumtrade.e2e;

import org.junit.Test;

import com.amumtrade.handler.TopGainersEPSHandler;
import com.amumtrade.handler.TopGainersVolumeHandler;
import com.amumtrade.handler.TopGainersHandler;

public class MasterTopGainersAnaylzerTest {
	@Test
	public void executeStockVolumeAnaylzer() throws Exception{
		long startTime= System.currentTimeMillis();
		//This will generate CSV file of Top Gainers
		TopGainersHandler topGainersHandler = new TopGainersHandler();
		topGainersHandler.execute();
		//This will add the volume for the Top Gainers
		TopGainersVolumeHandler volumeAnaylzer = new TopGainersVolumeHandler();
		volumeAnaylzer.execute();
		//This will compare with EPS
		TopGainersEPSHandler epsTopGainer = new TopGainersEPSHandler();
		epsTopGainer.execute(startTime);
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
