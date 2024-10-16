package org.aia.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Comparator;

import javax.mail.*;
import javax.mail.internet.*;
import static org.aia.java_mail_API.EmailConfig.*;

public class ExeEmailUtils {
    
    public static void emailDetails(String subject, String body, String[] toAddress) {
    	System.out.println("****************************************");
		System.out.println("Send Email - START");
		System.out.println("****************************************");
    	// Set up SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        // Set up authentication credentials
        String username = "aia500test@gmail.com";
        String password = "dpnlfpivjzpfozak";
        
        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            // Create a MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            InternetAddress[] addressTo = new InternetAddress[toAddress.length]; 
			for (int i = 0; i < toAddress.length; i++) 
			  addressTo[i] = new InternetAddress(toAddress[i]);
			message.setRecipients(Message.RecipientType.TO, addressTo);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            
            // Send the email
            Transport.send(message);
            System.out.println("****************************************");
			System.out.println("Email sent successfully.");
			System.out.println("Send Email - END");
			System.out.println("****************************************");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendEmail(String subject) {
    	 String directoryPath = System.getProperty("user.dir") + "/Reports/testReport.html";
         
        StringBuilder htmlContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(directoryPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    	emailDetails(subject,htmlContent.toString(),TO);
    }
    
}