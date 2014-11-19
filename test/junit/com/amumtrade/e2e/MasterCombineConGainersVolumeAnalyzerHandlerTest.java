package com.amumtrade.e2e;

import org.junit.Test;

import com.amumtrade.handler.EPSOnConGainersHandler;
import com.amumtrade.handler.MasterCombineConGainersVolumeAnalyzerHandler;

public class MasterCombineConGainersVolumeAnalyzerHandlerTest {
	@Test
	public void executeStockMarketStat() throws Exception{
		long startTime= System.currentTimeMillis();
		
		MasterCombineConGainersVolumeAnalyzerHandler mccvah = new MasterCombineConGainersVolumeAnalyzerHandler();
		mccvah.execute();
		
		EPSOnConGainersHandler fah = new EPSOnConGainersHandler();
		fah.execute();
		
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			 System.out.println("Execution total time  ==> "+ h +" : "+ m +" : "+ s);
	}
}