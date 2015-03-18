package com.amumtrade.others;

import java.util.ArrayList;
import java.util.List;


public class test {
	
public static void main(String str[]){
	List<String> src = new ArrayList<String>();
	List<String> dest = new ArrayList<String>();
	List<String> dest1 = new ArrayList<String>();
	List<String> dest2 = new ArrayList<String>();

	src.add("1");
	src.add("2");
	src.add("3");
	src.add("4");
	src.add("5");
	
	dest1.add("3");
	dest1.add("4");
	dest1.add("5");
	dest1.add("6");
	dest1.add("7");
	
	dest2.addAll(dest1);
	
	dest1.removeAll(src);
	for(String s: dest1){
		System.out.println(s);
		dest.add(s+",Y");
	}
	System.out.println("---------------------------");
	dest2.retainAll(src);
	for(String s: dest2){
		dest.add(s+",N");
		System.out.println(s);
	}
	
	System.out.println("------------------------------");
	for(String s: dest){
		//System.out.println(s);
	}
}
}
