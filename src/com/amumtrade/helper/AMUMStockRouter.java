package com.amumtrade.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.amumtrade.bean.AMUMStockBean;
import com.amumtrade.constant.AMUMStockConstant;
import com.amumtrade.dao.AMUMStockDAO;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class AMUMStockRouter {

    private List<AMUMStockBean> beanList = new ArrayList<AMUMStockBean>();
    private String inputPath;
    private String outputPath;
    private double startRange;
    private double endRange;
    private String exchName;
    private String line = null;
    private List<String> lineItem  = null;
    private String path = null;
    private FileWriter fwo = null;
    private BufferedWriter bwObj = null;
	private List<String> pdfList  = null;
	private String pdfPath = null;
	public AMUMStockRouter(double startRange, double endRange, String inputPath, String outputPath, String exchName) {
	this.startRange = startRange;
	this.endRange = endRange;
	this.inputPath = inputPath;
	this.outputPath = outputPath;
	this.exchName = exchName;
	}

	public void digest() throws IOException {
		
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
	        bwObj.close();
	        createHTMLTag(fwo);
	        createExtJsHtml(fwo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bwObj!=null)
			bwObj.close();
	       // createHTML(fwo, bwObj);
		}
		
	}


	private void createExtJsHtml(FileWriter fwo) throws IOException {
		String templatePath = "config/template/array-grid.js";
		String outputPath = "array-grid.js";
		
		StringBuffer htmlBuffer = new StringBuffer(); 
		BufferedReader br = null;
		
		fwo = new FileWriter( outputPath, false );
		bwObj = new BufferedWriter( fwo );
		
		try {
			br = new BufferedReader(new FileReader(path));
			pdfList = new ArrayList<String>();
			int count = 0;
			while ((line = br.readLine()) != null && line.length()>0) {
				if (count == 0) {
					count++;
					continue;
				}
			line = line.replace(",", "','"); 	
			line = "['"+count+"','"+line+"'],"+"\n";
			//line = "['"+line+"'],"+"\n";

			htmlBuffer.append(line);
			count++;
			}
			//create a new js file with the metric data
			if(htmlBuffer.length()>0){
				br = new BufferedReader(new FileReader(templatePath));
				while ((line = br.readLine()) != null) {
					if(line.contains("@AMUMTradeMetricData")){
						line = line.replace("@AMUMTradeMetricData", htmlBuffer.toString());
						bwObj.write(line+"\n");
					}else {
						bwObj.write(line+"\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				br.close();
			}
			if(bwObj!=null){
				bwObj.close();
			}
		}
		
	}
	private void createHTMLTag(FileWriter fwo) throws IOException {
		StringBuffer buffer = new StringBuffer(); 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			pdfList = new ArrayList<String>();
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
			pdfList.addAll(  Arrays.asList(line.split("\\s*,\\s*")));
			tableBody = tableBody.replace(",", "</td><td>");
			tableBody = "<tr><td>"+tableBody+"</td></tr>";
			buffer.append(tableBody);
			}
			createHTML(br, buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				br.close();
			}
			
		}
		
	}

	private void createHTML(BufferedReader br, String htmlText)throws IOException {

		String templatePath = "config/template/amumtrade.html";
		String outputPath = "config/output/html/"+exchName+"Report.html";
		StringBuffer buffer = new StringBuffer();
		try {
			fwo = new FileWriter( outputPath, false );
			bwObj = new BufferedWriter( fwo );
			
			br = new BufferedReader(new FileReader(templatePath));
			while ((line = br.readLine()) != null) {
				if(line.contains("@AMUMTradeStockName")){
					line = line.replace("@AMUMTradeStockName", exchName+" Stock Exchange Analysis Report");
					bwObj.write(line);
					buffer.append(line);
				}else if(line.contains("@REPLACE_TABLE")){
					line = line.replace("@REPLACE_TABLE",htmlText);
					bwObj.write(line);
					buffer.append(line);
				}else{
					bwObj.write(line);
					buffer.append(line);
				}
			}
			sendEmail(buffer.toString());
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
			 message.setContent(htmlText,"text/html" );
			//Transport.send(message);
			
			  // Create the message part 
	        // BodyPart messageBodyPart = new MimeBodyPart();

	       //  messageBodyPart.setContent(htmlText,"text/html");
	         // Fill the message
//	         messageBodyPart.setText("AMUMTrade "+exchName+" stock alert!!!");
	         
/*	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         DataSource source = new FileDataSource(pdfPath);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName("amumtrade.pdf");
	         multipart.addBodyPart(messageBodyPart);
	         message.setContent(multipart );*/

	         // Send message
	         Transport.send(message);
 
			System.out.println("Email send !!!");
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private String getHeader() {
		return
		AMUMStockConstant.SYMBOL + AMUMStockConstant.COMMA+
		AMUMStockConstant.PRICE + AMUMStockConstant.COMMA+
		"Diluted EPS" + AMUMStockConstant.COMMA+
		"P/E Ratio" + AMUMStockConstant.COMMA+
		"Operating Margin" + AMUMStockConstant.COMMA+
		"Return On Assets" + AMUMStockConstant.COMMA+
		"Return On Equity" + AMUMStockConstant.COMMA+
		"Revenue Per Share" + AMUMStockConstant.COMMA+
		"Sector" + AMUMStockConstant.COMMA+
		"Industry" + AMUMStockConstant.COMMA;
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
