package com.amumtrade.marketstat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amumtrade.bean.CashPriceEarningRatiosBean;


public class CashPriceEarningRatios {
	private String moneyControlApiUrl =  "http://www.moneycontrol.com";
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
		String urlAPI = null;
		Set<String> recordSet = null;
		try {
			 recordList = new ArrayList<CashPriceEarningRatiosBean>();
			 recordSet= new HashSet<String>();
			 website = new URL(url);
			 in= new BufferedReader(new InputStreamReader(website.openStream()));
		     String inputLine;
			     while ((inputLine = in.readLine()) != null)
			     {
			    	 if(inputLine.contains("<td class=\"brdrgtgry\"><a href=")){
		    			 bean = new CashPriceEarningRatiosBean();
			    		 urlAPI = inputLine.trim();
			    		 //<td class="brdrgtgry"><a href="/india/stockpricequote/computershardware/aciinfotech/ACI07" class="bl_12"><b>ACI Infotech</b></a></td>
			    		 if(urlAPI.contains("<a href=")){
			    			 urlAPI = urlAPI.substring(urlAPI.indexOf("<a href="),urlAPI.lastIndexOf("class"));
			    			 urlAPI = urlAPI.replace("<a href=", "");
			    			 urlAPI = urlAPI.replace("\"", "");
			    			 urlAPI = moneyControlApiUrl+urlAPI.trim();
			    		 }
			    		 companyName = inputLine.trim();
			    		 if(companyName.contains("</b></a></td>")){
			    			 companyName = companyName.substring(companyName.indexOf("<b>"), companyName.lastIndexOf("</b></a></td>"));
			    			 companyName = companyName.replace("<b>", "");
			    			// System.out.println(">>>"+companyName);
			    		 }
			    		 if(urlAPI != null && companyName != null && !urlAPI.contains("///") && !recordSet.contains(urlAPI)){
			    			 bean.setApi(urlAPI);
			    			 bean.setName(companyName);
			    			 recordList.add(bean);
			    			 recordSet.add(urlAPI);
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
