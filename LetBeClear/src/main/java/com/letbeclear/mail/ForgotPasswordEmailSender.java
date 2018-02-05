package com.letbeclear.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPasswordEmailSender 
{
	private class SMTPAuthenticator extends Authenticator
	{
		public SMTPAuthenticator()
		{
			
		}
		
		/**
	     * Called when password authentication is needed.  Subclasses should
	     * override the default implementation, which returns null. <p>
	     *
	     * Note that if this method uses a dialog to prompt the user for this
	     * information, the dialog needs to block until the user supplies the
	     * information.  This method can not simply return after showing the
	     * dialog.
	     * @return The PasswordAuthentication collected from the
	     *		user, or null if none is provided.
	     */
		@Override
	    protected PasswordAuthentication getPasswordAuthentication()
	    {
	        return new PasswordAuthentication("moptrainfotech25@gmail.com", "Moptra@2017");
	    }
	}
	
	public void sendEmail(String mailToSend,String password)
	{
		try
		{
			String host="smtp.gmail.com";
			String port="465";
			String user="moptrainfotech25@gmail.com";
			String pass="Moptra@2017";
			
			String to=mailToSend;
			String from="moptrainfotech25@gmail.com";
			
			String subject="Please don't share this password with anyone";
			String messageText="Your password is "+password;
			
			//boolean sessionDebug=false;
			
			//Getting the Session Object
			
			Properties property=System.getProperties();
		
			property.put("user", user);
			property.put("mail.smtp.host", host); 
		    property.put("mail.smtp.port", port);
			property.put("mail.smtp.starttls.enable", "true");
		    property.put("mail.smtp.debug","true");
		    property.put("mail.smtp.auth","true");
		    property.put("mail.smtp.socketFactory.port", port);
		    property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		    property.put("mail.smtp.socketFactory.fallback", "false");
		    
		    //property.put("mail.smtp.starttls.required","true");
		   
		    //Getting Session
		    
		    SMTPAuthenticator auth = new SMTPAuthenticator();
		    Session session = Session.getInstance(property, auth);
		    session.setDebug(true);
		    
//			Session session=Session.getDefaultInstance(property, null);
			
		    //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		    
		    
			//Composing the message
			
		    MimeMessage message= new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			message.setSubject(subject);
			message.setText(messageText);
			message.setSentDate(new Date());
			
			Transport transport=session.getTransport("smtps");
			transport.connect(host,Integer.valueOf(port), user, pass);
			
			transport.send(message, message.getAllRecipients());
			transport.close();
			
			System.out.println("message sent successfully");
		}
		catch(MessagingException mex)
		{
			System.out.println("Exception occurs Inside ForgotPasswordEmailSender");
			mex.printStackTrace();
		}
	}
}
