package com.amumtrade.engine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class EmailReader {

	//symbol
	public static void main(String str[]){
		 String symbol=null;
		 boolean flag=false;
		EmailReader emailReader = new EmailReader();
		//<td style="font-family: Verdana, sans-serif; font-size: 11px; color: #444444; border-bottom: 1px solid #D8D8D8; padding: 4px 4px 4px 8px; text-align: left; "><a href="http://research.tdameritrade.com/public/stocks/overview/overview.asp?symbol=KELYB">KELYB</a></td>
		
		//<td style="font-family: Verdana, sans-serif; font-size: 11px; color: #444444; border-bottom: 1px solid #D8D8D8; padding: 4px 4px 4px 8px; text-align: right;">$16.03</td>
	
		try {
			String mail = emailReader.getFileAsString(new File ("test/mail.txt"));
			StringBuffer buffer = new StringBuffer(mail);
			System.out.println(mail.substring(0, 30).replace("DATE:", ""));
			StringTokenizer tokenizer =new StringTokenizer(mail.trim());
			while(tokenizer.hasMoreTokens()){
				symbol=tokenizer.nextToken();
				if(symbol.contains("symbol")){
					symbol=symbol.substring(symbol.indexOf("symbol="), symbol.lastIndexOf("\">"));
					symbol=symbol.replace("symbol=", "");
					System.out.println(symbol);
					flag=true;
				}
				
				if(flag && symbol.contains("$")){
					symbol=symbol.substring(symbol.indexOf("$"), symbol.lastIndexOf("</td>"));
					System.out.println(symbol);
					System.out.println("_________________");
					flag=false;
				}
				//href="http://research.tdameritrade.com/public/stocks/overview/overview.asp?symbol=VRNG">VRNG</a></td>

				
			}
			//System.out.println("******************************************");
			//System.out.println(mail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFileAsString(File file) throws Exception{ 
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		StringBuffer sb = new StringBuffer();
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			while (dis.available() != 0) {
				sb.append( dis.readLine() +"\n");
			}
			fis.close();
			bis.close();
			dis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
		}
	
	public static String getFileContent(String filePath) throws Exception {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		StringBuilder fileContent = new StringBuilder();
		while ((str = br.readLine()) != null) {
			fileContent.append(str);
		}
		return fileContent.toString();
	}
}


