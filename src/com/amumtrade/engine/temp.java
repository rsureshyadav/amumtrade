package com.amumtrade.engine;

public class temp {

	public static void main(String str[]){
		String s ="href=\"http://research.tdameritrade.com/public/stocks/overview/overview.asp?symbol=CYCC\"";
		
		if(s.contains("symbol=")&&!s.contains("\">")){
			s=s.substring(s.lastIndexOf("symbol="));
			s=s.replace("symbol=", "");
			String s1=s.replace("\"", "");
			System.out.println(s1);
		}
	}
}
