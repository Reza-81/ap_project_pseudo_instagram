package authentication;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class SendEmail {
	final static String senderEmail = "@gmail.com"; //change email address
	final static String senderPassword = "****"; //change password
	final static String emailSMTPserver = "smtp.gmail.com";
	final static String emailServerPort = "465";
	
	public static class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(senderEmail, senderPassword);
		}
	}
	
	public static boolean run(String receiverEmail, String subject, String message) {
		Properties props = new Properties();
		props.put("mail.smtp.user",senderEmail);
		props.put("mail.smtp.host", emailSMTPserver);
		props.put("mail.smtp.port", emailServerPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", emailServerPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		try {
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			MimeMessage msg = new MimeMessage(session);
			msg.setText(message);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(senderEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
			Transport.send(msg);
			return true;
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}
}
