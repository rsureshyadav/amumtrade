package com.amumtrade.marketstat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.amumtrade.bean.CashPriceEarningRatiosBean;


public class CashPriceEarningRatios {
	private String cashPriceEarningRatiosUrl =  "http://www.moneycontrol.com/stocks/marketinfo/pe/bse/homebody.php?indcode=0&sortcode=2";
	private List<CashPriceEarningRatiosBean> cashPriceEarningRatiosList;
	public void execute() throws IOException{
		cashPriceEarningRatiosList = new ArrayList<CashPriceEarningRatiosBean>();
		//cashPriceEarningRatiosList.add("hello world!!!!");
		cashPriceEarningRatiosList = readFromMoneyControl(cashPriceEarningRatiosUrl);
	}
	
	private List<CashPriceEarningRatiosBean> readFromMoneyControl(String url)throws IOException {
		List<CashPriceEarningRatiosBean> recordList = null;
		BufferedReader in = null;
		URL website = null;
		CashPriceEarningRatiosBean bean = null;
		String companyName = null;
		try {
			 recordList = new ArrayList<CashPriceEarningRatiosBean>();
			 website = new URL(url);
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
		     String inputLine;
			     while ((inputLine = in.readLine()) != null)
			     {
			    	 if(inputLine.contains("class=\"bl_12\"")){
			    		 companyName = inputLine.trim();
			    		 if(companyName.contains("</b></a></td>")){
			    			 bean = new CashPriceEarningRatiosBean();
			    			 companyName = companyName.substring(companyName.indexOf("<b>"), companyName.lastIndexOf("</b></a></td>"));
			    			 companyName = companyName.replace("<b>", "");
			    			// System.out.println(">>>"+companyName);
			    			 bean.setName(companyName);
			    			 recordList.add(bean);
			    		 }
			    	 }
			     }	
	        in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in !=null){
				in.close();
			}
		}
		return recordList;
	}


	public List<CashPriceEarningRatiosBean> getCashPriceEarningRatiosList() {
		return cashPriceEarningRatiosList;
	}
	public void setCashPriceEarningRatiosList(
			List<CashPriceEarningRatiosBean> cashPriceEarningRatiosList) {
		this.cashPriceEarningRatiosList = cashPriceEarningRatiosList;
	}
	
}
