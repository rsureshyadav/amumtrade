package com.amumtrade.factory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.AMUMStockConstant;

public class ApiRunner implements Runnable {
	private String command;
	private URL urlConn;
	private Map<String,ConcurrentGainersBean> positiveMap;
	private BufferedWriter bwObj;
	
	public ApiRunner(URL httpUrl,Map<String,ConcurrentGainersBean> postiveTurnAroundMap,BufferedWriter bufferWriter,String s){
		this.urlConn=httpUrl;
		this.positiveMap = postiveTurnAroundMap;
		this.bwObj = bufferWriter;
		this.command=s;
	}
	public void run() {
	    //  System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
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
		String inputLine = null;
		String api = null;
		try {
			 while ((inputLine = bufferReader.readLine()) != null)
		        {
				 if(inputLine.contains("<dt class=\"home\">")){
					// bean = new ConcurrentGainersBean();
					  api = inputLine.trim();
					  api  = api.substring(api.indexOf("href"),api.lastIndexOf(">Quote"));
					  api  = api.replace("href=\"", ""); 
					  api  = api.replace("\"", "").trim();
					  api = AMUMStockConstant.STOCK_URL+api;
					  ConcurrentGainersBean postiveBean =  positiveMap.get(url);
					  postiveBean.setApi(api);
					 writeVolumeToCSVFile(postiveBean);
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
private void writeVolumeToCSVFile(ConcurrentGainersBean bean) {
	//CompanyName,QuarterSale,PrevYearSale,% ChgSale,QuarterNetProfit,PrevYearNetProfit,NetProfitChg(Rs.Cr.),Api
		try {
				bwObj.write(bean.getCompanyName()+","+bean.getQuarterSale()+","+ bean.getPrevYearSale()+","
						+ bean.getPercentChgSale()+","+ bean.getQuarterNetProfit()+","+ bean.getPrevYearNetProfit()+","
						+bean.getNetProfitChg()+","+bean.getPositiveAPI()+","+bean.getApi()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

@Override
    public String toString(){
        return this.command;
    }		 
}
