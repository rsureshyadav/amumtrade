package com.amumtrade.factory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.amumtrade.bean.TopGainerBean;

public class MoneyControlRatingRunner implements Runnable {
	private String command;
	private URL urlConn;
	private Map<String,TopGainerBean> topGainerMap;
	private BufferedWriter bwObj;
	
	public MoneyControlRatingRunner(URL httpUrl,BufferedWriter bufferWriter,String s){
		this.urlConn=httpUrl;
		this.bwObj = bufferWriter;
		this.command=s;
	}
	public void run() {
	        try {
				processCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.out.println(Thread.currentThread().getName()+" End.");
		
	}
	private void processCommand() throws IOException {
        try {
            Thread.sleep(5000);
            Reader reader = null;
            BufferedReader bufferReader = null;
            try {
		
		          bufferReader = new BufferedReader(new InputStreamReader(urlConn.openStream()));
		          findVolumeInReader(bufferReader,urlConn.toString());
		          
		        }
		        finally {
		          if (reader != null) 
		              reader.close();
		        }   
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	private void findVolumeInReader(BufferedReader bufferReader, String url) throws IOException {
		String inputLine=null;
		boolean isSentimeter=false;
		String rating=null;
	
		Set<String> urlSet = null;
		TopGainerBean gainerBean;
		try {
			urlSet = new HashSet<String>();
			 while ((inputLine = bufferReader.readLine()) != null)
		        {
				 if(inputLine.contains("<p class=\"gDl_15\">")){
					 isSentimeter = true;
				 }
				 if(isSentimeter){
					 if(inputLine.contains("<strong>buying</strong>")){
						 try {
							 rating = inputLine.substring(inputLine.indexOf("<span class"),inputLine.indexOf("</span> "));
							 rating = rating.replace("<span class=\"grnb_20\">", "");
							 isSentimeter = false;
							 
							 gainerBean = new TopGainerBean();
						 	 gainerBean.setSentimeterRating(rating);
							 gainerBean.setApi(url);
							 urlSet.add(url);
							 writeVolumeToCSVFile(gainerBean);
						} catch (Exception e) {
						}
						 
					 }
					
				 }
				}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bufferReader!=null){
				bufferReader.close();	
			}
		}
		
	}
private void writeVolumeToCSVFile(TopGainerBean bean) {
		try {
			bwObj.write(bean.getSentimeterRating()+","+bean.getApi()+"\n");
			} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

@Override
    public String toString(){
        return this.command;
    }		 
}
