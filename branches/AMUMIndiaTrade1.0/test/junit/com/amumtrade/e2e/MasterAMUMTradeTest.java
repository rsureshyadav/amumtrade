package com.amumtrade.e2e;

import org.junit.Test;

import com.amumtrade.handler.ConcurrentGainersAllHandler;
import com.amumtrade.handler.ConcurrentGainersEPSHandler;
import com.amumtrade.handler.CurrentConcurrentGainersTopGainersHandler;
import com.amumtrade.handler.CurrentConcurrentGainersVolumeHandler;
import com.amumtrade.handler.TopGainersEPSHandler;
import com.amumtrade.handler.TopGainersHandler;
import com.amumtrade.handler.TopGainersVolumeHandler;

public class MasterAMUMTradeTest {
	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		//Current ConcurrentGainers
		CurrentConcurrentGainersVolumeHandler volumeHandler = new CurrentConcurrentGainersVolumeHandler();
		volumeHandler.execute();
		ConcurrentGainersEPSHandler epsHandler = new ConcurrentGainersEPSHandler();
		epsHandler.execute(startTime);
	
		//TopGainers
		TopGainersHandler topGainersHandler = new TopGainersHandler();
		topGainersHandler.execute();
		TopGainersVolumeHandler volumeAnaylzer = new TopGainersVolumeHandler();
		volumeAnaylzer.execute();
		TopGainersEPSHandler epsTopGainer = new TopGainersEPSHandler();
		epsTopGainer.execute(startTime);
		
		//Combined ConcurrentGainers & TopGainers  
		CurrentConcurrentGainersTopGainersHandler combineHandler = new CurrentConcurrentGainersTopGainersHandler();
		combineHandler.execute(startTime);
	
		//All ConcurrentGainers
		ConcurrentGainersAllHandler allConcurrentHandler = new ConcurrentGainersAllHandler();
		allConcurrentHandler.execute(startTime);
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Total Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
