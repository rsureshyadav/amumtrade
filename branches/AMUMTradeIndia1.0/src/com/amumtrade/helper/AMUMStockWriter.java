package com.amumtrade.helper;

public class AMUMStockWriter {

	protected static StringBuffer AmumStockBuffer = new StringBuffer();

	public static StringBuffer getAmumStockBuffer() {
		return AmumStockBuffer;
	}

	public static void setAmumStockBuffer(StringBuffer amumStockBuffer) {
		AmumStockBuffer = amumStockBuffer;
	}
	
	
}
