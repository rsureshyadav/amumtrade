package com.amumtrade.handler;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadLinkHandler {

	public static void execute(String url, String filePath) throws Exception{
		
		 URL website = new URL(url); 
		    ReadableByteChannel rbc = Channels.newChannel(website.openStream()); 
		    FileOutputStream fos = new FileOutputStream(filePath); 
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24); 
	}
	
	public static void columnRemover(String filePath){
		
	}
		
}
