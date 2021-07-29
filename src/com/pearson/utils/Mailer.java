package com.pearson.utils;


import java.util.Properties;    
import javax.mail.*;    
import javax.mail.internet.*;

import org.apache.log4j.Logger;
   

public class Mailer
{
	private static Logger log=Logger.getLogger(Mailer.class);
	static String configFilename="./config/config";
	public static void send(String from,String password,String address,String sub,String msg)
	{ 

		//Get properties object   
		
		Properties props = new Properties();    
		props.put("mail.smtp.host", "smtp.gmail.com");    
		props.put("mail.smtp.socketFactory.port", "465");    
		props.put("mail.smtp.socketFactory.class",    
				"javax.net.ssl.SSLSocketFactory");    
		props.put("mail.smtp.auth", "true");    
		props.put("mail.smtp.port", "465");    
		//get Session   
		Session session = Session.getDefaultInstance(props,    
				new javax.mail.Authenticator() {    
			protected PasswordAuthentication getPasswordAuthentication() 
			{    
				return new PasswordAuthentication(from,password);  
			}    
		});    
		//compose message    
		try 
		{    
			MimeMessage message = new MimeMessage(session);   
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
			message.setSubject(sub);    
			//message.setText(msg);   
			message.setContent(msg, "text/html; charset=utf-8");
			//send message  
			Transport.send(message);    
			//System.out.println("Mail sent successfully");    
			log.info("Automation email report has been sent to all recipients");		
		} 
		catch (Exception e) 
		{
			log.error("Error sending email "+e);
			throw new RuntimeException(e);
		}    

	}
	
	
	

	public static void sendMail(String message, String email_subject) throws Exception
	{
		String senderEmail=PropertyUtil.getproperty(configFilename, "sender_email");
		String senderPassword=PropertyUtil.getproperty(configFilename, "sender_password");
		String recepientEmails=PropertyUtil.getproperty(configFilename, "recepient_emails");
		//String email_subject=PropertyUtil.getproperty(configFilename, "email_subject");
		Mailer.send(senderEmail, senderPassword, recepientEmails, email_subject, message);

	}

	public static void main(String[] args) 
	{    
		//from,password,to,subject,message  
		Mailer.send("abhinav.anand@magicedtech.com","Abhinav##8978","sadab.alam@magicedtech.com","Report","Etext");  
		//change from, password and to  
	}    
}    