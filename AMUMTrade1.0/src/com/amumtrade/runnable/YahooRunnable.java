package com.amumtrade.runnable;

import static com.amumtrade.constant.StringConstant.BUY;
import static com.amumtrade.constant.StringConstant.HOLD;
import static com.amumtrade.constant.StringConstant.NO_OPINION;
import static com.amumtrade.constant.StringConstant.NO_RATING;
import static com.amumtrade.constant.StringConstant.RATING_ADDI_END_TOKEN;
import static com.amumtrade.constant.StringConstant.RATING_ADDI_START_TOKEN;
import static com.amumtrade.constant.StringConstant.RATING_END_TOKEN;
import static com.amumtrade.constant.StringConstant.RATING_START_TOKEN;
import static com.amumtrade.constant.StringConstant.SELL;
import static com.amumtrade.constant.StringConstant.STRONG_BUY;
import static com.amumtrade.constant.StringConstant.STRONG_SELL;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.amumtrade.bean.StockBean;
import com.amumtrade.dao.StockEngineDAO;
import com.amumtrade.util.StockUtil;

public class YahooRunnable  extends BaseRun {



	public YahooRunnable(String urlString, String symbol,
			HashMap<String, String> ratingMap) {
		super(urlString, symbol, ratingMap);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		 String rating = null;
		try {
				URL url = new URL(urlString);
				URLConnection ucon = url.openConnection();
				String htmlContents = getResponseData(ucon);
				try {
					String tmp = htmlContents.substring(htmlContents.indexOf(RATING_START_TOKEN));
					String tmp1 = tmp.substring(tmp.indexOf(RATING_START_TOKEN),tmp.indexOf(RATING_END_TOKEN));
					rating = tmp1.substring(tmp.indexOf(RATING_ADDI_START_TOKEN),tmp1.lastIndexOf(RATING_ADDI_END_TOKEN));
					rating = rating.replace(RATING_ADDI_START_TOKEN, "").toLowerCase().trim();
					if (!NO_OPINION.equalsIgnoreCase(rating)
							|| !NO_RATING.equalsIgnoreCase(rating)) {
						double d = Double.valueOf(rating);
						rating = getRatingLabel(d, rating);
					}
				} catch (Exception e) {
					rating = NO_OPINION;
				}
			} catch (Exception e) {
				rating = NO_OPINION;
			}
			ratingMap.put(symbol, rating);
			System.out.println("Completed yahoo rating>>"+symbol+">>"+rating);

	}

	public String getResponseData(URLConnection conn) throws Exception {
		StringBuffer sb = new StringBuffer();
		String data = "";
		InputStream is = conn.getInputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			sb.append((char) ch);
		}

		data = sb.toString();
		is.close();
		is = null;
		sb = null;
		System.gc();
		return data;
	}
	
	private  String getRatingLabel(double key, String rating) {
		rating =null;
		if(key <= 1.0){
			rating=STRONG_BUY;
		}else if(key <= 2.0){
			rating=BUY;
		}else if(key <= 3.0){
			rating=HOLD;
		}else if(key <= 4.0){
			rating=SELL;
		}else if(key <= 5.0){
			rating=STRONG_SELL;
		}
		return rating;
	}
}
