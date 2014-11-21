package com.amumtrade.e2e;

import org.junit.Test;

import com.amumtrade.handler.EPSOnConGainersHandler;
import com.amumtrade.handler.EPSOnTopGainersHandler;
import com.amumtrade.handler.LastThreeConcurrentGainersVolumeAnalyzerHandler;
import com.amumtrade.handler.MasterAllConGainersVolumeAnalyzerHandler;
import com.amumtrade.handler.MasterCombineTopConGainersHandler;
import com.amumtrade.handler.TopGainerVolumeAnaylzerHandler;
import com.amumtrade.handler.TopGainersHandler;

public class MasterAMUMTradeTest {
	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		//Last Three ConcurrentGainers
		LastThreeConcurrentGainersVolumeAnalyzerHandler cgva = new LastThreeConcurrentGainersVolumeAnalyzerHandler();
		cgva.execute();
		EPSOnConGainersHandler fah = new EPSOnConGainersHandler();
		fah.execute(startTime);
		
		//TopGainers
		TopGainersHandler topGainersHandler = new TopGainersHandler();
		topGainersHandler.execute();
		TopGainerVolumeAnaylzerHandler volumeAnaylzer = new TopGainerVolumeAnaylzerHandler();
		volumeAnaylzer.execute();
		EPSOnTopGainersHandler epsTopGainer = new EPSOnTopGainersHandler();
		epsTopGainer.execute(startTime);
		
		//Combined ConcurrentGainers & TopGainers  
		MasterCombineTopConGainersHandler ctcgh = new MasterCombineTopConGainersHandler();
		ctcgh.execute(startTime);
		
		//All ConcurrentGainers
		MasterAllConGainersVolumeAnalyzerHandler mccvah = new MasterAllConGainersVolumeAnalyzerHandler();
		mccvah.execute(startTime);
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Total Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}
