//package com.drkrishi.usermanagement.service;
//
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class EmailServiceImpl implements EmailService {
//
//	String signature = "Regards,\nDr.Krishi";
//
//	@Autowired
//    private JavaMailSender javaMailSender;
//
//	@Override
//	public void invalidAttemptNotificationEmail(String emailAddress, String user) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("Account Access Attempt (Do Not Reply to this Email)");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email.\n\nWe have detected that an attempt was made to access your account. This attempt failed. If you are unaware of the failed password attempt, please contact admin immediately.\n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void oneTimePasswordEmail(String emailAddress, String user, String password) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//	        msg.setTo(emailAddress);
//
//	        msg.setSubject("One Time Password (Do Not Reply to this Email)");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\n Please do not reply to this email.\n Your one-time password is "+password+"\n\nPlease use the above password for logging into the Dr. Krishi. \n\n"+signature);
//
//	        javaMailSender.send(msg);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	@Override
//	public void passwordUpdatedEmail(String emailAddress, String user) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("Password Update (Do Not Reply to this Email) ");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email.Your password has been updated OR Your password has been reset successfully.\n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void userActivationEmail(String emailAddress, String user) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("User Activation :  ");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email. \n\n You have received the following notification from Cropdata.\n\n The status of the user has been changed from deactivated to activated. Kindly login at Dr. Krishi platform to access your account. \n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void userInActivationEmail(String emailAddress, String user){
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("User Deactivation: ");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email.\n\nYou have received the following notification from Cropdata.The status of the user has been changed from activated to deactivated. \n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	@Override
//	public void userActivationFromLockedStatusEmail(String emailAddress, String user, String username, String password){
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("User Activation : ");
//			msg.setText(
//					"Dr.Krishi "+user+", \n\nPlease do not reply to this email. \n\nYou have received the following notification from Cropdata.The status of the user has been changed from Locked to activated. Kindly login at Dr. Krishi platform to access your account. \n\n Login Details:\n\n UserName: "+username+" \n Password: "+password+"\n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void userCreationEMail(String emailAddress, String user, String username, String password){
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("User Creation : ");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email. \n\n You have received the following notification from Cropdata. You have been registered in Dr.Krishi. Kindly login at Dr. Krishi platform using below credentials to activate your account. You will be asked to change your password on first login. The password will be valid for 24 hours only. \n\nLogin Details:\n\nUserName: "+username+" \nPassword: "+password+"\n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void userUpdateNotificationEmail(String emailAddress, String user) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("Profile Details Update : ");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email. \n\nYour Profile details have been updated successfully.  \n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Override
//	public void roleAssignNotification(String emailAddress, String user, String newUser) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("Role Assignment (Do Not Reply to this Email)");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email.\n\n"+newUser+" has joined the organization. Please login in the platform  to assign a role to the new joinee.\n\n "+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void loggedOutNotificationEmail(String emailAddress, String user) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("Account Locked (Do Not Reply to this Email)");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email.\n\nYour account has been locked. You will be able to login again the next day. If you want login into the system now, please contact admin.\n\n" + signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void unLockedNotificationEmail(String emailAddress, String user, String username, String password) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("Account UnLocked-(Do Not Reply to this Email)");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email.\n\nYour account has been unlocked. You will be able to login through the below credentials. The password will be valid for 24 hours.\n\n Login Details:\n\n UserName: "+username+" \n Password: "+password+"\n\n" +signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void userRoleUpdateNotificationEmail(String emailAddress, String user) {
//
//		try {
//			emailAddress = formatEmailAddress(emailAddress);
//			SimpleMailMessage msg = new SimpleMailMessage();
//			msg.setTo(emailAddress);
//
//			msg.setSubject("Role Details Update (Do Not Reply to this Email)");
//			msg.setText(
//					"Dr.Krishi "+user+",\n\nPlease do not reply to this email. \n\nYour role has been updated successfully. \n\n"+signature);
//
//			javaMailSender.send(msg);
//		} catch (MailException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String formatEmailAddress(String emailAddress) {
//
//		if( emailAddress  != null ) {
//
//			int index = emailAddress.indexOf('@');
//			String substring = emailAddress.substring(index);
//
//			if( substring.equals("@xyz.com") ) {
//				emailAddress = "kondal.rao@3i-infotech.com";
//			}
//		}
//
//		return emailAddress;
//	}
//
//	@Override
//	public void sendEmailAsAttachment() {
//
//		try {
//			MimeMessage msg = javaMailSender.createMimeMessage();
//
//	        // true = multipart message
//	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//	        helper.setTo("1@gmail.com");
//
//	        helper.setSubject("Testing from Spring Boot....");
//
//	        // default = text/plain
//	        //helper.setText("Check attachment for image!");
//
//	        // true = text/html
//	        helper.setText("<h1>Check attachment for image!</h1>", true);
//
//	        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));
//
//	        //Resource resource = new ClassPathResource("android.png");
//	        //InputStream input = resource.getInputStream();
//
//	        //ResourceUtils.getFile("classpath:android.png");
//
//	        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
//
//	        javaMailSender.send(msg);
//
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
