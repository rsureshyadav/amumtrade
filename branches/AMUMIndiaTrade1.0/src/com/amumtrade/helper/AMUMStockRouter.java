package com.amumtrade.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amumtrade.bean.AMUMStockBean;
import com.amumtrade.constant.AMUMStockConstant;

public class AMUMStockRouter {

    private List<AMUMStockBean> beanList = new ArrayList<AMUMStockBean>();

    private double startRange;
    private double endRange;
    private String line = null;
    private List<String> lineItem  = null;
    private String outputPath;
    
	public AMUMStockRouter(double startRange, double endRange) {
	this.startRange = startRange;
	this.endRange = endRange;
	}

	public void digest() throws IOException {
		FileWriter fwo = null;
	//	BufferedWriter bwObj = null;
		try {
			//outputPath = AMUMStockConstant.BSE_OUTPUT_PATH+"_"+AMUMStockConstant.dateFormat.format(AMUMStockConstant.cal.getTime())+".csv";
			//fwo = new FileWriter( outputPath, false );
			//bwObj = new BufferedWriter( fwo );
			
	/*		beanList =	readBSEFile();
			System.out.println(beanList.size());
			for (AMUMStockBean stockBean : beanList) {
				System.out.println(stockBean.getScripCode()+" >> "+ stockBean.getScripId()+" >> "+
				stockBean.getScripName()+" >> "+
				stockBean.getStatus()+" >> "+
				stockBean.getGroup()+" >> "+
				stockBean.getFaceValue()+" >> "+
				stockBean.getISINNo()+" >> "+
				stockBean.getIndustry()+" >> "+
				stockBean.getInstrument());
			}
	*/		//for testing http request
			List<String> httpList = new ArrayList<String>();
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=A");
/*			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=B");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=C");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=D");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=E");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=F");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=G");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=H");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=I");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=J");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=K");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=L");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=M");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=N");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=O");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=P");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=Q");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=R");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=S");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=T");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=U");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=V");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=W");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=X");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=Y");
			httpList.add("http://msn.bankbazaar.com/welcome/stocksStartingWith?q=Z");*/
			
			URL url;
		    HttpURLConnection connection = null;
		    int count =0;
			for(String httpUrl : httpList){
					++count;
					 url = new URL(httpUrl);
				      connection = (HttpURLConnection)url.openConnection();
				      connection.setReadTimeout(5000);
				      
				      InputStream is = connection.getInputStream();
				      BufferedReader br = new BufferedReader(new InputStreamReader(is));
				      String line;
				     StringBuffer buffer = new StringBuffer();
				      boolean isCapture = false;
				      boolean isExit = false;
				  //    String newLine = null;
				      while((line = br.readLine()) != null) {
				    	  if(line.contains("<tbody>")){
				    		  isCapture=true;
				    	  }else if(isCapture){
				    		  if(line.contains("<td class=\"first\">")){
				    			  count=1;
					    		  line =  line.replace("<td class=\"first\">","");
					    		  line = line.substring(line.indexOf("\">"), line.lastIndexOf("</a>"));
					    		  line = line.replace("\">", "");
					    	      line = line.replace(",", "");
					    		  line = line.replace(".", "");
					    		  System.out.println(">>>>>>>>>>>>>>>"+line);
					    		  
					    	  }
				    		  if (count == 5){
				    			  System.out.println(">>>>>>>>>>>>>>>>>"+line);
				    			  count =0;
				    		  }
				    		  count++;
				    		  buffer.append(line+"\n");
				    		  if(line.contains("</tbody>")){
				    			  isExit = true;
				    		  }
				    	  }else if(isExit){
				    		  break;
				    	  }
				    	/*  if(line.contains("<td class=\"first\">")){
				    		  line =  line.replace("<td class=\"first\">","");
				    		  line = line.substring(line.indexOf("\">"), line.lastIndexOf("</a>"));
				    		  line = line.replace("\">", "");
				    	      line = line.replace(",", "");
				    		  line = line.replace(".", "");
				    	  }*/
				    	  
				    	  
				    	  
				    	  
				    	  /*else if(isCapture){
				    		  //buffer.append(line+"\n");
				    		  if(line.contains("</tbody>")){
				    			  isCapture = false;
				    			  isExit = true;
				    		  }
				    	  }else if(isExit){
				    		  break;
				    	  }*/
				      }
				     System.out.println("<><><><>>>>>>>>"+buffer.toString());
				      
				      System.out.println("*************************[ "+ count +" ]*****************************");
			}
			
			
			
			
			//ExecutorService executor = Executors.newFixedThreadPool(10);
		//	int totalCount=0;
					
	/*		for (AMUMStockBean stockBean : beanList) {
		
				if(stockBean.getLastSale()>= startRange && stockBean.getLastSale()<= endRange
						&& !stockBean.getSymbol().contains("/")
						&& !stockBean.getSymbol().contains("^")){
					stockBean.setTotalCount(totalCount++);		
					Runnable epsWorker = new AMUMStockDAO(stockBean,bwObj);
					executor.execute(epsWorker);	
				}
			}*/
			//executor.shutdown();
		   // while (!executor.isTerminated()) {
		    	 
	      //  }
	     //   System.out.println("\nFinished all threads");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//if(bwObj!=null)
			//bwObj.close();
	       // createHTML(fwo, bwObj);
		}
		
	}

	
/*	private void createHTML(FileWriter fwo, BufferedWriter bwObj) throws IOException {
		StringBuffer buffer = new StringBuffer(); 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
	
			String tableHeader = getHeader();
			tableHeader = tableHeader.replace(",", "</td><td>");
			tableHeader = "<tr><td>"+tableHeader+"</td></tr>";
			buffer.append(tableHeader);
			int count = 0;
			while ((line = br.readLine()) != null) {
				if (count == 0) {
					count++;
					continue;
				}
			String tableBody = line;
			tableBody = tableBody.replace(",", "</td><td>");
			tableBody = "<tr><td>"+tableBody+"</td></tr>";
			buffer.append(tableBody);
			}
			createHTMLTable(br, buffer.toString(),  fwo,  bwObj);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				br.close();
			}
		}
		
	}*/

	/*private void createHTMLTable(BufferedReader br, String htmlText, FileWriter fwo, BufferedWriter bwObj)throws IOException {
		path = "config/template/amumtrade.html";
		String outputPath = exchName+"Report.html";
		StringBuffer buffer = new StringBuffer();
		try {
			fwo = new FileWriter( outputPath, false );
			bwObj = new BufferedWriter( fwo );
			
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				if(line.contains("@AMUMTradeStockName")){
					line = line.replace("@AMUMTradeStockName", exchName+" Stock Exchange Analysis Report");
					bwObj.write(line);
					buffer.append(line);
				}else if(line.contains("@REPLACE_TABLE")){
					bwObj.write(htmlText);
					buffer.append(htmlText);
				}else{
					bwObj.write(line);
					buffer.append(line);
				}
			}
			sendEmail(htmlText, buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null)
				bwObj.close();
		}
		
	}*/

	/*private void sendEmail(String htmlText, StringBuffer buffer) {
		 String EMAIL_FROM = "admin@amumtrade.com";
		 String EMAIL_TO = "rsureshyadav@gmail.com";
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			Session session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("amumtrade@gmail.com", "welcomeamum");
						}
					  });
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(EMAIL_FROM));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(EMAIL_TO));
			 message.setSubject("AMUMTrade "+exchName+" stock alert!!!");
			 message.setContent(buffer.toString(),"text/html" );

 
			Transport.send(message);
 
			System.out.println("Email send !!!");
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/

/*	private String getHeader() {
		return
		AMUMStockConstant.SYMBOL + AMUMStockConstant.COMMA+
		AMUMStockConstant.PRICE + AMUMStockConstant.COMMA+
		"Diluted EPS" + AMUMStockConstant.COMMA+
		"Operating Margin" + AMUMStockConstant.COMMA+
		"Return On Assets" + AMUMStockConstant.COMMA+
		"Return On Equity" + AMUMStockConstant.COMMA+
		"Revenue Per Share";
	}*/

	private List<AMUMStockBean> readBSEFile() throws IOException {
		AMUMStockBean bean;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(AMUMStockConstant.BSE_A_INPUT_PATH));
			int count = 0;
			while ((line = br.readLine()) != null) {
				if (count == 0) {
					// skip first line to ignore the header
					count++;
					continue;
				}
				count++;
				bean = new AMUMStockBean();
				lineItem = Arrays.asList(line.split("\\s*,\\s*"));
				System.out.println("[ "+count+" ] ==> "+lineItem);
				bean.setScripCode(lineItem.get(0));
				bean.setScripId(lineItem.get(1));
				bean.setScripName(lineItem.get(2));
				bean.setStatus(lineItem.get(3));
				bean.setGroup(lineItem.get(4));
				bean.setFaceValue(lineItem.get(5));
				bean.setISINNo(lineItem.get(6));
				bean.setIndustry(lineItem.get(7));
				bean.setInstrument(lineItem.get(8));
				beanList.add(bean);
				
		/*		bean = new AMUMStockBean();
				line = validateLine(line);
				lineItem = Arrays.asList(line.split("\\s*,\\s*"));
				bean.setSymbol(lineItem.get(0));
				bean.setName(lineItem.get(1));
				bean.setLastSale(validateLastSale(lineItem.get(2)));
				bean.setMarketCap(lineItem.get(3));
				bean.setSector(lineItem.get(6));
				bean.setIndustry(lineItem.get(7));
				bean.setSummaryQuote(lineItem.get(8));
				beanList.add(bean);*/
			}
			
		} catch (Exception e) {
			System.out.println("StockRouteHelper exception occured: "+e.getMessage());
		}finally{
			if(br != null){
				br.close();
			}
		}
	
		return beanList;
	}
	
/*	private String validateLine(String line) {
		StringBuffer newLine = new StringBuffer();
		for (String s : line.split("\",")) {
			s = s.replace(",", "");
			s = s.replace("\"", "");
			newLine.append(s + ",");
		}
		return newLine.toString();
	}
	*/
/*	private double validateLastSale(String lastSale) {
		double value;
		if (lastSale.equalsIgnoreCase("n/a")) {
			value = 0;
		} else {
			value = Double.valueOf(lastSale);
		}
		return value;
	}*/
	
	
}
