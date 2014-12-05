package com.amumtrade.email;

import java.io.IOException;
import java.util.List;

import com.amumtrade.bean.ConcurrentGainersBean;
import com.amumtrade.constant.FileNameConstant;
import com.amumtrade.util.StockUtil;

public class CsvToEmailBody {

	public String execute(String fileName) throws IOException{
		String emailText = null;
		try {
			List<ConcurrentGainersBean> beanList  = StockUtil.convertCsvToBean(fileName);
			if(beanList != null){
				emailText = getEmailBodyFromBean(beanList);
				emailText = FileNameConstant.HTML_TEMPLATE.replace("@htmlBody", emailText);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailText;
	}

	private String getEmailBodyFromBean(List<ConcurrentGainersBean> beanList) {
		StringBuffer htmlText = new StringBuffer();
		int skipFirstLineHeader=0;
		try {
			for(ConcurrentGainersBean bean : beanList){
				if(skipFirstLineHeader==0){ 
					htmlText.append("<tr><th>"+bean.getCompanyName()+"</th>"
							+ "<th>"+bean.getCurrentPrice()+"</th>"
							+"<th>"+bean.getCurrentDayVolume()+"</th>"
							+"<th>"+bean.getFiveDayAvgVolume()+"</th>"
							+"<th>"+bean.getTenDayAvgVolume()+"</th>"
							+"<th>"+bean.getThirtyDayAvgVolume()+"</th>"
							+"<th>"+bean.getVolumeRating()+"</th>"
							+"<th>"+bean.getEps()+"</th>"
							+"<th>"+bean.getEpsRating()+"</th>"
							+"<th>"+bean.getStandaloneProfit()+"</th>"
							+"<th>"+bean.getRecommendation()+"</th>"
							+"<th>"+bean.getNews()+"</th></tr>\n");
				}else if(bean.getRecommendation().contains("BUY")|| bean.getRecommendation().contains("buy")){
					htmlText.append("<tr><td>"+bean.getCompanyName()+"</td>"
							+ "<td>"+bean.getCurrentPrice()+"</td>"
							+"<td>"+bean.getCurrentDayVolume()+"</td>"
							+"<td>"+bean.getFiveDayAvgVolume()+"</td>"
							+"<td>"+bean.getTenDayAvgVolume()+"</td>"
							+"<td>"+bean.getThirtyDayAvgVolume()+"</td>"
							+"<td>"+bean.getVolumeRating()+"</td>"
							+"<td>"+bean.getEps()+"</td>"
							+"<td>"+bean.getEpsRating()+"</td>"
							+"<td>"+bean.getStandaloneProfit()+"</td>"
							+"<td>"+bean.getRecommendation()+"</td>"
							+"<td>"+bean.getNews()+"</td></tr>\n");
				}
				skipFirstLineHeader++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlText.toString();
	}
}
