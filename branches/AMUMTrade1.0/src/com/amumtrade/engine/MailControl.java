package com.amumtrade.engine;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class MailControl {
/*
 *  Be Familiar with Gmail IMAP Folders

Gmail IMAP has the following special folders:

Inbox
[Gmail]/All Mail – This folder contains all of your Gmail messages.
[Gmail]/Drafts – Your drafts.
[Gmail]/Sent Mail – Messages you sent to other people.
[Gmail]/Spam – Messages marked as spam.
[Gmail]/Starred – Starred messages.
[Gmail]/Trash – Messages deleted from Gmail.
 */
	
	public MailControl(){
		
	}
    public static void main(String args[]) throws Exception {
    	List<String> tdUrMailList=null;
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
                Session session = Session.getDefaultInstance(props, null);
                Store store = session.getStore("imaps");

                // IMAP host for gmail. 
                // Replace <username> with the valid username of your Email ID.
                // Replace <password> with a valid password of your Email ID.

                store.connect("imap.gmail.com", "rsureshyadav@gmail.com", "hclt1981!");

                // IMAP host for yahoo.
                //store.connect("imap.mail.yahoo.com", "<username>", "<password>");

                System.out.println(store);

                //Folder inbox = store.getFolder("[Gmail]/AMERITRADE");
                Folder inbox = store.getFolder("inbox");

                inbox.open(Folder.READ_ONLY);
                BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Press (U) to get only unread mails OR Press (A) to get all mails:");
                try {
                    char answer = (char) optionReader.read();
                    if(answer=='A' || answer=='a'){
                        showAllMails(inbox);
                    }else if(answer=='U' || answer=='u'){
                    	tdUrMailList=(ArrayList<String>)tdReadMails(inbox);
                    }
                    if(tdUrMailList==null){
                    	throw  new Exception("No unread email on TD Ameritrade");
                    }else{
                    	getStockSymbolPrice(tdUrMailList);
                    }
                    optionReader.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
                
        } catch (NoSuchProviderException e) {
            System.out.println(e.toString());
            System.exit(1);
        } catch (MessagingException e) {
            System.out.println(e.toString());
            System.exit(2);
        }

    }
    
    private static void getStockSymbolPrice(List<String> mailList) {
    	String sendDate=null;
    	String code=null;
    	String symbol=null;
    	String price=null;
    	boolean flag=false;
    	System.out.println("TD mail Size>>>"+mailList.size());
		try {
			for(String content : mailList){
			StringBuffer buffer = new StringBuffer(content);
			sendDate=content.substring(0, 30).replace("DATE:", "");
			StringTokenizer tokenizer =new StringTokenizer(content.trim());
			while(tokenizer.hasMoreTokens()){
				code=tokenizer.nextToken();
				if(code.contains("symbol")){
					//uncomment for troubleshooting
					//System.out.println("*******************************");
					//System.out.println(code);
					//System.out.println("*******************************");
					//To avoid similar issue - else if will fix this type of string
					//href="http://research.tdameritrade.com/public/stocks/overview/overview.asp?symbol=CYCC"
					if(code.contains("symbol=")&&code.contains("\">")){
						code=code.substring(code.indexOf("symbol="), code.lastIndexOf("\">"));
						symbol=code.replace("symbol=", "");
						flag=true;
					}else if(code.contains("symbol=")&&!code.contains("\">")){
						code=code.substring(code.lastIndexOf("symbol="));
						code=code.replace("symbol=", "");
						symbol=code.replace("\"", "");
						flag=true;
					}
				}
				
				if(flag && code.contains("$")){
					price=code.substring(code.indexOf("$"), code.lastIndexOf("</td>"));
					flag=false;
				}
				//href="http://research.tdameritrade.com/public/stocks/overview/overview.asp?symbol=VRNG">VRNG</a></td>

				
			}
			System.out.println(">>"+symbol+">>"+price);
			//System.out.println(mail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
}

	static ArrayList<String> tdReadMails(Folder inbox) throws IOException{   
    	String date=null;
        String from =null;            
        String subject=null;
        String content=null;

        ArrayList<String> urMailList = new ArrayList<String>();
        try {
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message msg[] = inbox.search(ft);
            System.out.println("MAILS: "+msg.length);
            int count =0;
            File f;
            f=new File("test/tdmails.txt");
            if(!f.exists()){
            	
            	f.createNewFile();
            	
            }
            
            BufferedWriter out = new BufferedWriter(new FileWriter(f));
            for(Message message:msg) {
                try {
                     date="DATE: "+message.getSentDate().toString();
                     from ="FROM: "+message.getFrom()[0].toString();            
                     subject="SUBJECT: "+message.getSubject().toString();
                     content="CONTENT: "+message.getContent().toString();
                     if(from.contains("TDAlerts")){
                    	// System.out.println(content);
                    	 //System.out.println("******************************************");
                    	 
                   
               		out.write(content);
               		
                         urMailList.add(content);
                     } 

                     count++;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("No Information");
                }
            }
    		out.close();

        } catch (MessagingException e) {
        	
        	
        	
            System.out.println(e.toString());
        }
        System.out.println("ur size>>"+urMailList.size());
        return urMailList;
    }
    
    static public void showAllMails(Folder inbox){
        try {
            Message msg[] = inbox.getMessages();
            System.out.println("MAILS: "+msg.length);
            for(Message message:msg) {
                try {
                    System.out.println("DATE: "+message.getSentDate().toString());
                    System.out.println("FROM: "+message.getFrom()[0].toString());            
                    System.out.println("SUBJECT: "+message.getSubject().toString());
                    System.out.println("CONTENT: "+message.getContent().toString());
                    System.out.println("******************************************");
                } catch (Exception e) {
                    System.out.println("No Information");
                }
            }
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }

}