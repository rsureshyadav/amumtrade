package com.amumtrade.handler;

import org.junit.Test;

import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.email.CsvToEmailBody;

public class CsvToEmailBodyTest {

	@Test
	public void executeStock() throws Exception{
		
		CsvToEmailBody csvToemail = new  CsvToEmailBody();
		csvToemail.execute(FileNameConstant.ALL_CURRENT_CONCURRENT_GAINER);
	}
}
