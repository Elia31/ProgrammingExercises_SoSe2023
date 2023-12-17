package com.example.carsellingplatform.backend.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail
{
	private static final String username;
	private static final String password;
	private static final Properties props;

	static
	{
		username = Config.getEmailUsername();
		password = Config.getEmailPassword();

		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "mail.gmx.net");
		props.put("mail.smtp.port", "587");
	}

	private static Session prepareSession()
	{
		return Session.getDefaultInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});
	}

	public static void sendEmailRegistration(String recipientEmail)
	{
		try
		{
			MimeMessage message = new MimeMessage(prepareSession());
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			String content = "<html>"
					+ "<head>"
					+ "<style>"
					+ "body { font-family: Arial, sans-serif; background-color: #f7f7f7; }"
					+ "h1 { color: #333333; }"
					+ "p { color: #666666; }"
					+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
					+ "</style>"
					+ "</head>"
					+ "<body>"
					+ "<div class=\"container\">"
					+ "<h1>Welcome!</h1>"
					+ "<p>Thank you for registering with Carsale.</p>"
					+ "<p>We are delighted to have you as part of our community.</p>"
					+ "<p>If you have any questions or concerns, feel free to contact our support team.</p>"
					+ "<p>Enjoy your time and success with Carsale!</p>"
					+ "</div>"
					+ "</body>"
					+ "</html>";

			message.setSubject("Welcome at Carsale!");
			message.setContent(content, "text/html; charset=UTF-8");

			Transport.send(message);
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}

	public static void sendEmailDeletion(String recipientEmail)
	{
		try
		{
			MimeMessage message = new MimeMessage(prepareSession());
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			String content = "<html>"
					+ "<head>"
					+ "<style>"
					+ "body { font-family: Arial, sans-serif; background-color: #f7f7f7; }"
					+ "h1 { color: #333333; }"
					+ "p { color: #666666; }"
					+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
					+ "</style>"
					+ "</head>"
					+ "<body>"
					+ "<div class=\"container\">"
					+ "<h1>We are sad to see you go!</h1>"
					+ "<p>Thank you for being a part of our community.</p>"
					+ "<p>We appreciate your time and participation on Carsale.</p>"
					+ "<p>If you have any feedback or if there's anything we can do to improve, please let us know.</p>"
					+ "<p>Keep in mind that deleting your account will permanently remove all your data and listings.</p>"
					+ "<p>If you wish to proceed with the account deletion, please contact our support team.</p>"
					+ "<p>We hope to see you again in the future.</p>"
					+ "</div>"
					+ "</body>"
					+ "</html>";

			message.setSubject("Goodbye from Carsale!");
			message.setContent(content, "text/html; charset=UTF-8");

			Transport.send(message);
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}

	public static void sendOnetimeCode(String recipientEmail, String code)
	{
		try
		{
			MimeMessage message = new MimeMessage(prepareSession());
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			String content = "<html>"
					+ "<head>"
					+ "<style>"
					+ "body { font-family: Arial, sans-serif; background-color: #f7f7f7; }"
					+ "h1 { color: #333333; }"
					+ "p { color: #666666; }"
					+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
					+ "</style>"
					+ "</head>"
					+ "<body>"
					+ "<div class=\"container\">"
					+ "<h1>Here is your Code for resetting your password!</h1>"
					+ "<p>Please enter following code:</p>"
					+ "<p style=\"font-size: 24px; font-weight: bold; text-align: center;\">" + code + "</p>"
					+ "<p>Do not share it with anyone!</p>"
					+ "</div>"
					+ "</body>"
					+ "</html>";

			message.setSubject("Your password reset code!");
			message.setContent(content, "text/html; charset=UTF-8");

			Transport.send(message);
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}

	public static void sendNewPasswordConfirmation(String recipientEmail)
	{
		try
		{
			MimeMessage message = new MimeMessage(prepareSession());
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			String content = "<html>"
					+ "<head>"
					+ "<style>"
					+ "body { font-family: Arial, sans-serif; background-color: #f7f7f7; }"
					+ "h1 { color: #333333; }"
					+ "p { color: #666666; }"
					+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
					+ "</style>"
					+ "</head>"
					+ "<body>"
					+ "<div class=\"container\">"
					+ "<h1>Your Password has been updated!</h1>"
					+ "<p>Password reset has been successful!</p>"
					+ "</div>"
					+ "</body>"
					+ "</html>";

			message.setSubject("Your Password was updated!");
			message.setContent(content, "text/html; charset=UTF-8");

			Transport.send(message);
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}

	public static void sendMessage(String recipientEmail, String senderEmail, String senderUsername, String listing,
								   String text)
	{
		try
		{
			MimeMessage message = new MimeMessage(prepareSession());
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			String mailtoLink = "mailto:" + senderEmail + "?subject=Conversattion about " + listing;
			String content = "<html>"
					+ "<head>"
					+ "<style>"
					+ "body { font-family: Arial, sans-serif; background-color: #f7f7f7; }"
					+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
					+ ".message-container { background-color: #f0f0f0; padding: 10px; margin-bottom: 10px; }"
					+ ".user-message { color: #333333; }"
					+ ".reply-link { font-weight: bold; }"
					+ ".listing-title { font-size: 18px; margin-bottom: 10px; }"
					+ "</style>"
					+ "</head>"
					+ "<body>"
					+ "<div class=\"container\">"
					+ "<h1>" + senderUsername + " wrote to you about your listing:</h1>"
					+ "<h2 class=\"listing-title\">" + listing + "</h2>"
					+ "<div class=\"message-container\">"
					+ "<pre class=\"user-message\">" + text + "</pre>"
					+ "</div>"
					+ "<p>To answer " + senderUsername + ", click <a class=\"reply-link\" href=\"" + mailtoLink
					+ "\">here</a> or write him an email at " + senderEmail + ".</p>"
					+ "</div>"
					+ "</body>"
					+ "</html>";

			message.setSubject("Someone wrote you about " + listing + "!");
			message.setContent(content, "text/html; charset=UTF-8");

			Transport.send(message);
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}
