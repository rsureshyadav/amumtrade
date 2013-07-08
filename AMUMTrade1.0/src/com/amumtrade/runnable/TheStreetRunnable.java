package com.amumtrade.runnable;
import static com.amumtrade.constant.StringConstant.A;
import static com.amumtrade.constant.StringConstant.A_MINUS;
import static com.amumtrade.constant.StringConstant.A_PLUS;
import static com.amumtrade.constant.StringConstant.B;
import static com.amumtrade.constant.StringConstant.BUY;
import static com.amumtrade.constant.StringConstant.B_MINUS;
import static com.amumtrade.constant.StringConstant.B_Plus;
import static com.amumtrade.constant.StringConstant.C;
import static com.amumtrade.constant.StringConstant.C_MINUS;
import static com.amumtrade.constant.StringConstant.C_PLUS;
import static com.amumtrade.constant.StringConstant.D;
import static com.amumtrade.constant.StringConstant.D_MINUS;
import static com.amumtrade.constant.StringConstant.D_PLUS;
import static com.amumtrade.constant.StringConstant.E;
import static com.amumtrade.constant.StringConstant.E_MINUS;
import static com.amumtrade.constant.StringConstant.E_PLUS;
import static com.amumtrade.constant.StringConstant.F;
import static com.amumtrade.constant.StringConstant.HOLD;
import static com.amumtrade.constant.StringConstant.NO_OPINION;
import static com.amumtrade.constant.StringConstant.SELL;
import static com.amumtrade.constant.StringConstant.STRONG_BUY;
import static com.amumtrade.constant.StringConstant.STRONG_HOLD;
import static com.amumtrade.constant.StringConstant.STRONG_SELL;
import static com.amumtrade.constant.StringConstant.THE_STREET_FIRST_STRING;
import static com.amumtrade.constant.StringConstant.THE_STREET_FOURTH_STRING;
import static com.amumtrade.constant.StringConstant.THE_STREET_SECOND_STRING;
import static com.amumtrade.constant.StringConstant.THE_STREET_THIRD_STRING;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.amumtrade.bean.StockBean;

public class TheStreetRunnable extends BaseRun {



	public TheStreetRunnable(String urlString, String symbol,
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
				if(htmlContents.contains(THE_STREET_FOURTH_STRING)){
					rating=NO_OPINION;
				}else{
					String theStreetRating = htmlContents.substring(htmlContents.indexOf(THE_STREET_FIRST_STRING)+THE_STREET_FIRST_STRING.length(),htmlContents.indexOf(THE_STREET_SECOND_STRING));
					String grade=htmlContents.substring(htmlContents.indexOf(THE_STREET_SECOND_STRING)+THE_STREET_SECOND_STRING.length(),htmlContents.indexOf(THE_STREET_THIRD_STRING));
					rating= getTheStreetRatingLabel(theStreetRating ,grade);
				}
			} catch (Exception e) {
				rating=NO_OPINION;
			}
		} catch (Exception e) {
			rating=NO_OPINION;
		}
		ratingMap.put(symbol, rating);
		System.out.println("Completed the street rating>>"+symbol+">>"+rating);
	
	}

	private String getTheStreetRatingLabel(String rat, String key){
		String finalRating=null;
		if(rat.equalsIgnoreCase(BUY)){
			if(key.equalsIgnoreCase(A_PLUS)){
				finalRating=STRONG_BUY;
			}else if(key.equalsIgnoreCase(A)){
				finalRating=STRONG_BUY;
			}else if(key.equalsIgnoreCase(A_MINUS)){
				finalRating=BUY;
			}else if(key.equalsIgnoreCase(B_Plus)){
				finalRating=BUY;
			}else if(key.equalsIgnoreCase(B)){
				finalRating=BUY;
			}else if(key.equalsIgnoreCase(B_MINUS)){
				finalRating=HOLD;
			}else{
				finalRating=rat;
			}
		}else if(rat.equalsIgnoreCase(HOLD)){
			if(key.equalsIgnoreCase(C_PLUS)){
				finalRating=STRONG_HOLD;
			}else if(key.equalsIgnoreCase(C)){
				finalRating=HOLD;
			}else if(key.equalsIgnoreCase(C_MINUS)){
				finalRating=SELL;
			}else{
				finalRating=rat;
			}
			
		}else if(rat.equalsIgnoreCase(SELL)){
			if(key.equalsIgnoreCase(D_PLUS)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(D)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(D_MINUS)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(E_PLUS)){
				finalRating=SELL;
			}else if(key.equalsIgnoreCase(E)){
				finalRating=STRONG_SELL;
			}else if(key.equalsIgnoreCase(E_MINUS)){
				finalRating=STRONG_SELL;
			}else if(key.equalsIgnoreCase(F)){
				finalRating=STRONG_SELL;
			}else{
				finalRating=rat;
			}
		}
		return finalRating;
	}
}
