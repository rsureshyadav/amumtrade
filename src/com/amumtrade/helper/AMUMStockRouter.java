package com.amumtrade.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import com.amumtrade.bean.AMUMStockBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.dao.AMUMStockDAO;

public class AMUMStockRouter {

    private List<AMUMStockBean> beanList = new ArrayList<AMUMStockBean>();
    private String inputPath;
    private String outputPath;
    private double startRange;
    private double endRange;
    private String line = null;
    private List<String> lineItem  = null;
    private String path = null;
    
	public AMUMStockRouter(double startRange, double endRange, String inputPath, String outputPath) {
	this.startRange = startRange;
	this.endRange = endRange;
	this.inputPath = inputPath;
	this.outputPath = outputPath;
	}

	public void digest() throws IOException {
		FileWriter fwo = null;
		BufferedWriter bwObj = null;
		try {
			path = outputPath+"_"+AMUMStockConstant.dateFormat.format(AMUMStockConstant.cal.getTime())+".csv";
			fwo = new FileWriter( path, false );
			bwObj = new BufferedWriter( fwo );
			bwObj.write(getHeader());
			bwObj.newLine();
			System.out.println(getHeader());
			beanList =	readNASDAQFile();
			System.out.println(beanList.size());
			ExecutorService executor = Executors.newFixedThreadPool(10);
			int totalCount=0;
					
			for (AMUMStockBean stockBean : beanList) {
		
				if(stockBean.getLastSale()>= startRange && stockBean.getLastSale()<= endRange
						&& !stockBean.getSymbol().contains("/")
						&& !stockBean.getSymbol().contains("^")){
					stockBean.setTotalCount(totalCount++);		
					Runnable epsWorker = new AMUMStockDAO(stockBean,bwObj);
					executor.execute(epsWorker);	
				}
			}
			executor.shutdown();
		    while (!executor.isTerminated()) {
		    	 
	        }
	        System.out.println("\nFinished all threads");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null)
			bwObj.close();
	        createHTML(fwo, bwObj);
		}
		
	}

	
	private void createHTML(FileWriter fwo, BufferedWriter bwObj) throws IOException {
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
		
	}

	private void createHTMLTable(BufferedReader br, String htmlText, FileWriter fwo, BufferedWriter bwObj)throws IOException {
		path = "config/template/amumtrade.html";
		String outputPath = "index.html";
		try {
			fwo = new FileWriter( outputPath, false );
			bwObj = new BufferedWriter( fwo );
			
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				if(line.contains("@REPLACE_TABLE")){
					bwObj.write(htmlText);
				}else{
					bwObj.write(line);
				}
			}
			sendEmail(htmlText);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null)
				bwObj.close();
		}
		
	}

	private void sendEmail(String htmlText) {
		 String EMAIL_FROM = "admin@amumtrade.com";
		 String EMAIL_TO = "rsureshyadav@gmail.com";
		 String EMAIL_CC = "rsureshyadav@gmail.com";
		try {
			// Create properties, get Session
			 Properties props = new Properties();

			 props.put("mail.smtp.ssl.enable", "true");
			 props.put("mail.transport.protocol", "smtps");
			 props.put("mail.debug", "true");

			 // If using static Transport.send(),
			 // Need to specify which host to send it to
			 String host = "smtp.gmail.com"; //gmail SMTP server name
			 props.put("mail.smtps.host", host);

			 //Specify which port to connect to
			 int port = 465; //port for SSL connection
			 props.put("mail.smtps.port", port);

			 //Enable SSL
			 props.put("mail.smtps.socketFactory.port", Integer.toString(port));
			 props.put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			 props.put("mail.smtps.socketFactory.fallback", "false");
			 props.put("mail.smtps.auth", "true");

			 //Enable authentication by providing your Gmail account and password
			 final String senderAccount = "rsureshyadav2@gmail.com";
			 final String senderAccountPassword = "hclt1981";
			 Session session = Session.getInstance(props);
			 session = Session.getInstance(props, new javax.mail.Authenticator() {
			 protected PasswordAuthentication getPasswordAuthentication() {
			 return new PasswordAuthentication(senderAccount, senderAccountPassword);
			 }
			 });

			// Transport transport = session.getTransport("smtps");
			 //Connect to the host with the specified sender
			 //transport.connect(host, senderAccount);
				System.out.println(">>>>sendEmail>>>"+htmlText);

				Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress(EMAIL_FROM));
		            message.setRecipients(Message.RecipientType.TO,
		                InternetAddress.parse(EMAIL_TO));
		            message.setSubject("AMUMTrade stock alert!!!");
		             message.setContent(htmlText,"text/html" );
		             Transport.send(message);

						System.out.println(">>>>sendEmail>>>"+htmlText);

		
		} catch (Exception e) {
			// TODO: handle exception
		}
		/*
		System.out.println(">>>>sendEmail>>>"+htmlText);
		
		 String MAIL_SMTP_HOST = "smtp.gmail.com";
		 String DEFAULT_SMTP_SERVER = "nawinmsx021.enterprisenet.org";
		 String EMAIL_FROM = "admin@amumtrade.com";
		 String EMAIL_TO = "rsureshyadav@gmail.com";
		 String EMAIL_CC = "rsureshyadav@gmail.com";
		 Message message;
		 MimeBodyPart msgBodyPart;
		 Multipart multiPartMsg;
		 Session session;
		
		try {
		      String to = "rsureshyadav@gmail.com";
		      String from = "admin@amumtrade.com";

		      Properties props = System.getProperties();
		      props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.smtp.host", "smtp.gmail.com");
		        props.put("mail.smtp.port", "587");
				
				Session session = Session.getDefaultInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication("rsureshyadav2@gmail.com","hclt1981");
							}
						});
				try {
					Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress(to));
		            message.setRecipients(Message.RecipientType.TO,
		                InternetAddress.parse("to_email_address@domain.com"));
		            message.setSubject("AMUMTrade stock alert!!!");
		            message.setText("Dear Mail Crawler,"
		                + "\n\n No spam to my email, please!");
		           // message.setContent(htmlText,"text/html" );
		            Transport.send(message);

		            System.out.println("Done");
				} catch (MessagingException  e) {
					// TODO: handle exception
				}
				
  	     
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	*/}

	private String getHeader() {
		return
		AMUMStockConstant.SYMBOL + AMUMStockConstant.COMMA+
		AMUMStockConstant.PRICE + AMUMStockConstant.COMMA+
		"Diluted EPS" + AMUMStockConstant.COMMA+
		"Operating Margin" + AMUMStockConstant.COMMA+
		"Return On Assets" + AMUMStockConstant.COMMA+
		"Return On Equity" + AMUMStockConstant.COMMA+
		"Revenue Per Share";
	}

	private List<AMUMStockBean> readNASDAQFile() throws IOException {
		AMUMStockBean bean;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputPath));
			int count = 0;
			while ((line = br.readLine()) != null) {
				if (count == 0) {
					// skip first line to ignore the header
					count++;
					continue;
				}
				bean = new AMUMStockBean();
				line = validateLine(line);
				lineItem = Arrays.asList(line.split("\\s*,\\s*"));
				bean.setSymbol(lineItem.get(0));
				bean.setName(lineItem.get(1));
				bean.setLastSale(validateLastSale(lineItem.get(2)));
				bean.setMarketCap(lineItem.get(3));
				bean.setSector(lineItem.get(6));
				bean.setIndustry(lineItem.get(7));
				bean.setSummaryQuote(lineItem.get(8));
				beanList.add(bean);
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
	
	private String validateLine(String line) {
		StringBuffer newLine = new StringBuffer();
		for (String s : line.split("\",")) {
			s = s.replace(",", "");
			s = s.replace("\"", "");
			newLine.append(s + ",");
		}
		return newLine.toString();
	}
	
	private double validateLastSale(String lastSale) {
		double value;
		if (lastSale.equalsIgnoreCase("n/a")) {
			value = 0;
		} else {
			value = Double.valueOf(lastSale);
		}
		return value;
	}
	
	
}
