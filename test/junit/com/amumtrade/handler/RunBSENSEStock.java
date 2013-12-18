package com.amumtrade.handler;

import org.junit.Test;

public class RunBSENSEStock {
	
	@Test
	public void runStock() throws Exception{
		BSENSEHandler handler = new BSENSEHandler();
		handler.execute();
	}

}
