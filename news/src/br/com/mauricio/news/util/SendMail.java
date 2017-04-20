package br.com.mauricio.news.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class SendMail {

	private final static String userAut = "ti_sistema@recordnews.com.br";
	private final static String hostSmtp = "smtp.gmail.com";
	private final static String sslSmtpPort = "587";
	private final static String pwd = "consolador";
	

	public static void sendHtml(HtmlEmail email){ 
		try{  
			email.setFrom(userAut, "Intranet Record News"); 
		    email.setHostName(hostSmtp);  
		    email.setSslSmtpPort(sslSmtpPort);
		    email.setSmtpPort(Integer.parseInt(sslSmtpPort));    
		    email.setStartTLSEnabled(true);
		    email.setSSLOnConnect(true);
		    email.setAuthenticator(new Authenticator(){
		           protected PasswordAuthentication getPasswordAuthentication() 
		           {return new PasswordAuthentication(userAut, pwd); }
		    });
		   
	
		    email.send();  
		}catch(EmailException e){
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void sendSimple(SimpleEmail email){
		try {
			email.setFrom(userAut, "Intranet Record News"); 
		    email.setHostName(hostSmtp);  
		    email.setSslSmtpPort(sslSmtpPort);
		    email.setSmtpPort(Integer.parseInt(sslSmtpPort));    
		    email.setStartTLSEnabled(true);
		    email.setSSLOnConnect(true);
		    email.setAuthenticator(new Authenticator(){
		           protected PasswordAuthentication getPasswordAuthentication() 
		           {return new PasswordAuthentication(userAut, pwd); }
		    });
		   	
		    email.send();
		     
		} catch (EmailException e) {
		    e.printStackTrace();
		} 		
	}
	
}
