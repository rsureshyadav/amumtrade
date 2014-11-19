package com.amumtrade.others;

import org.junit.Test;

import com.amumtrade.handler.BSENSEHandler;

public class RunBSENSEStock {
	
	@Test
	public void runStock() throws Exception{
		BSENSEHandler handler = new BSENSEHandler();
		handler.execute();
	}

}
