package com.amumtrade.unused;

import static com.amumtrade.constant.StringConstant.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.amumtrade.engine.StockEngine;


public class test {


	private static String getResponseData(URLConnection conn) throws Exception {
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
	public static void main(String[] args) throws Exception {
		String s[] = {
		/*		"GST",
				"OGEN",
				"DGSE",
				"SED",
				"IBO",
				"UEC",
				"GV",
				"EXE",
				"AQQ",
				"HRT",
				"RWC",
				"EVK",
				"NG",
				"ONP",
				"BVX",
				"OBT",
				"TRX",
				"AZC",
				"VTG",
				"AMS",
				"IMUC",
				"OESX",
				"TGD",
				"IFMI",
				"AKG",
				"BTG",
				"VII",
				"NSPR",
				"NAVB",
				"NSU",
				"RTK",
				"TXMD",
				"NAK",*/
				"AAPL"
				
};
		
		/*for(int i=0 ; i<s.length;i++){
		
		String rating=null;
		try {
			String urlString=YAHOO_RATING_URL.replaceAll(SYMBOL, s[i]);
			URL url = new URL(urlString);
			URLConnection ucon = url.openConnection();
			String htmlContents = getResponseData(ucon);
			try {
				String tmp= htmlContents.substring(htmlContents.indexOf(RATING_START_TOKEN));
				String tmp1= tmp.substring(tmp.indexOf(RATING_START_TOKEN), tmp.indexOf(RATING_END_TOKEN));
				rating= tmp1.substring(tmp.indexOf(RATING_ADDI_START_TOKEN), tmp1.lastIndexOf(RATING_ADDI_END_TOKEN));
				rating=rating.replace(RATING_ADDI_START_TOKEN, "").toLowerCase().trim();
				if(!NO_OPINION.equalsIgnoreCase(rating)|| !NO_RATING.equalsIgnoreCase(rating)){
					double d= Double.valueOf(rating);
					//rating =StockUtil.getRatingLabel(d,rating);
				}
			} catch (Exception e) {
				rating=NO_OPINION;
			}
		} catch (Exception e) {
			rating=NO_OPINION;
		}
		System.out.println( rating);
		}*/
		
		for(int i=0 ; i<s.length;i++){
			String rating=null;
			try {
				String urlString=CNN_RATING_URL.replaceAll(SYMBOL, s[i]);
				System.out.println(urlString);
				URL url = new URL(urlString);
				URLConnection ucon = url.openConnection();
				String htmlContents = getResponseData(ucon);
				/*System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				System.out.println(htmlContents);
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");*/
				try {
					if(htmlContents.contains(BUY)){
						rating=BUY;
					}else if(htmlContents.contains(SELL)){
						rating=SELL;
					}else if(htmlContents.contains(CNN_THIRD_STRING) || htmlContents.contains(CNN_FIFTH_STRING)){
						rating=NO_OPINION;
					}else{
						CNN_FOURTH_STRING= CNN_FOURTH_STRING.replaceAll(SYMBOL, s[i]);
						if(htmlContents.contains(CNN_FOURTH_STRING)){
							rating=NO_OPINION;
						}else {
							String cnnRating = htmlContents.substring(htmlContents.indexOf(CNN_FIRST_STRING)+CNN_FIRST_STRING.length(),htmlContents.indexOf(CNN_SECOND_STRING));
							if(cnnRating.length()>=10){
								rating=NO_OPINION;
							}else{
								rating=cnnRating;						
							}
						}
					}
				} catch (Exception e) {
					rating=NO_OPINION;
				}
			} catch (Exception e) {
				rating=NO_OPINION;
			}
			System.out.println( rating);
		}
	}
		}
	
		/*
		 *
BUY 
A+ Strong Buy
A  Strong Buy
A- Buy
B+ Buy
B  Buy
B- Hold

HOLD
 C+ Strong Hold
 C  Hold
 C- Sell


 SELL

D+ Sell
D  Sell
D- Sell
E+ Sell
E  Strong Sell
E- Strong Sell
F  Strong Sell

		 */
		/*try{
			//PPHM - Buy
			//XBOR - No opinion
			//BBD - Hold
			URL url=new URL("http://money.cnn.com/quote/forecast/forecast.html?symb=WS");
			URLConnection ucon=url.openConnection();
			String htmlContents=getResponseData(ucon);
			//System.out.println(htmlContents);
			try {
				String CNN_FIRST_STRING="<strong class=\"wsod_rating\">";
				String CNN_SECOND_STRING="</strong>";
				String CNN_THIRD_STRING="There are no recommendations available";
				String CNN_FOURTH_STRING="Symbol not found";
				if(htmlContents.contains(CNN_THIRD_STRING) ||htmlContents.contains(CNN_FOURTH_STRING)){
					System.out.println("No Opinion");
				}else{
					String cnnRating = htmlContents.substring(htmlContents.indexOf(CNN_FIRST_STRING)+CNN_FIRST_STRING.length(),htmlContents.indexOf(CNN_SECOND_STRING));
					System.out.println(cnnRating);
				}
			} catch (Exception e) {
				System.out.println("No Opinion");
			}
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			
		}
			catch(Exception e)
			{
			e.printStackTrace();
			}
		*/
			
			
	/*	double key=2.0;
		String rating =null;
		if(key<=1.0){
			rating=STRONG_BUY;
		}else if(key<2.0){
			rating=BUY;
		}else if(key <3.0){
			rating=HOLD;
		}else if(key<4.0){
			rating=SELL;
		}else if(key<5.0){
			rating=STRONG_SELL;
		}
		System.out.println(rating);
	*/	
		/*
		String s[] = {"FSI",
				"BDR",
				"OESX",
				"BONE",
				"CEP",
				"SED",
				"WTT",
				"CRVP",
				"IFMI",
				"WS",
				"ONP"
				
};
		try{
			for(int i=0 ; i<s.length;i++){
			
			URL url=new URL("http://finance.yahoo.com/q/ao?s="+s[i]+"+Analyst+Opinion");
			//URL url=new URL("http://finance.yahoo.com/q/ao?s=SED+Analyst+Opinion");
			//no Analyst Opinion
			System.out.println(">>"+url.toString());
		//	System.out.println("Connecting to www.yahoo.com");
			URLConnection ucon=url.openConnection();
			//System.out.println("Connectied to www.yahoo.com");
			//System.out.println("Retrieving contents from www.yahoo.com");
			String htmlContents=getResponseData(ucon);
		//	System.out.println("Retrieved contents from Yahoo! as follows");
			*//**
			 * <div class="stksmry">
    <div class="h2 cf">APPLE INC: STOCK RATING SUMMARY</div>
    <div class="stksct">
        <div class="rtng">
                <div class="rat">
                    8
                </div>
                <div class="txt">
                    Stock<b>Scouter</b>
                </div>
        </div>
        Mean Recommendation (this week):
        Mean Recommendation (this week):</td><td class="yfnc_tabledata1">1.7</td></tr><tr>
        Mean Recommendation (last week):
        Change:
			 *//*
			//System.out.println(htmlContents);
			String htmlString = htmlContents;
			//System.out.println(htmlContents);
			//System.out.println(htmlString);
			//System.out.println("***************");
			String tmp; 
			try {
				tmp= htmlString.substring(htmlString.indexOf("Mean Recommendation (this week):"));
				String tmp1 ;
				tmp1= tmp.substring(tmp.indexOf("Mean Recommendation (this week):"), tmp.indexOf("<tr>"));
				//System.out.println(tmp1);
				String tmp2;
				tmp2= tmp1.substring(tmp.indexOf("\">"), tmp1.lastIndexOf("</td>"));
				tmp2=tmp2.replace("\">", "").toLowerCase().trim();
				
				System.out.println("This Week - " +tmp2);
				
				 tmp = htmlString.substring(htmlString.indexOf("Mean Recommendation (last week):"));
				 tmp1 = tmp.substring(tmp.indexOf("Mean Recommendation (last week):"), tmp.indexOf("<tr>"));
				//System.out.println(tmp1);
				tmp2 = tmp1.substring(tmp.indexOf("\">"), tmp1.lastIndexOf("</td>"));
				tmp2=tmp2.replace("\">", "").toLowerCase().trim();
				
				System.out.println("Last Week - " +tmp2);
					//tmp.substring(tmp.lastIndexOf(StringConstant.RATING_START_TOKEN), tmp.indexOf(StringConstant.RATING_END_TOKEN));
				//tmp1 = tmp1.replace(StringConstant.RATING_START_TOKEN, "");
				//System.out.println(tmp1.trim());
				//String temp= htmlString.substring(htmlString.indexOf("<div class=\"rat\">"), htmlString.indexOf("</div>"));
				//String temp1 = temp.substring(temp.indexOf(StringConstant.RATING_START_TOKEN),temp.indexOf(StringConstant.RATING_END_TOKEN));
				//System.out.println(temp1);
				//}	
			} catch (Exception e) {
				System.out.println("No Opinion");
			}
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			}
		}
			catch(Exception e)
			{
			e.printStackTrace();
			}
		
			*///}
	/*Long startTime=1349638902755;
		long endTime=1349638992303;*/
		//1349638992303
		
		
		/*

		 //SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd_MM_yyyy_HH_mm");

		// dateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
	 	 long startTime= System.currentTimeMillis();


		FileInputStream fstream = new FileInputStream(StringConstant.NASDAQ_INPUT_PATH);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int lineCount=1;
		while ((strLine = br.readLine()) != null)   {
			int beanCount=1;
			if(lineCount!=1){
				System.out.println(strLine);
				StringTokenizer stringTokenizer = new StringTokenizer(strLine,StringConstant.COMMA);
				while (stringTokenizer.hasMoreTokens()) {
					String token =stringTokenizer.nextToken();
					if(beanCount==1){
						System.out.println(token);
					}else if(beanCount==2){
						System.out.println(token);
					}else if(beanCount==3){
						System.out.println(token);
					}else if(beanCount==4){
						System.out.println(token);
					}else if(beanCount==5){
						System.out.println(token);
					}else if(beanCount==6){
						System.out.println(token);
					}else if(beanCount==7){
						System.out.println(token);
					}else if(beanCount==8){
						System.out.println(token);
					}else if(beanCount==9){
						System.out.println(token);
					}
					beanCount++;
				}

			}
			lineCount++;
			}
		in.close();
	
		System.out.println("*************************************************************");
		
		String path= StringConstant.NASDAQ_INPUT_PATH;


		CsvReader products = new CsvReader(path);
		
		products.readHeaders();

		while (products.readRecord())
		{
			
										

			String Symbol = products.get("Symbol");
			String Name = products.get("Name");
			String LastSale = products.get("LastSale");
			String MarketCap = products.get("MarketCap");
			String ADR_TSO = products.get("ADR TSO");
			String IPOyear	 = products.get("IPOyear");
			String Sector = products.get("Sector");
			String industry = products.get("industry");
			String Summary_Quote = products.get("Summary Quote");
			
			System.out.println(Symbol + ":" + Name + ":" + LastSale + ":" + MarketCap + ":" + ADR_TSO + ":" + IPOyear + ":" + Sector + ":" + industry+ ":" +Summary_Quote);
		}

		products.close();
	
		for(int i=0;i <10000000;i++){
			System.out.println(i);
		}
		 long endTime= System.currentTimeMillis();
		 System.out.println(">>"+startTime);
		 System.out.println("<<"+endTime);
		 long elapsedTime = endTime - startTime;
		 System.out.println(">><<"+endTime);
		 
		 System.out.println("Method execution total time in seconds ==> " + (endTime - startTime) / 1000 + " seconds");

		System.out.println("Start Time: " + dateFormat2.format(new Date(startTime)));
		System.out.println("End Time: " + dateFormat2.format(new Date(endTime)));
		System.out.println("Total Time: " + dateFormat2.format(new Date(elapsedTime)));  	
		*///}
		
		
		
		
		
		/*		StockWebBean webBean =null;
	 	Map<String, StockWebBean> webStockMap=StockUtil.getQuoteFromWebFile(CommonConstant.NYSE_WEB_PATH, webBean);
	 	for (String  symbol : webStockMap.keySet()) {
	 		webBean=webStockMap.get(symbol);
	 		Double price = Double.valueOf(webBean.getLastSale());
	 		System.out.println("Price >>"+price);
			 if(price>=CommonConstant.ONE && price<=CommonConstant.TWO){
			 		System.out.println("Symbol >>"+webBean.getSymbol());
	 		}
	 	}*/
		
		
		
		/*
	 	 long startTime= System.currentTimeMillis();
	 	StockWebBean webBean =null;
		 List<String> symbolList = new ArrayList<String>();
		 	Map<String, StockWebBean> webStockMap=StockUtil.getQuoteFromWebFile(CommonConstant.NYSE_INPUT_PATH, webBean);
		 	for (String  symbol : webStockMap.keySet()) {
		 		webBean=webStockMap.get(symbol);
		 		Double price = Double.valueOf(webBean.getLastSale());
		 		//System.out.println("Price >>"+price);
				 if(price>=CommonConstant.ONE && price<=CommonConstant.TWO){
					 symbolList.add(webBean.getSymbol());
		 		}
		 	}
		 	System.out.println("Final Quote List size>>"+symbolList.size());
		 	StockUtil.writeWebQuoteToFile(symbolList, "config/NYSE_Temp.txt");
		 	
	 	 long endTime= System.currentTimeMillis();
		 long elapsedTime = endTime - startTime;
		 SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
		 dateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
		         
		 System.out.println("Start Time: " + dateFormat2.format(new Date(startTime)));
		 System.out.println("End Time: " + dateFormat2.format(new Date(endTime)));
		 System.out.println("Total Time: " + dateFormat2.format(new Date(elapsedTime)));  
		*/
		
		/*String s ="DDD,3D Systems Corporation,36.63,2029405369.86,n/a,n/a,Technology,Computer Software: Prepackaged Software,http://www.nasdaq.com/symbol/ddd";
		StringTokenizer tokenizer = new StringTokenizer(s);
		int count=1;
		while (tokenizer.hasMoreTokens()){
			System.out.println(tokenizer.nextToken(","));
			count++;
		}*/
	//}

		 
		/* List<String> marketNameList = StockUtil.getQuoteFromFile(CommonConstant.AMEX_PATH);
		 StringBuffer buffer = new StringBuffer();
		 for(String q : marketNameList){
			 if(!q.contains("/")|| !q.contains("^")){
				 buffer.append(q.trim());
				 buffer.append("+");
			 }
		 }
		  System.out.println(buffer.toString()); 
	  }

}*/
//FAX+IAF+CH+ETF+FCO+IF+ISL+AXK+ACU+AQ+AE+ADK+API+ANX+ACY+AIM+AA^+AXX+AXU+ANV+AAU+APT+DIT+APP+ADGE+ALN+AMS+AQQ+AXN+BRD+AGX+ARR/WS+HRT+ATC+ATL+AZC+AZK+AWX+AVL+ASM+BONE+BKR+BTN+BKJ+BCV+BAA+BHB
//+BRN+BIR^A+BTX+BMJ+BZM+BLE+BLJ+BFY+BPS+BHV+BDR+BVX+BWL/A+BZC+BTI+CAK+CDY+CXM+ROX+CAW+CVM+CEF+GTU+CET+CCF+CQP+LNG+CVR+CNAM+CBP+CHGS+CMFO+CNR+CPHI+SHZ+CRC+MBA+CKX+CGR+GLV+GLQ+GLO+CRV+MOC+BTC+CIX+LODE+CTO+CEP+MCF+CUO+CMT+CRMD+CRMD/WS+CFP+CRF+CLM+COVR+CVU+CIK+DHY+EXE+CXZ+CRVP+CTP+QBC+DXR+DEJ+VCF+VFL+VMM+DLA+DNN+DGSE+DPW+DSS+DMF+GRF+ESTE+EVM+EIA+CEV+EVV+MAB+MMV+MIW+EMI+EIM+EIV+EMJ+EVJ+ENX+NYH+EVY+EIO+EVO+EIP+EVP+ECBE+ELMD+ETAK+ELLO+ECF+EMAN+EOX+MSN+ESA+ENA+EGT+EGI+EVI+ERB+ESP+ETUA+ETUB+EMXX+EVBN+EVK+EPM+EPM^A+XRA+FPP+FPP/WS+MXN+FEN+FWV+BDL+FSI+FFI+FSP+FTF+FOH+FRD+FRS+FLL+GLU+GGN+GGN^A+EGAS+GSX+GST+GST^A+JOB+GMO+GGR+GOK+GPR+GIG+GSB+GLOW+GRZ+GORO+GSV+AUMN+GSS+GV+GRC+GHM+GTE+GBG+GPL+GRH+GRH^C+SIM+GVP+HWG+HEB+HLM^+HKN+HMG+HH+HUSA+IBIO+IBO+IEC+IG+IMUC+IMH+IMO+IOT+IGC+IGC/U+IGC/WS+INFU+INV+IHT+INO+IFMI+IDN+INS+THM+INUV+VKI+VMV+VKL+ICH+IPT+ISR+ITI+KGN+KBX+KXM+LTS+LSG+LCI+LAQ+LGL+LBY+LON+LPH+LEI+MCZ+MVG+MHR^C+MHR^D+MHE+MHH+MDGN+MDGN/WS+MEET+IPB+MEA+MXC+CCA+MGT+MSL+MDW+MGH+MGN+MXA+MDM+MVF+MZA+NHC+NHC^A+NAVB+NBS+NBW+NHS+NBH+NBO+NRO+CUR+UWN+NSU+GBR+NEWN+NEN+NGD+PAL+NAK+NOG+NBY+NCQ+NG+NTN+NLP+NTS+NEA+NFZ+NFZ^C+NKR+NXE+NKX+NVX+NZH+NCB+NCU+CFD+NXZ+NZF+NVG+NKG+NKG^E+NGX+NMB+NMB^C+NZW+NZW^C+NOM+NMZ+NXJ+NUJ+NJV+NRK+NXK+NKO+NYV+NXI+NXI^D+NBJ+NBJ^A+NVJ+NVJ^A+NXM+NVY+NPN+JRS+OBT+TIS+ONP+OESX+OFI+PHF+PBM+PCG^A+PCG^B+PCG^C+PCG^D+PCG^E+PCG^G+PCG^H+PCG^I+PTN+PZG+PCYG+PRK+PTX+PIP+HNW+PLG+PCC+PLM+PW+APTS+PBTH+PLX+PDO+QMM+QRM+RLGT+REE+UTG+RWC+RCG+RTK+RVP+RVM+RNN+RIC+RIF+RPI+RBY+BQY+SGA+SAL+SSN+SDO^A+SDO^B+SDO^C+SDO^H+SAND+SARA+SEB+IDI+IDI/U+IDI/WS+SED+SNT+SVT+SIF+SVBL+SVLC+WWZ^K+MMZ^K+OOZ^K+QQZ^K+SS^K+SIHI+SLI+ZXX+ZXX^F+XPL+SOQ+SCE^B+SCE^C+SCE^D+SCE^E+SSE+SGB+LOV+SILU+SSY+STS+SYRG+SYN+TTTM+TTTM/WS/W+TTTM/WS/Z+TRX+TGB+TAS+TSH+TIK+TGC+TF+TPI+TLR+TGD+TOF+TMP+TAT+TA+TPLM+TRT+TCX+TWO/WS+HTM+UAMY+UVE+UPG+UUU+UQM+URG+URZ+UEC+VTG+VSR+VII+VHC+VGZ+VRNG+VRNG/WS+WAC+WSO/B+EAD+ERC+ERH+WGA+SBI+WRN+RVR+WYY+WMCO+WEX+WTT+WIS^+WZE+YMI+ZBB
