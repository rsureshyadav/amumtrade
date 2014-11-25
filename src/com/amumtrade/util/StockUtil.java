package com.amumtrade.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amumtrade.bean.ConcurrentGainersBean;

public class StockUtil {

	public static void initiateEmail(String filePath, long startTime, String htmlText ){
		String startDateTimeLog = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			startDateTimeLog = dateFormat.format(date);
			SendAttachmentInEmail email = new SendAttachmentInEmail();
			email.execute(filePath, startDateTimeLog, getTotalExecutionTime(startTime),htmlText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getTotalExecutionTime(long startTime){
		String totalExecutionTime = null;
		long endTime= System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
			
			int s = (int) ((elapsedTime / 1000) % 60);
			int m = (int) ((elapsedTime / (1000 * 60)) % 60);
			int h = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
			
			totalExecutionTime =  "Total Execution Time: "+ h +" : "+ m +" : "+ s;
		return totalExecutionTime;
	}
	
	public static List<ConcurrentGainersBean> convertCsvToBean(String csvFile) throws IOException {
		BufferedReader br =null;
		List<ConcurrentGainersBean>  gainerBeanList= new ArrayList<ConcurrentGainersBean>();
		ConcurrentGainersBean gainerBean = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
					gainerBean = new ConcurrentGainersBean();
					String[] conGainers = line.split(cvsSplitBy);
					gainerBean.setName(conGainers[0]);
					gainerBean.setCurrentPrice(conGainers[1]);
					gainerBean.setCurrentDayVolume(conGainers[2]);
					gainerBean.setFiveDayAvgVolume(conGainers[3]);
					gainerBean.setTenDayAvgVolume(conGainers[4]);
					gainerBean.setThirtyDayAvgVolume(conGainers[5]);
					gainerBean.setVolumeRating(conGainers[6]);
					gainerBean.setEps(conGainers[7]);
					gainerBean.setEpsRating(conGainers[8]);
					gainerBean.setStandaloneProfit(conGainers[9]);
					gainerBean.setRecommendation(conGainers[10]);
					gainerBean.setNews(conGainers[11]);
					gainerBean.setApi(conGainers[12]);
					gainerBeanList.add(gainerBean);
				//}
				//skipFirstLineHeader++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br !=null){
				br.close();
			}
		}
		return gainerBeanList;
	}
}
