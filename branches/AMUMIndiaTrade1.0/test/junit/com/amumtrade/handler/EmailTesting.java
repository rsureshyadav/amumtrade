package com.amumtrade.handler;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTesting {
	public static void main(String[] arg){
		execute();
	}
   public static  void execute() {
       // Recipient's email ID needs to be mentioned.
      String to = "rsureshyadav@gmail.com";
      // Sender's email ID needs to be mentioned
      String from = "amumreach@gmail.com";

      final String username = "amumreach";//change accordingly
      final String password = "amumreach!";//change accordingly

      // Assuming you are sending email through imap.gmail.com
      String host = "imap.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "25");

      // Get the Session object.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
         });

      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));

         // Set Subject: header field
         message.setSubject("Hello World!!!");

         // Create the message part
         BodyPart messageBodyPart = new MimeBodyPart();
         String title="AMUMTrade";
         String res = "Hiiiiiiiiiiii";
         // Now set the actual message
         messageBodyPart.setContent("<html>\n" +
                 "<head><title>" + title + "</title></head>\n" +
                 "<body bgcolor=\"#f0f0f0\">\n" +
                 "<h1 align=\"center\">" + title + "</h1>\n" +
                 "<p align=\"center\">" + res + "</p>\n" +
                 "</body></html>","text/html");

         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         String filename = "config/output/AMUM_ALL_ConcurrentGainers_Analyzer.csv";
         DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart);

         // Send message
         Transport.send(message);

         System.out.println("Sent email message successfully....");
  
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
}