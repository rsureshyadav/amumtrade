package com.amumtrade.runnable;

import static com.amumtrade.constant.StringConstant.BUY;
import static com.amumtrade.constant.StringConstant.CNN_FIFTH_STRING;
import static com.amumtrade.constant.StringConstant.CNN_FIRST_STRING;
import static com.amumtrade.constant.StringConstant.CNN_FOURTH_STRING;
import static com.amumtrade.constant.StringConstant.CNN_SECOND_STRING;
import static com.amumtrade.constant.StringConstant.CNN_THIRD_STRING;
import static com.amumtrade.constant.StringConstant.NO_OPINION;
import static com.amumtrade.constant.StringConstant.SELL;
import static com.amumtrade.constant.StringConstant.SYMBOL;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.amumtrade.bean.StockBean;

public class CNNRunnable extends BaseRun {




	public CNNRunnable(String urlString, String symbol,
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
					if(htmlContents.contains(BUY)){
						rating=BUY;
					}else if(htmlContents.contains(SELL)){
						rating=SELL;
					}else if(htmlContents.contains(CNN_THIRD_STRING) || htmlContents.contains(CNN_FIFTH_STRING) ){
						rating=NO_OPINION;
					}else{
						CNN_FOURTH_STRING= CNN_FOURTH_STRING.replaceAll(SYMBOL, symbol);
						if(htmlContents.contains(CNN_FOURTH_STRING)){
							rating=NO_OPINION;
						}else{
							String cnnRating = htmlContents.substring(htmlContents.indexOf(CNN_FIRST_STRING)+CNN_FIRST_STRING.length(),htmlContents.indexOf(CNN_SECOND_STRING));
							rating=cnnRating;
						}
					}
				} catch (Exception e) {
					rating=NO_OPINION;
				}
			} catch (Exception e) {
				rating=NO_OPINION;
			}
			ratingMap.put(symbol, rating);
			System.out.println("Completed cnn rating>>"+symbol+">>"+rating);

	}

}
