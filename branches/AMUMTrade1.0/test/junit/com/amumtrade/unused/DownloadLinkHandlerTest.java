package com.amumtrade.unused;

import org.junit.Test;

import com.amumtrade.constant.StringConstant;
import com.amumtrade.handler.DownloadLinkHandler;
 

public class DownloadLinkHandlerTest {

	@Test
	public void executeTest() throws Exception{
		DownloadLinkHandler.execute(StringConstant.AMEX_URL,StringConstant.AMEX_INPUT_PATH);
		DownloadLinkHandler.execute(StringConstant.NASDAQ_URL,StringConstant.NASDAQ_INPUT_PATH);
		DownloadLinkHandler.execute(StringConstant.NYSE_URL,StringConstant.NYSE_INPUT_PATH);

	}
}
