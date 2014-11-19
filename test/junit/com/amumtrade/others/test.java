package com.amumtrade.others;


public class test {
public static void main(String str[]){
	String companyUrl=null;
	String companyName = null;
	String companyGroup = null;
	String companyPrevClose = null;
	String companyCurrentPrice = null;
	double diff = 0;
	//Company	Group	Prev Close (Rs)	Current Price (Rs)	% Change
		
	String str1="<tr><td><ahref=http://money.rediff.com/companies/nestle-india/11120007>NestleIndiaIndia</a></td><td>A</td><td>5030.05</td><td>5178.85</td><td><FONTclass=green>+2.96</FONT></td></tr>";
	
	companyUrl = str1.substring(str1.indexOf("ahref")+6, str1.lastIndexOf("</a>"));
	companyName = companyUrl.substring(companyUrl.indexOf(">")+1);
	companyUrl = companyUrl.substring(0, companyUrl.indexOf(">"));
	companyGroup=str1.substring(str1.indexOf("</td><td>"), str1.lastIndexOf("<FONT"));
	companyGroup=companyGroup.replace("</td><td>", ",");
	companyGroup = companyGroup.substring(companyGroup.indexOf(",")+1, companyGroup.lastIndexOf(","));
	System.out.println(companyGroup);
	String[] strSplit = companyGroup.split(",");
	companyGroup = strSplit[0];
	companyPrevClose = strSplit[1]; 
	companyCurrentPrice = strSplit[2]; 
	System.out.println(companyUrl);
	System.out.println(companyName);
	String output = companyName.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
	System.out.println(output);
	System.out.println(companyGroup);
	System.out.println(companyPrevClose);
	System.out.println(companyCurrentPrice);
	
	double prevPrice =  Double.valueOf(companyPrevClose);
	double currPrice = Double.valueOf(companyCurrentPrice);
	diff = prevPrice - currPrice;
	double  priceRoundOff = Math.round(diff * 100.0) / 100.0;
	System.out.println(priceRoundOff);

}
}
