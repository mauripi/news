package br.com.mauricio.news.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
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

public class Email  extends Thread {
	private Properties props = new Properties();
	private String dests;
	private String assunto;
	private String texto;
	private String nomeFrom;
	private List<String> destinatarios;
	private List<File> anexos;
	
	private void getConfiguracao(){
        /** Parâmetros de conexão com servidor Gmail     */
        props.put("mail.smtp.user", "ti_sistema@recordnews.com.br");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.fallback", "true");
	}

	public Email(String nomeFrom, List<String> listDest,String ass,String txt ){
		this.destinatarios=listDest;
		this.assunto=ass;
		this.texto=txt;
	}
	
	public Email(String nomeFrom, List<String> listDest,String ass,String txt,List<File> anexos ){
		this.destinatarios=listDest;
		this.assunto=ass;
		this.texto=txt;
		this.anexos = anexos;
		this.nomeFrom = nomeFrom;
	}

	private  String getDestinatarios(List<String> dests) {
		StringBuilder sb = new StringBuilder();
		String ds="";
		for(int i=0;i<dests.size();i++){
			sb.append(dests.get(i)+",");
		}
		ds=sb.toString().substring(0, sb.toString().length()-1);
		return ds;
	}
	
	   @SuppressWarnings("deprecation")
	@Override
	   public void run() {
			getConfiguracao();
	        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
		           protected PasswordAuthentication getPasswordAuthentication() //"ti_sistema@recordnews.com.br", "rnews@#2015"
		           {
		                 return new PasswordAuthentication("ti_sistema@recordnews.com.br", "consolador");
		           }
		      });	
		    /** Ativa Debug para sessão */
		    session.setDebug(false);
			dests = getDestinatarios(destinatarios);
			try {

	           Message message = new MimeMessage(session);

	           try {
				message.setFrom(new InternetAddress("ti_sistema@recordnews.com.br", nomeFrom));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} //Remetente
	           Address[] toUser = InternetAddress.parse(dests);  //destinatarios

	           message.setRecipients(Message.RecipientType.TO, toUser);
	           message.setSubject(this.assunto);
	           Multipart multipart = new MimeMultipart();
	           
	           MimeBodyPart corpo = new MimeBodyPart();
	           corpo.setContent(this.texto,"text/html; charset=UTF-8");
	           corpo.setDisposition(MimeBodyPart.INLINE);
	           multipart.addBodyPart(corpo);
	           
	          if(anexos!=null){
		           for(File file:anexos){
			           MimeBodyPart attachment = new MimeBodyPart();
			           attachment.setDataHandler(new DataHandler(new FileDataSource(file)));
			           attachment.setFileName(file.getName());
			           multipart.addBodyPart(attachment);
		           }
	          }
	           message.setContent(multipart);
	           /**Método para enviar a mensagem criada*/ 
	           Transport.send(message);
		      } catch (MessagingException e) {
		    	  System.out.println(e.getLocalizedMessage());
		           throw new RuntimeException(e);
		     }
			stop();
	   }
}
