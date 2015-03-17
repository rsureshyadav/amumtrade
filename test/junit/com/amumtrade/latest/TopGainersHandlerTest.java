package com.amumtrade.latest;

import org.junit.Test;

import com.amumtrade.handler.SentimeterChartHandler;
import com.amumtrade.handler.TopGainersHandler;

public class TopGainersHandlerTest {

	@Test
	public void fetchBuyersStock() throws Exception{
		/*TopGainersHandler handler = new TopGainersHandler();
		handler.execute();*/
		SentimeterChartHandler sch = new SentimeterChartHandler();
		sch.execute();
		}
}
